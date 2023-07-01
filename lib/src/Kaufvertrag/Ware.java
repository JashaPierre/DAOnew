package Kaufvertrag;

import java.util.List;

public class Ware {
    private String bezeichnung;
    private String beschreibung;
    private double preis;
    private List<String> besonderheitenListe;
    private List<String> maengelListe;

    public Ware(String bezeichnung, double preis) {
        this.bezeichnung = bezeichnung;
        this.preis = preis;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public List<String> getBesonderheiten() {
        return besonderheitenListe;
    }

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
