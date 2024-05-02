package lab4;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class TripDAO {
    private final Connection connection;

    public TripDAO(Connection connection) {
        this.connection = connection;
    }

    public void displaySchedule() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Start Location Name: ");
        String startLoc = scanner.nextLine().trim();
        System.out.print("Destination Name: ");
        String destLoc = scanner.nextLine().trim();
        System.out.print("Date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();

        String query = "SELECT TO.\"ScheduledStartTime\", TO.\"ScheduledArrivalTime\", TO.\"DriverName\", TO.\"BusID\" " +
                       "FROM \"tripoffering\" TO " +
                       "JOIN \"trip\" T ON TO.\"TripNumber\" = T.\"TripNumber\" " +
                       "WHERE T.\"StartLocationName\" = ? AND T.\"DestinationName\" = ? AND TO.\"Date\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, startLoc);
            statement.setString(2, destLoc);
            statement.setDate(3, Date.valueOf(date));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Scheduled Start Time: " + resultSet.getTime("ScheduledStartTime"));
                System.out.println("Scheduled Arrival Time: " + resultSet.getTime("ScheduledArrivalTime"));
                System.out.println("Driver Name: " + resultSet.getString("DriverName"));
                System.out.println("Bus ID: " + resultSet.getInt("BusID"));
                System.out.println("---------------------------------------");
            }
        }
    }

    public void editSchedule() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select operation:");
        System.out.println("1. Delete a trip offering");
        System.out.println("2. Add trip offerings");
        System.out.println("3. Change driver");
        System.out.println("4. Change bus");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                deleteTripOffering();
                break;
            case 2:
                addTripOffering();
                break;
            case 3:
                changeDriver();
                break;
            case 4:
                changeBus();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void deleteTripOffering() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Trip Number: ");
        int tripNo = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();
        System.out.print("Scheduled Start Time: ");
        String startTime = scanner.nextLine().trim();

        String query = "DELETE FROM \"tripoffering\" WHERE \"TripNumber\" = ? AND \"Date\" = ? AND \"ScheduledStartTime\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tripNo);
            statement.setDate(2, Date.valueOf(date));
            statement.setTime(3, Time.valueOf(startTime));

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Trip offering deleted successfully");
            } else {
                System.out.println("No trip offering found with the specified details");
            }
        }
    }

    private void addTripOffering() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean addMore = true;

        while (addMore) {
            System.out.print("Trip Number: ");
            int tripNo = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Date (YYYY-MM-DD): ");
            String date = scanner.nextLine().trim();
            System.out.print("Scheduled Start Time: ");
            String startTime = scanner.nextLine().trim();
            System.out.print("Scheduled Arrival Time: ");
            String arrivalTime = scanner.nextLine().trim();
            System.out.print("Driver Name: ");
            String driver = scanner.nextLine().trim();
            System.out.print("Bus ID: ");
            int busID = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String query = "INSERT INTO \"tripoffering\" VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, tripNo);
                statement.setDate(2, Date.valueOf(date));
                statement.setTime(3, Time.valueOf(startTime));
                statement.setTime(4, Time.valueOf(arrivalTime));
                statement.setString(5, driver);
                statement.setInt(6, busID);

                statement.executeUpdate();
                System.out.println("Trip offering added successfully");

                System.out.print("Do you want to add another trip offering? (yes/no): ");
                String choice = scanner.nextLine().trim();
                addMore = choice.equalsIgnoreCase("yes");
            }
        }
    }

    private void changeDriver() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Trip Number: ");
        int tripNo = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();
        System.out.print("Scheduled Start Time: ");
        String startTime = scanner.nextLine().trim();
        System.out.print("New Driver Name: ");
        String newDriver = scanner.nextLine().trim();

        String query = "UPDATE \"tripoffering\" SET \"DriverName\" = ? WHERE \"TripNumber\" = ? AND \"Date\" = ? AND \"ScheduledStartTime\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newDriver);
            statement.setInt(2, tripNo);
            statement.setDate(3, Date.valueOf(date));
            statement.setTime(4, Time.valueOf(startTime));

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Driver updated successfully");
            } else {
                System.out.println("No trip offering found with the specified details");
            }
        }
    }

    private void changeBus() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Trip Number: ");
        int tripNo = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();
        System.out.print("Scheduled Start Time: ");
        String startTime = scanner.nextLine().trim();
        System.out.print("New Bus ID: ");
        int newBusID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String query = "UPDATE \"tripoffering\" SET \"BusID\" = ? WHERE \"TripNumber\" = ? AND \"Date\" = ? AND \"ScheduledStartTime\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, newBusID);
            statement.setInt(2, tripNo);
            statement.setDate(3, Date.valueOf(date));
            statement.setTime(4, Time.valueOf(startTime));

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Bus updated successfully");
            } else {
                System.out.println("No trip offering found with the specified details");
            }
        }
    }

    public void displayStops() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Trip Number: ");
        int tripNo = scanner.nextInt();

        String query = "SELECT * FROM \"tripstopinfo\" WHERE \"TripNumber\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tripNo);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Stop Number: " + resultSet.getInt("StopNumber"));
                System.out.println("Stop Name: " + resultSet.getString("StopName"));
                System.out.println("Sequence Number: " + resultSet.getInt("SequenceNumber"));
                System.out.println("---------------------------------------");
            }
        }
    }

    public void displayWeeklySchedule() throws ParseException, SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Driver Name: ");
        String driver = scanner.nextLine().trim();
        System.out.print("Date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine().trim();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date = new GregorianCalendar();
        date.setTime(df.parse(dateStr));

        for (int i = 0; i < 7; i++) {
            dateStr = df.format(date.getTime());

            String query = "SELECT \"TripNumber\", \"Date\", \"ScheduledStartTime\", \"ScheduledArrivalTime\", \"BusID\" " +
                           "FROM \"tripoffering\" " +
                           "WHERE \"DriverName\" LIKE ? AND \"Date\" = ? " +
                           "ORDER BY \"ScheduledStartTime\"";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, driver);
                statement.setDate(2, Date.valueOf(dateStr));

                ResultSet resultSet = statement.executeQuery();
                if (i == 0) {
                    System.out.println("----------------------Day 1----------------------------");
                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    int colCount = rsmd.getColumnCount();
                    for (int j = 1; j <= colCount; j++) {
                        if (j == 1 || j == 3)
                            System.out.print(rsmd.getColumnName(j) + "\t");
                        else
                            System.out.print(rsmd.getColumnName(j) + "\t\t");
                    }
                    System.out.println();
                }

                while (resultSet.next()) {
                    System.out.print(resultSet.getInt("TripNumber") + "\t\t");
                    System.out.print(resultSet.getDate("Date") + "\t\t");
                    System.out.print(resultSet.getTime("ScheduledStartTime") + "\t\t");
                    System.out.print(resultSet.getTime("ScheduledArrivalTime") + "\t\t");
                    System.out.print(resultSet.getInt("BusID") + "\t\t");
                    System.out.println();
                }
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("Error executing query: " + e.getMessage());
            }

            date.add(Calendar.DATE, 1);
            if (i < 6)
                System.out.println("----------------------Day " + (i + 2) + "----------------------------");
        }
        System.out.println("------------------------------------------------------");
    }

    public void addDriver() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Driver Name: ");
        String driverName = scanner.nextLine().trim();
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine().trim();

        String query = "INSERT INTO \"driver\" (\"DriverName\", \"PhoneNumber\") VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, driverName);
            statement.setString(2, phoneNumber);

            statement.executeUpdate();
            System.out.println("Driver added successfully");
        }
    }

    public void addBus() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Bus ID: ");
        int busID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Bus Model: ");
        String busModel = scanner.nextLine().trim();
        System.out.print("Bus Year: ");
        int busYear = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String query = "INSERT INTO \"bus\" (\"BusID\", \"BusModel\", \"BusYear\") VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, busID);
            statement.setString(2, busModel);
            statement.setInt(3, busYear);

            statement.executeUpdate();
            System.out.println("Bus added successfully");
        }
    }

    public void deleteBus() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Bus ID: ");
        int busID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String query = "DELETE FROM \"bus\" WHERE \"BusID\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, busID);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Bus deleted successfully");
            } else {
                System.out.println("No bus found with the specified ID");
            }
        }
    }

    public void recordActualData() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Trip Number: ");
        int tripNo = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();
        System.out.print("Scheduled Start Time: ");
        String startTime = scanner.nextLine().trim();
        System.out.print("Stop Number: ");
        int stopNo = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Scheduled Arrival Time: ");
        String arrivalTime = scanner.nextLine().trim();
        System.out.print("Actual Start Time: ");
        String actualStartTime = scanner.nextLine().trim();
        System.out.print("Actual Arrival Time: ");
        String actualArrivalTime = scanner.nextLine().trim();
        System.out.print("Passengers In: ");
        int passengersIn = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Passengers Out: ");
        int passengersOut = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String query = "INSERT INTO \"actualtripstopinfo\" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tripNo);
            statement.setDate(2, Date.valueOf(date));
            statement.setTime(3, Time.valueOf(startTime));
            statement.setInt(4, stopNo);
            statement.setTime(5, Time.valueOf(arrivalTime));
            statement.setTime(6, Time.valueOf(actualStartTime));
            statement.setTime(7, Time.valueOf(actualArrivalTime));
            statement.setInt(8, passengersIn);
            statement.setInt(9, passengersOut);

            statement.executeUpdate();
            System.out.println("Actual data recorded successfully");
        }
    }
}
