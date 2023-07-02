package Kaufvertrag.dataLayer.businessObjects;

import Kaufvertrag.businessObjects.IWare;

import java.util.List;

public class Ware implements IWare {
    private String bezeichnung;
    private String beschreibung;
    private long getId;
    private double preis;
    private List<String> besonderheitenListe;
    private List<String> maengelListe;
    public Ware(String bezeichnung, double preis) {
        this.bezeichnung = bezeichnung;
        this.preis = preis;
    }

    @Override
    public long getId() {
        return getId;
    }

    @Override
    public String getBezeichnung() {
        return bezeichnung;
    }

    @Override
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public double getPreis() {
        return preis;
    }

    @Override
    public void setPreis(double preis) {
        this.preis = preis;
    }

    @Override
    public List<String> getBesonderheiten() {
        return besonderheitenListe;
    }

    @Override
    public List<String> getMaengel() {
        return maengelListe;
    }

    @Override
    public String toString() {
        String text = "\n\t\tBezeichnung: " + bezeichnung;
        text += "\n\t\tBeschreibung: " + beschreibung;
        text += "\n\t\tPreis: " + preis;
        text += "\n\t\tBesonderheiten: " + besonderheitenListe;
        text += "\n\t\tMängel: " + maengelListe;
        text += "\n";
        return text;
    }
}
