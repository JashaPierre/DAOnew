package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects;

import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.Adresse;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;
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


    public void updateVertragspartnerUI(IVertragspartner partner){
        ConsoleManager ui = ConsoleManager.getInstance();

        while (true) {
            ConsoleManager.AnswerOption<Object> vornamenA = ui.new AnswerOption<>(() -> {
                String name = ui.returnInput(
                        "Wie lautet der Vorname des Vertragspartners?"
                );
                partner.setVorname(name);
                return null;
            }, "Vorname (aktueller Wert: " + partner.getVorname() + ")");

            ConsoleManager.AnswerOption<Object> nachnameA = ui.new AnswerOption<>(() -> {
                String name = ui.returnInput(
                        "Wie lautet der Nachname des Vertragspartners?"
                );
                partner.setNachname(name);
                return null;
            }, "Nachname (aktueller Wert: " + partner.getNachname() + ")");

            ConsoleManager.AnswerOption<Object> ausweisNrA = ui.new AnswerOption<>(() -> {
                partner.setAusweisNr("");
                return null;
            }, "Ausweisnummer (aktueller Wert: " + partner.getAusweisNr() + ")");


            String adresswert = "null";
            if(partner.getAdresse() != null)
                adresswert = partner.getAdresse().toString();
            ConsoleManager.AnswerOption<Object> adresseA = ui.new AnswerOption<>(() -> {
                if(partner.getAdresse() == null)
                    partner.setAdresse(new Adresse("null","null","null","null"));
                while (true) {
                    ConsoleManager.AnswerOption<Object> strasseA = ui.new AnswerOption<>(() -> {
                        partner.getAdresse().setStrasse("");
                        return null;
                    }, "Straße (aktueller Wert: " + partner.getAdresse().getStrasse() + ")");
                    ConsoleManager.AnswerOption<Object> hausNrA = ui.new AnswerOption<>(() -> {
                        partner.getAdresse().setHausNr("");
                        return null;
                    }, "Haus Nummer (aktueller Wert: " + partner.getAdresse().getHausNr() + ")");
                    ConsoleManager.AnswerOption<Object> plzA = ui.new AnswerOption<>(() -> {
                        partner.getAdresse().setPlz("");
                        return null;
                    }, "Postleitzahl (aktueller Wert: " + partner.getAdresse().getPlz() + ")");
                    ConsoleManager.AnswerOption<Object> ortA = ui.new AnswerOption<>(() -> {
                        partner.getAdresse().setOrt("");
                        return null;
                    }, "Ort (aktueller Wert: " + partner.getAdresse().getOrt() + ")");

                    Object result = ui.ConsoleOptions("Welchen Wert der Adresse wollen Sie aktualisieren?", strasseA, hausNrA, plzA, ortA);
                    if (result instanceof Boolean && (!(boolean) result)) {
                        break;
                    }
                }
                return null;
            }, "Adresse (aktueller Wert: " + adresswert + ")");

            System.out.println("test");
            Object result = ui.ConsoleOptions("Welchen Wert wollen Sie von diesem Vertragspartner aktualisieren?", vornamenA, nachnameA, ausweisNrA, adresseA);

            if(result instanceof Boolean && (!(boolean) result)) {
                break;
            }
        }
    }

    public void  updateWareUI(IWare ware){
        ConsoleManager ui = ConsoleManager.getInstance();

        while (true) {
            ConsoleManager.AnswerOption<Object> bezeichnungA = ui.new AnswerOption<>(() -> {
                String bezeichnung = ui.returnInput(
                        "Wie lautet der Bezeichnung des Vertragspartners?"
                );
                ware.setBezeichnung(bezeichnung);
                return null;
            }, "Bezeichnung (aktueller Wert: " + ware.getBezeichnung() + ")");

            ConsoleManager.AnswerOption<Object> beschreibungA = ui.new AnswerOption<>(() -> {
                String beschreibung = ui.returnInput(
                        "Was soll die neue Beschreibung sein?"
                );
                ware.setBeschreibung(beschreibung);
                return null;
            }, "Beschreibung (aktueller Wert: " + ware.getBeschreibung() + ")");

            ConsoleManager.AnswerOption<Object> preisA = ui.new AnswerOption<>(() -> {
                String preis = ui.returnInput(
                        "Was soll der neue Preis sein?"
                );
                ware.setPreis(Long.parseLong(preis));
                return null;
            }, "Preis (aktueller Wert: " + ware.getPreis() + ")");

            ConsoleManager.AnswerOption<Object> besonderheitenA = ui.new AnswerOption<>(() -> {
                boolean finished2 = false;
                while (!finished2) {
                    List<AnswerOption<String>> besonheitAListe = new ArrayList<>();

                    for (String besonderheit : ware.getBesonderheiten()) {
                        if (!besonderheit.equals("")) {
                            ConsoleManager.AnswerOption<String> besonderheitA = ui.new AnswerOption<>(() ->
                                    besonderheit, besonderheit);
                            besonheitAListe.add(besonderheitA);
                        }
                    }

                    @SuppressWarnings("unchecked")
                    ConsoleManager.AnswerOption<Element>[] array = new ConsoleManager.AnswerOption[besonheitAListe.size()];
                    array = besonheitAListe.toArray(array);
                    String besonderheit = (String) ui.ConsoleOptions("Welche besonderheit möchten Sie bearbeiten?", array);
                    int index = ware.getBesonderheiten().indexOf(besonderheit);
                    String neuBesonderheit = ui.returnInput("Geben Sie eine neue Besonderheit an.");
                    ware.getBesonderheiten().set(index, neuBesonderheit);

                    ConsoleManager.AnswerOption<Object> jaA = ui.new AnswerOption<>(null, "ja");
                    ConsoleManager.AnswerOption<Object> neinA = ui.new AnswerOption<>(() -> true, "ja");

                    finished2 = (Boolean) ui.ConsoleOptions("Möchten Sie eine weitere Besonderheit anpassen?", false, jaA, neinA);

                }
                return null;
            }, "Besonderheiten");

            ConsoleManager.AnswerOption<Object> maengelA = ui.new AnswerOption<>(() -> {
                boolean finished2 = false;
                while (!finished2) {
                    List<ConsoleManager.AnswerOption<String>> maengelAListe = new ArrayList<>();

                    for (String mangel : ware.getMaengel()) {
                        if (!mangel.equals("")) {
                            ConsoleManager.AnswerOption<String> mangelA = ui.new AnswerOption<>(() ->
                                    mangel, mangel);
                            maengelAListe.add(mangelA);
                        }
                    }
                    ConsoleManager.AnswerOption<String> newMangelA = ui.new AnswerOption<>(() ->
                    {
                        return null;
                    }, "neuen mangel hinzufügen");
                    maengelAListe.add(newMangelA);

                    @SuppressWarnings("unchecked")
                    ConsoleManager.AnswerOption<Element>[] array = new ConsoleManager.AnswerOption[maengelAListe.size()];
                    array = maengelAListe.toArray(array);
                    String mangel = (String) ui.ConsoleOptions("Welchen Mangel möchten Sie bearbeiten?", array);
                    int index = ware.getMaengel().indexOf(mangel);
                    String neuerMangel = ui.returnInput("Geben Sie eine neue Mangel an.");
                    ware.getMaengel().set(index, neuerMangel);

                    ConsoleManager.AnswerOption<Object> jaA = ui.new AnswerOption<>(null, "ja");
                    ConsoleManager.AnswerOption<Object> neinA = ui.new AnswerOption<>(() -> true, "ja");

                    finished2 = (Boolean) ui.ConsoleOptions("Möchten Sie eine weitere Mangel anpassen?", false, jaA, neinA);

                }
                return null;
            }, "Mängel");


            Object result = ui.ConsoleOptions("Welchen Wert wollen Sie von dieser Ware aktualisieren?", bezeichnungA, beschreibungA, preisA, besonderheitenA, maengelA);
            if (result instanceof Boolean && (!(boolean) result)) {
                break;
            }
        }
    }
}
