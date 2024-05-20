package FileIndexer;

import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

class FileIndexer {
  private class Node {
    public String fileName;
    public String filePath;
    public Node left;
    public Node right;

    public Node(String name, String path) {
      fileName = name;
      filePath = path;
      left = null;
      right = null;
    }
  }

  private Node root;

  //Insert node to tree
  private void insertNode(String fileName, String filePath) {
    //If the tree is empty, insert node here
    if (root == null) {
      root = new Node(fileName, filePath);
      return;
    }

    //If not empty, then go down the tree
    Node current = root;
    while (true) {
      if (fileName.compareTo(current.fileName) < 0) {
        if (current.left == null) {
          current.left = new Node(fileName, filePath);
          return;
        }
        current = current.left;
      } else {
        if (current.right == null) {
          current.right = new Node(fileName, filePath);
          return;
        }
        current = current.right;
      }
    }
  }

  //Index the specified directory
  private void indexDirectoryHelper(String dirPath) {
    //If it's not a directory, return
    if (!Files.isDirectory(Paths.get(dirPath)))
      return;

    //Loop over files within directory
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dirPath))) {
      for (Path entry : stream) {
        if (Files.isRegularFile(entry)) {
          String fileName = entry.getFileName().toString();
          insertNode(fileName, entry.toString());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    //Loop over directories within directory
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dirPath))) {
      for (Path entry : stream) {
        if (Files.isDirectory(entry)) {
          //Recursive indexing
          indexDirectoryHelper(entry.toString());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //Deallocate memory recursively
  private void deleteSubtree(Node root) {
    if (root != null) {
      deleteSubtree(root.left);
      deleteSubtree(root.right);
      root = null;
    }
  }

  private void traverse(Node root) {
    if (root != null) {
      traverse(root.left);
      System.out.println(root.fileName + ": " + root.filePath);
      traverse(root.right);
    }
  }

  public void indexDirectory(String directoryPath) {
    root = null;
    indexDirectoryHelper(directoryPath);
  }

  public void printFiles() {
    System.out.println("Indexed files:");
    traverse(root);
  }

  //Search for a file in the BST
  public String searchFileLocation(String filename) {
    //Traverse the tree until a match is found or the tree is exhausted
    Node current = root;
    while (current != null) {
      if (filename.equals(current.fileName)) {
        return current.filePath; //File found
      } else if (filename.compareTo(current.fileName) < 0) {
        current = current.left; //Search in the left subtree
      } else {
        current = current.right; //Search in the right subtree
      }
    }
    return ""; //File not found
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Path to index recursively: ");
    String path = scanner.nextLine();

    FileIndexer indexer = new FileIndexer();
    indexer.indexDirectory(path);
    indexer.printFiles();

    System.out.print("Let's search for a file's location. Give the file name: ");
    String filenameToSearch = scanner.nextLine();

    String location = indexer.searchFileLocation(filenameToSearch);
    if (!location.isEmpty()) {
      System.out.println("File " + filenameToSearch
          + " found. Location: " + location);
    } else {
      System.out.println("File " + filenameToSearch + " not found.");
    }

    scanner.close();
  }
}
