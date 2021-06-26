package controller;

import entity.ResultsStatistics;
import org.apache.log4j.Logger;
import repository.MongoRepository;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

/* https://www.apple.com/  */

public class ConsoleStarter {
    private static final Logger LOGGER = Logger.getLogger(ConsoleStarter.class);

    static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH-mm-ss");
    static String urlWebPage;
    static ResultsStatistics resultsStatistics;

    public static void main(String[] args) {

        //запрашиваем у пользователя адрес веб-страницы для анализа
        LOGGER.info("запущенна программа web word counter");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Введите web-адрес страницы сайта, которую хотите проанализировать");
            urlWebPage = reader.readLine();
            LOGGER.info("анализируем страницу " + urlWebPage);
        } catch (IOException e) {
            LOGGER.error("что-то пошло не так", e);
            return;
        }

        FileWorker fileWorker = new FileWorker(urlWebPage);
        try {
            LOGGER.debug("создаем файл, в который будем сохранять содержимое страницы");
            fileWorker.createFile();
            LOGGER.debug("записываем сожержимое веб-страницы в файл на локальном компьютере");
            fileWorker.writeFile();
        } catch (IOException e) {
            LOGGER.error("не удалось записать файл.", e);
        }

        LOGGER.debug("получаем список слов web-страницы");
        List<String> listWord;
        try {
            listWord = fileWorker.parseFile();
        } catch (IOException e) {
            LOGGER.error("что-то пошло не так.", e);
            return;
        }

        LOGGER.debug("производим подсчет слов в файле");
        HashMap<String, Integer> mapWords = WordCounter.countWords(listWord);


        LOGGER.debug("сохраняем статистику в базу данных");
        try {
            MongoRepository mongoRepository = new MongoRepository();
            resultsStatistics = mongoRepository.saveToDataBase(mapWords, urlWebPage);
        } catch (Exception e){
            LOGGER.error("не удалось записать данные в БД", e);
        }

        LOGGER.debug("выводим результат подсчета в консоль");
        printToConsole(resultsStatistics);
    }

    private static void printToConsole(ResultsStatistics resultsStatistics) {
            System.out.println(resultsStatistics);
    }
}
