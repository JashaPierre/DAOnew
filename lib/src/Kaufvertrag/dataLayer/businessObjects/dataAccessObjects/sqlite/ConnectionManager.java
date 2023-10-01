package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;


import Kaufvertrag.Main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {

        // JDBC driver name and database URL
        private static ConnectionManager instance;
        static final String JDBC_DRIVER = "org.sqlite.JDBC";
        static final String URL = "jdbc:sqlite:" + Main.PROJECTPATH + "\\sqlite.db";
        private static Connection connection;
        private ConnectionManager(){}
        public static ConnectionManager getInstance(){
            if(instance == null){
                return instance = new ConnectionManager();
            }
            return instance;
        }

        public Connection getConnection(){
            if(connection == null){
                connection = connectToDatabase();
            }
            return  connection;
        }

        private Connection connectToDatabase(){
            File dbFile = new File(Main.PROJECTPATH + "\\sqlite.db");
            Connection conn = null;
            try {
                if (dbFile.exists()) {
                    Class.forName(JDBC_DRIVER);
                    conn = DriverManager.getConnection(URL);
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

        public void createNewConnection(File dbFile){
            try {
                try {
                    boolean created = dbFile.createNewFile();
                    if (created) {
                        System.out.println(dbFile.getName() + "Successfully created.");
                    }
                } catch (IOException e) {
                    System.err.println("Error creating SQLight Database: " + e.getMessage());
                }
                Connection conn = DriverManager.getConnection(URL);
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