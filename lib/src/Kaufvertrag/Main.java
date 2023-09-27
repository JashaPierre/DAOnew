package Kaufvertrag;

import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.DataLayerManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import Kaufvertrag.exceptions.DaoException;


public class Main {
    public static final String PROJECTPATH = System.getProperty("user.dir");
    public static void main(String[] args) {
        try {
            DataLayerManager dlm = DataLayerManager.getInstance();
            IDataLayer dataLayer = dlm.getDataLayer();
            ConsoleManager ui = ConsoleManager.getInstance();
            ConsoleManager.AnswerOption<Object> wareAt = ui.new AnswerOption<>(dataLayer::getDaoWare, "Eine Ware");
            ConsoleManager.AnswerOption<Object> partnerAt = ui.new AnswerOption<>(dataLayer::getDaoVertragspartner, "Einen Vertragspartner");
            ui.ConsoleOptions("Was möchten Sie persistieren?", wareAt, partnerAt);
            ui.closeScanner();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void create(){

    }
}