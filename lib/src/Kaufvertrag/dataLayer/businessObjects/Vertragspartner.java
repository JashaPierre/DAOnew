package Kaufvertrag.dataLayer.businessObjects;

import Kaufvertrag.businessObjects.IAdresse;
import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.DataLayerManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.UIManager;

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
        if(!ausweisNr.equals(""))
            this.ausweisNr = ausweisNr;
        else {
            UIManager ui = UIManager.getInstance();
            this.ausweisNr = ui.returnInput(
                    "Geben Sie eine gültige Ausweisnummer ein.",
                    "[A-Z0-9]{6,12}",
                    "Kein gültiges format für eine Ausweisnummer."
            );
        }
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
