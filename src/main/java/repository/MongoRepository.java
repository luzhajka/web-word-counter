package repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entity.ResultsStatistics;
import org.bson.Document;
import java.util.Map;

public class MongoRepository {
    private static final String CONNECTION_STRING = "mongodb+srv://SimpleUser:098098@cluster0.bf4he.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
    private static final String DATABASE_NAME = "wordCounterDb";
    private static final String COLLECTION_NAME = "parsingStatistics";

    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> statisticsCollection;

    public MongoRepository() {
        mongoClient = MongoClients.create(CONNECTION_STRING);
        database = mongoClient.getDatabase(DATABASE_NAME);
        statisticsCollection = database.getCollection(COLLECTION_NAME);
    }

    public ResultsStatistics saveToDataBase(Map<String, Integer> mapWords, String urlWebPage) {
        ResultsStatistics resultsStatistics = new ResultsStatistics(urlWebPage, mapWords);
        saveResult(resultsStatistics);
        return resultsStatistics;
    }

    public void saveResult(ResultsStatistics resultsStatistics) {
        Document doc = new Document();
        doc.append("name", resultsStatistics.getWebPage())
                .append("date", resultsStatistics.getDate())
                .append("time", resultsStatistics.getTime())
                .append("statistic", resultsStatistics.getWordStatistic());
        statisticsCollection.insertOne(doc);
    }
}
