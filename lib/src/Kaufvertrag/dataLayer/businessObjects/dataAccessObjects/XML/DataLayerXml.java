package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.Ware;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import org.jdom2.Document;
import org.jdom2.Element;

import java.io.File;
import java.util.List;

public class DataLayerXml implements IDataLayer {


    @Override
    public IDao<IVertragspartner, String> getDaoVertragspartner() {
        VertragspartnerDaoXml partnerXmlDao = new VertragspartnerDaoXml();
        ConsoleManager ui = ConsoleManager.getInstance();
        ServiceXml sXML = ServiceXml.getInstance();

        //create
        ConsoleManager.AnswerOption<Object> createA = ui.new AnswerOption<>(() -> {
            Vertragspartner partner1 = (Vertragspartner) partnerXmlDao.create();
            ConsoleManager.AnswerOption<Object> jaA = ui.new AnswerOption<>(partnerXmlDao::create, "ja");
            ConsoleManager.AnswerOption<Object> neinA = ui.new AnswerOption<>(null, "nein");
            Vertragspartner partner2 = (Vertragspartner) ui.ConsoleOptions("Für einen Vertrag werden zwei Partner benötigt. Wollen Sie einen weiteren Partner hinzufügen?", jaA, neinA);

            String fileName = ui.returnInput("Wie soll das neue XML heißen?");
            if(partner2 != null)
                sXML.newXML(fileName, "Vertrag", sXML.newXMLVertragspartnerknoten(partner1), sXML.newXMLVertragspartnerknoten(partner2));
            else
                sXML.newXML(fileName, "Vertrag", sXML.newXMLVertragspartnerknoten(partner1));
            return  null;
        }, "Einen neuen Vertragspartner erstellen");

        //create insert
        ConsoleManager.AnswerOption<Object> creatInsertA = ui.new AnswerOption<>(() -> {
            var fileList = sXML.getXMLFileList();
            File openedFile = sXML.chooseXML(fileList);
            Document doc = sXML.readXMLFile(openedFile);
            var partnerKnoten = sXML.UnterKnotenAuswahlen(doc,"Vertragspartner","Vorname","Welchen Vertragspartner möchten Sie überschreiben?");
            Vertragspartner partner = (Vertragspartner) partnerXmlDao.parseXMLtoPartner(partnerKnoten);
            doc.getRootElement().removeContent(partnerKnoten);
            partnerXmlDao.create(partner);

            return  null;
        }, "Einen vorhandenen Vertragspartner mit einem neuem überschreiben");

        //read
        // macht nicht viel sinn. Was soll denn damit gemacht werden?
        /*ConsoleManager.AnswerOption<Object> readA = ui.new AnswerOption<>(() -> {
            partnerXmlDao.readAll();
            return  null;
        }, "Vorhanden Vertragspartner finden");*/

        //update
        ConsoleManager.AnswerOption<Object> updateA = ui.new AnswerOption<>(() -> {
            List<File> fileList = sXML.getXMLFileList();
            File openedFile = sXML.chooseXML(fileList);
            Document doc = sXML.readXMLFile(openedFile);

            Element partnerKnoten = sXML.UnterKnotenAuswahlen(doc,"Vertragspartner","Vorname","Welchen Vertragspartner möchten Sie überarbeiten?");
            Vertragspartner partner = (Vertragspartner) partnerXmlDao.parseXMLtoPartner(partnerKnoten);
            partnerXmlDao.update(partner);
            Element newPartnerKnoten = sXML.newXMLVertragspartnerknoten(partner);
            doc.getRootElement().removeContent(partnerKnoten);
            doc.getRootElement().setContent(newPartnerKnoten);

            sXML.saveXML(doc, openedFile);
            return  null;
        }, "Einem vorhandenen Vertragspartner aktualisieren");

        // delete
        ConsoleManager.AnswerOption<Object> deleteA = ui.new AnswerOption<>(() -> {
            List<File> fileList = sXML.getXMLFileList();
            File openedFile = sXML.chooseXML(fileList, "In welcher Datei möchten Sie einen Knoten löschen?");
            Document doc = sXML.readXMLFile(openedFile);
            Element partnerKnoten = sXML.UnterKnotenAuswahlen(doc,"Vertragspartner","Vorname","Welchen Vertragspartner möchten Sie Löschen?");
            partnerXmlDao.delete(partnerKnoten.getAttributeValue("id"));
            return null;
        }, "Einem vertragspartner löschen");

        // Map<ConsoleManager.AnswerOption<Object>, Object> results
        ui.ConsoleOptions("Wie möchten Sie den Vertragspartner persistieren?", createA, creatInsertA/*, readA*/, updateA, deleteA);

        return partnerXmlDao;
    }

