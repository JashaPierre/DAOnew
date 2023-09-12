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
            default -> throw new DaoException("Unrecognized persistence Type.");
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


    //EXTRA
    @SafeVarargs
    public static <V> V ConsoleOptions(String frage, AnswerOption<V>... answers){
        StringBuilder antwortString = new StringBuilder();
        if(!frage.equals("")) {
            antwortString.append(frage);
        }
        boolean anyAnswertext = false;
        for (int i = 0;i < answers.length; i++){
            if(!answers[i].answerText.isEmpty() && !anyAnswertext){
                antwortString.append(": ");
                anyAnswertext = true;
            }
            antwortString.append(answers[i].answerText);
            antwortString.append(" (").append("\u001B[32m").append(i + 1).append("\u001B[0m").append(") ");
        }
        V result;
        if(!antwortString.isEmpty()) {
            if(antwortString.length() > 150){
                String searchString = ": ";
                int insertionPoint = antwortString.indexOf(searchString) + searchString.length();
                antwortString.insert(insertionPoint, "\n");
            }
            System.out.println(antwortString);
        }
        do{
            String c = Main.sc.next();
            try{
                int choice = Integer.parseInt(c);
                if(answers[choice-1] != null){
                    result = answers[choice-1].executeCallable();
                    break;
                }
            }catch (Exception e){
                if(e instanceof IndexOutOfBoundsException) {
                    System.out.println("\"" +c+ "\"  als option nicht vorhanden!");
                }
                else {
                    System.out.println("\"" +c+ "\" war Keine gültige Eingabe!" );
                }
            }
        }while (true);
        return result;
    }

 /*   public static <V> Map<AnswerOption<V>, V> ConsoleOptionsAnd(AnswerOption<V>... answers){
        Map<AnswerOption<V>, V> results = new HashMap<>();
        StringBuilder antwortString = new StringBuilder();
        boolean anyAnswertext = false;
        for (int i = 0;i < answers.length; i++){
            if(!answers[i].answerText.isEmpty() && !anyAnswertext){
                antwortString.append(": ");
                anyAnswertext = true;
            }
            antwortString.append(answers[i].answerText);
            antwortString.append(" (").append("\u001B[32m").append(i).append("\u001B[0m").append(") ");
        }

        V result;
        if(!antwortString.isEmpty()) {
            System.out.println("length" + antwortString.length());
            if(antwortString.length() > 150){
                String searchString = ": ";
                int insertionPoint = antwortString.indexOf(searchString) + searchString.length();
                antwortString.insert(insertionPoint, "\n");
            }
            System.out.println(antwortString);
        }
        do{
            String c = Main.sc.next();
            try{
                int choice = Integer.parseInt(c);
                if(answers[choice] != null){
                    result = answers[choice].executeCallable();
                    if (result != null)
                        results.put(answers[choice], result);
                    break;
                }
            }catch (Exception e){
                if(e instanceof IndexOutOfBoundsException) {
                    System.out.println("\"" +c+ "\"  als option nicht vorhanden!");
                }
                else {
                    System.out.println("\"" +c+ "\" war Keine gültige Eingabe! " );
                }
            }
        }while (true);
        return results;
    }
*/


    public class AnswerOption<T> {
        private final ExecutorService es = Executors.newSingleThreadExecutor();
        private final String answerText;
        private final Callable<T> callable;
        public AnswerOption(Callable<T> callable) {
            this.answerText = "";
            this.callable = callable;
        }
        public AnswerOption(Callable<T> callable, String answerText) {
            this.answerText = answerText;
            this.callable = callable;
        }

        public T executeCallable() {
            T result = null;
            if(callable != null){
                Future<T> future = es.submit(callable);
                try{
                    result = future.get();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            es.shutdown();
            return result;
        }
    }
}
