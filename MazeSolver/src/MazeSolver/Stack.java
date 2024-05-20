package MazeSolver;

public class Stack<T> {
  private T[] items;
  private int top;

  public Stack(int capacity) {
    items = (T[]) new Object[capacity];
    top = -1;
  }

  public boolean isEmpty() {
    return top == -1;
  }

  public void push(T t) {
    top++;
    items[top] = t;
  }

  public T pop() {
    return items[top--];
  }
}
