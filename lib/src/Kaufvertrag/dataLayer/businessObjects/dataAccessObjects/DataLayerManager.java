package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects;

import Kaufvertrag.Main;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML.DataLayerXml;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite.DataLayerSqlite;
import Kaufvertrag.exceptions.DaoException;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        switch (persistenceType){
            case "xml" -> {
                return new DataLayerXml();
            }
            case "sqlite" -> {
                return new DataLayerSqlite();
            }
            default -> throw new DaoException("Unbekannter Persistierungstyp.");
        }
    }

    private String readPersistenceType(){
        //Schritt 1.2
        System.out.println("Welche Form der Persistierung möchten Sie Nutzen? XML (1) oder Sqlite(2)");
        String type = "";
        do{
            switch (Main.sc.next()) {
                case "1" -> type = "xml";
                case "2" -> type = "sqlite";
                default -> System.out.println("Keine gültige Eingabe!");
            }
        }while (!type.equals("xml") && !type.equals("sqlite"));
        return type;
    }





}



