package dataLayer.businessObjects;

import businessObjects.IAdresse;
import businessObjects.IVertragspartner;

public class Vertragspartner implements IVertragspartner {
    private String vorname;
    private String nachname;
    private String ausweisNr;
    private IAdresse adresse;

    public Vertragspartner(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
    @Override
    public String getAusweisNr() {
        return ausweisNr;
    }
    @Override
    public void setAusweisNr(String ausweisNr) {
        this.ausweisNr = ausweisNr;
    }
    @Override
    public IAdresse getAdresse() {
        return adresse;
    }
    @Override
    public void setAdresse(IAdresse adresse) {
        this.adresse = adresse;
    }


    @Override
    public String toString() {
        String text = vorname + " " + nachname;
        text += "\n\t\tAusweisNr: " + ausweisNr;
        text += "\n\t\tAdresse: " + adresse;
        text += "\n";
        return text;
    }

}
