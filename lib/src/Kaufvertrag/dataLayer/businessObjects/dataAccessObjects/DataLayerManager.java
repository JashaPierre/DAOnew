package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects;

import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML.DataLayerXml;
import Kaufvertrag.exceptions.DaoException;
import jdk.jshell.spi.ExecutionControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class DataLayerManager {
    private static DataLayerManager instance;
    private String persistenceType;
    private DataLayerManager(){
        readPersistenceType();
    }

    public static DataLayerManager getInstance(){
        if(instance == null)
            return instance = new DataLayerManager();
        return instance;
    }
    public IDataLayer getDataLayer() throws DaoException {
       //return either XML or sqlite datalayer
        System.out.println("Not implemented!");
        return null;
    }

    private String readPersistenceType(){
        Scanner sc = new Scanner(System.in);

        switch (sc.nextLine()) {
            case "XML" -> persistenceType = "xml";
            case "Sqlite" -> persistenceType = "Sqlite";
            default -> throw new IllegalArgumentException("Ungültige Persistierungsoption: " + sc);
        };
        sc.close();
        return null;
    }
}
