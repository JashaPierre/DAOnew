package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataLayerManager {
    private static DataLayerManager instance;
    private String persistenceType;
    private DataLayerManager(){
        // chose what kind: XML or SQL
         try {
           BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
           System.out.println("Welche art der Persistierung würden Sie gerne nutzen?");
           String input = reader.readLine();
           if(input)
           instance = new IDataLayer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static DataLayerManager getInstance(){
        return instance;
    }
    public IDataLayer getDataLayer() {
        return null;
    }

    String readPersistenceType(){
        return null;
    }
}
