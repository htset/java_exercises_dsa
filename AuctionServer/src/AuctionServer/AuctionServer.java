package AuctionServer;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

public class AuctionServer {

  //Client class representing each client connected to the server
  static class Client {
    private final Socket socket;
    private final BufferedWriter writer;
    private final int id;

    //Constructor for the Client class
    public Client(Socket socket, int id) throws IOException {
      this.socket = socket;
      //Setting up writer to send data to the client
      this.writer
          = new BufferedWriter(
              new OutputStreamWriter(socket.getOutputStream(),
                  StandardCharsets.UTF_8));
      this.id = id;
    }

    public Socket getSocket() {
      return socket;
    }

    public BufferedWriter getWriter() {
      return writer;
    }

    public int getId() {
      return id;
    }
  }

  //Constants
  private static final int PORT = 8080; //Port number for the server
  private static final int MAX_CLIENTS = 5; //Maximum number of clients

  //Server and clients
  private static ServerSocket server;
  private static Client[] clients = new Client[MAX_CLIENTS]; //Array to hold clients

  //Auction details
  private static int bestBid = 0; //Highest bid received
  private static int winningClient = 0; //Client ID of the highest bidder

  //Timer for auction end
  private static Timer timer;

  public static void main(String[] args) {
    try {
      server = new ServerSocket(PORT); //Setting up the server socket
      System.out.println("Server listening on port " + PORT);

      //Start the timer
      timer = new Timer();
      timer.schedule(new TimerTask() {
        @Override
        public void run() {
          timerCompletionRoutine();
        }
      }, 20000, 20000); //Schedule the timer to trigger every 20 seconds

      while (true) {
        //Accept a new client connection
        Socket clientSocket = server.accept();
        System.out.println("Client connected: "
            + clientSocket.getRemoteSocketAddress());

        //Find an available slot for the client
        int clientId = -1;
        for (int i = 0; i < MAX_CLIENTS; i++) {
          if (clients[i] == null) {
            clientId = i;
            clients[i] = new Client(clientSocket, clientId + 1);
            System.out.println("Client no. " + (clientId + 1) + " connected.");
            break;
          }
        }

        //Handle client in a separate thread
        int finalClientId = clientId;
        new Thread(() -> handleClient(finalClientId)).start();
      }
    } catch (Exception ex) {
      System.out.println("Server error: " + ex.getMessage());
    } finally {
      try {
        if (server != null) server.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  //Handle client bids
  private static void handleClient(int clientId) {
    try {
      Client client = clients[clientId];
      Socket clientSocket = client.getSocket();
      BufferedReader reader
          = new BufferedReader(
              new InputStreamReader(clientSocket.getInputStream(),
                  StandardCharsets.UTF_8));

      String bid;
      while (true) {
        //Read a bid from the client
        bid = reader.readLine();
        if (bid == null) {
          //Client disconnected
          System.out.println("Client disconnected: "
              + clientSocket.getRemoteSocketAddress());
          clients[clientId] = null;
          break;
        }

        int bidAmount = Integer.parseInt(bid);
        System.out.println("Received bid " + bidAmount + " from client "
            + client.getId());

        synchronized (AuctionServer.class) {
          if (bidAmount > bestBid) {
            bestBid = bidAmount;
            winningClient = client.getId();

            String msg = "New best bid: " + bestBid
                + " (Client: " + winningClient + ")";
            broadcastMessage(msg);
            System.out.println("New best bid: " + bestBid
                + " (Client: " + winningClient + ")");

            //Reset the timer
            timer.cancel();
            timer = new Timer();
            timer.schedule(new TimerTask() {
              @Override
              public void run() {
                timerCompletionRoutine();
              }
            }, 20000, 20000);
          } else {
            String msg = "Received lower bid. Best bid remains at: "
                + bestBid;
            broadcastMessage(msg);
            System.out.println("Received lower bid. Best bid remains at: "
                + bestBid);
          }
        }
      }
    } catch (Exception ex) {
      System.out.println("Client error: " + ex.getMessage());
    }
  }

  //Broadcast message to all clients
  private static void broadcastMessage(String msg) throws IOException {
    for (int i = 0; i < MAX_CLIENTS; i++) {
      if (clients[i] != null) {
        clients[i].getWriter().write(msg);
        clients[i].getWriter().newLine();
        clients[i].getWriter().flush();
      }
    }
  }

  //Timer completion routine when auction ends
  private static void timerCompletionRoutine() {
    System.out.println("Auction finished. Winning bid: " + bestBid + ", winner: client no. " + winningClient);

    String msg = "Auction finished. Winning bid: " + bestBid + ", winner: client no. " + winningClient;
    try {
      broadcastMessage(msg);
    } catch (IOException e) {
      e.printStackTrace();
    }

    //Exit the program
    System.exit(0);
  }
}
