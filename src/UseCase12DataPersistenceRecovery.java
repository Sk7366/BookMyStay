/**
 * UseCase12DataPersistenceRecovery
 *
 * Demonstrates saving and restoring system state
 * using file serialization.
 */

import java.io.*;
import java.util.*;


/* Reservation Model */

class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}


/* Inventory Service */

class InventoryService implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public void displayInventory() {

        System.out.println("\nInventory State:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms: " + inventory.get(type));
        }
    }
}


/* Booking History */

class BookingHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation r) {
        reservations.add(r);
    }

    public void displayHistory() {

        System.out.println("\nBooking History:");

        for (Reservation r : reservations) {
            r.display();
        }
    }
}


/* Persistence Service */

class PersistenceService {

    private static final String FILE_NAME = "bookmystay_data.ser";

    public static void saveSystemState(InventoryService inventory,
                                       BookingHistory history) {

        try {

            ObjectOutputStream out =
                    new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            out.writeObject(inventory);
            out.writeObject(history);

            out.close();

            System.out.println("\nSystem state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    public static Object[] loadSystemState() {

        try {

            ObjectInputStream in =
                    new ObjectInputStream(new FileInputStream(FILE_NAME));

            InventoryService inventory = (InventoryService) in.readObject();
            BookingHistory history = (BookingHistory) in.readObject();

            in.close();

            System.out.println("\nSystem state restored successfully.");

            return new Object[]{inventory, history};

        } catch (Exception e) {

            System.out.println("\nNo previous data found. Starting fresh.");

            return null;
        }
    }
}


/* Main Application */

public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       BookMyStay System         ");
        System.out.println("       Hotel Booking v12.0       ");
        System.out.println("=================================");

        InventoryService inventory;
        BookingHistory history;

        /* Attempt recovery */

        Object[] recoveredState = PersistenceService.loadSystemState();

        if (recoveredState != null) {

            inventory = (InventoryService) recoveredState[0];
            history = (BookingHistory) recoveredState[1];

        } else {

            inventory = new InventoryService();
            history = new BookingHistory();
        }

        /* Simulated booking */

        Reservation r1 = new Reservation("R101", "Alice", "Single");
        Reservation r2 = new Reservation("R102", "Bob", "Double");

        history.addReservation(r1);
        history.addReservation(r2);

        inventory.displayInventory();
        history.displayHistory();

        /* Save system state before shutdown */

        PersistenceService.saveSystemState(inventory, history);

        System.out.println("\nSystem shutdown complete.");
    }
}