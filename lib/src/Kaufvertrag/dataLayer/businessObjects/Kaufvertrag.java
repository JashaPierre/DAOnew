package Kaufvertrag.dataLayer.businessObjects;

import Kaufvertrag.businessObjects.IKaufvertrag;
import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;

public class Kaufvertrag implements IKaufvertrag {
    private IVertragspartner verkaeufer;
    private IVertragspartner kaeufer;
    private IWare ware;
    private String zahlungsModalitaet;

    public Kaufvertrag(IVertragspartner vertragspartner, IVertragspartner kaeufer, Ware ware) {
        this.verkaeufer = vertragspartner;
        this.kaeufer = kaeufer;
        this.ware = ware;
    }

    @Override
    public IVertragspartner getVerkaeufer() {
        return verkaeufer;
    }

    @Override
    public void setVerkaeufer(IVertragspartner verkaeufer) {
        this.verkaeufer = verkaeufer;
    }

    @Override
    public IVertragspartner getKaeufer() {
        return kaeufer;
    }

    @Override
    public void setKaeufer(IVertragspartner kaeufer) {
        this.kaeufer = kaeufer;
    }

    @Override
    public IWare getWare() {
        return ware;
    }

    @Override
    public void setWare(IWare ware) {
        this.ware = ware;
    }

    @Override
    public String getZahlungsModalitaeten() {
        return zahlungsModalitaet;
    }

    @Override
    public void setZahlungsModalitaeten(String zahlungsModalitaet) {
        this.zahlungsModalitaet = zahlungsModalitaet;
    }

    public String toString() {
        String text = "Kaufvertrag: ";
        text += "\n\tVerkäufer: " + verkaeufer;
        text += "\n\tKäufer: " + kaeufer;
        text += "\n\tWare: " + ware;
        text += "\n\tZahlungsmodalitäten: ";
        text += "\n\t\t" + zahlungsModalitaet;
        text += "\n";
        return text;
    }

}

