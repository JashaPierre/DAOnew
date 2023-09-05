package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects;

import Kaufvertrag.Main;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML.DataLayerXml;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML.ServiceXml;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite.DataLayerSqlite;
import Kaufvertrag.exceptions.DaoException;

import java.util.Scanner;

public class DataLayerManager {
    private static DataLayerManager instance;
    private String persistenceType;
    private DataLayerManager(){
        persistenceType = readPersistenceType();
    }

    public static DataLayerManager getInstance(){
        //Schritt 1.1
        if(instance == null)
            return instance = new DataLayerManager();
        return instance;
    }
    public IDataLayer getDataLayer() throws DaoException {
        //
        switch (readPersistenceType()){
            case "xml" -> {
                Main.seviceXML = new ServiceXml();
                return new DataLayerXml();
            }
            case "sqlite" -> {
                return new DataLayerSqlite();
            }
            default -> throw new DaoException("Unrecognized persistence Type.");
        }
    }

    private String readPersistenceType(){
        //Schritt 1.2
        System.out.println("Welche Form der Persistierung möchten Sie Nutzen? XML (1) oder Sqlite(2)");
        String type = "";
        Scanner sc = new Scanner(System.in);
        do{
            switch (sc.nextLine()) {
                case "1" -> type = "xml";
                case "2" -> type = "sqlite";
                default -> System.out.println("Keine gültige Eingabe!");
            }
        }while (!type.equals("xml") && !type.equals("sqlite"));
        sc.close();
        return type;
    }
}
