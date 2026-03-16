/**
 * UseCase9ErrorHandlingValidation
 *
 * Demonstrates validation and structured error handling
 * for booking requests in the BookMyStay system.
 *
 * @author SK7366
 * @version 9.0
 */

import java.util.*;

/* Custom Exception */

class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}


/* Reservation Model */

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
        inventory.put("Double", 1);
        inventory.put("Suite", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void decrementRoom(String roomType) throws InvalidBookingException {

        int available = getAvailability(roomType);

        if (available < 0) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (available == 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }

        inventory.put(roomType, available - 1);
    }
}


/* Validator */

class InvalidBookingValidator {

    public static void validateReservation(Reservation r, InventoryService inventory)
            throws InvalidBookingException {

        if (r.guestName == null || r.guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (inventory.getAvailability(r.roomType) == -1) {
            throw new InvalidBookingException("Room type does not exist: " + r.roomType);
        }
    }
}


/* Booking Service */

class BookingService {

    InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void confirmReservation(Reservation reservation) {

        try {

            InvalidBookingValidator.validateReservation(reservation, inventory);

            inventory.decrementRoom(reservation.roomType);

            System.out.println("Reservation Confirmed for "
                    + reservation.guestName +
                    " (" + reservation.roomType + " room)");

        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}


/* Application Entry */

public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       BookMyStay System         ");
        System.out.println("       Hotel Booking v9.0        ");
        System.out.println("=================================");

        InventoryService inventory = new InventoryService();

        BookingService bookingService = new BookingService(inventory);

        /* Test cases */

        Reservation r1 = new Reservation("Alice", "Single");
        Reservation r2 = new Reservation("Bob", "Suite");   // no availability
        Reservation r3 = new Reservation("", "Double");     // invalid name
        Reservation r4 = new Reservation("David", "Luxury"); // invalid type

        bookingService.confirmReservation(r1);
        bookingService.confirmReservation(r2);
        bookingService.confirmReservation(r3);
        bookingService.confirmReservation(r4);

        System.out.println("\nSystem continues running safely.");
    }
}