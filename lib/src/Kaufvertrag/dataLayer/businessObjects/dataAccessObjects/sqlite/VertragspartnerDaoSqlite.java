package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;

import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.exceptions.DaoException;
import org.jdom2.Element;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class VertragspartnerDaoSqlite implements IDao<IVertragspartner, String> { //calls connectManager

    @Override
    public IVertragspartner create() {
        ConsoleManager ui = ConsoleManager.getInstance();
        String vorname = ui.returnInput(
                "Wie lautet der Vorname des Vertragspartners?"
        );
        String nachname = ui.returnInput(
                "Wie lautet der Nachname des Vertragspartners?"
        );
        return new Vertragspartner(vorname, nachname);
    }

    @Override
    public void create(IVertragspartner objectToInsert) throws DaoException {
        String sql = "SELECT * FROM Vertragspartner WHERE id = ?";
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            // Verbindung zur SQLite-Datenbank herstellen
            connection = ConnectionManager.getConnection();

            // SQL-Statement zum Einfügen der Daten
            String insertSQL
                    = "INSERT INTO Vertragspartner (ausweisNr, vorname, nachname, strasse, hausNr, plz, ort) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";


            // PreparedStatement erstellen
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, objectToInsert.getAusweisNr());
            preparedStatement.setString(2, objectToInsert.getVorname());
            preparedStatement.setString(3, objectToInsert.getNachname());
            if(objectToInsert.getAdresse() != null) {
                preparedStatement.setString(4, objectToInsert.getAdresse().getStrasse());
                preparedStatement.setString(5, objectToInsert.getAdresse().getHausNr());
                preparedStatement.setString(6, objectToInsert.getAdresse().getPlz());
                preparedStatement.setString(7, objectToInsert.getAdresse().getOrt());
            }

            // Daten in die Datenbank einfügen
            preparedStatement.executeUpdate();
            System.out.println("Daten erfolgreich in die Datenbank eingefügt.");
        } catch (SQLException /*| ClassNotFoundException */e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IVertragspartner read(String id) throws DaoException {
        Connection connection = ConnectionManager.getConnection();
        return null;
        //select auf tabelle genau eins -> Ergibt keinen Sinn dies zu Implementieren.
    }

    @Override
    public List<IVertragspartner> readAll() throws DaoException {
        Connection connection = ConnectionManager.getConnection();
        String readSql = "SELECT * FROM Vertragspartner";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    @Override
    public void update(IVertragspartner objectToUpdate) throws DaoException {
        Connection connection = ConnectionManager.getConnection();
        ConsoleManager ui = ConsoleManager.getInstance();
        ui.updateVertragspartnerUI(objectToUpdate);

        String sql = "UPDATE Vertragspartner SET bezeichnung = ?, beschreibung = ?, preis = ?, besonderheiten = ?, maengel = ? WHERE id = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String id) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "DELETE FROM Vertragspartner WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public IVertragspartner parseSQLtoPartner(Element partnerNode){
        return null;
    }
}