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
        //Schritt 1.2
        /* System.out.println("Welche Form der Persistierung möchten Sie Nutzen? XML (1) oder Sqlite(2)");
        String type = "";
        do{
            switch (Main.sc.next()) {
                case "1" -> type = "xml";
                case "2" -> type = "sqlite";
                default -> System.out.println("Keine gültige Eingabe!");
            }
        }while (!type.equals("xml") && !type.equals("sqlite"));
        return type;
        */
        UIManager ui = UIManager.getInstance();
        UIManager.AnswerOption<String> xmlA = ui.new AnswerOption<>(()-> "xml", "XML");
        UIManager.AnswerOption<String> sqlA = ui.new AnswerOption<>(()-> "sql" , "Sqlite");
        return (String) ui.ConsoleOptions("Welche Form der Persistierung möchten Sie Nutzen?", xmlA, sqlA);
    }





}



