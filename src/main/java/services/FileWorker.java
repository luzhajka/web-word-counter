package services;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.jsoup.internal.StringUtil.isBlank;

public class FileWorker {
    private static final Logger LOGGER = Logger.getLogger(FileWorker.class);
    LocalDateTime currentDateTime;
    String urlWebPage;
    File fileForParse;

    public FileWorker(String urlWebPage) {
        this.urlWebPage = urlWebPage;
        currentDateTime = LocalDateTime.now();
    }

    public void createFile() {
        fileForParse = new File("fileForParse-" + currentDateTime.format(ofPattern("dd-MM-yy_HH-mm-ss")) + ".html");
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
            LOGGER.error("не удалось получить доступ к файлу " + fileForParse + " чтение будет продолжено с web-страницы");
            try {
                doc = Jsoup.connect(urlWebPage).get();
            } catch (Exception ex) {
                LOGGER.error("не удалось получить данные web-страницы " + urlWebPage, ex);
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
