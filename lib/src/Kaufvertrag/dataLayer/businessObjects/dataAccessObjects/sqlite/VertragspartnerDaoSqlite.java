package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;

import Kaufvertrag.Main;
import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.dataLayer.businessObjects.Adresse;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.UIManager;
import Kaufvertrag.exceptions.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class VertragspartnerDaoSqlite implements IDao<IVertragspartner, String> { //calls connectManager


    public IVertragspartner creat() {
        UIManager ui = UIManager.getInstance();

        System.out.println("Wie lautet der Vorname des Vertragspartners?");
        String vorname = ui.getScanner().next();
        System.out.println("Wie lautet der Nachname des Vertragspartners?");
        String nachname = ui.getScanner().next();
        Vertragspartner vertragspartner = new Vertragspartner(vorname, nachname);

        UIManager.AnswerOption<Object>  jaAt = ui.new AnswerOption<>(() -> {
          vertragspartner.setAusweisNr("");
          return null;
        }, "Ja");
        UIManager.AnswerOption<Object> neinAt = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie dem Vertragspartner eine Ausweisnummer zuweisen?", jaAt, neinAt);
        jaAt = ui.new AnswerOption<>(() -> {

            String strasse = ui.returnInput(
                    "Geben Sie einen Straßennamen ein.",
                    "^[-\\p{L}\\s]*$",
                    "Kein gültiges format für einen Straßennamen."
            );
            //Connection mit datenbank
            //Insert into Datenbankt Srtring Strasse

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
            return null;
        },"Ja");
        neinAt = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie dem Vertragspartner eine Adresse zuordnen?", jaAt, neinAt);
        return vertragspartner;

        //Connection connection = new ConnectionManager().getNewConnection();
        //String insertSQL = "INSERT INTO Vertragspartner (Vorname, Nachname, Ausweisnr, Adresse_ID) VALUES ()";
        //return null;
    }


    @Override
    public IVertragspartner create() {
        return  null;
    }

    @Override
    public void create(IVertragspartner objectToInsert) throws DaoException {
        //iware
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