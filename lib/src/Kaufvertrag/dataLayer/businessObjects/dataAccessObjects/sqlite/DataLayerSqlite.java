package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;

import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;

public class DataLayerSqlite implements IDataLayer {




    @Override
    public IDao<IVertragspartner, String> getDaoVertragspartner() {
        VertragspartnerDaoSqlite partnerSqlite = new VertragspartnerDaoSqlite();
        ConsoleManager ui = ConsoleManager.getInstance();

        ConsoleManager.AnswerOption<Object> createAt = ui.new AnswerOption<>(() -> {
            var partner = partnerSqlite.create();

            return  null;
        }, "Einen neuen Vertragspartner Erstellen");
        ConsoleManager.AnswerOption<Object> creatInsertAt = ui.new AnswerOption<>(() -> {
            return  null;
        }, "Einen neuen Vertragspartner Einfügen");
        ConsoleManager.AnswerOption<Object> readAt = ui.new AnswerOption<>(() -> {
            return  null;
        }, "Vorhanden Vertragspartner finden");
        ConsoleManager.AnswerOption<Object> updateAt = ui.new AnswerOption<>(() -> {
            //partnerXmlDao.update();
            return  null;
        }, "Einem vorhandenen Vertragspartner aktualisieren");
        ConsoleManager.AnswerOption<Object> deleteAt = ui.new AnswerOption<>(() -> {
            return null;
        }, "Einem vertragspartner löschen");

        // Map<ConsoleManager.AnswerOption<Object>, Object> results
        Object result = ui.ConsoleOptions("Wie möchten Sie den Vertragspartner persistieren?", createAt, creatInsertAt, readAt, updateAt, deleteAt);




        //Scanner scanner = new Scanner(System.in);
        //System.out.println("Bitte geben sie die Daten für einen neuen Vertragspartner ein");
        //VertragspartnerDaoSqlite partner = new VertragspartnerDaoSqlite();
        //partner.creat();
        return null;
    }

    @Override
    public IDao<IWare, Long> getDaoWare() {
        return null;
    }
}
