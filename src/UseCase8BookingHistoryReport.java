/**
 * UseCase8BookingHistoryReport
 *
 * Demonstrates booking history tracking and reporting.
 * Confirmed reservations are stored in a list and reports
 * are generated from stored booking history.
 *
 * @author SK7366
 * @version 8.0
 */

import java.util.*;

/* Reservation Class */

class Reservation {

    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType);
    }
}


/* Booking History */

class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}


/* Booking Report Service */

class BookingReportService {

    public void generateReport(List<Reservation> reservations) {

        System.out.println("\n===== Booking History Report =====");

        if (reservations.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Reservation r : reservations) {
            r.display();
        }

        System.out.println("\nTotal Bookings: " + reservations.size());
    }
}


/* Application Entry */

public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       BookMyStay System         ");
        System.out.println("       Hotel Booking v8.0        ");
        System.out.println("=================================");

        BookingHistory history = new BookingHistory();

        /* Simulated confirmed reservations */

        history.addReservation(new Reservation("R101", "Alice", "Single"));
        history.addReservation(new Reservation("R102", "Bob", "Double"));
        history.addReservation(new Reservation("R103", "Charlie", "Suite"));

        BookingReportService reportService = new BookingReportService();

        reportService.generateReport(history.getAllReservations());

        System.out.println("\nReport generated successfully.");
    }
}