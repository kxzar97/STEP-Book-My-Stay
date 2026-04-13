import java.io.*;
import java.util.*;

/**
 * Use Case 12: Data Persistence & System Recovery
 * This class ensures that critical system state survives application restarts[cite: 6].
 */
public class BookMyStay {

    // --- SUPPORTING CLASSES ---

    static class RoomInventory {
        private Map<String, Integer> inventory = new LinkedHashMap<>();

        public void addRooms(String type, int count) {
            inventory.put(type, count);
        }

        public Map<String, Integer> getAllInventory() {
            return inventory;
        }

        public void displayInventory() {
            System.out.println("\nCurrent Inventory:");
            inventory.forEach((type, count) -> System.out.println(type + ": " + count));
        }
    }

    // --- PERSISTENCE SERVICE ---

    /**
     * CLASS - FilePersistenceService [cite: 9]
     * Responsible for persisting critical system state to a plain text file.
     */
    static class FilePersistenceService {

        /**
         * Saves room inventory state to a file[cite: 13].
         * Format: roomType=availableCount
         */
        public void saveInventory(RoomInventory inventory, String filePath) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                for (Map.Entry<String, Integer> entry : inventory.getAllInventory().entrySet()) {
                    writer.println(entry.getKey() + "=" + entry.getValue());
                }
                System.out.println("Inventory saved successfully.");
            } catch (IOException e) {
                System.out.println("Error saving inventory: " + e.getMessage());
            }
        }

        /**
         * Loads room inventory state from a file[cite: 15].
         * Handles missing or corrupted files gracefully.
         */
        public void loadInventory(RoomInventory inventory, String filePath) {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("No valid inventory data found. Starting fresh."); [cite: 46]
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        inventory.addRooms(parts[0], Integer.parseInt(parts[1]));
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Error loading inventory, starting with default values.");
            }
        }
    }

    // --- MAIN EXECUTION ---

    public static void main(String[] args) {
        System.out.println("System Recovery"); [cite: 46]

        String persistenceFile = "inventory_state.txt";
        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();

        // 1. Restore persisted data during application startup [cite: 33]
        persistenceService.loadInventory(inventory, persistenceFile);

        // 2. Initialize with default values if no saved state exists
        if (inventory.getAllInventory().isEmpty()) {
            inventory.addRooms("Single", 5);
            inventory.addRooms("Double", 3);
            inventory.addRooms("Suite", 2);
        }

        // 3. Show current state [cite: 16]
        inventory.displayInventory();

        // 4. Persist inventory state to a file before closing [cite: 32]
        persistenceService.saveInventory(inventory, persistenceFile);
    }