    @Override
    public IDao<IWare, Long> getDaoWare() {
        WareDaoXml wareDaoXml = new WareDaoXml();
        ConsoleManager ui = ConsoleManager.getInstance();
        ServiceXml sXML = ServiceXml.getInstance();

        //create
        ConsoleManager.AnswerOption<Object> createA = ui.new AnswerOption<>(() -> {
            Ware ware = (Ware) wareDaoXml.create();
            String fileName = ui.returnInput("Wie soll das neue XML heißen?");
            sXML.newXML(fileName, "Ware", sXML.newXMLWarenknoten(ware));
            return  null;
        }, "Eine neue Ware erstellen");

        //create insert
        ConsoleManager.AnswerOption<Object> creatInsertA = ui.new AnswerOption<>(() -> {
            var fileList = sXML.getXMLFileList();
            File openedFile = sXML.chooseXML(fileList);
            Document doc = sXML.readXMLFile(openedFile);
            var warenKnoten = sXML.UnterKnotenAuswahlen(doc,"Vertragspartner","Vorname","Welchen Vertragspartner möchten Sie überschreiben?");
            Ware ware = (Ware) wareDaoXml.parseXMLtoWare(warenKnoten);
            doc.getRootElement().removeContent(warenKnoten);

            wareDaoXml.create(ware);
            return  null;
        }, "Eine neue Ware in eine vorhandene Datei einfügen");

        //read
//        ConsoleManager.AnswerOption<Object> readA = ui.new AnswerOption<>(() -> {
//            wareDaoXml.readAll();
//            return  null;
//        }, "Vorhanden Vertragspartner finden");

        //update
        ConsoleManager.AnswerOption<Object> updateA = ui.new AnswerOption<>(() -> {
            List<File> fileList = sXML.getXMLFileList();
            File openedFile = sXML.chooseXML(fileList);
            Document doc = sXML.readXMLFile(openedFile);

            Element warenKnoten = sXML.UnterKnotenAuswahlen(doc,"Ware","Bezeichnung","Welchen Ware möchten Sie überarbeiten?");
            Ware ware = (Ware) wareDaoXml.parseXMLtoWare(warenKnoten);
            wareDaoXml.update(ware);
            Element newWarenKnoten = sXML.newXMLWarenknoten(ware);
            doc.getRootElement().removeContent(warenKnoten);
            doc.getRootElement().setContent(newWarenKnoten);

            sXML.saveXML(doc, openedFile);
            return  null;
        }, "Eine vorhandene Ware aktualisieren");

        // delete
        ConsoleManager.AnswerOption<Object> deleteA = ui.new AnswerOption<>(() -> {
            List<File> fileList = sXML.getXMLFileList();
            File openedFile = sXML.chooseXML(fileList, "In welcher Datei möchten Sie einen Knoten löschen?");
            Document doc = sXML.readXMLFile(openedFile);
            Element warenKnoten = sXML.UnterKnotenAuswahlen(doc,"Ware","Bezeichnung","Welchen Vertragspartner möchten Sie Löschen?");
            wareDaoXml.delete(Long.parseLong(warenKnoten.getAttributeValue("id")));
            return null;
        }, "Einem vertragspartner löschen");
        ui.ConsoleOptions("Wie möchten Sie die Ware persistieren?", createA, creatInsertA/*, readA*/, updateA, deleteA);


        return wareDaoXml;
    }

}
