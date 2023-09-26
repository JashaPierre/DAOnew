package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects;

import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UIManager {
    private static UIManager instance;
    private Scanner sc;
    private UIManager(){
        sc = new Scanner(System.in);
    }
    public static UIManager getInstance(){
        if(instance == null){
            return instance = new UIManager();
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

    public Object ConsoleOptions(String frage, AnswerOption<?>... answers){
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
        if(!antwortString.isEmpty()) {
            if(antwortString.length() > 150){
                String searchString = ": ";
                int insertionPoint = antwortString.indexOf(searchString) + searchString.length();
                antwortString.insert(insertionPoint, "\n");
            }
            System.out.println(antwortString);
        }
        do{
            String c = sc.next();
            try{
                int choice = Integer.parseInt(c);
                if(answers[choice-1] != null){
                    return answers[choice-1].executeCallable();
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
    }

    // AusweisNr T220001293
    // Irgendwo-Straße 33

    public String returnInput(String request, String format, String errorMessage) {
        if (!request.equals("")) {
            System.out.println(request);
        }
        String input;
        boolean useFormat = !format.equals("");
        do {
            // Consume any remaining input
            sc.next();

            input = sc.nextLine();

            if (input.matches(format) || !useFormat) {
                return input;
            } else if (!errorMessage.equals("")) {
                System.out.println(errorMessage);
            } else {
                System.out.println("Keine gültige Eingabe");
            }
        } while (true);
    }

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
}
