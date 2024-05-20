package SocialNetwork;

class FriendNode {
  public String name;
  public FriendNode next;

  public FriendNode(String name) {
    this.name = name;
    this.next = null;
  }
}

class User {
  public String name;
  public FriendNode friends;

  public User(String name) {
    this.name = name;
    this.friends = null;
  }
}

class MyQueue {
  private class QueueNode {
    public int userIndex;
    public QueueNode next;

    public QueueNode(int userIndex) {
      this.userIndex = userIndex;
      this.next = null;
    }
  }

  private QueueNode front;
  private QueueNode rear;

  public MyQueue() {

    front = rear = null;
  }

  public boolean isEmpty() {

    return front == null;
  }

  public void enqueue(int userIndex) {
    QueueNode newNode = new QueueNode(userIndex);
    if (isEmpty()) {
      front = rear = newNode;
    } else {
      rear.next = newNode;
      rear = newNode;
    }
  }

  public int dequeue() {
    if (isEmpty()) {
      System.out.println("Queue is empty!");
      return -1;
    }

    int userIndex = front.userIndex;
    front = front.next;

    if (front == null) {
      rear = null;
    }

    return userIndex;
  }
}

class Graph {
  private static final int MAX_USERS = 100;
  private User[] users;
  private int numUsers;

  public Graph() {
    users = new User[MAX_USERS];
    numUsers = 0;
  }

  public void addUser(String name) {
    if (numUsers >= MAX_USERS) {
      System.out.println("Max user limit reached!");
      return;
    }

    users[numUsers] = new User(name);
    numUsers++;
  }

  public void addConnection(int src, int dest) {
    if (src < 0 || src >= numUsers || dest < 0 || dest >= numUsers) {
      System.out.println("Invalid user index!");
      return;
    }

    //Add bidirectional connection
    FriendNode newFriendSrc = new FriendNode(users[dest].name);
    newFriendSrc.next = users[src].friends;
    users[src].friends = newFriendSrc;

    FriendNode newFriendDest = new FriendNode(users[src].name);
    newFriendDest.next = users[dest].friends;
    users[dest].friends = newFriendDest;
  }

  public void recommendFriends(int userIndex) {
    System.out.println("Recommended friends for " + users[userIndex].name + ":");

    //Store the indexes of the user’s friends
    MyQueue queue = new MyQueue();
    //Store the persons that we have already visited
    boolean[] visited = new boolean[MAX_USERS];

    visited[userIndex] = true;
    //Enqueue the starting user
    queue.enqueue(userIndex);

    while (!queue.isEmpty()) {
      int currentUserIndex = queue.dequeue();
      FriendNode current = users[currentUserIndex].friends;

      //Traverse the user's friends
      while (current != null) {
        int friendIndex = -1;
        //Find the friend's index
        for (int i = 0; i < numUsers; i++) {
          if (current.name.equals(users[i].name)) {
            friendIndex = i;
            break;
          }
        }

        //Check if the friend is already visited
        if (friendIndex != -1 && !visited[friendIndex]) {
          System.out.println("- " + current.name);
          //Add friend to visited array
          visited[friendIndex] = true;
          //Enqueue friend
          queue.enqueue(friendIndex);
        }

        //Move to the next friend
        current = current.next;
      }
    }
  }
}

public class SocialNetwork {
  public static void main(String[] args) {
    Graph graph = new Graph();
    graph.addUser("User A");
    graph.addUser("User B");
    graph.addUser("User C");
    graph.addUser("User D");
    graph.addUser("User E");
    graph.addUser("User F");
    graph.addUser("User G");
    graph.addUser("User H");

    graph.addConnection(0, 1);
    graph.addConnection(1, 2);
    graph.addConnection(2, 3);
    graph.addConnection(4, 5);
    graph.addConnection(5, 7);
    graph.addConnection(3, 6);

    graph.recommendFriends(0);
    graph.recommendFriends(1);
    graph.recommendFriends(7);
  }
}
