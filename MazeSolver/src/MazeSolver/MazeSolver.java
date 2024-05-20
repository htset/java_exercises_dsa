package MazeSolver;

public class MazeSolver {
  public static void main(String[] args) {
    Maze maze = new Maze();

    System.out.println("This is the maze:");
    maze.print();

    if (maze.solve(0, 0) == 1) {
      System.out.println("\n\nThis is the path found:");
      maze.printPath();

      System.out.println("\n\nThis is the maze with all the points crossed:");
      maze.print();
    } else {
      System.out.println("No path found");
    }
  }
}
