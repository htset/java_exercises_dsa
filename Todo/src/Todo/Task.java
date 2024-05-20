package Todo;

public class Task {
  public String description;
  public int priority;
  public Task next;

  public Task() {
    this.description = null;
    this.priority = 0;
    this.next = null;
  }
}