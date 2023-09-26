package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.Ware;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.UIManager;

public class DataLayerXml implements IDataLayer {

    @Override
    public IDao<IVertragspartner, String> getDaoVertragspartner() {
        VertragspartnerDaoXml partnerXmlDao = new VertragspartnerDaoXml();
        UIManager ui = UIManager.getInstance();
        ServiceXml sXML = ServiceXml.getInstance();

        //create
        UIManager.AnswerOption<Object> createA = ui.new AnswerOption<>(() -> {
            Vertragspartner partner1 = (Vertragspartner) partnerXmlDao.create();

            UIManager.AnswerOption<Object> jaA = ui.new AnswerOption<>(partnerXmlDao::create, "ja");
            UIManager.AnswerOption<Object> neinA = ui.new AnswerOption<>(null, "nein");
            Vertragspartner partner2 = (Vertragspartner) ui.ConsoleOptions("Für einen Vertrag werden zwei Partner benötigt. Wollen Sie einen weiteren Partner hinzufügen?", jaA, neinA);

            String fileName = ui.returnInput("Wie soll das neue XML heißen?");
            if(partner2 != null)
                sXML.newXML(fileName, "Vertrag", sXML.newXMLVertragspartnerknoten(partner1), sXML.newXMLVertragspartnerknoten(partner2));
            else
                sXML.newXML(fileName, "Vertrag", sXML.newXMLVertragspartnerknoten(partner1));
            return  null;
        }, "Einen neuen Vertragspartner erstellen");

        //create insert
        UIManager.AnswerOption<Object> creatInsertA = ui.new AnswerOption<>(() -> {

            Vertragspartner partner = (Vertragspartner) partnerXmlDao.create();

            return  null;
        }, "Einen neuen Vertragspartner in eine vorhandene Datei einfügen");

        //read
        UIManager.AnswerOption<Object> readA = ui.new AnswerOption<>(() -> {
            partnerXmlDao.readAll();
            return  null;
        }, "Vorhanden Vertragspartner finden");


        //update
        UIManager.AnswerOption<Object> updateA = ui.new AnswerOption<>(() -> {
            //partnerXmlDao.update();
            return  null;
        }, "Einem vorhandenen Vertragspartner aktualisieren");

        // delete
        UIManager.AnswerOption<Object> deleteA = ui.new AnswerOption<>(() -> {
            partnerXmlDao.delete(ui.getScanner().next());
            return null;
        }, "Einem vertragspartner löschen");

        // Map<UIManager.AnswerOption<Object>, Object> results
        ui.ConsoleOptions("Wie möchten Sie den Vertragspartner persistieren?", createA, creatInsertA, readA, updateA, deleteA);
        IDao<IVertragspartner, String> result;
        return partnerXmlDao;
    }

    @Override
    public IDao<IWare, Long> getDaoWare() {
        WareDaoXml wareDaoXml = new WareDaoXml();
        UIManager ui = UIManager.getInstance();
        ServiceXml sXML = ServiceXml.getInstance();

        //create
        UIManager.AnswerOption<Object> createA = ui.new AnswerOption<>(() -> {
            Ware ware = (Ware) wareDaoXml.create();
            String fileName = ui.returnInput("Wie soll das neue XML heißen?");
            sXML.newXML(fileName, "Ware", sXML.newXMLWarenknoten(ware));
            return  null;
        }, "Eine neue Ware erstellen");

        //create insert
        UIManager.AnswerOption<Object> creatInsertA = ui.new AnswerOption<>(() -> {

            Vertragspartner partner = (Vertragspartner) wareDaoXml.create();

            return  null;
        }, "Einen neuen Vertragspartner in eine vorhandene Datei einfügen");

        //read
        UIManager.AnswerOption<Object> readA = ui.new AnswerOption<>(() -> {
            wareDaoXml.readAll();
            return  null;
        }, "Vorhanden Vertragspartner finden");


        //update
        UIManager.AnswerOption<Object> updateA = ui.new AnswerOption<>(() -> {
            //partnerXmlDao.update();
            return  null;
        }, "Einem vorhandenen Vertragspartner aktualisieren");

        // delete
        UIManager.AnswerOption<Object> deleteA = ui.new AnswerOption<>(() -> {
            wareDaoXml.delete(ui.getScanner().nextLong());
            return null;
        }, "Einem vertragspartner löschen");
        ui.ConsoleOptions("Wie möchten Sie die Ware persistieren?", createA, creatInsertA, readA, updateA, deleteA);


        return wareDaoXml;
    }

}
