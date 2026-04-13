import java.util.*;

public class UC9
{

    /**
     * CLASS - RoomInventory
     * Acts as the centralized single source of truth for availability.
     */
    static class RoomInventory {
        private Map<String, Integer> roomAvailability = new HashMap<>();

        public RoomInventory() {
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
            roomAvailability.put("Suite", 2);
        }

        public Map<String, Integer> getRoomAvailability() {
            return roomAvailability;
        }

        public void updateAvailability(String roomType, int count) {
            roomAvailability.put(roomType, count);
        }
    }

    /**
     * CLASS - Room
     * Stores static characteristics.
     */
    static class Room {
        private String type;
        private double price;

        public Room(String type, double price) {
            this.type = type;
            this.price = price;
        }

        public String getType() { return type; }
        public double getPrice() { return price; }
    }

    /**
     * CLASS - InventorySyncService
     * Use Case 9: Ensures synchronization between booking actions and inventory display.
     */
    static class InventorySyncService {

        public void processBooking(String guest, String type, RoomInventory inventory) {
            Map<String, Integer> availability = inventory.getRoomAvailability();
            int currentCount = availability.getOrDefault(type, 0);

            if (currentCount > 0) {
                // Deduct from inventory
                inventory.updateAvailability(type, currentCount - 1);
                System.out.println("Inventory Synced: Booking confirmed for " + guest + " (" + type + ")");
            } else {
                System.out.println("Sync Alert: " + type + " is out of stock for " + guest);
            }
        }

        public void displayLiveStatus(RoomInventory inventory) {
            System.out.println("\n--- Live Synchronized Inventory Status ---");
            inventory.getRoomAvailability().forEach((type, count) ->
                    System.out.println(type + " Rooms Available: " + count));
        }
    }

    /**
     * MAIN CLASS - UC9bms
     */
    public static void main(String[] args) {
        System.out.println("Hotel Management System: Final Inventory Synchronization\n");

        // 1. Setup
        RoomInventory inventory = new RoomInventory();
        InventorySyncService syncService = new InventorySyncService();

        // 2. Initial State
        syncService.displayLiveStatus(inventory);
        System.out.println("------------------------------------------");

        // 3. Simulate Bookings and Immediate Sync
        syncService.processBooking("Abhi", "Single", inventory);
        syncService.processBooking("Subha", "Double", inventory);
        syncService.processBooking("Vanmathi", "Suite", inventory);

        // 4. Final Synchronized Check
        // This demonstrates that the search results are now consistent with the bookings made.
        syncService.displayLiveStatus(inventory);
    }
}