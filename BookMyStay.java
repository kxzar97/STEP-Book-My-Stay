import java.util.*;

/**
 * Hotel Booking Management System
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 */
public class BookMyStay {

    // --- SUPPORTING CLASSES ---

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

    static class RoomInventory {
        // Shared Mutable State - Inventory shared across threads
        private Map<String, Integer> inventory = new HashMap<>();

        public void addRooms(String type, int count) {
            inventory.put(type, count);
        }

        public int getCount(String type) {
            return inventory.getOrDefault(type, 0);
        }

        public void updateCount(String type, int count) {
            inventory.put(type, count);
        }

        public void displayInventory() {
            System.out.println("\nRemaining Inventory:");
            System.out.println("Single: " + getCount("Single"));
            System.out.println("Double: " + getCount("Double"));
            System.out.println("Suite: " + getCount("Suite"));
        }
    }

    static class BookingRequestQueue {
        // Shared Mutable State - Booking queue shared across threads
        private Queue<Reservation> queue = new LinkedList<>();

        public void addRequest(Reservation res) {
            queue.add(res);
        }

        public Reservation pollRequest() {
            return queue.poll();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    static class RoomAllocationService {
        public void allocateRoom(Reservation res, RoomInventory inventory) {
            int currentCount = inventory.getCount(res.getRoomType());
            if (currentCount > 0) {
                inventory.updateCount(res.getRoomType(), currentCount - 1);
                // Expected Output Format
                System.out.println("Booking confirmed for Guest: " + res.getGuestName() + ", Room ID: " + res.getRoomType() + "-1");
            }
        }
    }

    // --- CORE PROCESSOR (AS PER DOCS) ---

    /**
     * Concurrent Booking Processor
     * Implements Runnable for multi-threaded execution
     */
    static class ConcurrentBookingProcessor implements Runnable {
        private BookingRequestQueue bookingQueue;
        private RoomInventory inventory;
        private RoomAllocationService allocationService;

        public ConcurrentBookingProcessor(BookingRequestQueue bookingQueue,
                                          RoomInventory inventory,
                                          RoomAllocationService allocationService) {
            this.bookingQueue = bookingQueue;
            this.inventory = inventory;
            this.allocationService = allocationService;
        }

        @Override
        public void run() {
            while (true) {
                Reservation reservation = null;

                // Synchronized access to ensure only one thread retrieves a request
                synchronized (bookingQueue) {
                    if (bookingQueue.isEmpty()) {
                        break;
                    }
                    reservation = bookingQueue.pollRequest();
                }

                if (reservation != null) {
                    // Critical section for room allocation and inventory updates
                    synchronized (inventory) {
                        allocationService.allocateRoom(reservation, inventory);
                    }
                }
            }
        }
    }

    // --- MAIN ENTRY POINT ---

    public static void main(String[] args) {
        // Header
        System.out.println("Concurrent Booking Simulation");

        // 1. Initialize shared resources [cite: 29]
        RoomInventory inventory = new RoomInventory();
        inventory.addRooms("Single", 5);
        inventory.addRooms("Double", 3);
        inventory.addRooms("Suite", 2);

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        RoomAllocationService allocationService = new RoomAllocationService();

        // 2. Create booking processor tasks [cite: 45]
        Thread t1 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));

        // 3. Start concurrent processing [cite: 45]
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            // Error Handling
            System.out.println("Thread execution interrupted.");
        }

        // 4. Show remaining inventory [cite: 46]
        inventory.displayInventory();
    }
}