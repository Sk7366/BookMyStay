/**
 * UseCase11ConcurrentBookingSimulation
 *
 * Demonstrates thread-safe booking processing
 * using synchronized methods.
 */

import java.util.*;

/* Booking Request */

class BookingRequest {

    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}


/* Inventory Service */

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
    }

    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }

        return false;
    }

    public void displayInventory() {

        System.out.println("\nRemaining Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms: " + inventory.get(type));
        }
    }
}


/* Booking Queue */

class BookingQueue {

    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }
}


/* Concurrent Booking Processor */

class BookingProcessor extends Thread {

    private BookingQueue queue;
    private InventoryService inventory;

    public BookingProcessor(BookingQueue queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            BookingRequest request = queue.getRequest();

            if (request == null)
                break;

            boolean success = inventory.allocateRoom(request.roomType);

            if (success) {
                System.out.println(Thread.currentThread().getName()
                        + " confirmed booking for "
                        + request.guestName
                        + " (" + request.roomType + ")");
            } else {
                System.out.println(Thread.currentThread().getName()
                        + " failed booking for "
                        + request.guestName
                        + " (" + request.roomType + ")");
            }
        }
    }
}


/* Main Application */

public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       BookMyStay System         ");
        System.out.println("       Hotel Booking v11.0       ");
        System.out.println("=================================");

        InventoryService inventory = new InventoryService();

        BookingQueue queue = new BookingQueue();

        /* Simulated guest requests */

        queue.addRequest(new BookingRequest("Alice", "Single"));
        queue.addRequest(new BookingRequest("Bob", "Single"));
        queue.addRequest(new BookingRequest("Charlie", "Single"));
        queue.addRequest(new BookingRequest("David", "Double"));
        queue.addRequest(new BookingRequest("Eva", "Double"));

        /* Create multiple threads */

        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);
        BookingProcessor t3 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();

        System.out.println("\nConcurrent booking simulation completed.");
    }
}