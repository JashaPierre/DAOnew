package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;

import Kaufvertrag.Main;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.UIManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML.ServiceXml;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML.VertragspartnerDaoXml;

import java.util.Scanner;

public class DataLayerSqlite implements IDataLayer {




    @Override
    public IDao<IVertragspartner, String> getDaoVertragspartner() {
        VertragspartnerDaoSqlite partnerSqlite = new VertragspartnerDaoSqlite();
        UIManager ui = UIManager.getInstance();

        UIManager.AnswerOption<Object> createAt = ui.new AnswerOption<>(() -> {
            var partner = partnerSqlite.create();

            return  null;
        }, "Einen neuen Vertragspartner Erstellen");
        UIManager.AnswerOption<Object> creatInsertAt = ui.new AnswerOption<>(() -> {
            return  null;
        }, "Einen neuen Vertragspartner Einfügen");
        UIManager.AnswerOption<Object> readAt = ui.new AnswerOption<>(() -> {
            return  null;
        }, "Vorhanden Vertragspartner finden");
        UIManager.AnswerOption<Object> updateAt = ui.new AnswerOption<>(() -> {
            //partnerXmlDao.update();
            return  null;
        }, "Einem vorhandenen Vertragspartner aktualisieren");
        UIManager.AnswerOption<Object> deleteAt = ui.new AnswerOption<>(() -> {
            return null;
        }, "Einem vertragspartner löschen");

        // Map<UIManager.AnswerOption<Object>, Object> results
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
