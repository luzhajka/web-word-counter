package controller;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.jsoup.internal.StringUtil.isBlank;

public class FileWorker {
    private static final Logger LOGGER = Logger.getLogger(FileWorker.class);
    LocalDate currentDate;
    LocalTime currentTime;
    String urlWebPage;
    File fileForParse;

    public FileWorker(String urlWebPage) {
        this.urlWebPage = urlWebPage;
        currentDate = LocalDate.now();
        currentTime = LocalTime.now();
    }


    public void createFile() throws IOException {
        Path dir = Paths.get("/testTask/" + currentDate);
        if (!Files.exists(dir)) {
            Files.createDirectory(dir);
        }
        fileForParse = new File(dir.toFile(), "/fileForParse-" + currentTime.format(ConsoleStarter.timeFormatter) + ".html");
    }

    public void writeFile() throws IOException {
        try (BufferedWriter fileWriter =
                     new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileForParse), UTF_8))) {
            fileWriter.write(Jsoup.connect(urlWebPage).get().outerHtml());
        }
    }

    public List<String> parseFile() throws IOException {
        Document doc;
        try {
            doc = Jsoup.parse(fileForParse, UTF_8.name());
        } catch (IOException e) {
            LOGGER.error("�� ������� �������� ������ � ����� " + fileForParse + " ������ ����� ���������� � web-��������");
            try {
                doc = Jsoup.connect(urlWebPage).get();
            } catch (Exception ex) {
                LOGGER.error("�� ������� �������� ������ web-�������� " + urlWebPage, ex);
                throw ex;
            }
        }
        List<String> list = new ArrayList<>();
        for (String word : doc.text().split("[^\\w\\pL]")) {
            if (!isBlank(word)) {
                list.add(word);
            }
        }
        return list;
    }

}
