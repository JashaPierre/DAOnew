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
        while (true){
            String preisString = ui.returnInput("Geben Sie eine Bezeichnung der Ware ein",
                    "^\\d+(,\\d{1,2})?€$",
                    "Keine gültige Eingabe für einen Preis.");
            try{
                preis = Double.parseDouble(preisString);
                break;
            }catch (NumberFormatException e){
                System.out.println("Keine gültige Eingabe für einen Preis.");
            }
        }
        Ware ware = new Ware(bezeichnung, preis);

        ConsoleManager.AnswerOption<Object> jaA = ui.new AnswerOption<>(() ->{
            String bez = ui.returnInput("Geben Sie eine Beschreibung ein.");
            ware.setBeschreibung(bez);
            return null;}, "Ja");
        ConsoleManager.AnswerOption<Object> neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie der Ware eine Beschreibung hinzufügen?", jaA, neinA);

       jaA = ui.new AnswerOption<>(() ->{
           while(true){
               String bez = ui.returnInput("Geben Sie eine Beschreibung ein.");
           }
//            ware.setBeschreibung(bez);
            return null;}, "Ja");
        neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie der Ware Besondehieten hinzufügen?", jaA, neinA);



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
