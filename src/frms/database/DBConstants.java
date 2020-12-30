package frms.database;

public class DBConstants {

    // == database names ==

    public static final String DATABASE_FRMS = "frms";

    // == tables ==

    protected static final String TABLE_FLIGHTS = "flights";
    protected static final String TABLE_PASSENGERS = "passengers";

    // == columns ==

    protected static final String COLUMN_FLIGHTS_AIRLINE_NAME = "airline_name";
    protected static final String COLUMN_FLIGHTS_FLIGHT_CODE = "flight_code";
    protected static final String COLUMN_FLIGHTS_ORIGIN = "origin";
    protected static final String COLUMN_FLIGHTS_DESTINATION = "destination";
    protected static final String COLUMN_FLIGHTS_ARRIVAL_TIME = "arrival_time";
    protected static final String COLUMN_FLIGHTS_DEPARTURE_TIME = "departure_time";
    protected static final String COLUMN_FLIGHTS_TOTAL_SEATS = "total_seats";
    protected static final String COLUMN_FLIGHTS_SEATS_REMAINING = "seats_remaining";
    protected static final String COLUMN_FLIGHTS_SEATS_OCCUPIED = "seats_occupied";
    protected static final String COLUMN_FLIGHTS_FARE = "fare";
    protected static final String COLUMN_FLIGHTS_DATE = "date";

    protected static final int INDEX_FLIGHTS_AIRLINE_NAME = 1;
    protected static final int INDEX_FLIGHTS_FLIGHT_CODE = 2;
    protected static final int INDEX_FLIGHTS_ORIGIN = 3;
    protected static final int INDEX_FLIGHTS_DESTINATION = 4;
    protected static final int INDEX_FLIGHTS_ARRIVAL_TIME = 5;
    protected static final int INDEX_FLIGHTS_DEPARTURE_TIME = 6;
    protected static final int INDEX_FLIGHTS_TOTAL_SEATS = 7;
    protected static final int INDEX_FLIGHTS_SEATS_REMAINING = 8;
    protected static final int INDEX_FLIGHTS_SEATS_OCCUPIED = 9;
    protected static final int INDEX_FLIGHTS_FARE = 10;
    protected static final int INDEX_FLIGHTS_DATE = 11;

    protected static final String COLUMN_PASSENGERS_FLIGHT_CODE = "flight_code";
    protected static final String COLUMN_PASSENGERS_NAME = "passengers_name";
    protected static final String COLUMN_PASSENGERS_AGE = "age";
    protected static final String COLUMN_PASSENGERS_DATE_OF_BIRTH = "birth_of_birth";
    protected static final String COLUMN_PASSENGERS_NATIONALITY = "nationality";
    protected static final String COLUMN_PASSENGERS_ID_NO = "id_no";
    protected static final String COLUMN_PASSENGERS_PASSPORT_NO = "passport_no";
    protected static final String COLUMN_PASSENGERS_NEED_SPECIAL_ASSISTANCE = "need_special_assistance";

    protected static final int INDEX_PASSENGERS_FLIGHT_CODE = 1;
    protected static final int INDEX_PASSENGERS_NAME = 2;
    protected static final int INDEX_PASSENGERS_AGE = 3;
    protected static final int INDEX_PASSENGERS_DATE_OF_BIRTH = 4;
    protected static final int INDEX_PASSENGERS_NATIONALITY = 5;
    protected static final int INDEX_PASSENGERS_ID_NO = 6;
    protected static final int INDEX_PASSENGERS_PASSPORT_NO = 7;
    protected static final int INDEX_PASSENGERS_NEED_SPECIAL_ASSISTANCE = 8;

    // == queries ==

    protected static final String QUERY_ADD_FLIGHT = "INSERT INTO " + DATABASE_FRMS + "." + TABLE_FLIGHTS + " VALUES(?,?,?,?,?,?,?,?,?,?,?)";

    protected static final String QUERY_ADD_PASSENGER = "INSERT INTO " + DATABASE_FRMS + "." + TABLE_PASSENGERS + " VALUES(?,?,?,?,?,?,?,?)";

    protected static final String QUERY_FIND_FLIGHTS = "SELECT * FROM " + DATABASE_FRMS + "." + TABLE_FLIGHTS + " WHERE " +
            COLUMN_FLIGHTS_ORIGIN + " = ?" + " AND " + COLUMN_FLIGHTS_DESTINATION + " = ?";

    protected static final String QUERY_LOAD_FLIGHTS = "SELECT * FROM " + DATABASE_FRMS + "." + TABLE_FLIGHTS;

    protected static final String QUERY_LOAD_PASSENGERS = "SELECT * FROM " + DATABASE_FRMS + "." + TABLE_PASSENGERS;

    protected static final String QUERY_DELETE_FLIGHT = "DELETE FROM " + DATABASE_FRMS + "." + TABLE_FLIGHTS + " WHERE "
            + COLUMN_FLIGHTS_FLIGHT_CODE + " = ?";

    protected static final String QUERY_DELETE_PASSENGER = "DELETE FROM " + DATABASE_FRMS + "." + TABLE_PASSENGERS + " WHERE "
            + COLUMN_PASSENGERS_FLIGHT_CODE + " = ? AND " + COLUMN_PASSENGERS_ID_NO + " = ?";


    protected static final String QUERY_DELETE_ALL_PASSENGERS_OF_A_FLIGHT = "DELETE FROM " + DATABASE_FRMS + "." + TABLE_PASSENGERS + " WHERE "
            + COLUMN_PASSENGERS_FLIGHT_CODE + " = ?";

    protected static final String QUERY_UPDATE_FLIGHT_SEATS = "UPDATE " + DATABASE_FRMS + "." + TABLE_FLIGHTS +
            " SET " + COLUMN_FLIGHTS_SEATS_OCCUPIED  + " = ? , " + COLUMN_FLIGHTS_SEATS_REMAINING + " = ? WHERE " +
            COLUMN_FLIGHTS_FLIGHT_CODE + " = ?";

    protected static final String QUERY_UPDATE_PASSENGERS_FLIGHT_CODE = "UPDATE " + DATABASE_FRMS + "." + TABLE_PASSENGERS +
            " SET " + COLUMN_PASSENGERS_FLIGHT_CODE  + " = ?" + " WHERE " +
            COLUMN_PASSENGERS_FLIGHT_CODE + " = ?";

    protected static final String QUERY_GET_FLIGHT_WITH_FLIGHT_CODE = "SELECT * FROM " + DATABASE_FRMS + "." + TABLE_FLIGHTS +
            " WHERE " + COLUMN_FLIGHTS_FLIGHT_CODE + " = ?";

}
