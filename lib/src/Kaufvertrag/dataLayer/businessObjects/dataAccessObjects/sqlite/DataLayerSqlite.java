package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;

import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;

import java.util.Scanner;

public class DataLayerSqlite implements IDataLayer {

    @Override
    public IDao<IVertragspartner, String> getDaoVertragspartner() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Wählen Sie eine Option:");
            System.out.println("1. Vertragspartner erstellen");
            System.out.println("2. BEENDEN");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            VertragspartnerDaoSqlite partner = new VertragspartnerDaoSqlite();

            Vertragspartner partnerSql = new Vertragspartner("", "");
            switch (choice) {
                case 1 -> partnerSql = (Vertragspartner) partner.create();
                //case 3 -> showVertragspartner();
                //case 4 -> showWare();
                case 2 -> {
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
