package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.Main;
import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.dataLayer.businessObjects.Adresse;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.DataLayerManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.exceptions.DaoException;

import java.util.List;

public class VertragspartnerDaoXml implements IDao<IVertragspartner,String> {

    @Override
    public IVertragspartner create() {
        DataLayerManager dlm = DataLayerManager.getInstance();

        System.out.println("Wie lautet der Vorname des Vertragspartners?");
        String vorname = Main.sc.next();
        System.out.println("Wie lautet der Nachname des Vertragspartners?");
        String nachname = Main.sc.next();
        Vertragspartner partner = new Vertragspartner(vorname, nachname);

        DataLayerManager.AnswerOption<Object> jaAt = dlm.new AnswerOption<>(() -> {partner.setAusweisNr(""); return null;}, "Ja");
        DataLayerManager.AnswerOption<Object> neinAt = dlm.new AnswerOption<>(null, "Nein");
        DataLayerManager.ConsoleOptions("Möchten Sie dem Vertragspartner eine Ausweisnummer geben?", jaAt, neinAt);
        jaAt = dlm.new AnswerOption<>(() -> {
            String strasse = dlm.returnInput(
                    "Geben Sie einen Straßennamen ein.",
                    "\\p{L}+\\s+\\p{L}+",
                    "Kein gültiges format für eine Straßennamen."
            );
            String hausNr  = dlm.returnInput(
                    "Geben Sie einen Hausnummer ein.",
                    "\\b\\d+\\S*\\b",
                    "Kein gültiges format für eine Hausnummer."
            );
            String plz  = dlm.returnInput(
                    "Geben Sie einen Postleitzahl ein.",
                    "\\b\\d{5}\\b",
                    "Kein gültiges format für eine Postleitzahl."
            );
            String ort = dlm.returnInput(
                    "Geben Sie einen Ort ein.",
                    "\\b\\w+\\b",
                    "Kein gültiges format für einen Ort."
            );
            partner.setAdresse(new Adresse(strasse, hausNr, plz , ort));
            return null;
            }, "Ja");
        neinAt = dlm.new AnswerOption<>(null, "Nein");
        DataLayerManager.ConsoleOptions("Möchten Sie dem Vertragspartner eine Adresse zuordnen?", jaAt, neinAt);
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
