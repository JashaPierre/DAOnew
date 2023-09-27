package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.dataLayer.businessObjects.Adresse;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.UIManager;
import Kaufvertrag.exceptions.DaoException;
import org.jdom2.Document;
import org.jdom2.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VertragspartnerDaoXml implements IDao<IVertragspartner, String> {

    /**
     * Erschafft einen neuen Vertragspartner, wenn gewünscht auch zwei und stattet ihn mit den gewünschten Paramtern aus.
     * */
    @Override
    public IVertragspartner create() {
        UIManager ui = UIManager.getInstance();
        System.out.println("Wie lautet der Vorname des Vertragspartners?");
        String vorname = ui.getScanner().next();
        System.out.println("Wie lautet der Nachname des Vertragspartners?");
        String nachname = ui.getScanner().next();
        Vertragspartner partner = new Vertragspartner(vorname, nachname);

        UIManager.AnswerOption<Object> jaA = ui.new AnswerOption<>(() -> {
            partner.setAusweisNr(""); return null;}, "Ja");
        UIManager.AnswerOption<Object> neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie dem Vertragspartner eine Ausweisnummer geben?", jaA, neinA);
        jaA = ui.new AnswerOption<>(() -> {
            String strasse = ui.returnInput(
                    "Geben Sie einen Straßennamen ein.",
                    "^[-\\p{L}\\s]*$",
                    "Kein gültiges format für einen Straßennamen."
            );
            String hausNr  = ui.returnInput(
                    "Geben Sie einen Hausnummer ein.",
                    "\\b\\d+\\S*\\b",
                    "Kein gültiges format für eine Hausnummer."
            );
            String plz  = ui.returnInput(
                    "Geben Sie einen Postleitzahl ein.",
                    "\\b\\d{5}\\b",
                    "Kein gültiges format für eine Postleitzahl."
            );
            String ort = ui.returnInput(
                    "Geben Sie einen Ort ein.",
                    "\\b\\w+\\b",
                    "Kein gültiges format für einen Ort."
            );
            partner.setAdresse(new Adresse(strasse, hausNr, plz , ort));
            return null;
            }, "Ja");
        neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie dem Vertragspartner eine Adresse zuordnen?", jaA, neinA);
        return partner;
    }

    @Override
    public void create(IVertragspartner objectToInsert) throws DaoException {
        //PLACEHOLDER
    }

    /**
     * Sucht einen Vertragspartner durch die sich im XML befindenden ID.
     * Relativ wenig Nutzen, da man alle XMLs laden muss, um mit dem ID parameter etwas suchen zu können
     * */
    @Override
    public IVertragspartner read(String id) throws DaoException {
        //return parseXMLtoPartner(id);
        ServiceXml sXML = ServiceXml.getInstance();
        if(sXML.getXMLFileList().isEmpty())
            return null;
        for (File file : sXML.getXMLFileList()){
            Document doc = sXML.readXMLFile(file);
            Element root = doc.getRootElement();
            if(root.getChild("Vertragspartner").getAttributeValue("ID").equals(id)){
                Element partnerNode = root.getChild("Vertragspartner");
                return this.parseXMLtoPartner(partnerNode);
            }
        }
        return null;
    }

    /**
     * Sucht alle XMLs, die Vertragspartner beinhalten, und gibt diese als Liste zurück
     * */
    @Override
    public List<IVertragspartner> readAll() throws DaoException {
        List<IVertragspartner> partnerList = new ArrayList<>();
        ServiceXml sXML = ServiceXml.getInstance();
        if(sXML.getXMLFileList().isEmpty())
            return null;
        for (File file : sXML.getXMLFileList()){
           Document doc = sXML.readXMLFile(file);
           Element root = doc.getRootElement();
           if(root.getChild("Vertragspartner") != null){
               Element partnerNode = root.getChild("Vertragspartner");
               partnerList.add(this.parseXMLtoPartner(partnerNode));
           }
        }
        return partnerList;
    }

    @Override
    public void update(IVertragspartner objectToUpdate) throws DaoException {
        UIManager ui = UIManager.getInstance();
        var vorname = objectToUpdate.getVorname();
        var nachname = objectToUpdate.getNachname();
        var asuweisNr = objectToUpdate.getAusweisNr();
        var adresse = objectToUpdate.getAdresse();

        var strasse = adresse.getStrasse();
        var hausNr = adresse.getHausNr();
        var plz = adresse.getPlz();
        var ort = adresse.getOrt();

        UIManager.AnswerOption<Object> createA = ui.new AnswerOption(() ->, "");

        ui.ConsoleOptions("Welchen Wert wollen Sie von diesem Vertragspartner aktualisieren?", createA);

    }

    @Override
    public void delete(String id) throws DaoException {

    }

    public IVertragspartner parseXMLtoPartner(Element partnerNode){
        String vorname = partnerNode.getChild("Vorname").getValue();
        String nachname = partnerNode.getChild("Nachname").getValue();

        String ausweisNr = Optional.ofNullable(partnerNode.getChild("AusweisNr")).map(Element::getValue).orElse("");
        String adresse_s = Optional.ofNullable(partnerNode.getChild("Adresse")).map(Element::getValue).orElse("");
        String strasse = Optional.ofNullable(partnerNode.getChild("Strasse")).map(Element::getValue).orElse("");
        String hausNr = Optional.ofNullable(partnerNode.getChild("HausNr")).map(Element::getValue).orElse("");
        String plz = Optional.ofNullable(partnerNode.getChild("PLZ")).map(Element::getValue).orElse("");
        String ort = Optional.ofNullable(partnerNode.getChild("Ort")).map(Element::getValue).orElse("");

        Vertragspartner partner = new Vertragspartner(vorname, nachname);

        if(!ausweisNr.equals(""))
            partner.setAusweisNr(ausweisNr);

        Adresse adresse;
        if(!adresse_s.equals("") && !strasse.equals("") && !hausNr.equals("") && !plz.equals("") && !ort.equals("")){
            adresse = new Adresse(strasse,hausNr,plz,ort);
            partner.setAdresse(adresse);
        }
        return partner;
    }


}
