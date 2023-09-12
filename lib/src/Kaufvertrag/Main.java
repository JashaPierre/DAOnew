package Kaufvertrag;

import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.DataLayerManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import Kaufvertrag.exceptions.DaoException;

import java.util.Scanner;


public class Main {
    public static final String PROJECTPATH = System.getProperty("user.dir");
    public static Scanner sc;
    public static void main(String[] args) {
        sc = new Scanner(System.in);
        DataLayerManager dlm = DataLayerManager.getInstance();
        try {
            IDataLayer dataLayer = dlm.getDataLayer();
            DataLayerManager.AnswerOption<Object> wareAt = dlm.new AnswerOption<>(() -> {
                return dataLayer.getDaoWare();
            }, "Eine Ware");
            DataLayerManager.AnswerOption<Object> partnerAt = dlm.new AnswerOption<>(() -> {
                return dataLayer.getDaoVertragspartner();
            }, "Einen Vertragspartner");
            DataLayerManager.ConsoleOptions("Was möchten Sie persitieren?", wareAt, partnerAt);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        sc.close();
    }
    private static void test(){
        String test = "TEST TEST";
        System.out.println(test);
    }
}