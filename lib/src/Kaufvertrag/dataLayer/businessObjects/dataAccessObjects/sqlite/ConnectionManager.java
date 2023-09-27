package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;


import java.sql.*;

public class ConnectionManager {

    final private static String className = "org.sqlite.JDBC";
    final private static String url = "jdbc:sqlite:sqlite.db";
//    private final File databaseFile = new File("my_database.db"); // Adjust the file name
//    boolean databaseExists = databaseFile.exists();
    private static Connection connection;

    public Connection connectToDatabase(){
        try {
            // Register the SQLite JDBC driver
            Class.forName(className);
            // Create a connection to the database or open the existing one
            return connection = DriverManager.getConnection(url);

           /* if (!databaseExists) {
                connection.close();
                System.out.println("Neue Database erstellt und initialisiert.");
            }*/
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createNewDatabase (Connection connection){

    }


    public Connection getNewConnection() {

        // Datenbankklasse dynamisch erzeugen.
        /*try {
            loadClass();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }*/

        // Verbindung initialisieren.
        String datei = "sqlite.db";  //Dateiname inklusive Pfad.
        String url = "jdbc:sqlite:sqlite.db" + datei;
        // Verbindung aufbauen.
        try {
            connection = DriverManager.getConnection(url);
            String createAdresseTableSQL = "CREATE TABLE IF NOT EXISTS Adresse (" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Strasse TEXT," +
                    "Stadt TEXT," +
                    "Plz TEXT" +
                    ")";
            connection.createStatement().execute(createAdresseTableSQL);
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
            connection.createStatement().execute(createVertragspartnerTableSQL);
            }  catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return connection;
    }

   /* public Connection getExistingConnection() {
        if(connection != null) return connection;
        else {
            try {
                return getNewConnection();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }*/

    public void close(ResultSet resultSet, Statement statement, Connection connection){
        resultSet = null;
        statement = null;
        connection = null;
    }


}