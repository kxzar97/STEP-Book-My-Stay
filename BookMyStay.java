import java.util.LinkedList;
import java.util.Queue;

public class BookMyStay {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        InventoryService inventoryService = new InventoryService(inventory);
        BookingService bookingService = new BookingService(inventoryService);

        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Abhi", "Single"));
        bookingQueue.add(new Reservation("Subha", "Double"));
        bookingQueue.add(new Reservation("Vanmathi", "Suite"));

        bookingService.processBookings(bookingQueue);
    }
}