package WebServerCache;

public class LRUCache {
  static final int CACHE_SIZE = 3;
  Node head;
  Node tail;
  int size;

  static class Node {
    String url;
    String content;
    Node prev;
    Node next;
  }

  public LRUCache() {
    size = 0;
    head = null;
    tail = null;
  }

  //Get the content associated with a URL from the cache
  public String getContent(String url) {
    Node current = head;
    while (current != null) {
      if (current.url.equals(url)) {
        moveToHead(current);
        System.out.println("Got content from cache: " + current.content);
        return current.content;
      }
      current = current.next;
    }
    return "";
  }

  //Put a URL-content pair into the cache
  public void putContent(String url, String content) {
    if (size == CACHE_SIZE) {
      deleteNode(tail);
      size--;
    }
    Node newNode = createNode(url, content);
    insertAtHead(newNode);
    size++;
  }

  //Create a new node
  Node createNode(String url, String content) {
    Node newNode = new Node();
    newNode.url = url;
    newNode.content = content;
    newNode.prev = null;
    newNode.next = null;
    System.out.println("New node created: " + content);
    return newNode;
  }

  //Insert a new node at the head of the cache
  void insertAtHead(Node node) {
    node.next = head;
    node.prev = null;
    if (head != null)
      head.prev = node;
    head = node;
    if (tail == null)
      tail = node;
    System.out.println("Node inserted at head: " + node.content);
  }

  //Move a node to the head of the cache
  void moveToHead(Node node) {
    if (node == head)
      return;
    if (node.prev != null)
      node.prev.next = node.next;
    if (node.next != null)
      node.next.prev = node.prev;
    node.prev = null;
    node.next = head;
    if (head != null)
      head.prev = node;
    head = node;
    if (tail == null)
      tail = node;
    System.out.println("Node moved to head: " + node.content);
  }

  //Delete a node from the cache
  void deleteNode(Node node) {
    if (node == null)
      return;
    if (node == head)
      head = node.next;
    if (node == tail)
      tail = node.prev;
    if (node.prev != null)
      node.prev.next = node.next;
    if (node.next != null)
      node.next.prev = node.prev;
    System.out.println("Node deleted: " + node.content);
  }
}

