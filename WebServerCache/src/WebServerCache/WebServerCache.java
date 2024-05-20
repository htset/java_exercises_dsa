package WebServerCache;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

class HttpServer {
  static final int PORT = 8080;
  static final int MAX_REQUEST_SIZE = 1024;
  LRUCache cache;

  public HttpServer() {
    cache = new LRUCache();
  }

  public void start() throws IOException {
    //Open server socket
    ServerSocket server = new ServerSocket(PORT);
    System.out.println("Server started on port " + PORT);

    while (true) {
      //Wait for connections
      Socket client = server.accept();
      handleRequest(client);
      client.close();
    }
  }

  void handleRequest(Socket client) throws IOException {
    //Read request
    InputStream inputStream = client.getInputStream();
    byte[] buffer = new byte[MAX_REQUEST_SIZE];
    int bytesRead = inputStream.read(buffer);
    String request = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
    System.out.println("Received request: " + request);

    //Find request type (GET)
    String[] parts = request.split(" ");
    if (parts.length < 2 || !parts[0].equals("GET")) {
      System.out.println("Invalid request format.");
      return;
    }

    //Get request url and check whether it is already stored in the cache
    String url = parts[1];
    String content = cache.getContent(url);

    if (content.isEmpty()) {
      //If not found in cache, read it from the HTML file
      try {
        content = new String(Files.readAllBytes(new File(url.substring(1)).toPath()),
            StandardCharsets.UTF_8);
        System.out.println("Got content from file: " + content);
        cache.putContent(url, content);
      } catch (FileNotFoundException e) {
        System.out.println("File not found: " + url.substring(1));
        content = "HTTP/1.1 404 Not Found\r\n\r\n";
      } catch (IOException e) {
        System.out.println("File not found: " + url.substring(1));
        content = "HTTP/1.1 404 Not Found\r\n\r\n";
      }

    } else {
      System.out.println("Serving content from cache.");
    }

    //Check if the content is HTML or plain text
    String contentType = "text/plain"; //Default content type is plain text
    if (url.endsWith(".html") || url.endsWith(".htm")) {
      //If the URL ends with .html or .htm, it's HTML content
      contentType = "text/html";
    }

    //Build the response
    String response = "HTTP/1.1 200 OK\r\nContent-Type: "
        + contentType + "\r\n\r\n" + content;

    byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
    OutputStream outputStream = client.getOutputStream();
    outputStream.write(responseBytes);
  }

  public static void main(String[] args) {
    HttpServer server = new HttpServer();
    try {
      server.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
