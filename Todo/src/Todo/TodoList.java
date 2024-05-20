package Todo;

public class TodoList {
  private Task head;
  private int size;

  public TodoList() {
    head = null;
    size = 0;
  }

  public void addTask(String description, int priority) {
    Task task = new Task();
    task.description = description;
    task.priority = priority;
    task.next = null;

    if (head == null) {
      //List is empty
      head = task;
    } else {
      Task temp = head;
      //Find the last node
      while (temp.next != null) {
        temp = temp.next;
      }
      //Insert the new task after the last node
      temp.next = task;
    }
    size++;
  }

  public void removeTask(int index) {
    if (head == null) {
      System.out.println("List is empty.");
      return;
    }

    if (index == 0) {
      //If we remove the first item in the list
      Task temp = head;
      head = head.next;
      temp = null;
      size--;
      return;
    }

    Task previous = null;
    Task current = head;
    int i = 0;
    //Go to the selected index
    while (current != null && i < index) {
      previous = current;
      current = current.next;
      i++;
    }

    if (current == null) {
      System.out.println("Index out of bounds.");
      return;
    }

    previous.next = current.next;
    current = null;
    size--;
  }

  public void displayTasks() {
    Task temp = head;
    int i = 1;
    while (temp != null) {
      System.out.println(i++ + ") Description: " + temp.description
          + ", Priority: " + temp.priority);
      temp = temp.next;
    }
  }

  public void sortTasks() {
    int swapped;
    Task ptr1;
    Task ptr2 = null;

    if (head == null)
      return;

    do {
      swapped = 0; //will change if swapping happens
      ptr1 = head;

      while (ptr1.next != ptr2) {
        if (ptr1.priority > ptr1.next.priority) {
          //Swap data of adjacent nodes
          int tempPriority = ptr1.priority;
          ptr1.priority = ptr1.next.priority;
          ptr1.next.priority = tempPriority;

          String tempDescription = ptr1.description;
          ptr1.description = ptr1.next.description;
          ptr1.next.description = tempDescription;

          swapped = 1; //swap happened in this loop pass; don't stop yet
        }
        ptr1 = ptr1.next;
      }
      ptr2 = ptr1;
    } while (swapped != 0); //quit loop when no swap happened
  }
}
