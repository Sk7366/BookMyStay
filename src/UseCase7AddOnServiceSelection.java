/**
 * UseCase7AddOnServiceSelection
 *
 * Demonstrates optional add-on services for reservations.
 * Services are mapped to reservations without modifying booking logic.
 *
 * @author SK7366
 * @version 7.0
 */

import java.util.*;

/* Service Class */

class Service {

    String serviceName;
    double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getServiceName() {
        return serviceName;
    }
}


/* Add-On Service Manager */

class AddOnServiceManager {

    private HashMap<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    /* Add service to reservation */

    public void addService(String reservationId, Service service) {

        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println(service.getServiceName() +
                " added to reservation " + reservationId);
    }

    /* Calculate additional cost */

    public double calculateAdditionalCost(String reservationId) {

        double total = 0;

        List<Service> services = reservationServices.get(reservationId);

        if (services != null) {

            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }

    /* Display services */

    public void displayServices(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        System.out.println("\nAdd-On Services for Reservation " + reservationId);

        if (services == null) {
            System.out.println("No services selected.");
            return;
        }

        for (Service s : services) {
            System.out.println("- " + s.getServiceName() + " ($" + s.getCost() + ")");
        }

        System.out.println("Total Add-On Cost: $" +
                calculateAdditionalCost(reservationId));
    }
}


/* Application Entry */

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       BookMyStay System         ");
        System.out.println("       Hotel Booking v7.0        ");
        System.out.println("=================================");

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "R101";

        Service breakfast = new Service("Breakfast", 20);
        Service airportPickup = new Service("Airport Pickup", 40);
        Service spa = new Service("Spa Access", 50);

        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, airportPickup);
        manager.addService(reservationId, spa);

        manager.displayServices(reservationId);

        System.out.println("\nBooking and inventory remain unchanged.");
    }
}