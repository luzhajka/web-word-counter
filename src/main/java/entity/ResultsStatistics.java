package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static java.lang.System.lineSeparator;


public class ResultsStatistics {
    String date;
    String time;
    String webPage;
    HashMap<String, Integer> wordStatistic;


    public ResultsStatistics(String webPage, HashMap<String, Integer> wordStatistic) {
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yy"));
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss"));
        this.webPage = webPage;
        this.wordStatistic = wordStatistic;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getWebPage() {
        return webPage;
    }

    public HashMap<String, Integer> getWordStatistic() {
        return wordStatistic;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ResultsStatistics:")
                .append(lineSeparator())
                .append("date = ")
                .append(date)
                .append(lineSeparator())
                .append("time = ")
                .append(time)
                .append(lineSeparator())
                .append("webPage = ")
                .append(webPage)
                .append(lineSeparator())
                .append("wordStatistics: ");
        if (wordStatistic == null){
            return sb.toString();
        }
        wordStatistic.forEach((key, val) -> sb.append(lineSeparator())
                .append(key)
                .append(" - ")
                .append(val));
        return sb.toString();
    }
}
