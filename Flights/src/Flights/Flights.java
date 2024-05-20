package Flights;

import java.util.*;

public class Flights {

  //Structure to represent each city
  public static class City {
    public String name;
    public Map<String, Integer> flights;

    public City(String name) {
      this.name = name;
      this.flights = new HashMap<>();
    }
  }

  //Graph class to represent all cities and flights
  public static class FlightGraph {
    //Map of city names and their objects
    public Map<String, City> cities = new HashMap<>();

    //Add a city to the graph
    public void addCity(String name) {
      cities.put(name, new City(name));
    }

    //Add a flight between two cities and its cost
    public void addFlight(String src, String dest, int cost) {
      //Assuming flights are bidirectional
      cities.get(src).flights.put(dest, cost);
      cities.get(dest).flights.put(src, cost);
    }

    //Function to find the cheapest route between two cities
    //using Dijkstra's algorithm
    public List<String> findCheapestRoute(String src, String dest,
                                          Map<String, Integer> dist) {
      //Initialize the distance map and previous node map
      dist.clear();
      Map<String, String> prev = new HashMap<>();
      PriorityQueue<Map.Entry<String, Integer>> pq
          = new PriorityQueue<>(Map.Entry.comparingByValue());

      //Set all distances to infinity initially
      for (String city : cities.keySet()) {
        dist.put(city, Integer.MAX_VALUE);
        prev.put(city, null);
      }

      //Distance to the source is 0
      dist.put(src, 0);
      pq.offer(new AbstractMap.SimpleEntry<>(src, 0));

      //Main loop to process each node
      while (!pq.isEmpty()) {
        Map.Entry<String, Integer> entry = pq.poll();
        String u = entry.getKey();
        int uDist = entry.getValue();

        //Process each neighbor of the current node
        for (Map.Entry<String, Integer> flight : cities.get(u).flights.entrySet()) {
          String v = flight.getKey();
          int cost = flight.getValue();

          //If a shorter path to v is found
          if (dist.get(u) != Integer.MAX_VALUE && dist.get(u) + cost < dist.get(v)) {
            dist.put(v, dist.get(u) + cost);
            prev.put(v, u);
            pq.offer(new AbstractMap.SimpleEntry<>(v, dist.get(v)));
          }
        }
      }

      //Reconstructing the path from source to destination
      List<String> path = new ArrayList<>();
      for (String at = dest; at != null; at = prev.get(at)) {
        path.add(at);
      }
      Collections.reverse(path);

      return path;
    }

    //Display all possible flights between two cities using DFS
    public void displayAllFlights(String src, String dest) {
      if (!cities.containsKey(src) || !cities.containsKey(dest)) {
        System.out.println("Invalid cities entered.");
        return;
      }

      Set<String> visited = new HashSet<>();
      Stack<String> path = new Stack<>();
      path.push(src);
      dfs(src, dest, visited, path);
    }

    //Recursive DFS function to find all flights between source and destination
    private void dfs(String src, String dest, Set<String> visited,
                     Stack<String> path) {
      visited.add(src);

      if (src.equals(dest)) {
        printPath(path);
      } else {
        for (String flight : cities.get(src).flights.keySet()) {
          if (!visited.contains(flight)) {
            path.push(flight);
            dfs(flight, dest, visited, path);
            path.pop();
          }
        }
      }

      visited.remove(src);
    }

    //Helper function to print a path (stack content)
    private void printPath(Stack<String> path) {
      System.out.println(String.join(" -> ", path));
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    FlightGraph graph = new FlightGraph();

    graph.addCity("London");
    graph.addCity("Paris");
    graph.addCity("Berlin");
    graph.addCity("Rome");
    graph.addCity("Madrid");
    graph.addCity("Amsterdam");

    graph.addFlight("London", "Paris", 100);
    graph.addFlight("London", "Berlin", 150);
    graph.addFlight("London", "Madrid", 200);
    graph.addFlight("Paris", "Berlin", 120);
    graph.addFlight("Paris", "Rome", 180);
    graph.addFlight("Berlin", "Rome", 220);
    graph.addFlight("Madrid", "Rome", 250);
    graph.addFlight("Madrid", "Amsterdam", 170);
    graph.addFlight("Amsterdam", "Berlin", 130);

    System.out.print("Enter departure city: ");
    String departure = scanner.nextLine();
    System.out.print("Enter destination city: ");
    String destination = scanner.nextLine();

    //Display all possible flights
    System.out.println("All possible flights between " + departure
        + " and " + destination + ":");
    graph.displayAllFlights(departure, destination);

    //Find the cheapest route and total price
    Map<String, Integer> dist = new HashMap<>();
    List<String> route = graph.findCheapestRoute(departure, destination, dist);
    int totalPrice = dist.get(destination);

    //Display the cheapest route and total price
    System.out.print("Cheapest Route: ");
    System.out.println(String.join(" -> ", route));
    System.out.println("Total Price: " + totalPrice);
  }
}
