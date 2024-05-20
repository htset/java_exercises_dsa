package Restaurant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Restaurant {
  private final List<Table> tables = new ArrayList<>();
  private final List<Reservation> reservations = new ArrayList<>();

  public void addTable(Table table) {
    tables.add(table);
  }

  public boolean isTableAvailable(Table table, int startTimeSlot, int endTimeSlot) {
    return reservations.stream().noneMatch(reservation ->
        reservation.table.id == table.id &&
            ((startTimeSlot >= reservation.startTimeSlot
                && startTimeSlot < reservation.endTimeSlot) ||
                (endTimeSlot > reservation.startTimeSlot
                    && endTimeSlot <= reservation.endTimeSlot) ||
                (startTimeSlot <= reservation.startTimeSlot
                    && endTimeSlot >= reservation.endTimeSlot)));
  }

  public List<Table> findAvailableTables(int capacity, int startTimeSlot, int endTimeSlot) {
    List<Table> availableTables = tables.stream()
        .filter(table -> table.capacity >= capacity
            && isTableAvailable(table, startTimeSlot, endTimeSlot))
        .collect(Collectors.toList());
    availableTables.sort(Comparator.comparingInt(a -> a.capacity));
    return availableTables;
  }

  public void addReservation(String name, int capacity, int startSlot, int endSlot) {
    List<Table> availableTables = findAvailableTables(capacity, startSlot, endSlot);
    if (!availableTables.isEmpty()) {
      reservations.add(new Reservation(new Customer(name),
          availableTables.get(0), startSlot, endSlot));
      System.out.println("Reservation successfully added.");
    } else {
      System.out.println("No available tables for the requested time slot.");
    }
  }

  public void printReservations() {
    System.out.println("All reservations:");
    for (Reservation reservation : reservations) {
      System.out.println("Customer: " + reservation.customer.name +
          ", Table Capacity: " + reservation.table.capacity +
          ", Start Time Slot: " + reservation.startTimeSlot +
          ", End Time Slot: " + reservation.endTimeSlot);
    }
  }

  public static void main(String[] args) {
    Restaurant restaurant = new Restaurant();

    //Add tables
    restaurant.addTable(new Table(1, 6));
    restaurant.addTable(new Table(2, 4));
    restaurant.addTable(new Table(3, 2));

    //Find available tables for a new reservation
    restaurant.addReservation("Customer 1", 4, 1, 3);
    restaurant.addReservation("Customer 2", 6, 2, 4);
    restaurant.addReservation("Customer 3", 4, 3, 5);
    restaurant.addReservation("Customer 4", 4, 1, 3);

    restaurant.printReservations();
  }
}
