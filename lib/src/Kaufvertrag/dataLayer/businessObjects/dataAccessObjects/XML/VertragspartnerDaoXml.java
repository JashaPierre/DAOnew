package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.Main;
import Kaufvertrag.businessObjects.IVertragspartner;
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

        DataLayerManager.AnswerOption<Object> readAt = dlm.new AnswerOption<>(() -> {

        }, "Ja");

        Object result = DataLayerManager.ConsoleOptions("Möchten Sie dem Vertragspartner eine Auswesnummer geben?", );

        return new Vertragspartner(vorname, nachname);
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
