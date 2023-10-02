package Kaufvertrag;

import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.DataLayerManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML.DataLayerXml;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML.VertragspartnerDaoXml;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite.DataLayerSqlite;
import Kaufvertrag.exceptions.DaoException;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static final String PROJECTPATH = System.getProperty("user.dir");

    public static void main(String[] args) {
        try {
            ConsoleManager ui = ConsoleManager.getInstance();
            DataLayerManager dlm = DataLayerManager.getInstance();
            IDataLayer dataLayer = dlm.getDataLayer();
            while(true) {
                //Vertragspartner
                ConsoleManager.AnswerOption<Object> partnerAt = ui.new AnswerOption<>(() -> {
                    IDao<IVertragspartner, String> daoPartner = dataLayer.getDaoVertragspartner();
                    //create
                    ConsoleManager.AnswerOption<Object> creatNewA = ui.new AnswerOption<>(() -> {
                        createNewVertragspartner(daoPartner);
                        return null;
                    }, "Einen neuen Vertragspartner erstellen");

                    //overwrite
                    ConsoleManager.AnswerOption<Object> creatOverwriteA = ui.new AnswerOption<>(() -> {
                        createOverwriteVertragspartner(daoPartner, dataLayer);
                        return null;
                    }, "Einen vorhandenen Vertragspartner mit einem neuem überschreiben");

                    //update
                    ConsoleManager.AnswerOption<Object> updateA = ui.new AnswerOption<>(() -> {
                        updateVertragspartner(daoPartner, dataLayer);
                        return null;
                    }, "Einem vorhandenen Vertragspartner aktualisieren");

                    //delete
                    ConsoleManager.AnswerOption<Object> deleteA = ui.new AnswerOption<>(() -> {
                        deleteVertragspartner(daoPartner, dataLayer);
                        return null;
                    }, "Einem vertragspartner löschen");

                    ui.ConsoleOptions("Wie möchten Sie den Vertragspartner persistieren?", creatNewA, creatOverwriteA, updateA, deleteA);
                    return null;
                }, "Einen Vertragspartner");

                //ware
                ConsoleManager.AnswerOption<Object> wareAt = ui.new AnswerOption<>(() -> {
                    IDao<IWare, Long> daoWare = dataLayer.getDaoWare();
                    //create
                    ConsoleManager.AnswerOption<Object> creatNewA = ui.new AnswerOption<>(() -> {
                        createWare(daoWare);
                        return null;
                    }, "Einen neue Ware erstellen");

                    //overwrite
                    ConsoleManager.AnswerOption<Object> creatOverwriteA = ui.new AnswerOption<>(() -> {
                        createInsertWare(daoWare,dataLayer);
                        return null;
                    }, "Eine vorhandene Ware mit einer neuen überschreiben");

                    //update
                    ConsoleManager.AnswerOption<Object> updateA = ui.new AnswerOption<>(() -> {
                        updateWare(daoWare);
                        return null;
                    }, "Eine vorhandene Ware aktualisieren");

                    //delete
                    ConsoleManager.AnswerOption<Object> deleteA = ui.new AnswerOption<>(() -> {
                        deleteWare(daoWare);
                        return null;
                    }, "Eine Ware löschen");

                    ui.ConsoleOptions("Wie möchten Sie die Ware persistieren?", creatNewA, creatOverwriteA, updateA, deleteA);
                    return null;
                }, "Eine Ware");

                Object result = ui.ConsoleOptions("Was möchten Sie persistieren?", partnerAt, wareAt);
                if (result instanceof Boolean && (!((boolean) result))) {
                    break;
                }
            }
            ui.closeScanner();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    /**
     * Vertragspartner
     **/
    public static void createNewVertragspartner(IDao<IVertragspartner, String> daoPartner){
        try {
            Vertragspartner partner = (Vertragspartner) daoPartner.create();
            daoPartner.create(partner);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createOverwriteVertragspartner(IDao<IVertragspartner, String> daoPartner, IDataLayer dataLayer){
        try {
            List<IVertragspartner> partnerList = daoPartner.readAll();
            if(partnerList.isEmpty()){
                System.out.println("Konnte keinen Vertragspartner zum überschreiben finden!");
                return;
            }
            Object result = makeAnswerList(partnerList, "Welchen Vertragspartner möchten Sie überschreiben?");
            if(result instanceof IVertragspartner oldPartner) {
                String id = getVertragspartnerId(dataLayer,daoPartner,oldPartner);
                IVertragspartner newPartner = daoPartner.create();
                daoPartner.create(newPartner);
                daoPartner.delete(id);
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
    public static void readVertragspartner(IDao<IVertragspartner, String> daoPartner,  IDataLayer dataLayer){
        ConsoleManager ui = ConsoleManager.getInstance();
        //read
        // macht nicht viel sinn. Was soll denn damit gemacht werden?
        /*ConsoleManager.AnswerOption<Object> readA = ui.new AnswerOption<>(() -> {
            partnerXmlDao.readAll();
            return  null;
        }, "Vorhanden Vertragspartner finden");*/
    }
    public static void updateVertragspartner(IDao<IVertragspartner, String> daoPartner, IDataLayer dataLayer){
        try {
            List<IVertragspartner> partnerList = daoPartner.readAll();
            if(partnerList.isEmpty()){
                System.out.println("Konnte keinen Vertragspartner zum überarbeiten finden!");
                return;
            }
            Object result = makeAnswerList(partnerList, "Welchen Vertragspartner möchten Sie überarbeiten?");
            if(result instanceof IVertragspartner partner) {
//                daoPartner.delete(getId(dataLayer, "Vertragspartner", partner.getVorname()));
                String id = getVertragspartnerId(dataLayer,daoPartner,partner);
                daoPartner.update(partner);
                daoPartner.delete(id);
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteVertragspartner(IDao<IVertragspartner, String> daoPartner, IDataLayer dataLayer){
        try {
            List<IVertragspartner> partnerList = daoPartner.readAll();
            if(partnerList.isEmpty()){
                System.out.println("Konnte keinen Vertragspartner zum löschen finden!");
                return;
            }
            Object result = makeAnswerList(partnerList, "Welchen Vertragspartner möchten Sie löschen?");
            if(result instanceof IVertragspartner partner) {
//                String id = getId(dataLayer, "Vorname", partner.getVorname());
                String id = getVertragspartnerId(dataLayer,daoPartner,partner);
                daoPartner.delete(id);
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ware
     **/
    public static void createWare(IDao<IWare, Long> daoWare){
        try {
            IWare ware = daoWare.create();
            daoWare.create(ware);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createInsertWare(IDao<IWare, Long> daoWare, IDataLayer dataLayer){
        try {
            List<IWare> warenListe = daoWare.readAll();
            if(warenListe.isEmpty()){
                System.out.println("Konnte keine Ware zum überschreiben finden!");
                return;
            }
            Object result = makeAnswerList(warenListe, "Welche Ware möchten Sie überschreiben?");
            if(result instanceof IWare oldWare) {
                Long id = oldWare.getId();
                IWare newWare = daoWare.create();
                daoWare.create(newWare);
                daoWare.delete(id);
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readWare(IDao<IWare, Long> daoWare){
        ConsoleManager ui = ConsoleManager.getInstance();

        //Nicht Implementiert

    }

    public static void updateWare(IDao<IWare, Long> daoWare){
        try {
            List<IWare> warenListe = daoWare.readAll();
            if(warenListe.isEmpty()){
                System.out.println("Konnte keine Ware zum überarbeiten finden!");
                return;
            }
            Object result = makeAnswerList(warenListe, "Welche Ware möchten Sie überarbeiten?");
            if(result instanceof IWare ware) {
                Long id = ware.getId();
                daoWare.update(ware);
                daoWare.delete(id);
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }

    public static void deleteWare(IDao<IWare, Long> daoWare){
        try {
            List<IWare> warenListe = daoWare.readAll();
            if(warenListe.isEmpty()){
                System.out.println("Konnte keine Ware zum löschen finden!");
                return;
            }
            Object result = makeAnswerList(warenListe, "Welchen Vertragspartner möchten Sie löschen?");
            if(result instanceof IWare ware) {
                Long id = ware.getId();
                daoWare.delete(id);
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Utility
     * */

    private static String getVertragspartnerId(IDataLayer layer, IDao<IVertragspartner, String> dao, IVertragspartner partner){
        String id = null;
        if(layer instanceof DataLayerXml){
            var daoXML = (VertragspartnerDaoXml) dao;
            id = daoXML.idStore.get(partner);
        }
        else if (layer instanceof DataLayerSqlite) {

        }
        return id;
    }


    private static <T> Object makeAnswerList(List<T> objects, String frage){
        ConsoleManager ui = ConsoleManager.getInstance();

        List<ConsoleManager.AnswerOption<T>> answerList = new ArrayList<>();
        for(T object : objects){
            ConsoleManager.AnswerOption<T> answerA = ui.new AnswerOption<>(() ->
                    object, object.toString());
            answerList.add(answerA);
        }

        @SuppressWarnings("unchecked")
        ConsoleManager.AnswerOption<T>[] array = new ConsoleManager.AnswerOption[answerList.size()];
        array = answerList.toArray(array);
        if(frage.equals(""))
            frage = "Was möchten Sie wählen?";
        return ui.ConsoleOptions(frage, array);
    }

}