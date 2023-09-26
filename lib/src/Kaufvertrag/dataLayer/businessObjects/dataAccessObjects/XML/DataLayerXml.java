package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.UIManager;

public class DataLayerXml implements IDataLayer {

    @Override
    public IDao<IVertragspartner, String> getDaoVertragspartner() {
        VertragspartnerDaoXml partnerXmlDao = new VertragspartnerDaoXml();
        UIManager ui = UIManager.getInstance();
        ServiceXml sXML = ServiceXml.getInstance();
        UIManager.AnswerOption<Object> createAt = ui.new AnswerOption<>(() -> {
            Vertragspartner partner1 = (Vertragspartner) partnerXmlDao.create();
            UIManager.AnswerOption<Object> jaAt = ui.new AnswerOption<>(partnerXmlDao::create, "nein");
            UIManager.AnswerOption<Object> neinAt = ui.new AnswerOption<>(null, "nein");
            Vertragspartner partner2 = (Vertragspartner) ui.ConsoleOptions("Für einen Vertrag werden zwei Partner benötigt. Wollen Sie einen weiteren Partner hinzufügen?", jaAt, neinAt);
            String fileName = ui.returnInput("Wie soll das neue XML heißen?");
            if(partner1 != null && partner2 != null){
                sXML.newVertragspartnerXML(partner1, fileName);
            }
            return  null;
        }, "Einen neuen Vertragspartner Erstellen");
        UIManager.AnswerOption<Object> creatInsertAt = ui.new AnswerOption<>(() -> {
            partnerXmlDao.create();
            return  null;
        }, "Einen neuen Vertragspartner Einfügen");
        UIManager.AnswerOption<Object> readAt = ui.new AnswerOption<>(() -> {
            partnerXmlDao.read(ui.getScanner().next());
            return  null;
        }, "Vorhanden Vertragspartner finden");
        UIManager.AnswerOption<Object> updateAt = ui.new AnswerOption<>(() -> {
            //partnerXmlDao.update();
            return  null;
        }, "Einem vorhandenen Vertragspartner aktualisieren");
        UIManager.AnswerOption<Object> deleteAt = ui.new AnswerOption<>(() -> {
            partnerXmlDao.delete(ui.getScanner().next());
            return null;
        }, "Einem vertragspartner löschen");

        // Map<UIManager.AnswerOption<Object>, Object> results
        Object result = ui.ConsoleOptions("Wie möchten Sie den Vertragspartner persistieren?", createAt, creatInsertAt, readAt, updateAt, deleteAt);
        return partnerXmlDao;
    }

    @Override
    public IDao<IWare, Long> getDaoWare() {
        WareDaoXml wareDaoXml = new WareDaoXml();
        return wareDaoXml;
    }

}
