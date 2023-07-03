package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.Main;
import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.Kaufvertrag;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileOutputStream;
import java.io.IOException;

public class ServiceXml {
    public void SerializeXml(Kaufvertrag kaufvertrag) throws IOException {
        Document document = new Document();
        Element root = new Element("kaufvertrag");
        document.setRootElement(root);

        //Käufer
        Element kaeufer = AddVertragspartner(kaufvertrag.getKaeufer(), "kaeufer");
        //Verkäufer
        Element verkaeufer = AddVertragspartner(kaufvertrag.getVerkaeufer(), "verkaeufer");
        //Ware
        Element AddWare = AddWare(kaufvertrag.getWare());
        Element zahlung = new Element("zahlung");
        zahlung.setText("Privater Barverkauf");

        document.getRootElement().addContent(kaeufer);
        document.getRootElement().addContent(verkaeufer);
        document.getRootElement().addContent(AddWare);
        document.getRootElement().addContent(zahlung);

        String datei =  Main.projectPath + "/XML.xml";
        FileOutputStream fileOutputStream = new FileOutputStream(datei);
        Format format = Format.getCompactFormat();
        format.setIndent("    ");
        XMLOutputter xmlOutputter = new XMLOutputter(format);
        xmlOutputter.output(document, fileOutputStream);
    }

    private Element AddVertragspartner(IVertragspartner partner, String partnerType) {

        Element partnerElement = new Element(partnerType);
        partnerElement.setAttribute( "ausweisNr", partner.getAusweisNr());

        Element vorname = new Element("vorname");
        vorname.setText(partner.getVorname());
        partnerElement.addContent(vorname);

        Element nachname = new Element("nachname");
        nachname.setText(partner.getNachname());
        partnerElement.addContent(nachname);

        Element adresse = new Element("adresse");
        Element strasse = new Element("strasse");
        strasse.setText(partner.getAdresse().getStrasse());
        adresse.addContent(strasse);

        Element hausNr = new Element("hausNr");
        hausNr.setText(partner.getAdresse().getHausNr());
        adresse.addContent(hausNr);

        Element plz = new Element("plz");
        plz.setText(partner.getAdresse().getPlz());
        adresse.addContent(plz);

        Element ort = new Element("ort");
        ort.setText(partner.getAdresse().getOrt());
        adresse.addContent(ort);
        partnerElement.addContent(adresse);
        return partnerElement;
    }

    private Element AddWare(IWare ware){
        Element warenElement = new Element("ware");
        warenElement.setAttribute( "bezeichnung", ware.getBezeichnung());

        Element beschreibung = new Element("beschreibung");
        beschreibung.setText(ware.getBeschreibung());
        warenElement.addContent(beschreibung);

        Element preis = new Element("preis");
        preis.setText(String.valueOf(ware.getPreis()));
        warenElement.addContent(preis);

        Element besonderheitenListe = new Element("besonderheitenListe");
        for(var bes : ware.getBesonderheiten()){
            Element besonderheit = new Element("besonderheit");
            besonderheit.setText(bes);
            besonderheitenListe.addContent(besonderheit);
        }
        warenElement.addContent(besonderheitenListe);

        Element maengelListe = new Element("maengelListe");
        for(var mang : ware.getMaengel()){
            Element mangel = new Element("mangel");
            mangel.setText(mang);
            maengelListe.addContent(mangel);
        }
        warenElement.addContent(maengelListe);

        return warenElement;
    }

}
