package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.dataLayer.businessObjects.Adresse;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.UIManager;
import Kaufvertrag.exceptions.DaoException;

import java.util.List;

public class VertragspartnerDaoXml implements IDao<IVertragspartner,String> {

    @Override
    public IVertragspartner create() {
        UIManager ui = UIManager.getInstance();
        System.out.println("Wie lautet der Vorname des Vertragspartners?");
        String vorname = ui.getScanner().next();
        System.out.println("Wie lautet der Nachname des Vertragspartners?");
        String nachname = ui.getScanner().next();
        Vertragspartner partner = new Vertragspartner(vorname, nachname);

        UIManager.AnswerOption<Object> jaAt = ui.new AnswerOption<>(() -> {partner.setAusweisNr(""); return null;}, "Ja");
        UIManager.AnswerOption<Object> neinAt = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie dem Vertragspartner eine Ausweisnummer geben?", jaAt, neinAt);
        jaAt = ui.new AnswerOption<>(() -> {
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
        neinAt = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie dem Vertragspartner eine Adresse zuordnen?", jaAt, neinAt);
        return partner;
    }

    @Override
    public void create(IVertragspartner objectToInsert) throws DaoException {
        //PLACEHOLDER
    }

    @Override
    public IVertragspartner read(String id) throws DaoException {
        return null; //PLACEHOLDER
    }

    @Override
    public List readAll() throws DaoException {
        return null; //PLACEHOLDER
    }

    @Override
    public void update(IVertragspartner objectToUpdate) throws DaoException {
        //PLACEHOLDER
    }

    @Override
    public void delete(String id) throws DaoException {
        //PLACEHOLDER
    }


}
