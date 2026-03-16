/**
 * UseCase6RoomAllocationService
 *
 * Demonstrates reservation confirmation and safe room allocation.
 * Ensures unique room IDs and prevents double booking.
 *
 * @author SK7366
 * @version 6.0
 */

import java.util.*;

/* Reservation Class */

class Reservation {

    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}


/* Inventory Service */

class InventoryService {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}


/* Booking Service */

class BookingService {

    private Queue<Reservation> requestQueue;
    private HashMap<String, Set<String>> allocatedRooms;
    private Set<String> usedRoomIds;
    private InventoryService inventory;

    public BookingService(Queue<Reservation> requestQueue, InventoryService inventory) {
        this.requestQueue = requestQueue;
        this.inventory = inventory;

        allocatedRooms = new HashMap<>();
        usedRoomIds = new HashSet<>();
    }

    public void processBookings() {

        while (!requestQueue.isEmpty()) {

            Reservation r = requestQueue.poll();

            String roomType = r.roomType;

            if (inventory.getAvailability(roomType) > 0) {

                String roomId = generateRoomId(roomType);

                inventory.decrementRoom(roomType);

                allocatedRooms
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                usedRoomIds.add(roomId);

                System.out.println("Reservation Confirmed");
                System.out.println("Guest: " + r.guestName);
                System.out.println("Room Type: " + roomType);
                System.out.println("Room ID: " + roomId);
                System.out.println("--------------------------");

            } else {

                System.out.println("Reservation Failed for " + r.guestName +
                        " (No " + roomType + " rooms available)");
            }
        }
    }

    private String generateRoomId(String roomType) {

        String roomId;

        do {
            roomId = roomType.substring(0,1).toUpperCase() +
                    (int)(Math.random()*1000);
        } while (usedRoomIds.contains(roomId));

        return roomId;
    }
}


/* Application Entry */

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       BookMyStay System         ");
        System.out.println("       Hotel Booking v6.0        ");
        System.out.println("=================================");

        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Alice", "Single"));
        bookingQueue.add(new Reservation("Bob", "Double"));
        bookingQueue.add(new Reservation("Charlie", "Single"));
        bookingQueue.add(new Reservation("David", "Suite"));
        bookingQueue.add(new Reservation("Eva", "Suite"));

        InventoryService inventory = new InventoryService();

        BookingService bookingService =
                new BookingService(bookingQueue, inventory);

        bookingService.processBookings();

        System.out.println("\nAll booking requests processed.");
    }
}