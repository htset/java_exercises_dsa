package WordFrequencies;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordFrequencies {

  static String cleanWord(String word) {
    return new String(word
        .chars()
        .filter(Character::isLetter)
        .map(Character::toLowerCase)
        .toArray(), 0, (int) word.chars().filter(Character::isLetter).count());
  }

  public static void main(String[] args) {
    Map<String, Integer> wordFrequency = new HashMap<>();

    //Read text from file
    try (BufferedReader inputFile
             = new BufferedReader(new FileReader("input.txt"))) {
      String line;
      while ((line = inputFile.readLine()) != null) {
        for (String word : line.split(" ")) {
          String cleanedWord = cleanWord(word);
          if (!cleanedWord.isEmpty()) {
            wordFrequency.put(cleanedWord,
                wordFrequency.getOrDefault(cleanedWord, 0) + 1);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    //Display word frequencies
    System.out.println("Word Frequencies:");
    wordFrequency.forEach((key, value)
        -> System.out.println(key + ": " + value));
  }
}
