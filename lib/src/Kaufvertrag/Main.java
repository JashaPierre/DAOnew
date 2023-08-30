package Kaufvertrag;

import Kaufvertrag.dataLayer.businessObjects.Adresse;
import Kaufvertrag.dataLayer.businessObjects.Kaufvertrag;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.Ware;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.DataLayerManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import Kaufvertrag.exceptions.DaoException;

import java.util.Scanner;


public class Main {

    public static final String PROJECTPATH = System.getProperty("user.dir");
    public static void main(String[] args) {
        Vertragspartner kaeufer = new Vertragspartner("Käu", "Fer");
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

        Kaufvertrag kaufvertrag = new Kaufvertrag(kaeufer, verkaeufer, ware1);

        DataLayerManager manager = DataLayerManager.getInstance();
        try {
           IDataLayer dataLayer = manager.getDataLayer();
           IDao ware = dataLayer.getDaoWare();
           IDao vertragspartner = dataLayer.getDaoVertragspartner();

        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void readOptions(IDao iDao){
        //Schritt 1.2

        Scanner sc = new Scanner(System.in);
        System.out.println("Welche Option möchten Sie für + " + iDao.toString() + " ausführen? creat(1) create insert(2) read(3) readall(4) update(5) delete(6)");
        boolean finished = false;
        do{
            switch (sc.nextLine()) {
                case "1" -> {
                   iDao.create();
                }
                case "2" -> {

                }
                case "3" -> {

                }
                case "4" -> {

                }
                case "5" -> {

                }
                case "6" -> {

                }
                default -> {
                    System.out.println("Keine gültige Eingabe!");
                }
            }
        }while (finished);
        sc.close();
    }
}