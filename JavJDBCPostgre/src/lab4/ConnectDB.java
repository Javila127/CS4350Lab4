package lab4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {
    private static final String DB_NAME = "postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "123";
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "5432";
    private static final String JDBC_URL = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    public static Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            System.out.println("Connected to the database");

            // Initialize tables
            initializeTables(connection);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
        return connection;
    }

    private static void initializeTables(Connection connection) {
        String[] createTableQueries = {
            "CREATE TABLE IF NOT EXISTS Trip (" +
            "    \"TripNumber\" SERIAL PRIMARY KEY," +
            "    \"StartLocationName\" VARCHAR(100)," +
            "    \"DestinationName\" VARCHAR(100)" +
            ");",

            "CREATE TABLE IF NOT EXISTS TripOffering (" +
            "    \"TripNumber\" INT," +
            "    \"Date\" DATE," +
            "    \"ScheduledStartTime\" TIME," +
            "    \"ScheduledArrivalTime\" TIME," +
            "    \"DriverName\" VARCHAR(100)," +
            "    \"BusID\" INT," +
            "    FOREIGN KEY (\"TripNumber\") REFERENCES Trip(\"TripNumber\")" +
            ");",

            "CREATE TABLE IF NOT EXISTS Bus (" +
            "    \"BusID\" SERIAL PRIMARY KEY," +
            "    \"Model\" VARCHAR(100)," +
            "    \"Year\" INT" +
            ");",

            "CREATE TABLE IF NOT EXISTS Driver (" +
            "    \"DriverName\" VARCHAR(100) PRIMARY KEY," +
            "    \"DriverTelephoneNumber\" VARCHAR(20)" +
            ");",

            "CREATE TABLE IF NOT EXISTS Stop (" +
            "    \"StopNumber\" SERIAL PRIMARY KEY," +
            "    \"StopAddress\" VARCHAR(100)" +
            ");",

            "CREATE TABLE IF NOT EXISTS ActualTripStopInfo (" +
            "    \"TripNumber\" INT," +
            "    \"Date\" DATE," +
            "    \"ScheduledStartTime\" TIME," +
            "    \"StopNumber\" INT," +
            "    \"ScheduledArrivalTime\" TIME," +
            "    \"ActualStartTime\" TIME," +
            "    \"ActualArrivalTime\" TIME," +
            "    \"NumberOfPassengerIn\" INT," +
            "    \"NumberOfPassengerOut\" INT," +
            "    FOREIGN KEY (\"TripNumber\") REFERENCES Trip(\"TripNumber\")," +
            "    FOREIGN KEY (\"StopNumber\") REFERENCES Stop(\"StopNumber\")" +
            ");",

            "CREATE TABLE IF NOT EXISTS TripStopInfo (" +
            "    \"TripNumber\" INT," +
            "    \"StopNumber\" INT," +
            "    \"SequenceNumber\" INT," +
            "    \"DrivingTime\" TIME," +
            "    PRIMARY KEY (\"TripNumber\", \"StopNumber\")," +
            "    FOREIGN KEY (\"TripNumber\") REFERENCES Trip(\"TripNumber\")," +
            "    FOREIGN KEY (\"StopNumber\") REFERENCES Stop(\"StopNumber\")" +
            ");"
        };

        try (Statement statement = connection.createStatement()) {
            for (String query : createTableQueries) {
                statement.executeUpdate(query);
            }
            System.out.println("Tables initialized successfully");
        } catch (SQLException e) {
            System.out.println("Error initializing tables: " + e.getMessage());
        }
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed");
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
