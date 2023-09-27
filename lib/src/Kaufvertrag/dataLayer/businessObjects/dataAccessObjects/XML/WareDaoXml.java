package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.Ware;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.exceptions.DaoException;

import java.util.List;

/**
 * Soll IWare später benutzen.
 * */
public class WareDaoXml implements IDao<IWare, Long> {
    @Override
    public IWare create() {
        ConsoleManager ui = ConsoleManager.getInstance();
        String bezeichnung = ui.returnInput("Geben Sie eine Bezeichnung der Ware ein");
        double preis;
        String preisString = ui.returnInput("Geben Sie einen Preis der Ware ein",
                "^\\d+(,\\d{1,2})?€$",
                "Keine gültige Eingabe für einen Preis.");
        preis = Double.parseDouble(preisString);
        Ware ware = new Ware(bezeichnung, preis);

        ConsoleManager.AnswerOption<Object> jaA = ui.new AnswerOption<>(() ->{
            String bez = ui.returnInput("Geben Sie eine Beschreibung ein.");
            ware.setBeschreibung(bez);
            return null;}, "Ja");
        ConsoleManager.AnswerOption<Object> neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie der Ware eine Beschreibung hinzufügen?", jaA, neinA);

       jaA = ui.new AnswerOption<>(() ->{
           boolean finished = false;
           while(!finished){
               String bes = ui.returnInput("Geben Sie eine Besonderheit an.");
               ware.getBesonderheiten().add(bes);
               ConsoleManager.AnswerOption<Object> jaA2 = ui.new AnswerOption<>(() -> false ,"ja");
               ConsoleManager.AnswerOption<Boolean> neinA2 = ui.new AnswerOption<>(() -> true ,"nein");
               finished = (Boolean) ui.ConsoleOptions("Wollen Sie der Ware eine weiter Besonderheit geben?", jaA2, neinA2);
           }
            return null;
           }, "Ja");
        neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie der Ware Besonderheiten hinzufügen?", jaA, neinA);

        jaA = ui.new AnswerOption<>(() ->{
            boolean finished = false;
            while(!finished){
                String man = ui.returnInput("Geben Sie einen Mangel an.");
                ware.getMaengel().add(man);
                ConsoleManager.AnswerOption<Object> jaA2 = ui.new AnswerOption<>(() -> false ,"ja");
                ConsoleManager.AnswerOption<Boolean> neinA2 = ui.new AnswerOption<>(() -> true ,"nein");
                finished = (Boolean) ui.ConsoleOptions("Wollen Sie der Ware eine weiter Besonderheit geben?", jaA2, neinA2);
            }
            return null;
        }, "Ja");
        neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie der Ware Mängel hinzufügen?", jaA, neinA);

        return ware;
    }

    @Override
    public void create(IWare objectToInsert) throws DaoException {

    }

    @Override
    public IWare read(Long id) throws DaoException {
        return null; //PLACEHOLDER
    }

    @Override
    public List<IWare> readAll() throws DaoException {
        return null; //PLACEHOLDER
    }

    @Override
    public void update(IWare objectToUpdate) throws DaoException {


    }

    @Override
    public void delete(Long id) throws DaoException {

    }
}
