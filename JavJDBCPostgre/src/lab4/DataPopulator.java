package lab4;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataPopulator {

    public static void main(String[] args) {
        // Establish database connection
        Connection connection = ConnectDB.connect();

        // Populate tables with sample data
        populateStopTable(connection);
        populateTripTable(connection);
        populateTripOfferingTable(connection);
        populateDriverTable(connection);
        populateBusTable(connection);
        populateTripStopInfoTable(connection);
        populateActualTripStopInfoTable(connection);

        // Close connection
        ConnectDB.close(connection);
    }
    
    public static void populateStopTable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            // Populate the 'stop' table with sample data
            String sql = "INSERT INTO Stop (\"StopAddress\") VALUES " +
                         "('Stop 1 Address')," +
                         "('Stop 2 Address')," +
                         "('Stop 3 Address')";
            statement.executeUpdate(sql);
            System.out.println("Stop table populated successfully.");
        } catch (SQLException e) {
            System.out.println("Error populating Stop table: " + e.getMessage());
        }
    }

    private static void populateTripTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Trip (\"StartLocationName\", \"DestinationName\") VALUES " +
                    "('City A', 'City B'), " +
                    "('City B', 'City C'), " +
                    "('City C', 'City D'), " +
                    "('City D', 'City A')");
            statement.close();
            System.out.println("Trip table populated successfully.");
        } catch (SQLException e) {
            System.out.println("Error populating Trip table: " + e.getMessage());
        }
    }

    private static void populateTripOfferingTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO TripOffering (\"TripNumber\", \"Date\", \"ScheduledStartTime\", \"ScheduledArrivalTime\", \"DriverName\", \"BusID\") VALUES " +
                    "(1, '2024-04-28', '08:00:00', '10:00:00', 'Driver1', 101), " +
                    "(1, '2024-04-28', '10:30:00', '12:30:00', 'Driver2', 102), " +
                    "(2, '2024-04-28', '09:00:00', '11:00:00', 'Driver1', 103), " +
                    "(3, '2024-04-29', '08:30:00', '10:30:00', 'Driver2', 101), " +
                    "(4, '2024-04-29', '10:00:00', '12:00:00', 'Driver1', 102)");
            statement.close();
            System.out.println("TripOffering table populated successfully.");
        } catch (SQLException e) {
            System.out.println("Error populating TripOffering table: " + e.getMessage());
        }
    }

    private static void populateDriverTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Driver (\"DriverName\", \"DriverTelephoneNumber\") VALUES " +
                    "('Driver1', '1234567890'), " +
                    "('Driver2', '9876543210')");
            statement.close();
            System.out.println("Driver table populated successfully.");
        } catch (SQLException e) {
            System.out.println("Error populating Driver table: " + e.getMessage());
        }
    }

    private static void populateBusTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Bus (\"Model\", \"Year\") VALUES " +
                    "('Model A', 2020), " +
                    "('Model B', 2019), " +
                    "('Model C', 2021)");
            statement.close();
            System.out.println("Bus table populated successfully.");
        } catch (SQLException e) {
            System.out.println("Error populating Bus table: " + e.getMessage());
        }
    }

    private static void populateTripStopInfoTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO TripStopInfo (\"TripNumber\", \"StopNumber\", \"SequenceNumber\", \"DrivingTime\") VALUES " +
                    "(1, 1, 1, '08:00:00'), " +
                    "(1, 2, 2, '08:30:00'), " +
                    "(1, 3, 3, '09:00:00'), " +
                    "(2, 1, 1, '09:00:00'), " +
                    "(2, 2, 2, '09:30:00'), " +
                    "(2, 3, 3, '10:00:00'), " +
                    "(3, 1, 1, '08:30:00'), " +
                    "(3, 2, 2, '09:00:00'), " +
                    "(3, 3, 3, '09:30:00'), " +
                    "(4, 1, 1, '10:00:00'), " +
                    "(4, 2, 2, '10:30:00'), " +
                    "(4, 3, 3, '11:00:00')");
            statement.close();
            System.out.println("TripStopInfo table populated successfully.");
        } catch (SQLException e) {
            System.out.println("Error populating TripStopInfo table: " + e.getMessage());
        }
    }

    private static void populateActualTripStopInfoTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO ActualTripStopInfo (\"TripNumber\", \"Date\", \"ScheduledStartTime\", \"StopNumber\", \"ScheduledArrivalTime\", \"ActualStartTime\", \"ActualArrivalTime\", \"NumberOfPassengerIn\", \"NumberOfPassengerOut\") VALUES " +
                    "(1, '2024-04-28', '08:00:00', 1, '09:00:00', '08:05:00', '09:05:00', 10, 2), " +
                    "(1, '2024-04-28', '08:00:00', 2, '09:00:00', '08:10:00', '09:10:00', 8, 3), " +
                    "(1, '2024-04-28', '08:00:00', 3, '09:00:00', '08:20:00', '09:20:00', 6, 1), " +
                    "(2, '2024-04-28', '09:00:00', 1, '10:00:00', '09:10:00', '10:10:00', 12, 5), " +
                    "(2, '2024-04-28', '09:00:00', 2, '10:00:00', '09:20:00', '10:20:00', 15, 3), " +
                    "(2, '2024-04-28', '09:00:00', 3, '10:00:00', '09:30:00', '10:30:00', 8, 4)");
            statement.close();
            System.out.println("ActualTripStopInfo table populated successfully.");
        } catch (SQLException e) {
            System.out.println("Error populating ActualTripStopInfo table: " + e.getMessage());
        }
    }
}
