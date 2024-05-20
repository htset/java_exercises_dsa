package SyntaxChecker;

public class Stack {
  private char[] items;
  private int top;

  public Stack() {
    items = new char[100];
    top = -1;
  }

  public void push(char c) {
    if (top == items.length - 1) {
      System.out.println("Stack is full");
      System.exit(1);
    }
    items[++top] = c;
  }

  public char pop() {
    if (top == -1) {
      System.out.println("Stack is empty");
      System.exit(1);
    }
    return items[top--];
  }

  public boolean isEmpty() {
    return (top == -1);
  }
}