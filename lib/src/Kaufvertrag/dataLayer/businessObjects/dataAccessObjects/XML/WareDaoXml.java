package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.Ware;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.UIManager;
import Kaufvertrag.exceptions.DaoException;

import java.util.List;

/**
 * Soll IWare später benutzen.
 * */
public class WareDaoXml implements IDao<IWare, Long> {
    @Override
    public IWare create() {
        UIManager ui = UIManager.getInstance();
        Ware ware = null;
        String bezeichnung = ui.returnInput("Geben Sie eine Bezeichnung der Ware ein");
        while(true){
            String preis = ui.returnInput("Geben Sie eine Bezeichnung der Ware ein",
                    "^\\d+(,\\d{1,2})?€$",
                    "Keine gültige Eingabe für einen Preis.");
            try{
                ware = new Ware(bezeichnung, Double.parseDouble(preis));
                break;
            }catch (NumberFormatException e){
                System.out.println("Keine gültige Eingabe für einen Preis.");
            }
            UIManager.AnswerOption<Object> jaA = ui.new AnswerOption<>(() ->
               ui.returnInput("Geben Sie eine Beschreibung ein.")
               , "Ja");
            UIManager.AnswerOption<Object> neinA = ui.new AnswerOption<>(null, "Nein");
            String beschreibung = (String) ui.ConsoleOptions("Möchten Sie der Ware eine Beschreibung hinzufügen?", jaA, neinA);
            if(beschreibung != null){
                ware.setBeschreibung(beschreibung);
            }
        }


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
