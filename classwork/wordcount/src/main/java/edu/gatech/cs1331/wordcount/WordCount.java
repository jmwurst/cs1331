package edu.gatech.cs1331.wordcount;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Scanner;

public class WordCount {

    public WordCount(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String curWord = scanner.next();
            System.out.println(curWord);
        }
    }

    public Collection<String> getWords() {
        return null;
    }

    public int getCount(String word) {
        return 0;
    }

    public static void main(String[] args) throws FileNotFoundException {
        WordCount wc = new WordCount(args[0]);
        for (String word : wc.getWords()) {
            System.out.println(word + ": " + wc.getCount(word));
        }
    }
}
