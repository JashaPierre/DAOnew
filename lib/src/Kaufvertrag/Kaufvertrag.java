package Kaufvertrag;

public class Kaufvertrag {
    private Vertragspartner verkaeufer;
    private Vertragspartner kaeufer;
    private Ware ware;
    private String zahlungsModalitaet;

    public Kaufvertrag(Vertragspartner vertragspartner, Vertragspartner kaeufer, Ware ware) {
        this.verkaeufer = vertragspartner;
        this.kaeufer = kaeufer;
        this.ware = ware;
    }

    public Vertragspartner getVerkaeufer() {
        return verkaeufer;
    }

    public void setVerkaeufer(Vertragspartner verkaeufer) {
        this.verkaeufer = verkaeufer;
    }

    public Vertragspartner getKaeufer() {
        return kaeufer;
    }

    public void setKaeufer(Vertragspartner kaeufer) {
        this.kaeufer = kaeufer;
    }

    public Ware getWare() {
        return ware;
    }

    public void setWare(Ware ware) {
        this.ware = ware;
    }

    public String getZahlungsModalitaet() {
        return zahlungsModalitaet;
    }

    public void setZahlungsModalitaet(String zahlungsModalitaet) {
        this.zahlungsModalitaet = zahlungsModalitaet;
    }

    @Override
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

