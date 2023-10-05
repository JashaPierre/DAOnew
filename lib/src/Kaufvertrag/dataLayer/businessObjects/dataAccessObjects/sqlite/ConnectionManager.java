package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;


import Kaufvertrag.Main;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class ConnectionManager {
    private static final String CLASSNAME = "org.sqlite.JDBC";
    private static final String CONNECTIONSTRING = "jdbc:sqlite:" + Main.PROJECTPATH + "\\sqlite.db";
    private static Connection existingConnection;
    private static boolean classLoaded;

    public static Connection getConnection(){
        try {
            if (existingConnection == null || existingConnection.isClosed()) {
                existingConnection = connectToDatabase();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return existingConnection;
    }

    private static Connection connectToDatabase(){
        File dbFile = new File(Main.PROJECTPATH + "\\sqlite.db");
        Connection conn = null;
        try {
            if (dbFile.exists()) {
                if (!classLoaded) {
                    Class.forName(CLASSNAME);
                    classLoaded = true;
                }
                conn = DriverManager.getConnection(CONNECTIONSTRING);
                createTablesIfNotExists(conn);
            } else {
                createNewConnection(dbFile);
            }
        } catch(SQLException e){
            System.err.println("Error connecting to SQLite database: " + e.getMessage());
        } catch(ClassNotFoundException e){
            System.err.println("JDBC driver not found");
        }
        return conn;
    }
    private static void createNewConnection(File dbFile){
        try {
            try {
                boolean created = dbFile.createNewFile();
                if (created) {
                    System.out.println(dbFile.getName() + "Successfully created.");
                }
            } catch (IOException e) {
                System.err.println("Error creating SQLight Database: " + e.getMessage());
            }
            if (!classLoaded) {
                Class.forName(CLASSNAME);
                classLoaded = true;
            }
            Connection conn = DriverManager.getConnection(CONNECTIONSTRING);
            createTablesIfNotExists(conn);
        }
        catch(SQLException e){
            System.err.println("Error connecting to SQLite database: " + e.getMessage());
        } catch(ClassNotFoundException e){
            System.err.println("JDBC driver not found");
        }
    }

    private static void createTablesIfNotExists(Connection conn) throws SQLException {
        if(!checkIfTablesArePresent(conn)){
            try (Statement statement = conn.createStatement()) {
                statement.execute(
                "CREATE TABLE IF NOT EXISTS Vertragspartner (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Vorname  VARCHAR(255)," +
                    "Nachname VARCHAR(255)," +
                    "AusweisNr  VARCHAR(255)," +
                    "Adresse INTEGER REFERENCES Adresse(id)" +
                    ");"
                );
                statement.execute(
                "CREATE TABLE IF NOT EXISTS Adresse (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Strasse  VARCHAR(255)," +
                    "HausNr  VARCHAR(255)," +
                    "PLZ  VARCHAR(255)," +
                    "Ort  VARCHAR(255)" +
                    ");"
                );
                statement.execute(
                        "CREATE TABLE IF NOT EXISTS Ware (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "Bezeichnung  VARCHAR(255)," +
                                "Beschreibung VARCHAR(255)," +
                                "Preis  REAL" +
                                ");"
                );
                statement.execute(
                "CREATE TABLE IF NOT EXISTS Besonderheiten (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "WarenID INTEGER REFERENCES Ware(id)," +
                    "Besonderheit  VARCHAR(255)" +
                    ");"
                );
                statement.execute(
                "CREATE TABLE IF NOT EXISTS Maengel (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "WarenID INTEGER REFERENCES Ware(id)," +
                    "Mangel  VARCHAR(255)" +
                    ");"
                );
            }catch(SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static boolean checkIfTablesArePresent(Connection conn){
        try {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            int count = 0;
            while (rs.next()) {
                String tableName = rs.getString(3);
                if (tableName.equals("Vertragspartner") ||
                        tableName.equals("Ware") ||
                        tableName.equals("Adresse") ||
                        tableName.equals("Besonderheiten") ||
                        tableName.equals("Maengel")) {
                    if (count == 5) {
                        return true;
                    }
                    count++;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static void closeConnection(){
        try {
            if(existingConnection != null && !existingConnection.isClosed()){
                existingConnection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}