package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.Main;
import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.DataLayerManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;

public class DataLayerXml implements IDataLayer {

    @Override
    public IDao<IVertragspartner, String> getDaoVertragspartner() {
        VertragspartnerDaoXml partnerXmlDao = new VertragspartnerDaoXml();
        DataLayerManager dlm = DataLayerManager.getInstance();
        DataLayerManager.AnswerOption<Object> createAt = dlm.new AnswerOption<>(() -> {
            return partnerXmlDao.create();
        }, "Einen neuen Vertragspartner Erstellen");
        DataLayerManager.AnswerOption<Object> creatInsertAt = dlm.new AnswerOption<>(() -> {
            return partnerXmlDao.create();
        }, "Einen neuen Vertragspartner Einfügen");
        DataLayerManager.AnswerOption<Object> readAt = dlm.new AnswerOption<>(() -> {
            return partnerXmlDao.create();
        }, "Vorhanden Vertragspartner finden");
        DataLayerManager.AnswerOption<Object> updateAt = dlm.new AnswerOption<>(() -> {
            return partnerXmlDao.create();
        }, "Einem vorhandenen Vertragspartner aktualisieren");
        DataLayerManager.AnswerOption<Object> deleteAt = dlm.new AnswerOption<>(() -> {
            partnerXmlDao.delete(Main.sc.next());
            return null;
        }, "Einem vertragspartner löschen");

        // Map<DataLayerManager.AnswerOption<Object>, Object> results
        Object result = DataLayerManager.ConsoleOptions("Wie möchten Sie den Vertragspartner persistieren?", createAt, creatInsertAt, readAt, updateAt, deleteAt);
        return partnerXmlDao;
    }

    @Override
    public IDao<IWare, Long> getDaoWare() {
        WareDaoXml wareDaoXml = new WareDaoXml();
        return wareDaoXml;
    }

}
