package MazeSolver;

public class Maze {
  private Stack<Point> stack = new Stack<>(ROWS * COLS);

  private static final int ROWS = 15;
  private static final int COLS = 15;
  private int[][] matrix = {
      {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
      {0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
      {0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0},
      {0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0},
      {0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0},
      {0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0},
      {0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0},
      {0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0},
      {0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0},
      {0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0},
      {0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0},
      {0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0},
      {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}
  };

  public Maze() { }

  //Check if we can move to this cell
  private boolean canMove(int row, int col) {
    return (row >= 0
        && row < ROWS
        && col >= 0
        && col < COLS
        && matrix[row][col] == 0);
  }

  public void print() {
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println();
    }
  }

  //Solve the maze using backtracking
  public int solve(int row, int col) {
    if (row == ROWS - 1 && col == COLS - 1) {
      //destination reached
      stack.push(new Point(row, col));
      return 1;
    }

    if (canMove(row, col)) {
      stack.push(new Point(row, col));
      matrix[row][col] = 2; //Mark visited

      //Move right
      if (solve(row, col + 1) == 1)
        return 1;

      //Move down
      if (solve(row + 1, col) == 1)
        return 1;

      //Move left
      if (solve(row, col - 1) == 1)
        return 1;

      //Move up
      if (solve(row - 1, col) == 1)
        return 1;

      //If none of the above movements work, backtrack
      stack.pop();
      return 0;
    }

    return 0;
  }

  public void printPath() {
    while (!stack.isEmpty()) {
      Point p = stack.pop();
      System.out.print("(" + p.row + ", " + p.col + "), ");
    }
  }
}
