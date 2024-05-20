package Library;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Library {
  private static final String BooksFilename = "books.txt";
  private static final String LendingFilename = "lending_events.txt";

  public void addBook() throws IOException {
    try (PrintWriter file = new PrintWriter(new FileWriter(BooksFilename, true))) {
      Book book = new Book();
      Scanner scanner = new Scanner(System.in);
      System.out.print("Book title: ");
      book.title = scanner.nextLine();

      System.out.print("Author: ");
      book.author = scanner.nextLine();

      //Mark book as available
      book.available = 1;

      file.println(book.title + "|" + book.author + "|" + book.available);
    }
    System.out.println("Book added successfully.");
  }

  public void listBooks() throws IOException {
    //check if text file exists
    if (!new File(BooksFilename).exists()) {
      System.out.println("No books entered so far");
      return;
    }

    System.out.println("Books available in the library:");
    try (BufferedReader file = new BufferedReader(new FileReader(BooksFilename))) {
      String line;
      while ((line = file.readLine()) != null) {
        String[] parts = line.split("\\|");
        System.out.println("Title: " + parts[0]);
        System.out.println("Author: " + parts[1]);
        System.out.println("Available: "
            + (parts[2].equals("1") ? "True" : "False"));
        System.out.println("------------------------------");
      }
    }
  }

  public void lendBook() throws IOException {
    //check if text file exists
    if (!new File(BooksFilename).exists()) {
      System.out.println("No books entered so far");
      return;
    }

    String bookTitle, userName;
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the title of the book to lend: ");
    bookTitle = scanner.nextLine();

    //Get a string array of all lines in file
    String[] lines = new BufferedReader(new FileReader(BooksFilename))
        .lines().toArray(String[]::new);
    boolean bookFound = false;

    for (int i = 0; i < lines.length; i++) {
      //Split line into book details
      String[] parts = lines[i].split("\\|");
      if (parts[0].equals(bookTitle) && parts[2].equals("1")) {
        lines[i] = parts[0] + "|" + parts[1] + "|0";
        bookFound = true;

        System.out.print("Enter your name: ");
        userName = scanner.nextLine();

        //Write lending event into file
        try (PrintWriter lendingFile
                 = new PrintWriter(new FileWriter(LendingFilename, true))) {
          lendingFile.println(bookTitle + "|" + userName + "|"
              + LocalDateTime.now() + "|0");
        }

        System.out.println("Book '" + bookTitle + "' has been lent to "
            + userName + ".");
        break;
      }
    }

    if (!bookFound) {
      System.out.println("Book '" + bookTitle + "' not found or not available.");
    }

    //Write books back to file (overwrite file)
    try (PrintWriter file = new PrintWriter(new FileWriter(BooksFilename))) {
      for (String line : lines) {
        file.println(line);
      }
    }
  }

  public void returnBook() throws IOException {
    if (!new File(LendingFilename).exists()) {
      System.out.println("No lending events entered so far");
      return;
    }

    String bookTitle;
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the title of the book to return: ");
    bookTitle = scanner.nextLine();

    //Get a string array of all lines in file
    String[] booksLines = new BufferedReader(new FileReader(BooksFilename))
        .lines().toArray(String[]::new);
    boolean bookFound = false;

    for (int i = 0; i < booksLines.length; i++) {
      String[] parts = booksLines[i].split("\\|");
      if (parts[0].equals(bookTitle) && parts[2].equals("0")) {
        booksLines[i] = parts[0] + "|" + parts[1] + "|1";
        bookFound = true;

        //Get all lending events
        String[] lendingLines = new BufferedReader(new FileReader(LendingFilename))
            .lines().toArray(String[]::new);

        for (int j = 0; j < lendingLines.length; j++) {
          parts = lendingLines[j].split("\\|");
          if (parts[0].equals(bookTitle) && parts[3].equals("0")) {
            lendingLines[j] = parts[0] + "|" + parts[1] + "|" + parts[2] + "|1";
            System.out.println("Book '" + bookTitle + "' has been returned.");
            break;
          }
        }

        //Write lending events back to file
        try (PrintWriter lendingFile
                 = new PrintWriter(new FileWriter(LendingFilename))) {
          for (String line : lendingLines) {
            lendingFile.println(line);
          }
        }
        break;
      }
    }

    if (!bookFound) {
      System.out.println("Book '" + bookTitle + "' not found or already returned.");
    }

    //Write books back to file
    try (PrintWriter file = new PrintWriter(new FileWriter(BooksFilename))) {
      for (String line : booksLines) {
        file.println(line);
      }
    }
  }

  public void listLendingEvents() throws IOException {
    if (!new File(LendingFilename).exists()) {
      System.out.println("No lending events entered so far");
      return;
    }

    System.out.println("Lending events:");
    try (BufferedReader file = new BufferedReader(new FileReader(LendingFilename))) {
      String line;
      while ((line = file.readLine()) != null) {
        String[] parts = line.split("\\|");
        System.out.println("Book Title: " + parts[0]);
        System.out.println("User Name: " + parts[1]);
        System.out.println("Lending Date: " + parts[2]);
        System.out.println("Returned: " + (parts[3].equals("1") ? "True" : "False"));
        System.out.println("------------------------------");
      }
    }
  }

  public static void main(String[] args) throws IOException {
    Library lib = new Library();
    int choice;
    Scanner scanner = new Scanner(System.in);
    do {
      System.out.println("\n1. Add a book\n2. List all books\n" +
          "3. Lend a book\n4. Return a book\n5. List lending events\n0. Exit");
      System.out.print("Enter your choice: ");
      choice = scanner.nextInt();

      switch (choice) {
        case 1:
          lib.addBook();
          break;
        case 2:
          lib.listBooks();
          break;
        case 3:
          lib.lendBook();
          break;
        case 4:
          lib.returnBook();
          break;
        case 5:
          lib.listLendingEvents();
          break;
        case 0:
          System.out.println("Exiting.");
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
          break;
      }

    } while (choice != 0);
  }
}
