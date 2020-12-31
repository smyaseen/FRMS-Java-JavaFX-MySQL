package frms.database;

import frms.model.Flight;
import frms.model.FlightSLDS;
import frms.model.Passenger;

import java.sql.*;

public class DataSource {

    // == private static fields ==

    private static DataSource dataSource = null;

    // == private final fields ==

    private final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/frms";

    // == private fields ==

    private Connection connection;

    private PreparedStatement addFlight;
    private PreparedStatement addPassenger;
    private PreparedStatement findFlights;
    private PreparedStatement loadFlights;
    private PreparedStatement loadPassengers;
    private PreparedStatement deleteFlight;
    private PreparedStatement deletePassenger;
    private PreparedStatement updateFlightSeats;
    private PreparedStatement deleteAllPassengersFromAFlight;
    private PreparedStatement updatePassengersFlightCode;
    private PreparedStatement getFlightWithFlightCode;
    // == constructors ==


    private DataSource() {
    }

    // == public static methods ==

    public static DataSource getInstance() {
        if (dataSource == null)
            dataSource = new DataSource();

        return dataSource;
    }

    // == public static fields ==

    public boolean openDb() {

        try {

            connection = DriverManager.getConnection(CONNECTION_STRING, "root", "admin");

            addFlight = connection.prepareStatement(DBConstants.QUERY_ADD_FLIGHT);
            addPassenger = connection.prepareStatement(DBConstants.QUERY_ADD_PASSENGER);
            findFlights = connection.prepareStatement(DBConstants.QUERY_FIND_FLIGHTS);
            loadFlights = connection.prepareStatement(DBConstants.QUERY_LOAD_FLIGHTS);
            loadPassengers = connection.prepareStatement(DBConstants.QUERY_LOAD_PASSENGERS);
            deleteFlight = connection.prepareStatement(DBConstants.QUERY_DELETE_FLIGHT);
            deletePassenger = connection.prepareStatement(DBConstants.QUERY_DELETE_PASSENGER);
            updateFlightSeats = connection.prepareStatement(DBConstants.QUERY_UPDATE_FLIGHT_SEATS);
            deleteAllPassengersFromAFlight =
                    connection.prepareStatement(DBConstants.QUERY_DELETE_ALL_PASSENGERS_OF_A_FLIGHT);
            updatePassengersFlightCode =
                    connection.prepareStatement(DBConstants.QUERY_UPDATE_PASSENGERS_FLIGHT_CODE);
            getFlightWithFlightCode = connection.prepareStatement(DBConstants.QUERY_GET_FLIGHT_WITH_FLIGHT_CODE);
            return true;

        } catch (Exception e) {
            System.out.println("openDb() error: " + e.getMessage());
//            e.printStackTrace();
            return false;
        }

    }

    public void closeDb() {
        try {

            if (addFlight == null)
                throw new Exception("Add Flight PS null");

            else if (addPassenger == null)
                throw new Exception("Add Passenger PS null");

            else if (loadFlights == null)
                throw new Exception("load flights PS null");

            else if (loadPassengers == null)
                throw new Exception("load passengers PS null");

            else if (findFlights == null)
                throw new Exception("find flights PS null");

            else if (deleteFlight == null)
                throw new Exception("delete flight PS null");

            else if (deletePassenger == null)
                throw new Exception("delete passenger PS null");

            else if (updateFlightSeats == null)
                throw new Exception("update passenger PS null");

            else if (deleteAllPassengersFromAFlight == null)
                throw new Exception("delete all passenger PS null");

            else if (updatePassengersFlightCode == null)
                throw new Exception("update passenger code PS null");

            else if (getFlightWithFlightCode == null)
                throw new Exception("getFlightWithFlightCode PS null");


            addFlight.close();
            addPassenger.close();
            loadFlights.close();
            loadPassengers.close();
            findFlights.close();
            deleteFlight.close();
            deletePassenger.close();
            updateFlightSeats.close();
            deleteAllPassengersFromAFlight.close();
            updatePassengersFlightCode.close();
            getFlightWithFlightCode.close();

            connection.close();

        } catch (Exception e) {
            System.out.println("closeDb() error:" + e.getMessage());
//            e.printStackTrace();
        }
    }

