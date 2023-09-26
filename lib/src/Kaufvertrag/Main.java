package Kaufvertrag;

import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.DataLayerManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.UIManager;
import Kaufvertrag.exceptions.DaoException;

import java.io.Reader;


public class Main {
    public static final String PROJECTPATH = System.getProperty("user.dir");    public static Reader reader;
    public static void main(String[] args) {
        try {
            DataLayerManager dlm = DataLayerManager.getInstance();
            IDataLayer dataLayer = dlm.getDataLayer();
            UIManager ui = UIManager.getInstance();
            UIManager.AnswerOption<Object> wareAt = ui.new AnswerOption<>(dataLayer::getDaoWare, "Eine Ware");
            UIManager.AnswerOption<Object> partnerAt = ui.new AnswerOption<>(dataLayer::getDaoVertragspartner, "Einen Vertragspartner");
            ui.ConsoleOptions("Was möchten Sie persistieren?", wareAt, partnerAt);
            ui.closeScanner();
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }
}