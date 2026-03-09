public class BookMyStay {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        SingleRoom single = new SingleRoom();
        DoubleRoom dbl = new DoubleRoom();
        SuiteRoom suite = new SuiteRoom();

        System.out.println("Hotel Room Inventory Status\n");

        single.displayRoom(inventory.getAvailability("Single Room"));
        dbl.displayRoom(inventory.getAvailability("Double Room"));
        suite.displayRoom(inventory.getAvailability("Suite Room"));
    }
}