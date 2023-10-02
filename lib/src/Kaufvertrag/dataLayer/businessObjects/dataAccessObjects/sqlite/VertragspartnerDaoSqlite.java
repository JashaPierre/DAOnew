package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;

import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.dataLayer.businessObjects.Adresse;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.exceptions.DaoException;
import java.sql.*;

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
        Vertragspartner partner = new Vertragspartner(vorname, nachname);

        ConsoleManager.AnswerOption<Object> jaA = ui.new AnswerOption<>(() -> {
            partner.setAusweisNr(""); return null;}, "Ja");
        ConsoleManager.AnswerOption<Object> neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie dem Vertragspartner eine Ausweisnummer geben?",false, jaA, neinA);
        jaA = ui.new AnswerOption<>(() -> {
            String strasse = ui.returnInput(
                    "Geben Sie einen Straßennamen ein.",
                    "^[-\\p{L}\\s]*$",
                    "Kein gültiges format für einen Straßennamen."
            );
            String hausNr  = ui.returnInput(
                    "Geben Sie einen Hausnummer ein.",
                    "\\b\\d+\\S*\\b",
                    "Kein gültiges format für eine Hausnummer."
            );
            String plz  = ui.returnInput(
                    "Geben Sie einen Postleitzahl ein.",
                    "\\b\\d{5}\\b",
                    "Kein gültiges format für eine Postleitzahl."
            );
            String ort = ui.returnInput(
                    "Geben Sie einen Ort ein.",
                    "\\b\\w+\\b",
                    "Kein gültiges format für einen Ort."
            );
            partner.setAdresse(new Adresse(strasse, hausNr, plz , ort));
            return null;
        }, "Ja");
        neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie dem Vertragspartner eine Adresse zuordnen?",false, jaA, neinA);
        return partner;
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
        return null;
        //select auf tabelle genau eins -> Ergibt keinen Sinn dies zu Implementieren.
    }

    @Override
    public List<IVertragspartner> readAll() throws DaoException {
       /* PreparedStatement preparedStatement = null;
        Connection connection = connectionManager.getExistingConnection();
        String readSql = "SELECT * FROM Vertragspartner";
        try {
            preparedStatement = connection.prepareStatement(readSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
        return null;

    }

    @Override
    public void update(IVertragspartner objectToUpdate) throws DaoException {

       /* Connection connection;
        try {
            connection = connectionManager.getNewConnection();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String sql = "UPDATE Vertragspartner SET bezeichnung = ?, beschreibung = ?, preis = ?, besonderheiten = ?, maengel = ? WHERE id = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
    }

    @Override
    public void delete(String id) throws DaoException {
        /*try (Connection connection = connectionManager.getExistingConnection()) {
            String sql = "DELETE FROM Vertragspartner WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
}