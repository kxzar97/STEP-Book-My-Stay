public class BookMyStay {

    public static void main(String[] args) {

        BookingRequestQueue queue = new BookingRequestQueue();

        // Guest booking requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Double"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        // Process queue
        queue.processRequests();
    }
}