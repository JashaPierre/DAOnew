package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;


import Kaufvertrag.exceptions.DaoException;

import java.sql.*;

public class ConnectionManager {

    final private static String className = "org.sqlite.JDBC";
    //final private static String connectionString = "jdbc:sqlite:sqlite.db";
    final private static String connectionString = "/Users/jashameusel/StudioProjects/LF5/DAOnew/sqlite.db";

    private static Connection existingConnection;
    private static boolean classLoaded = false;

    private void loadClass() throws DaoException {
        if (classLoaded) return;
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new DaoException(e.getMessage());
        }
        classLoaded = true;
    }
    public Connection getNewConnection() throws ClassNotFoundException {


        // Datenbankklasse dynamisch erzeugen.

        // Verbindung initialisieren.
        String datei = "sqlite.db";  //Dateiname inklusive Pfad.
        String url = "jdbc:sqlite:sqlite.db" + datei;
        // Verbindung aufbauen.
        try {
            existingConnection = DriverManager.getConnection(connectionString);
            String createAdresseTableSQL = "CREATE TABLE IF NOT EXISTS Adresse (" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Strasse TEXT," +
                    "Stadt TEXT," +
                    "Plz TEXT" +
                    ")";
            existingConnection.createStatement().execute(createAdresseTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

            try {
            String createVertragspartnerTableSQL = "CREATE TABLE IF NOT EXISTS Vertragspartner (" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Vorname TEXT," +
                    "Nachname TEXT," +
                    "Ausweisnr TEXT," +
                    "Adresse_Id INTEGER," + // Fremdschlüssel auf die "adresse" Tabelle
                    "FOREIGN KEY (Adresse_Id) REFERENCES Adresse(Id)" +
                    ")";
            existingConnection.createStatement().execute(createVertragspartnerTableSQL);
            }  catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return  existingConnection;
    }

    //TODO: prüfen ob bullshit?
    public Connection getExistingConnection() {
        if(existingConnection != null) return existingConnection;
        else {
            try {
                return getNewConnection();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void close(ResultSet resultSet, Statement statement, Connection connection){
        resultSet = null;
        statement = null;
        connection = null;
    }


}