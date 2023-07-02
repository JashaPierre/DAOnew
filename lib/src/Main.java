import Kaufvertrag.dataLayer.businessObjects.Adresse;
import Kaufvertrag.dataLayer.businessObjects.Kaufvertrag;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.Ware;

public class Main {

    public static String projectPath = System.getProperty("user.dir");
    public static void main(String[] args) {
        Vertragspartner kaeufer = new Vertragspartner("Käu", "Fer");
        Adresse adresseKaeufer = new Adresse("Musterstr", "6", "28855", "Bremen");
        kaeufer.setAdresse(adresseKaeufer);
        kaeufer.setAusweisNr("0815");

        Vertragspartner verkaeufer = new Vertragspartner("Ver", "Käufer");
        Adresse adresseVerkaeufer = new Adresse("GysiStraße", "22", "28866", "Oldenburg");
        verkaeufer.setAdresse(adresseVerkaeufer);
        verkaeufer.setAusweisNr("2407");

        Ware ware = new Ware("Produkt A", 19.99);
        ware.setBeschreibung("Produkt A is ein tolles Produkt...");

        Ware ware1 = new Ware("Produkt B", 9.99);
        ware1.setBeschreibung("Produkt B ist ebenfalls ein tolles Produkt...");

        Kaufvertrag kaufvertrag = new Kaufvertrag(kaeufer, verkaeufer, ware);

        //System.out.println(kaufvertrag);
        System.out.println("Project Path: " + projectPath);
    }
}