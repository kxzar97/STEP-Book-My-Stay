import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class BookingService {

    private InventoryService inventoryService;
    private Set<String> usedRoomIds = new HashSet<>();
    private int roomCounter = 1;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void processBookings(Queue<Reservation> queue) {

        while (!queue.isEmpty()) {

            Reservation request = queue.poll();
            String roomType = request.getRoomType();

            boolean allocated = inventoryService.allocateRoom(roomType);

            if (allocated) {

                String roomId = generateRoomId(roomType);

                System.out.println("Reservation Confirmed");
                System.out.println("Guest: " + request.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Assigned Room ID: " + roomId);
                System.out.println();
            }
            else {
                System.out.println("Reservation Failed for " +
                        request.getGuestName() +
                        " (No rooms available)");
            }
        }
    }

    private String generateRoomId(String roomType) {

        String id;

        do {
            id = roomType.substring(0,1).toUpperCase() + roomCounter++;
        }
        while (usedRoomIds.contains(id));

        usedRoomIds.add(id);
        return id;
    }
}
