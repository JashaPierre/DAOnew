package Kaufvertrag.dataLayer.businessObjects;

import Kaufvertrag.businessObjects.IAdresse;
import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;

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
        if(vorname != null && !vorname.isBlank())
            return vorname;
        return "null";
    }
    public String getNachname() {
        if(nachname != null && !nachname.isBlank())
            return nachname;
        return "null";
    }
    @Override
    public String getAusweisNr() {
        if(ausweisNr != null && !ausweisNr.isBlank())
            return ausweisNr;
        return "null";
    }
    @Override
    public IAdresse getAdresse() {
         return adresse;
    }
    public void setVorname(String vorname) {
        if(!vorname.equals(""))
            this.vorname = vorname;
        else {
            ConsoleManager ui = ConsoleManager.getInstance();
            this.vorname = ui.returnInput(
                    "Wie lautet der Vorname des Vertragspartners?"
            );
        }
    }
    public void setNachname(String nachname) {
        if(!nachname.equals(""))
            this.nachname = nachname;
        else {
            ConsoleManager ui = ConsoleManager.getInstance();
            this.nachname = ui.returnInput(
                    "Wie lautet der Nachname des Vertragspartners?"
            );
        }
    }

    @Override
    public void setAusweisNr(String ausweisNr) {
        if(!ausweisNr.equals(""))
            this.ausweisNr = ausweisNr;
        else {
            ConsoleManager ui = ConsoleManager.getInstance();
            this.ausweisNr = ui.returnInput(
                    "Geben Sie eine gültige Ausweisnummer ein.",
                    "[A-Z0-9]{6,12}",
                    "Kein gültiges format für eine Ausweisnummer."
            );
        }
    }
    @Override
    public void setAdresse(IAdresse adresse) {
        this.adresse = adresse;
    }


    @Override
    public String toString() {
        String text = vorname + " " + nachname;
        text += ", AusweisNr: " + ausweisNr;
        text += ", Adresse: " + adresse;
        return text;
    }


}
