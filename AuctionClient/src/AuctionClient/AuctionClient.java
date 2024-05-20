package AuctionClient;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class AuctionClient {

  private static final int PORT = 8080; //Port number to connect to the server
  private static final String SERVER_IP = "127.0.0.1"; //Server IP address

  private static Socket client;
  private static boolean isRunning = true;
  private static Thread receiveThread;

  public static void main(String[] args) {
    //Connect to the server
    try {
      client = new Socket(SERVER_IP, PORT);
      System.out.println("Connected to server.");
    } catch (Exception ex) {
      System.out.println("Connect failed: " + ex.getMessage());
      return;
    }

    //Start receive handler thread
    receiveThread = new Thread(AuctionClient::receiveHandler);
    receiveThread.start();

    //Send bids until the user quits
    try (BufferedReader consoleReader
             = new BufferedReader(new InputStreamReader(System.in));
         BufferedWriter writer
             = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),
             StandardCharsets.UTF_8))) {

      String bid;
      while (isRunning) {
        System.out.print("\nEnter your bid (or 'q' to quit): ");
        bid = consoleReader.readLine();

        if ("q".equalsIgnoreCase(bid)) {
          isRunning = false;
          client.close();
          receiveThread.interrupt(); //Interrupt the receive thread
          break;
        }

        try {
          writer.write(bid);
          writer.newLine();
          writer.flush();
        } catch (Exception ex) {
          System.out.println("Send failed: " + ex.getMessage());
          return;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    //Ensure client is closed
    try {
      client.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //Handle incoming messages from the server
  private static void receiveHandler() {
    try (BufferedReader reader
             = new BufferedReader(
                 new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8))) {
      while (isRunning) {
        try {
          if (Thread.interrupted()) {
            break; //Exit if the thread is interrupted
          }
          String message = reader.readLine();
          if (message == null || message.isEmpty()) {
            System.out.println("\nServer disconnected.");
            break;
          }

          System.out.println("\nServer: " + message);

          if (message.startsWith("Auction")) {
            System.out.println("Auction ended. Exiting program.");
            isRunning = false;
            break;
          }
        } catch (IOException ex) {
          if (isRunning) {
            //Check if still running to avoid printing
            // error when stopping intentionally
            System.out.println("Receive failed: " + ex.getMessage());
          }
          break;
        }
      }
    } catch (IOException e) {
      if (isRunning) {
        //Check if still running to avoid printing
        // error when stopping intentionally
        e.printStackTrace();
      }
    }
  }
}
