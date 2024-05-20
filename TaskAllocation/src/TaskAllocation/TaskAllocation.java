package TaskAllocation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TaskAllocation {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    List<Task> tasks = new ArrayList<>();
    PriorityQueue<Worker> workerQueue
        = new PriorityQueue<>(Comparator.comparingInt(w -> w.workload));

    System.out.print("Enter the number of workers: ");
    int numWorkers = scanner.nextInt();

    //Initialize workers with ID and 0 workload
    for (int i = 0; i < numWorkers; i++) {
      workerQueue.add(new Worker(i, 0));
    }

    int choice;
    do {
      System.out.println("\nMenu:");
      System.out.println("1. Add Task");
      System.out.println("2. Display Tasks");
      System.out.println("3. Print Workers Queue");
      System.out.println("4. Exit");
      System.out.print("Enter your choice: ");
      choice = scanner.nextInt();

      switch (choice) {
        case 1:
          addTask(workerQueue, tasks, scanner);
          break;
        case 2:
          displayTasks(tasks);
          break;
        case 3:
          printWorkersQueue(workerQueue);
          break;
        case 4:
          System.out.println("Exiting program...");
          break;
        default:
          System.out.println("Invalid choice! Please try again.");
          break;
      }
    } while (choice != 4);

    scanner.close();
  }

  //Method to add a task and allocate it to a worker
  static void addTask(PriorityQueue<Worker> workerQueue,
                      List<Task> tasks, Scanner scanner) {
    System.out.print("Enter task description: ");
    String description = scanner.next();
    System.out.print("Enter task duration (in minutes): ");
    int duration = scanner.nextInt();

    if (workerQueue.size() == 0) {
      System.out.println("No workers available! Task cannot be assigned.");
      return;
    }

    //Dequeue the worker with the shortest workload
    Worker worker = workerQueue.poll();

    //Assign the task to the worker and update workload
    tasks.add(new Task(description, duration));
    System.out.println("Task added successfully and allocated to Worker "
        + worker.id + "!");

    //Update workload
    worker.workload += duration;

    //Store updated worker back to queue
    workerQueue.add(worker);
  }

  //Method to display all tasks
  static void displayTasks(List<Task> tasks) {
    System.out.println("Task List:");
    for (Task task : tasks) {
      System.out.println("Task description: " + task.description
          + ", Duration: " + task.duration + " minutes");
    }
  }

  //Method to print the workers queue
  static void printWorkersQueue(PriorityQueue<Worker> workerQueue) {
    System.out.println("Workers Queue:");
    for (Worker item : workerQueue) {
      System.out.println("Worker ID: " + item.id + ", Workload: "
          + item.workload + " minutes");
    }
  }
}