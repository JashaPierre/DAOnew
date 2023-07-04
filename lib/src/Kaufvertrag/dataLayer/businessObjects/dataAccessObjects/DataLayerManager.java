package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects;

import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML.DataLayerXml;
import Kaufvertrag.exceptions.DaoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataLayerManager {
    private static DataLayerManager instance;
    private String persistenceType;
    private DataLayerManager(){
       // chose what kind: XML or SQL
       System.out.println("Welche art der Persistierung würden Sie gerne nutzen?");
       readPersistenceType();
    }

    public static DataLayerManager getInstance(){
        return instance;
    }
    public IDataLayer getDataLayer() throws DaoException {

    }
    private String readPersistenceType(){
        String type;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();
            switch (input) {
                case "XML":
                    return type  = "xml";
                case "Sqlite":
                    return type  = "Sqlite";
                default:
                    throw new IllegalArgumentException("Ungültige Persistierungsoption: " + input);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
     }
}
