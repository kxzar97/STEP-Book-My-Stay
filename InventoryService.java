public class InventoryService {

    private RoomInventory inventory;

    public InventoryService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public boolean allocateRoom(String roomType) {

        int available = inventory.getAvailability(roomType);

        if (available > 0) {
            inventory.updateAvailability(roomType, -1);
            return true;
        }

        return false;
    }
}