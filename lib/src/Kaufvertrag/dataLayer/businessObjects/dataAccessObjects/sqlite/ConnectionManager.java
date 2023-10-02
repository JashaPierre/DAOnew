package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;


import Kaufvertrag.Main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
    private static final String CLASSNAME = "org.sqlite.JDBC";
    private static final String CONNECTIONSTRING = "jdbc:sqlite:" + Main.PROJECTPATH + "\\sqlite.db";
    private static Connection existingConnection;
    private static boolean classLoaded;

    public static Connection getConnection(){
        if(existingConnection == null){
            existingConnection = connectToDatabase();
        }
        return existingConnection;
    }

    private static Connection connectToDatabase(){
        File dbFile = new File(Main.PROJECTPATH + "\\sqlite.db");
        Connection conn = null;
        try {
            if (dbFile.exists()) {
                Class.forName(CLASSNAME);
                conn = DriverManager.getConnection(CONNECTIONSTRING);
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

    public static void createNewConnection(File dbFile){
        try {
            try {
                boolean created = dbFile.createNewFile();
                if (created) {
                    System.out.println(dbFile.getName() + "Successfully created.");
                }
            } catch (IOException e) {
                System.err.println("Error creating SQLight Database: " + e.getMessage());
            }
            Connection conn = DriverManager.getConnection(CONNECTIONSTRING);
            Statement stmt = conn.createStatement();

            String createTableSQL =
                    "CREATE TABLE IF NOT EXISTS " +
                            "mytable (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name TEXT, age INTEGER)";
            stmt.execute(createTableSQL);
        }
        catch(SQLException e){
            System.err.println("Error connecting to SQLite database: " + e.getMessage());
        }
    }


}