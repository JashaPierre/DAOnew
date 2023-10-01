package Kaufvertrag;

import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.Ware;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.DataLayerManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML.DataLayerXml;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML.ServiceXml;
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
                        return null;
                    }, "Einen neue Ware erstellen");

                    //overwrite
                    ConsoleManager.AnswerOption<Object> creatOverwriteA = ui.new AnswerOption<>(() -> {
                        return null;
                    }, "Eine vorhandene Ware mit einer neuen überschreiben");

                    //update
                    ConsoleManager.AnswerOption<Object> updateA = ui.new AnswerOption<>(() -> {
                        return null;
                    }, "Eine vorhandene Ware aktualisieren");

                    //delete
                    ConsoleManager.AnswerOption<Object> deleteA = ui.new AnswerOption<>(() -> {
                        return null;
                    }, "Eine Ware löschen");

                    ui.ConsoleOptions("Wie möchten Sie die Ware persistieren?", creatNewA, creatOverwriteA, updateA, deleteA);
                    return null;
                }, "Eine Ware");

                Object result = ui.ConsoleOptions("Was möchten Sie persistieren?",false, partnerAt, wareAt);
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
        ConsoleManager ui = ConsoleManager.getInstance();
        try {
            Vertragspartner partner = (Vertragspartner) daoPartner.create();
            ConsoleManager.AnswerOption<Object> jaA = ui.new AnswerOption<>(daoPartner::create, "ja");
            ConsoleManager.AnswerOption<Object> neinA = ui.new AnswerOption<>(null, "nein");
            Vertragspartner partner2 = (Vertragspartner) ui.ConsoleOptions("Für einen Vertrag werden zwei Partner benötigt. Wollen Sie einen weiteren Partner hinzufügen?",false, jaA, neinA);
            daoPartner.create(partner);
            if(partner2 != null){
                daoPartner.create(partner2);
            }
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
                String id = getId(dataLayer, "Vertragspartner", oldPartner.getVorname());
                IVertragspartner newPartner = daoPartner.create();
                daoPartner.delete(id);
                daoPartner.create(newPartner);
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
                System.out.println("Konnte keinen Vertragspartner zum überschreiben finden!");
                return;
            }
            Object result = makeAnswerList(partnerList, "Welchen Vertragspartner möchten Sie überarbeiten?");
            if(result instanceof IVertragspartner partner) {
                daoPartner.delete(getId(dataLayer, "Vertragspartner", partner.getVorname()));
                daoPartner.update(partner);
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteVertragspartner(IDao<IVertragspartner, String> daoPartner, IDataLayer dataLayer){
        try {
            List<IVertragspartner> partnerList = daoPartner.readAll();
            if(partnerList.isEmpty()){
                System.out.println("Konnte keinen Vertragspartner zum überschreiben finden!");
                return;
            }
            Object result = makeAnswerList(partnerList, "Welchen Vertragspartner möchten Sie löschen?");
            if(result instanceof IVertragspartner partner) {
                String id = getId(dataLayer, "Vertragspartner", partner.getVorname());
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
            Ware ware = (Ware) daoWare.create();
            daoWare.create(ware);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createInsertWare(IDao<IWare, Long> daoWare, IDataLayer dataLayer){
        ConsoleManager ui = ConsoleManager.getInstance();
        try {
            List<IWare> warenListe = daoWare.readAll();
            Object result = makeAnswerList(warenListe, "Welche Ware möchten Sie überschreiben?");
            IWare newWare = daoWare.create();
            if(result instanceof IWare oldWare) {
                daoWare.delete(oldWare.getId());
                daoWare.create(newWare);
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readWare(IDao<IWare, Long> daoWare){
        ConsoleManager ui = ConsoleManager.getInstance();

        //read
//        ConsoleManager.AnswerOption<Object> readA = ui.new AnswerOption<>(() -> {
//            wareDaoXml.readAll();
//            return  null;
//        }, "Vorhanden Vertragspartner finden");

    }

    public static void updateWare(IDao<IWare, Long> daoWare){
        ConsoleManager ui = ConsoleManager.getInstance();
        //update
        /*ConsoleManager.AnswerOption<Object> updateA = ui.new AnswerOption<>(() -> {
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
        }, "Eine vorhandene Ware aktualisieren");*/
    }

    public static void deleteWare(IDao<IWare, Long> daoWare){
        ConsoleManager ui = ConsoleManager.getInstance();
        // delete
       /* ConsoleManager.AnswerOption<Object> deleteA = ui.new AnswerOption<>(() -> {
            List<File> fileList = sXML.getXMLFileList();
            File openedFile = sXML.chooseXML(fileList, "In welcher Datei möchten Sie einen Knoten löschen?");
            Document doc = sXML.readXMLFile(openedFile);
            Element warenKnoten = sXML.UnterKnotenAuswahlen(doc,"Ware","Bezeichnung","Welchen Vertragspartner möchten Sie Löschen?");
            daoWare.delete(Long.parseLong(warenKnoten.getAttributeValue("id")));
            return null;
        }, "Einem vertragspartner löschen");*/
    }

    /**
     * Utility
     * */

    private static <T> String getId(IDataLayer layer, String type, String id){
        if(layer instanceof DataLayerXml){
            ServiceXml sXML = ServiceXml.getInstance();
            sXML.idSeachAllXml(type, id);
        }
        else if (layer instanceof DataLayerSqlite) {

        }
        return id;
    }

    private static <T> Object makeAnswerList(List<T> objects, String frage){
        ConsoleManager ui = ConsoleManager.getInstance();

        List<ConsoleManager.AnswerOption<T>> answerList = new ArrayList<>();
        for(T object : objects){
            ConsoleManager.AnswerOption<T> mangelA = ui.new AnswerOption<>(() ->
                    object, object.toString());
            answerList.add(mangelA);
        }

        @SuppressWarnings("unchecked")
        ConsoleManager.AnswerOption<T>[] array = new ConsoleManager.AnswerOption[answerList.size()];
        array = answerList.toArray(array);
        if(frage.equals(""))
            frage = "Was möchten Sie wählen?";
        return ui.ConsoleOptions(frage, array);
    }

}