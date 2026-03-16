/**
 * UseCase10BookingCancellation
 *
 * Demonstrates safe booking cancellation with inventory rollback
 * using a Stack data structure.
 */

import java.util.*;

/* Reservation Model */

class Reservation {

    String reservationId;
    String roomType;
    String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}


/* Inventory Service */

class InventoryService {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 1);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms: " + inventory.get(type));
        }
    }
}


/* Cancellation Service */

class CancellationService {

    private Map<String, Reservation> reservations = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();
    private InventoryService inventory;

    public CancellationService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public void cancelReservation(String reservationId) {

        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        Reservation r = reservations.remove(reservationId);

        rollbackStack.push(r.getRoomId());

        inventory.incrementRoom(r.getRoomType());

        System.out.println("Reservation Cancelled Successfully.");
        System.out.println("Released Room ID: " + r.getRoomId());
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (recently released rooms):");
        System.out.println(rollbackStack);
    }
}


/* Application Entry */

public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       BookMyStay System         ");
        System.out.println("       Hotel Booking v10.0       ");
        System.out.println("=================================");

        InventoryService inventory = new InventoryService();

        CancellationService cancellationService =
                new CancellationService(inventory);

        /* Simulated confirmed reservations */

        Reservation r1 = new Reservation("R101", "Single", "S1");
        Reservation r2 = new Reservation("R102", "Double", "D1");

        cancellationService.addReservation(r1);
        cancellationService.addReservation(r2);

        /* Cancel booking */

        cancellationService.cancelReservation("R101");

        /* Invalid cancellation */

        cancellationService.cancelReservation("R999");

        cancellationService.showRollbackStack();

        inventory.displayInventory();
    }
}