    public boolean addFlight(Flight flight) {

        try {

            addFlight.setString(DBConstants.INDEX_FLIGHTS_AIRLINE_NAME, flight.getAirlineName());
            addFlight.setString(DBConstants.INDEX_FLIGHTS_FLIGHT_CODE, flight.getFlightCode());
            addFlight.setString(DBConstants.INDEX_FLIGHTS_ORIGIN, flight.getOrigin());
            addFlight.setString(DBConstants.INDEX_FLIGHTS_DESTINATION, flight.getDestination());
            addFlight.setString(DBConstants.INDEX_FLIGHTS_ARRIVAL_TIME, flight.getArrivalTime());
            addFlight.setString(DBConstants.INDEX_FLIGHTS_DEPARTURE_TIME, flight.getDepartureTime());
            addFlight.setInt(DBConstants.INDEX_FLIGHTS_TOTAL_SEATS, flight.getTotalSeats());
            addFlight.setInt(DBConstants.INDEX_FLIGHTS_SEATS_REMAINING, flight.getSeatsRemaining());
            addFlight.setInt(DBConstants.INDEX_FLIGHTS_SEATS_OCCUPIED, flight.getSeatsOccupied());
            addFlight.setDouble(DBConstants.INDEX_FLIGHTS_FARE, flight.getFare());
            addFlight.setString(DBConstants.INDEX_FLIGHTS_DATE, flight.getDate());


            int affectedRows = addFlight.executeUpdate();

            if (affectedRows != 1)
                throw new SQLException("Couldn't add user");

            return true;

        } catch (Exception e) {
            System.out.println("addFlight() exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public boolean loadAllFlights() {

        try {

            loadFlights.execute(DBConstants.QUERY_LOAD_FLIGHTS);
            ResultSet resultSet = loadFlights.executeQuery();

            while (resultSet.next()) {

                String airlineName = resultSet.getString(DBConstants.INDEX_FLIGHTS_AIRLINE_NAME);
                String flightCode = resultSet.getString(DBConstants.INDEX_FLIGHTS_FLIGHT_CODE);
                String origin = resultSet.getString(DBConstants.INDEX_FLIGHTS_ORIGIN);
                String destination = resultSet.getString(DBConstants.INDEX_FLIGHTS_DESTINATION);
                String arrivalTime = resultSet.getString(DBConstants.INDEX_FLIGHTS_ARRIVAL_TIME);
                String destinationTime = resultSet.getString(DBConstants.INDEX_FLIGHTS_DEPARTURE_TIME);
                int totalSeats = resultSet.getInt(DBConstants.INDEX_FLIGHTS_TOTAL_SEATS);
                int seatsRemaining = resultSet.getInt(DBConstants.INDEX_FLIGHTS_SEATS_REMAINING);
                int seatsOccupied = resultSet.getInt(DBConstants.INDEX_FLIGHTS_SEATS_OCCUPIED);
                double fare = resultSet.getDouble(DBConstants.INDEX_FLIGHTS_FARE);
                String date = resultSet.getString(DBConstants.INDEX_FLIGHTS_DATE);

                Flight flight = new Flight(airlineName,flightCode,origin,destination,arrivalTime,destinationTime
                ,totalSeats,fare,date);

                flight.setSeatsRemaining(- (totalSeats-seatsRemaining) );
                flight.setSeatsOccupied(seatsOccupied);

                FlightSLDS.getInstance().addFlight(flight);
            }

            return true;

        } catch (Exception e) {
            System.out.println("loadAllFlights error : " + e.getMessage());
            return false;
        }

    }

    public void addPassenger(String flightCode, Passenger passenger) throws Exception {


            addPassenger.setString(DBConstants.INDEX_PASSENGERS_FLIGHT_CODE, flightCode);
            addPassenger.setString(DBConstants.INDEX_PASSENGERS_NAME, passenger.getName());
            addPassenger.setInt(DBConstants.INDEX_PASSENGERS_AGE, passenger.getAge());
            addPassenger.setString(DBConstants.INDEX_PASSENGERS_NATIONALITY, passenger.getNationality());
            addPassenger.setString(DBConstants.INDEX_PASSENGERS_ID_NO, passenger.getIdNo());
            addPassenger.setString(DBConstants.INDEX_PASSENGERS_PASSPORT_NO, passenger.getPassportNo());
            addPassenger.setBoolean(DBConstants.INDEX_PASSENGERS_NEED_SPECIAL_ASSISTANCE, passenger.isNeedSpecialAssistance());

            int affectedRows = addPassenger.executeUpdate();

            if (affectedRows != 1)
                throw new SQLException("Couldn't add passenger");

    }

    public void loadAllPassengers() {

        if (FlightSLDS.getInstance().isEmpty())
            return;

        try {

            loadPassengers.execute(DBConstants.QUERY_LOAD_PASSENGERS);
            ResultSet resultSet = loadPassengers.executeQuery();

            while (resultSet.next()) {

                String flightCode = resultSet.getString(DBConstants.INDEX_PASSENGERS_FLIGHT_CODE);
                String name = resultSet.getString(DBConstants.INDEX_PASSENGERS_NAME);
                int age = resultSet.getInt(DBConstants.INDEX_PASSENGERS_AGE);
                String nationality = resultSet.getString(DBConstants.INDEX_PASSENGERS_NATIONALITY);
                String idNo = resultSet.getString(DBConstants.INDEX_PASSENGERS_ID_NO);
                String passportNo = resultSet.getString(DBConstants.INDEX_PASSENGERS_PASSPORT_NO);

                Passenger passenger = new Passenger(name,age,nationality,idNo,passportNo);

                for (Flight i = FlightSLDS.getInstance().getHead(); i != null; i = i.getNext()) {

                    if (i.getFlightCode().equals(flightCode)) {
                        i.getPassengers().insertPassenger(passenger);

                        break;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("loadPassengers error : " + e.getMessage());
        }
    }

    public void updateFlightSeats(String flightCode,int seatsOccupied,int seatsRemaining) throws Exception {

            updateFlightSeats.setInt(1,seatsOccupied);
            updateFlightSeats.setInt(2,seatsRemaining);
            updateFlightSeats.setString(3,flightCode);

            updateFlightSeats.executeUpdate();

    }

    public boolean deleteFlight(String flightCode,boolean deletePassengersAlso) {

        try {

            deleteFlight.setString(1,flightCode);

            int rowsAffected = deleteFlight.executeUpdate();

            if (deletePassengersAlso)
            deleteAllPassengersFromAFlight.executeUpdate();

            if (rowsAffected != 1)
                throw new Exception("delete error!");

            return true;

        } catch (Exception e) {
            System.out.println("delete flight exception: " + e.getMessage());
            return false;
        }

    }

    public void deletePassenger(String flightCode,String idNo) throws Exception {

            deletePassenger.setString(1,flightCode);
            deletePassenger.setString(2,idNo);

            int rowsAffected = deletePassenger.executeUpdate();

            if (rowsAffected != 1)
                throw new Exception("delete passenger error!");

    }

    public void updatePassengersFlightCode(String oldFlightCode,String newFlightCode) throws Exception {

        updatePassengersFlightCode.setString(1,newFlightCode);
        updatePassengersFlightCode.setString(2,oldFlightCode);


    }

}
