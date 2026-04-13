import java.util.*;

public class BookMyStay {

    // --- FROM PREVIOUS USE CASES ---
    static class Room {
        private String type;
        private double pricePerNight;

        public Room(String type, double pricePerNight) {
            this.type = type;
            this.pricePerNight = pricePerNight;
        }

        public String getType() { return type; }
        public double getPricePerNight() { return pricePerNight; }
    }

    static class RoomInventory {
        private Map<String, Integer> roomAvailability = new HashMap<>();
        public RoomInventory() {
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
            roomAvailability.put("Suite", 2);
        }
        public Map<String, Integer> getRoomAvailability() { return roomAvailability; }
        public void updateAvailability(String type, int count) { roomAvailability.put(type, count); }
    }

    static class Reservation {
        private String guestName;
        private String roomType;
        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
        public String getGuestName() { return guestName; }
        public String getRoomType() { return roomType; }
    }

    // --- USE CASE 7: BILLING & REVENUE SERVICE ---
    static class BillingService {
        private double totalRevenue = 0.0;

        /**
         * Generates a bill for a confirmed booking and updates total revenue.
         */
        public void generateBill(String guestName, Room room) {
            double amount = room.getPricePerNight();
            totalRevenue += amount;
            System.out.println("Bill generated for " + guestName + ": $" + amount);
        }

        public double getTotalRevenue() {
            return totalRevenue;
        }
    }

    /**
     * MAIN CLASS - UC7bms
     */
    public static void main(String[] args) {
        System.out.println("Hotel Billing & Revenue Summary\n");

        // 1. Setup Data
        RoomInventory inventory = new RoomInventory();
        BillingService billingService = new BillingService();

        // Define Room Pricing
        Map<String, Room> roomDefinitions = new HashMap<>();
        roomDefinitions.put("Single", new Room("Single Room", 1500.0));
        roomDefinitions.put("Double", new Room("Double Room", 2500.0));
        roomDefinitions.put("Suite", new Room("Suite Room", 5000.0));

        // 2. Create Booking Requests
        List<Reservation> requests = new ArrayList<>();
        requests.add(new Reservation("Abhi", "Single"));
        requests.add(new Reservation("Subha", "Double"));
        requests.add(new Reservation("Vanmathi", "Suite"));

        // 3. Process Bookings and Billing
        for (Reservation request : requests) {
            String type = request.getRoomType();
            int currentStock = inventory.getRoomAvailability().getOrDefault(type, 0);

            if (currentStock > 0) {
                // Update Inventory
                inventory.updateAvailability(type, currentStock - 1);

                // Generate Bill
                Room roomDetail = roomDefinitions.get(type);
                billingService.generateBill(request.getGuestName(), roomDetail);
            }
        }

        // 4. Final Revenue Report
        System.out.println("\n-------------------------------");
        System.out.println("Total Hotel Revenue: $" + billingService.getTotalRevenue());
        System.out.println("-------------------------------");
    }
}