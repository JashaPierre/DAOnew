package Kaufvertrag;

import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.DataLayerManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.UIManager;
import Kaufvertrag.exceptions.DaoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;


public class Main {
    public static final String PROJECTPATH = System.getProperty("user.dir");
    public static Scanner sc;
    public static Reader reader;
    public static void main(String[] args) {
        sc = new Scanner(System.in);
        DataLayerManager dlm = DataLayerManager.getInstance();
        UIManager ui = UIManager.getInstance();
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            IDataLayer dataLayer = dlm.getDataLayer();
            UIManager.AnswerOption<Object> wareAt = ui.new AnswerOption<>(dataLayer::getDaoWare, "Eine Ware");
            UIManager.AnswerOption<Object> partnerAt = ui.new AnswerOption<>(dataLayer::getDaoVertragspartner, "Einen Vertragspartner");
            UIManager.ConsoleOptions("Was möchten Sie persistieren?", wareAt, partnerAt);
            reader.close();
        } catch (DaoException | IOException e) {
            e.printStackTrace();
        }
        sc.close();

    }
    private static void test(){
        String test = "TEST TEST";
        System.out.println(test);
    }
}