package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;

import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.Ware;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.exceptions.DaoException;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

public class WareDaoSqlite implements IDao<IWare, Long> {
    @Override
    public IWare create() {
        ConsoleManager ui = ConsoleManager.getInstance();
        String bezeichnung = ui.returnInput("Geben Sie eine Bezeichnung der Ware ein");
        double preis;
        while (true){
            String preisString = ui.returnInput("Geben Sie einen Preis der Ware ein",
                    "^\\d+(,\\d{1,2})?$",
                    "Keine gültige Eingabe für einen Preis.");
            try{
                preis = Double.parseDouble(preisString);
                break;
            }catch (NumberFormatException e){
                System.out.println("Keine gültige Eingabe für einen Preis.");
            }
        }
        return new Ware(bezeichnung, preis);
    }

    @Override
    public void create(IWare objectToInsert) throws DaoException {
        ConsoleManager ui = ConsoleManager.getInstance();


    }

    @Override
    public IWare read(Long id) throws DaoException {

        return null;
    }

    @Override
    public List<IWare> readAll() throws DaoException {
        List<IWare> warenListe = new ArrayList<>();

        return warenListe;
    }

    @Override
    public void update(IWare objectToUpdate) throws DaoException {
        ConsoleManager ui = ConsoleManager.getInstance();
        ui.updateWareUI(objectToUpdate);

    }

    @Override
    public void delete(Long id) throws DaoException {

    }

    public IWare parseSQLtoWare(Element wareKnoten){
      return null;
    }
}
