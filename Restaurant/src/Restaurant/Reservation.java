package Restaurant;

public class Reservation {
  public Customer customer;
  public Table table;
  public int startTimeSlot;
  public int endTimeSlot;

  public Reservation(Customer customer, Table table, int startTimeSlot, int endTimeSlot) {
    this.customer = customer;
    this.table = table;
    this.startTimeSlot = startTimeSlot;
    this.endTimeSlot = endTimeSlot;
  }
}
