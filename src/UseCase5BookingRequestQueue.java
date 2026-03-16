/**
 * UseCase5BookingRequestQueue
 *
 * Demonstrates booking request intake using Queue (FIFO).
 * Requests are stored in arrival order before allocation.
 *
 * @author SK7366
 * @version 5.0
 */

import java.util.LinkedList;
import java.util.Queue;


/* Reservation Class */

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}


/* Booking Request Queue */

class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    /* Add request to queue */
    public void addRequest(Reservation reservation) {

        queue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    /* Display queued requests */
    public void displayRequests() {

        System.out.println("\nCurrent Booking Request Queue:");

        for (Reservation r : queue) {
            r.displayReservation();
        }
    }
}


/* Application Entry */

public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("        BookMyStay Application       ");
        System.out.println("        Hotel Booking System v5.0    ");
        System.out.println("=====================================");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        /* Guest booking requests */
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        /* Add requests to queue */
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        /* Show queue order */
        bookingQueue.displayRequests();

        System.out.println("\nRequests will be processed in FIFO order.");
        System.out.println("Application Terminated.");
    }
}