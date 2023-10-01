package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects;

import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConsoleManager {
    private static ConsoleManager instance;
    private Scanner sc;
    private ConsoleManager(){
        sc = new Scanner(System.in);
    }
    public static ConsoleManager getInstance(){
        if(instance == null){
            return instance = new ConsoleManager();
        }
        return instance;
    }

    public Scanner getScanner() {
        return sc;
    }

    public void closeScanner() {
        if(sc != null)
            sc.close();
    }
    //Hauptsächlich für Testzwecke
    public void setNewScanner(Scanner newScanner) {
        this.closeScanner();
        sc = newScanner; // Set the new scanner
    }

    public Object ConsoleOptions(String frage, AnswerOption<?>... answers){
        return ConsoleOptions(frage,true, answers);
    }
    public Object ConsoleOptions(String frage, boolean cancelOption, AnswerOption<?>... answers){
        StringBuilder answerString = new StringBuilder();
        if(!frage.equals("")) {
            answerString.append(frage);
        }
        boolean anyAnswertext = false;
        for (int i = 0;i < answers.length; i++){
            if(answers[i] == null){
                i--;
                continue;
            }
            if(!answers[i].answerText.isEmpty() && !anyAnswertext){
                answerString.append(": ");
                anyAnswertext = true;
            }
            answerString.append(answers[i].answerText);
            answerString.append(" (").append("\u001B[32m").append(i + 1).append("\u001B[0m").append(") ");
        }
        if(!answerString.isEmpty()) {
            if(cancelOption){
                answerString.append(" Abbrechen");
                answerString.append(" (").append("\u001B[31m").append(answers.length + 1).append("\u001B[0m").append(") ");
            }
            if(answerString.length() > 150){
                String searchString = ": ";
                int insertionPoint = answerString.indexOf(searchString) + searchString.length();
                answerString.insert(insertionPoint, "\n");
            }
            System.out.println(answerString);
        }
        do{
            String c = sc.next();
            try{
                int choice = Integer.parseInt(c);
                if(answers[choice-1] != null){
                    return answers[choice-1].executeCallable();
                }
                else
                    System.out.println("\"" +c+ "\" war Keine gültige Eingabe!" );
            }catch (Exception e){
                if(e instanceof IndexOutOfBoundsException) {
                    if(c.equals(Integer.toString(answers.length + 1)) && cancelOption){
                        return false;
                    }
                    else{
                         System.out.println("\"" +c+ "\"  als option nicht vorhanden!");
                    }
                }
                else {
                    System.out.println("\"" +c+ "\" war Keine gültige Eingabe!" );
                }
            }
        }while (true);
    }

    // AusweisNr T220001293
    // Irgendwo-Straße 33
    public String returnInput(String request) {
        return returnInput(request, "", "", -1);
    }
    public String returnInput(String request, String format) {
        return returnInput(request,format, "", -1);
    }
    public String returnInput(String request, String format, String errorMessage) {
        return returnInput(request,format, errorMessage, -1);
    }
    public String returnInput(String request, String format, String errorMessage, int iterations) {
        if (!request.equals("")) {
            System.out.println(request);
        }
        String input;
        boolean useFormat = !format.equals("");
        int i = 0;

        while (true) {
            do {
                input = sc.nextLine();
            } while (input.isBlank());

            if (input.matches(format) || !useFormat) {
                return input;
            } else if (!errorMessage.equals("")) {
                System.out.println(errorMessage);
            } else {
                System.out.println("Keine gültige Eingabe");
            }

            if (iterations != -1) {
                i++;
                if (i >= iterations) {
                    return null;
                }
            }
        }
    }

    public class AnswerOption<T> {
        private final ExecutorService es = Executors.newSingleThreadExecutor();
        private final String answerText;
        private final Callable<T> callable;
        public AnswerOption(Callable<T> callable) {
            this.callable = callable;
            this.answerText = "";
        }
        public AnswerOption(Callable<T> callable, String answerText) {
            this.callable = callable;
            this.answerText = answerText;
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
