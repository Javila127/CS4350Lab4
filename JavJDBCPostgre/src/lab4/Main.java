package lab4;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Establish database connection
        Connection connection = ConnectDB.connect();
        
        // Create TripDAO object
        TripDAO tripDAO = new TripDAO(connection);

        // Perform transactions
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;
        
        while (continueLoop) {
            System.out.println("Select transaction:");
            System.out.println("1. Display schedule of trips");
            System.out.println("2. Edit schedule");
            System.out.println("3. Display stops of a trip");
            System.out.println("4. Display weekly schedule of a driver");
            System.out.println("5. Add a driver");
            System.out.println("6. Add a bus");
            System.out.println("7. Delete a bus");
            System.out.println("8. Record actual trip data");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            try {
                switch (choice) {
                    case 1:
                        tripDAO.displaySchedule();
                        break;
                    case 2:
                        tripDAO.editSchedule();
                        break;
                    case 3:
                        tripDAO.displayStops();
                        break;
                    case 4:
                        tripDAO.displayWeeklySchedule();
                        break;
                    case 5:
                        tripDAO.addDriver();
                        break;
                    case 6:
                        tripDAO.addBus();
                        break;
                    case 7:
                        tripDAO.deleteBus();
                        break;
                    case 8:
                        tripDAO.recordActualData();
                        break;
                    case 9:
                        continueLoop = false;
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (SQLException e) {
                System.out.println("Error executing transaction: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }

        // Close connection
        ConnectDB.close(connection);
    }
}
