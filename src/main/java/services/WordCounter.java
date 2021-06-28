package services;

import java.util.HashMap;
import java.util.List;

public class WordCounter {
    private WordCounter() {
    }

    public static HashMap<String, Integer> countWords(List<String> strings) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String string : strings) {
            if (map.putIfAbsent(string, 1) != null) {
                map.put(string, (map.get(string) + 1));
            }
        }
        return map;
    }
}
