package Inventory;

import java.util.Random;

class Product {
  public int id;
  public String name;
  public float price;
  public int quantity;
}

class InventoryNode {
  public Product product;
  public InventoryNode left;
  public InventoryNode right;
  public int height;
}

class Inventory {
  private InventoryNode root;

  private int getHeight(InventoryNode node) {

    return node == null ? 0 : node.height;
  }

  private int getBalance(InventoryNode node) {

    return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
  }

  //Create a new node with the given product
  private InventoryNode newNode(Product product) {
    InventoryNode node = new InventoryNode();
    node.product = product;
    node.left = null;
    node.right = null;
    node.height = 1;
    return node;
  }

  //Right rotate subtree
  private InventoryNode rotateRight(InventoryNode y) {
    InventoryNode x = y.left;
    InventoryNode T2 = x.right;

    //Perform rotation
    x.right = y;
    y.left = T2;

    //Update heights
    y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
    x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

    //Return new root
    return x;
  }

  //Left rotate subtree
  private InventoryNode rotateLeft(InventoryNode x) {
    InventoryNode y = x.right;
    InventoryNode T2 = y.left;

    //Perform rotation
    y.left = x;
    x.right = T2;

    //Update heights
    x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
    y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

    //Return new root
    return y;
  }

  //Insert a product in the AVL tree
  private InventoryNode insertProduct(InventoryNode node, Product product) {
    if (node == null)
      return newNode(product);

    //Insert the product
    if (product.id < node.product.id)
      node.left = insertProduct(node.left, product);
    else if (product.id > node.product.id)
      node.right = insertProduct(node.right, product);
    else
      return node; //Duplicate IDs not allowed

    //Update height of this node
    node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

    //Get balance factor
    int balance = getBalance(node);

    //Left Left Case
    if (balance > 1 && product.id < node.left.product.id)
      return rotateRight(node);

    //Right Right Case
    if (balance < -1 && product.id > node.right.product.id)
      return rotateLeft(node);

    //Left Right Case
    if (balance > 1 && product.id > node.left.product.id) {
      node.left = rotateLeft(node.left);
      return rotateRight(node);
    }

    //Right Left Case
    if (balance < -1 && product.id < node.right.product.id) {
      node.right = rotateRight(node.right);
      return rotateLeft(node);
    }

    return node;
  }


  private void traverseTree(InventoryNode node) {
    if (node != null) {
      traverseTree(node.left);
      System.out.println("ID: " + node.product.id + ", " +
          "Name: " + node.product.name + ", " +
          "Price: " + node.product.price + ", " +
          "Quantity: " + node.product.quantity);
      traverseTree(node.right);
    }
  }

  private InventoryNode searchProduct(InventoryNode node, int id) {
    if (node == null || node.product.id == id) {
      if (node == null)
        System.out.println("Product not found.");
      else
        System.out.println("Found product: ID: " + node.product.id + ", " +
            "Name: " + node.product.name + ", " +
            "Price: " + node.product.price + ", " +
            "Quantity: " + node.product.quantity);
      return node;
    }

    System.out.println("Visited product ID: " + node.product.id);

    if (id < node.product.id)
      return searchProduct(node.left, id);
    else
      return searchProduct(node.right, id);
  }

  public void insertProduct(Product product) {

    root = insertProduct(root, product);
  }

  public void traverseTree() {

    traverseTree(root);
  }

  public InventoryNode searchProduct(int id) {

    return searchProduct(root, id);
  }

  public static void main(String[] args) {
    Inventory inv = new Inventory();
    Product[] products = new Product[100];

    Random random = new Random();
    //Initialize products with random values
    for (int i = 0; i < 100; i++) {
      products[i] = new Product();
      products[i].id = i + 1;
      products[i].name = "Product " + (i + 1);
      products[i].price = random.nextFloat() * 100.0f;
      products[i].quantity = random.nextInt(100) + 1;
    }

    //Shuffle products array
    for (int i = 99; i >= 0; i--) {
      int j = random.nextInt(i + 1);
      Product temp = products[i];
      products[i] = products[j];
      products[j] = temp;
    }

    //Insert products into the inventory
    for (Product product : products) {
      inv.insertProduct(product);
    }

    //Display all products in the inventory
    System.out.println("Inventory:");
    inv.traverseTree();

    //Search for a specific product
    int productIdToSearch = 35;
    InventoryNode foundProduct = inv.searchProduct(productIdToSearch);
    if (foundProduct != null) {
      System.out.println("Product found: ID: " + foundProduct.product.id + ", " +
          "Name: " + foundProduct.product.name + ", " +
          "Price: " + foundProduct.product.price + ", " +
          "Quantity: " + foundProduct.product.quantity);
    } else {
      System.out.println("Product with ID " + productIdToSearch + " not found.");
    }
  }
}