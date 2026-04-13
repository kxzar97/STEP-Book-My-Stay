import java.util.*;

public class BookMyStay {

    /**
     * CLASS - Room
     * Stores static characteristics and base price.
     */
    static class Room {
        private String type;
        private double basePrice;

        public Room(String type, double basePrice) {
            this.type = type;
            this.basePrice = basePrice;
        }

        public String getType() { return type; }
        public double getBasePrice() { return basePrice; }
    }

    /**
     * CLASS - DynamicPricingService
     * Use Case 10: Calculates the adjusted price based on seasonal demand.
     */
    static class DynamicPricingService {

        /**
         * Calculates final price: Base Price * Multiplier
         * @param room The room being booked
         * @param seasonMultiplier e.g., 1.2 for 20% increase during peak season
         * @return adjusted price
         */
        public double calculateAdjustedPrice(Room room, double seasonMultiplier) {
            return room.getBasePrice() * seasonMultiplier;
        }

        public void displayPriceComparison(Room room, double multiplier) {
            double adjusted = calculateAdjustedPrice(room, multiplier);
            System.out.println("Pricing Update for " + room.getType() + ":");
            System.out.println("  Standard Rate: $" + room.getBasePrice());
            System.out.println("  Seasonal Rate (" + (int)(multiplier * 100) + "%): $" + adjusted);
            System.out.println();
        }
    }

    /**
     * MAIN CLASS - UC10bms
     */
    public static void main(String[] args) {
        System.out.println("Hotel Management System: Dynamic Pricing Engine\n");

        // 1. Initialize Service
        DynamicPricingService pricingService = new DynamicPricingService();

        // 2. Define Rooms with Base Prices
        Room single = new Room("Single Room", 1500.0);
        Room doubleRm = new Room("Double Room", 2500.0);
        Room suite = new Room("Suite Room", 5000.0);

        // 3. Define Seasonal Multipliers (e.g., Holiday Season = 1.25x)
        double holidayMultiplier = 1.25;
        double offSeasonMultiplier = 0.90;

        // 4. Demonstrate Price Adjustments
        System.out.println("--- PEAK SEASON RATES ---");
        pricingService.displayPriceComparison(single, holidayMultiplier);
        pricingService.displayPriceComparison(doubleRm, holidayMultiplier);
        pricingService.displayPriceComparison(suite, holidayMultiplier);

        System.out.println("--- OFF-SEASON DISCOUNT RATES ---");
        pricingService.displayPriceComparison(suite, offSeasonMultiplier);
    }
}