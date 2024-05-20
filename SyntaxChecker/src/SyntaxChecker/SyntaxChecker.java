package SyntaxChecker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;

public class SyntaxChecker {
  static int checkBalanced(String filename) {
    try (BufferedReader file = new BufferedReader(new FileReader(filename))) {
      int c;
      Stack stack = new Stack();

      while ((c = file.read()) != -1) {
        char ch = (char) c; //read() returns an int
        if (ch == '(' || ch == '[' || ch == '{') {
          stack.push(ch);
        } else if (ch == ')' || ch == ']' || ch == '}') {
          if (stack.isEmpty()) {
            return 0;
          }

          char openingChar = stack.pop();

          if ((ch == ')' && openingChar != '(') ||
              (ch == ']' && openingChar != '[') ||
              (ch == '}' && openingChar != '{')) {
            return 0;
          }
        }
      }

      return stack.isEmpty() ? 1 : 0;
    } catch (IOException e) {
      e.printStackTrace();
      return 0;
    }
  }

  public static void main(String[] args) {
    try (BufferedReader reader
             = new BufferedReader(new InputStreamReader(System.in))) {
      System.out.print("Path to the source file: ");
      String filename = reader.readLine();

      if (checkBalanced(filename) == 1) {
        System.out.println("The input file is balanced.");
      } else {
        System.out.println("The input file is not balanced.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
