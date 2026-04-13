import java.util.*;

public class BookMyStay {

    // --- REUSABLE COMPONENTS ---
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

    static class BillingService {
        private double totalRevenue = 0.0;
        public void processPayment(double amount) { totalRevenue += amount; }
        public double getTotalRevenue() { return totalRevenue; }
    }

    /**
     * CLASS - ReportGenerator
     * Use Case 8: Generates management reports for inventory and revenue.
     */
    static class ReportGenerator {

        public void generateInventoryReport(RoomInventory inventory) {
            System.out.println("--- Current Inventory Report ---");
            inventory.getRoomAvailability().forEach((type, count) ->
                    System.out.println(type + " Rooms remaining: " + count));
        }

        public void generateRevenueReport(BillingService billing) {
            System.out.println("\n--- Financial Revenue Report ---");
            System.out.println("Total Earnings: $" + billing.getTotalRevenue());
        }

        public void generateFinalSummary(RoomInventory inventory, BillingService billing) {
            System.out.println("\n==================================");
            System.out.println("      FINAL BUSINESS SUMMARY      ");
            System.out.println("==================================");
            generateInventoryReport(inventory);
            generateRevenueReport(billing);
            System.out.println("==================================");
        }
    }

    /**
     * MAIN CLASS - UC8bms
     */
    public static void main(String[] args) {
        // 1. Initialize System
        RoomInventory inventory = new RoomInventory();
        BillingService billing = new BillingService();
        ReportGenerator reportGenerator = new ReportGenerator();

        // 2. Simulate some business activity (Bookings)
        // Guest 1: Abhi books Single ($1500)
        inventory.updateAvailability("Single", 4);
        billing.processPayment(1500.0);

        // Guest 2: Subha books Double ($2500)
        inventory.updateAvailability("Double", 2);
        billing.processPayment(2500.0);

        // Guest 3: Vanmathi books Suite ($5000)
        inventory.updateAvailability("Suite", 1);
        billing.processPayment(5000.0);

        // 3. Generate Reports (Matches Screenshot Logic)
        reportGenerator.generateFinalSummary(inventory, billing);
    }
}