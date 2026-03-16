/**
 * UseCase3InventorySetup
 *
 * Demonstrates centralized room inventory management using HashMap.
 * Replaces scattered availability variables with a single inventory structure.
 *
 * @author SK7366
 * @version 3.0
 */

import java.util.HashMap;

/* Inventory Management Class */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        /* Initial Room Availability */
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 2);
    }

    /* Get availability of a room type */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /* Update availability */
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    /* Display complete inventory */
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");

        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " : " + inventory.get(roomType) + " rooms available");
        }
    }
}


/* Application Entry Point */
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("      Welcome to BookMyStay App        ");
        System.out.println("      Hotel Booking System v3.0        ");
        System.out.println("=======================================");

        /* Initialize Inventory */
        RoomInventory inventory = new RoomInventory();

        /* Display current inventory */
        inventory.displayInventory();

        /* Example update */
        System.out.println("\nUpdating inventory...");

        inventory.updateAvailability("Single Room", 8);

        /* Display updated inventory */
        inventory.displayInventory();

        System.out.println("\nApplication Terminated.");
    }
}