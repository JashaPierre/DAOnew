package Kaufvertrag.dataLayer.businessObjects;

import Kaufvertrag.businessObjects.IAdresse;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;

//TODO: find out if Adress implements, extends or smth like that with Adresse (composition)
public class Adresse implements IAdresse {
    private String strasse;
    private String hausNr;
    private String plz;
    private String ort;

    public Adresse(String strasse, String hausNr, String plz, String ort) {
        this.strasse = strasse;
        this.hausNr = hausNr;
        this.plz = plz;
        this.ort = ort;
    }
    @Override
    public String getStrasse() {
        if(strasse != null && !strasse.isBlank())
            return strasse;
        return "null";
    }
    @Override
    public String getHausNr() {
        if(hausNr != null && !hausNr.isBlank())
            return hausNr;
        return "null";
    }
    @Override
    public String getPlz() {
        if(plz != null && !plz.isBlank())
            return plz;
        return "null";
    }
    @Override
    public String getOrt() {
        if(ort != null && !ort.isBlank())
            return ort;
        return "null";
    }
    @Override
    public void setStrasse(String strasse) {
        if(!strasse.equals(""))
            this.strasse = strasse;
        else {
            ConsoleManager ui = ConsoleManager.getInstance();
            this.strasse = ui.returnInput(
                    "Geben Sie einen Straßennamen ein.",
                    "^[-\\p{L}\\s]*$",
                    "Kein gültiges format für einen Straßennamen."
            );
        }
    }
    @Override
    public void setHausNr(String hausNr) {
        if(!hausNr.equals(""))
            this.hausNr = hausNr;
        else {
            ConsoleManager ui = ConsoleManager.getInstance();
            this.hausNr = ui.returnInput(
                    "Geben Sie einen Hausnummer ein.",
                    "\\b\\d+\\S*\\b",
                    "Kein gültiges format für eine Hausnummer."
            );
        }
    }
    @Override
    public void setPlz(String plz) {
        if(!plz.equals(""))
            this.plz = plz;
        else {
            ConsoleManager ui = ConsoleManager.getInstance();
            this.plz = ui.returnInput(
                    "Geben Sie einen Postleitzahl ein.",
                    "\\b\\d{5}\\b",
                    "Kein gültiges format für eine Postleitzahl."
            );
        }
    }
    @Override
    public void setOrt(String ort) {
        if(!ort.equals(""))
            this.ort = ort;
        else {
            ConsoleManager ui = ConsoleManager.getInstance();
            this.ort = ui.returnInput(
                    "Geben Sie einen Ort ein.",
                    "\\b\\w+\\b",
                    "Kein gültiges format für einen Ort."
            );
        }
    }

    @Override
    public String toString() {
        return "Straße: " + strasse + " ,Hausnummer: " + hausNr + " ,PLZ: " + plz + " ,Ort: " + ort + "\n";
    }
}
