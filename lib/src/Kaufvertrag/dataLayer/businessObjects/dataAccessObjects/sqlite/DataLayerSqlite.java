package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;

import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.exceptions.DaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class DataLayerSqlite implements IDataLayer {

    @Override
    public IDao<IVertragspartner, String> getDaoVertragspartner() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Wählen Sie eine Option:");
            System.out.println("1. Vertragspartner erstellen");
            //System.out.println("2. Partner inserten!!!!!!!!");
            System.out.println("3. BEENDEN");
            //System.out.println("4. Ware anzeigen");
            //System.out.println("5. Beenden");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            VertragspartnerDaoSqlite partner = new VertragspartnerDaoSqlite();

            Vertragspartner partnerSql = new Vertragspartner("", "");
            switch (choice) {
                case 1 -> partnerSql = (Vertragspartner) partner.create();
                //case 3 -> showVertragspartner();
                //case 4 -> showWare();
                case 5 -> {
                    System.out.println("Programm wird beendet.");
                    System.exit(0);
                }
                default -> System.out.println("Ungültige Option. Bitte erneut wählen.");
            }
        }

    }


    @Override
    public IDao<IWare, Long> getDaoWare() {
        return null;
    }
}
