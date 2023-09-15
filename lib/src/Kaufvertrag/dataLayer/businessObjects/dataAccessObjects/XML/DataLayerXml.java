package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.Main;
import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.DataLayerManager;
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
            var partner = partnerXmlDao.create();
            String fileName = ui.returnInput("Wie soll das neue XML heißen?", "","");
            sXML.newVertragspartnerXML(partner, fileName);
            return  null;
        }, "Einen neuen Vertragspartner Erstellen");
        UIManager.AnswerOption<Object> creatInsertAt = ui.new AnswerOption<>(() -> {
            partnerXmlDao.create();
            return  null;
        }, "Einen neuen Vertragspartner Einfügen");
        UIManager.AnswerOption<Object> readAt = ui.new AnswerOption<>(() -> {
            partnerXmlDao.read(Main.sc.next());
            return  null;
        }, "Vorhanden Vertragspartner finden");
        UIManager.AnswerOption<Object> updateAt = ui.new AnswerOption<>(() -> {
            //partnerXmlDao.update();
            return  null;
        }, "Einem vorhandenen Vertragspartner aktualisieren");
        UIManager.AnswerOption<Object> deleteAt = ui.new AnswerOption<>(() -> {
            partnerXmlDao.delete(Main.sc.next());
            return null;
        }, "Einem vertragspartner löschen");

        // Map<UIManager.AnswerOption<Object>, Object> results
        Object result = UIManager.ConsoleOptions("Wie möchten Sie den Vertragspartner persistieren?", createAt, creatInsertAt, readAt, updateAt, deleteAt);
        return partnerXmlDao;
    }

    @Override
    public IDao<IWare, Long> getDaoWare() {
        WareDaoXml wareDaoXml = new WareDaoXml();
        return wareDaoXml;
    }

}
