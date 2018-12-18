package edu.gatech.cs1331.wordcount;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordCount {

    private class RankComparator implements Comparator<String> {
        public int compare(String w1,  String w2) {
            return 0;
        }
    }

    private Map<String, Double> wordCounts;

    public WordCount(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        wordCounts = new HashMap<>();
        double total = 0;
        while (scanner.hasNext()) {
            String curWord = scanner.next();
            if (wordCounts.containsKey(curWord)) {
                double count = wordCounts.get(curWord);
                wordCounts.put(curWord, count + 1.0);
            } else {
                wordCounts.put(curWord, 1.0);
            }
            total++;
        }
        for (String word : wordCounts.keySet()) {
            wordCounts.put(word, wordCounts.get(word) / total);
        }
    }

    public Collection<String> getWords() {
        TreeSet<String> sortedWords = new TreeSet<>(new RankComparator());
        sortedWords.addAll(wordCounts.keySet());
        return wordCounts.keySet();
    }

    public double getCount(String word) {
        return wordCounts.get(word);
    }

    public static void main(String[] args) throws FileNotFoundException {
        WordCount wc = new WordCount(args[0]);
        for (String word : wc.getWords()) {
            System.out.println(word + ": " + wc.getCount(word));
        }
    }
}
