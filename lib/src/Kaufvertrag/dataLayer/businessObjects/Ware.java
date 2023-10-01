package Kaufvertrag.dataLayer.businessObjects;

import Kaufvertrag.businessObjects.IWare;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ware implements IWare {
    private String bezeichnung;
    private String beschreibung;
    private final long ID;
    private double preis;
    private List<String> besonderheitenListe;
    private List<String> maengelListe;
    private static final Random RANDOM = new Random();
    public Ware(String bezeichnung, double preis) {
        this.bezeichnung = bezeichnung;
        this.preis = preis;
        this.ID = genrateID();
    }

    @Override
    public long getId() {
        return ID;
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
