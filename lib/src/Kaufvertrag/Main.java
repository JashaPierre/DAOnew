package Kaufvertrag;

import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.DataLayerManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML.ServiceXml;
import Kaufvertrag.exceptions.DaoException;

import java.util.Scanner;


public class Main {

    public static final String PROJECTPATH = System.getProperty("user.dir");
    public static ServiceXml seviceXML;

    public static void main(String[] args) {
       /* Vertragspartner kaeufer = new Vertragspartner("Käu", "Fer");
        Adresse adresseKaeufer = new Adresse("Musterstr", "6", "28855", "Bremen");
        kaeufer.setAdresse(adresseKaeufer);
        kaeufer.setAusweisNr("0815");

        Vertragspartner verkaeufer = new Vertragspartner("Ver", "Käufer");
        Adresse adresseVerkaeufer = new Adresse("GysiStraße", "22", "28866", "Oldenburg");
        verkaeufer.setAdresse(adresseVerkaeufer);
        verkaeufer.setAusweisNr("2407");

        Ware ware1 = new Ware("Produkt A", 19.99);
        ware1.setBeschreibung("Produkt A is ein tolles Produkt...");

        Ware ware2 = new Ware("Produkt B", 9.99);
        ware2.setBeschreibung("Produkt B ist ebenfalls ein tolles Produkt...");

        Kaufvertrag kaufvertrag = new Kaufvertrag(kaeufer, verkaeufer, ware1);*/

        DataLayerManager manager = DataLayerManager.getInstance();
        try {
           IDataLayer dataLayer = manager.getDataLayer();
           IDao<IWare, Long> ware = dataLayer.getDaoWare();
           IDao<IVertragspartner, String> vertragspartner = dataLayer.getDaoVertragspartner();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void consoleOptions(String frage, AnswerOption... antworten){
        Scanner sc = new Scanner(System.in);
        // System.out.println("Welche Option möchten Sie für + " + iDao.toString() + " ausführen? creat(1) create insert(2) read(3) readall(4) update(5) delete(6)");
        System.out.println(frage);
        StringBuilder antwortString = new StringBuilder();
        for (int i = 0;i < antworten.length; i++){
            antwortString.append(antworten[i].answerText);
            antwortString.append(" (" +i+ " ) ");
        }
        System.out.println(antwortString);
        boolean finished = false;
        do{
           for (var antwort : antworten){
               antwort.executeAction();
           }
        }while (!finished);
        sc.close();
    }

    static class AnswerOption {
        private final String answerText;
        private final Runnable action;
        public AnswerOption(String answerText, Runnable action) {
            this.answerText = answerText;
            this.action = action;
        }

        public String getAnswerText() {
            return answerText;
        }

        public void executeAction() {
            action.run();
        }
    }

}