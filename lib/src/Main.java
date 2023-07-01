import Kaufvertrag.Kaufvertrag;
import Kaufvertrag.Vertragspartner;
import Kaufvertrag.Ware;

public class Main {
    public static void main(String[] args) {
        Vertragspartner kaeufer = new Vertragspartner("Käu", "Fer");
        Vertragspartner verkaeufer = new Vertragspartner("Ver", "Käufer");

        Ware ware = new Ware("Produkt A", 19.99);

        Kaufvertrag kaufvertrag = new Kaufvertrag(kaeufer, verkaeufer, ware);

        System.out.println("hello world");
        System.out.println(kaufvertrag);
    }
}