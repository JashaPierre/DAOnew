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
    ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public IVertragspartner create() {



        //erstellt den Vertragspartner
        ConsoleManager ui = ConsoleManager.getInstance();

        System.out.println("Wie lautet der Vorname des Vertragspartners?");
        String vorname = ui.getScanner().next();
        System.out.println("Wie lautet der Nachname des Vertragspartners?");
        String nachname = ui.getScanner().next();
        Vertragspartner vertragspartner = new Vertragspartner(vorname, nachname);

        ConsoleManager.AnswerOption<Object> jaAt = ui.new AnswerOption<>(() -> {
            vertragspartner.setAusweisNr("");
            return null;
        }, "Ja");
        ConsoleManager.AnswerOption<Object> neinAt = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie dem Vertragspartner eine Ausweisnummer zuweisen?", jaAt, neinAt);
        jaAt = ui.new AnswerOption<>(() -> {

            String strasse = ui.returnInput(
                    "Geben Sie einen Straßennamen ein.",
                    "^[-\\p{L}\\s]*$",
                    "Kein gültiges format für einen Straßennamen."
            );
            String hausNr = ui.returnInput(
                    "Geben Sie einen Hausnummer ein.",
                    "\\b\\d+\\S*\\b",
                    "Kein gültiges format für eine Hausnummer."
            );
            String plz = ui.returnInput(
                    "Geben Sie einen Postleitzahl ein.",
                    "\\b\\d{5}\\b",
                    "Kein gültiges format für eine Postleitzahl."
            );
            String ort = ui.returnInput(
                    "Geben Sie einen Ort ein.",
                    "\\b\\w+\\b",
                    "Kein gültiges format für einen Ort."
            );
            vertragspartner.setAdresse(new Adresse(strasse, hausNr, plz, ort));
            return vertragspartner; //changed 21:23
        }, "Ja");
        neinAt = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie dem Vertragspartner eine Adresse zuordnen?", jaAt, neinAt);

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Verbindung zur SQLite-Datenbank herstellen
            connection = connectionManager.getNewConnection();

            // SQL-Statement zum Einfügen der Daten
            String insertSQL = "INSERT INTO Vertragspartner (ausweisNr, vorname, nachname, strasse, hausNr, plz, ort) VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Daten aus deinem Objekt extrahieren
            int ausweisNr = 123; // Zum Beispiel
            vertragspartner.getVorname();
            vertragspartner.getNachname();
            vertragspartner.getAdresse().getStrasse();
            vertragspartner.getAdresse().getHausNr();
            vertragspartner.getAdresse().getPlz();
            vertragspartner.getAdresse().getOrt();

            // PreparedStatement erstellen
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, ausweisNr);
            preparedStatement.setString(2, vorname);
            preparedStatement.setString(3, nachname);
            preparedStatement.setString(4, vertragspartner.getAdresse().getStrasse());
            preparedStatement.setString(5, vertragspartner.getAdresse().getHausNr());
            preparedStatement.setString(6, vertragspartner.getAdresse().getPlz());
            preparedStatement.setString(7, vertragspartner.getAdresse().getOrt());

            // Daten in die Datenbank einfügen
            preparedStatement.executeUpdate();

            System.out.println("Daten erfolgreich in die Datenbank eingefügt.");
        } catch (SQLException /*| ClassNotFoundException */e) {
            throw new RuntimeException(e);
        }





        return vertragspartner;


    }

    @Override
    public void create(IVertragspartner objectToInsert) throws DaoException {


    }

    @Override
    public IVertragspartner read(String id) throws DaoException {
        return null;
        //select auf tabelle genau eins
    }

    @Override
    public List<IVertragspartner> readAll() throws DaoException {
        return null;
        //select auf tabelle ALLE
    }

    @Override
    public void update(IVertragspartner objectToUpdate) throws DaoException {

    }

    @Override
    public void delete(String id) throws DaoException {

    }
}