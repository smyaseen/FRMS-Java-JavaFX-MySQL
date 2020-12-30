module flight.demo {

    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    requires java.sql;

    opens frms;
    opens frms.model;
    opens frms.controller;
    opens frms.img;
    opens frms.view;
    opens frms.database;

}