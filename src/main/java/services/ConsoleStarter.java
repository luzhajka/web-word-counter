package services;
import entity.ResultsStatistics;
import org.apache.log4j.Logger;
import repository.MongoRepository;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static java.lang.System.lineSeparator;

public class ConsoleStarter {
    private static final Logger LOGGER = Logger.getLogger(ConsoleStarter.class);
    static String urlWebPage;
    static ResultsStatistics resultsStatistics;

    public static void main(String[] args) {
        LOGGER.info("запущенна программа web word counter");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Введите web-адрес страницы сайта, которую хотите проанализировать");
            urlWebPage = reader.readLine();
            LOGGER.info("анализируем страницу " + urlWebPage);
        } catch (Exception e) {
            LOGGER.error("что-то пошло не так", e);
            return;
        }

        FileWorker fileWorker = new FileWorker(urlWebPage);
        try {
            LOGGER.debug("создаем файл, в который будем сохранять содержимое страницы");
            fileWorker.createFile();
            LOGGER.debug("записываем сожержимое веб-страницы в файл на локальном компьютере");
            fileWorker.writeFile();
        } catch (Exception e) {
            LOGGER.error("не удалось записать файл.", e);
        }

        LOGGER.debug("получаем список слов web-страницы");
        List<String> listWord;
        try {
            listWord = fileWorker.parseFile();
        } catch (Exception e) {
            LOGGER.error("что-то пошло не так.", e);
            return;
        }

        LOGGER.debug("производим подсчет слов в файле");
        Map<String, Integer> mapWords = WordCounter.countWords(listWord);

        LOGGER.debug("выводим статистику в консоль");
        printToConsole(mapWords);

        LOGGER.debug("сохраняем статистику в базу данных");
        try {
            MongoRepository mongoRepository = new MongoRepository();
            resultsStatistics = mongoRepository.saveToDataBase(mapWords, urlWebPage);
        } catch (Exception e) {
            LOGGER.error("не удалось записать данные в БД", e);
        }
    }

    private static void printToConsole(Map<String, Integer> mapWords) {
        StringBuilder sbMap = new StringBuilder();
        for (Entry<String, Integer> entry : mapWords.entrySet()) {
            sbMap.append(entry.getKey())
                    .append(" - ")
                    .append(entry.getValue())
                    .append(lineSeparator());
        }
        System.out.println(sbMap);
    }
}
