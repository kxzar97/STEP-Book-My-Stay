public class SearchService {

    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void displayAvailableRooms(Room[] rooms) {

        System.out.println("Room Search\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            if (available > 0) {   // Filter unavailable rooms
                System.out.println(room.getRoomType() + ":");
                System.out.println("Beds: " + room.beds);
                System.out.println("Size: " + room.size + " sqft");
                System.out.println("Price per night: " + room.price);
                System.out.println("Available: " + available);
                System.out.println();
            }
        }
    }
}
