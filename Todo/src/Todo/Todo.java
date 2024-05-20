package Todo;
import java.util.Scanner;

public class Todo {
  public static void main(String[] args) {
    TodoList list = new TodoList();
    Scanner scanner = new Scanner(System.in);

    int choice = 0;
    String description;
    int priority;
    int index;

    do {
      try {
        System.out.println("\nTo-Do List Manager");
        System.out.println("1. Add Task");
        System.out.println("2. Remove Task");
        System.out.println("3. Display Tasks");
        System.out.println("4. Sort Tasks by Priority");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
          case 1:
            System.out.print("Enter task description: ");
            description = scanner.nextLine();
            System.out.print("Enter priority: ");
            priority = Integer.parseInt(scanner.nextLine());
            list.addTask(description, priority);
            System.out.println("Task added successfully.");
            break;
          case 2:
            System.out.print("Enter number of task to remove: ");
            index = Integer.parseInt(scanner.nextLine());
            list.removeTask(index - 1);
            System.out.println("Task removed successfully.");
            break;
          case 3:
            System.out.println("List of tasks:");
            list.displayTasks();
            break;
          case 4:
            list.sortTasks();
            System.out.println("Tasks sorted by priority.");
            break;
          case 0:
            System.out.println("Exiting...");
            break;
          default:
            System.out.println("Invalid choice. Please try again.");
            break;
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
      }
    } while (choice != 0);
    scanner.close();
  }
}