/**
 * BookMyStay
 *
 * This class demonstrates Hostel Room Initialization using domain models before introducing centralized inventory management.
 * Availability is represented using simple variables to highlight limitations.
 *
 * @author YourName
 * @version 2.0
 */
public class BookMyStay {
    public static void main(String[] args) {

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        SingleRoom single = new SingleRoom();
        DoubleRoom dbl = new DoubleRoom();
        SuiteRoom suite = new SuiteRoom();

        System.out.println("Hotel Room Initialization\n");

        single.displayRoom(singleAvailable);
        dbl.displayRoom(doubleAvailable);
        suite.displayRoom(suiteAvailable);
    }
}