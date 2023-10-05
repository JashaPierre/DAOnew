package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects;

import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML.DataLayerXml;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite.DataLayerSqlite;
import Kaufvertrag.exceptions.DaoException;

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
        ConsoleManager ui = ConsoleManager.getInstance();
        ConsoleManager.AnswerOption<String> xmlA = ui.new AnswerOption<>(()-> "xml", "XML");
        ConsoleManager.AnswerOption<String> sqlA = ui.new AnswerOption<>(()-> "sqlite" , "Sqlite");
        return (String) ui.ConsoleOptions("Welche Form der Persistierung möchten Sie Nutzen?",false, xmlA, sqlA);
    }





}



