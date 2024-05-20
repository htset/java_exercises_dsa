package ContactList;

class Contact {
  public String name;
  public String phone;
  public Contact next;

  public Contact() {
    this.name = null;
    this.phone = null;
    this.next = null;
  }
}

public class ContactList {
  private static final int HASH_SIZE = 100;
  private Contact[] bucketTable;

  public ContactList() {
    bucketTable = new Contact[HASH_SIZE];
    for (int i = 0; i < HASH_SIZE; i++) {
      bucketTable[i] = null;
    }
  }

  private int hash(String name) {
    int hash = 5381;
    for (int i = 0; i < name.length(); i++) {
      hash = ((hash << 5) + hash) + name.charAt(i);
    }
      return Math.abs(hash) % HASH_SIZE;
  }

  public void contactAdd(String name, String phone) {
    //get index from contact name
    int hashIndex = hash(name);
    Contact newContact = new Contact();
    newContact.name = name;
    newContact.phone = phone;
    newContact.next = bucketTable[hashIndex];
    bucketTable[hashIndex] = newContact;
  }

  public void contactRemove(String name) {
    int index = hash(name);
    Contact contact = bucketTable[index];
    Contact previous = null;

    while (contact != null) {
      if (contact.name.equals(name)) {
        if (previous == null) {
          //Contact to remove is the head of the list
          bucketTable[index] = contact.next;
        } else {
          //Contact to remove is not the head of the list
          previous.next = contact.next;
        }
        System.out.println("Contact '" + name + "' removed successfully.");
        return;
      }
      previous = contact;
      contact = contact.next;
    }
    System.out.println("Contact '" + name + "' not found.");
  }

  public void contactSearch(String name) {
    int hashIndex = hash(name);
    Contact contact = bucketTable[hashIndex];
    while (contact != null) {
      if (contact.name.equals(name)) {
        System.out.println("Name: " + contact.name
            + "\nPhone Number: " + contact.phone);
        return;
      }
      contact = contact.next;
    }
    System.out.println("Contact '" + name + "' not found.");
  }

  public static void main(String[] args) {
    ContactList phonebook = new ContactList();
    phonebook.contactAdd("John", "235454545");
    phonebook.contactAdd("Jane", "775755454");
    phonebook.contactAdd("George", "4344343477");

    phonebook.contactSearch("John");
    phonebook.contactSearch("Alex");
    phonebook.contactSearch("George");

    phonebook.contactRemove("Jake");
    phonebook.contactRemove("Jane");
    phonebook.contactSearch("Jane");
  }
}
