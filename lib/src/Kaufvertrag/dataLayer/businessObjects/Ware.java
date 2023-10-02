package Kaufvertrag.dataLayer.businessObjects;

import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ware implements IWare {
    private String bezeichnung;
    private String beschreibung;
    private long id;
    private double preis;
    private List<String> besonderheitenListe;
    private List<String> maengelListe;
    private static final Random RANDOM = new Random();
    public Ware(String bezeichnung, double preis) {
        this.bezeichnung = bezeichnung;
        this.preis = preis;

        ConsoleManager ui = ConsoleManager.getInstance();
        ConsoleManager.AnswerOption<Object> jaA = ui.new AnswerOption<>(() ->{
            String bez = ui.returnInput("Geben Sie eine Beschreibung ein.");
            setBeschreibung(bez);
            return null;}, "Ja");
        ConsoleManager.AnswerOption<Object> neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie der Ware eine Beschreibung hinzufügen?",false, jaA, neinA);

        //Frage ob Besondehieten hinzuefügt werden sollen

        jaA = ui.new AnswerOption<>(() ->{
            while(true){
                String bes = ui.returnInput("Geben Sie eine Besonderheit an.");
                getBesonderheiten().add(bes);
                ConsoleManager.AnswerOption<Object> jaA2 = ui.new AnswerOption<>(() -> null ,"ja");
                ConsoleManager.AnswerOption<Boolean> neinA2 = ui.new AnswerOption<>(() -> false ,"nein");
                Object result = ui.ConsoleOptions("Wollen Sie der Ware eine weiter Besonderheit geben?",false, jaA2, neinA2);
                if(result instanceof Boolean && (!(boolean) result))
                    break;
            }
            return null;
        }, "Ja");
        neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie der Ware Besonderheiten hinzufügen?", jaA, neinA);

        //Frage ob Mängel hinzugefügt werden sollen
        jaA = ui.new AnswerOption<>(() ->{
            while(true){
                String man = ui.returnInput("Geben Sie einen Mangel an.");
                getMaengel().add(man);
                ConsoleManager.AnswerOption<Object> jaA2 = ui.new AnswerOption<>(() -> null ,"ja");
                ConsoleManager.AnswerOption<Boolean> neinA2 = ui.new AnswerOption<>(() -> false ,"nein");
                Object result = ui.ConsoleOptions("Wollen Sie der Ware einen weiteren Mangel hinzufügen?",false, jaA2, neinA2);
                if(result instanceof Boolean && (!(boolean) result))
                    break;
            }
            return null;
        }, "Ja");
        neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie der Ware Mängel hinzufügen?", jaA, neinA);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getBezeichnung() {
        return bezeichnung;
    }

    @Override
    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public double getPreis() {
        return preis;
    }

    @Override
    public List<String> getBesonderheiten() {
        if(besonderheitenListe == null){
            besonderheitenListe = new ArrayList<>();
        }
        return besonderheitenListe;
    }

    @Override
    public List<String> getMaengel() {
        if(maengelListe == null){
            maengelListe = new ArrayList<>();
        }
        return maengelListe;
    }

    @Override
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public void setPreis(double preis) {
        this.preis = preis;
    }

    public void setId(Long id){
        this.id = id;
    }

    @Override
    public String toString() {
        return bezeichnung + ", ID: " + getId();
    }

    private Long genrateID(){
        long timestamp = System.currentTimeMillis(); // Current timestamp in milliseconds
        long randomPart = RANDOM.nextLong(); // Random long value
        return timestamp + randomPart;
    }
}
