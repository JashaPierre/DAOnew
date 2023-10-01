package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.dataLayer.businessObjects.Adresse;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.exceptions.DaoException;
import org.jdom2.Document;
import org.jdom2.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VertragspartnerDaoXml implements IDao<IVertragspartner, String> {

    /**
     * Erschafft einen neuen Vertragspartner, wenn gewünscht auch zwei und stattet ihn mit den gewünschten Parametern aus.
     * */
    @Override
    public IVertragspartner create() {
        ConsoleManager ui = ConsoleManager.getInstance();
        String vorname = ui.returnInput(
                "Wie lautet der Vorname des Vertragspartners?"
        );
        String nachname = ui.returnInput(
                "Wie lautet der Nachname des Vertragspartners?"
        );
        Vertragspartner partner = new Vertragspartner(vorname, nachname);

        ConsoleManager.AnswerOption<Object> jaA = ui.new AnswerOption<>(() -> {
            partner.setAusweisNr(""); return null;}, "Ja");
        ConsoleManager.AnswerOption<Object> neinA = ui.new AnswerOption<>(null, "Nein");
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

    /**
     * Persistiert den gewünschten Vertragspartner im XML.
     */
    @Override
    public void create(IVertragspartner objectToInsert) throws DaoException {
        ConsoleManager ui = ConsoleManager.getInstance();
        ServiceXml sXML = ServiceXml.getInstance();

        ConsoleManager.AnswerOption<String> vorhandenA = ui.new AnswerOption<>(()-> {
            File file = sXML.chooseXML(sXML.getXMLFileList(),"Vertrag");
            Document doc = sXML.readXMLFile(file);
            Element root = doc.getRootElement();

            Element newPartnerKnoten = sXML.newXMLVertragspartnerknoten(objectToInsert);
            root.setContent(newPartnerKnoten);
            sXML.saveXML(doc, file);
            return null;
        }, "Vorhandenem hinzufügen");

        ConsoleManager.AnswerOption<String> neuA = ui.new AnswerOption<>(()-> {
            String fileName = ui.returnInput("Wie soll das neue XML heißen?");
            sXML.newXML(fileName, "Vertrag", sXML.newXMLVertragspartnerknoten(objectToInsert));
            return null;
        }, "Neu erstellen");

        ui.ConsoleOptions("Wollen Sie den Vertragspartner einem vorhandenen oder einem neuem XML hinzufügen", vorhandenA, neuA);
    }

    /**
     * Sucht einen Vertragspartner durch die sich im XML befindenden ID.
     * Relativ wenig Nutzen, da man alle XMLs laden muss, um mit dem ID parameter etwas suchen zu können und man im nicht direkt ein Element übergeben kann.
     * */
    @Override
    public IVertragspartner read(String id) throws DaoException {
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
               partnerList.add(parseXMLtoPartner(partnerNode));
           }
        }
        return partnerList;
    }

    @Override
    public void update(IVertragspartner objectToUpdate) throws DaoException {
        ConsoleManager ui = ConsoleManager.getInstance();
        ServiceXml sXML = ServiceXml.getInstance();
        Adresse adresse = (Adresse) objectToUpdate.getAdresse();

        IVertragspartner updatedPartner = null;

        boolean finished = false;
        while(!finished){
            ConsoleManager.AnswerOption<Object> abschliessenA = ui.new AnswerOption<>(()-> true, "Abschließen");

            ConsoleManager.AnswerOption<Object> vornamenA = ui.new AnswerOption<>(() -> {
                String name = ui.returnInput(
                        "Wie lautet der Vorname des Vertragspartners?"
                );
                objectToUpdate.setVorname(name);
                return null;
            }, "Vorname (aktueller Wert: " + objectToUpdate.getVorname() + ")");

            ConsoleManager.AnswerOption<Object> nachnameA = ui.new AnswerOption<>(()->{
                String name = ui.returnInput(
                        "Wie lautet der Nachname des Vertragspartners?"
                );
                objectToUpdate.setNachname(name);
                return null;}, "Nachname (aktueller Wert: "+  objectToUpdate.getNachname() + ")");

            ConsoleManager.AnswerOption<Object> ausweisNrA = null;
            if(objectToUpdate.getAusweisNr() != null){
                ausweisNrA = ui.new AnswerOption<>(()-> {
                    objectToUpdate.setAusweisNr(""); return null;}, "Ausweisnummer (aktueller Wert: "+  objectToUpdate.getAusweisNr() + ")");
            }

            ConsoleManager.AnswerOption<Object> adresseA = null;
            if(adresse != null){
                adresseA = ui.new AnswerOption<>(()-> {
                    boolean finished2 = false;
                    while (!finished2){
                        ConsoleManager.AnswerOption<Object> strasseA = ui.new AnswerOption<>(()-> {
                            adresse.setStrasse(""); return null;
                        }, "Straße (aktueller Wert: "+ adresse.getStrasse() + ")");
                        ConsoleManager.AnswerOption<Object> hausNrA = ui.new AnswerOption<>(()-> {
                            adresse.setHausNr(""); return null;
                        }, "Straße (aktueller Wert: "+ adresse.getStrasse() + ")");
                        ConsoleManager.AnswerOption<Object> plzA = ui.new AnswerOption<>(()-> {
                            adresse.setPlz(""); return null;
                        }, "Straße (aktueller Wert: "+ adresse.getStrasse() + ")");
                        ConsoleManager.AnswerOption<Object> ortA = ui.new AnswerOption<>(()-> {
                            adresse.setOrt(""); return null;
                        }, "Straße (aktueller Wert: "+ adresse.getStrasse() + ")");

                        Object result = ui.ConsoleOptions("Welchen Wert der Adresse wollen Sie aktualisieren?", strasseA, hausNrA, plzA, ortA, abschliessenA);
                        if(result instanceof Boolean){
                            finished2 = (boolean) result;
                        }
                    }
                    return null;
                }, "Adresse (aktueller Wert: " + adresse.getStrasse()+" "+ adresse.getHausNr()+" "+ adresse.getPlz()+" "+ adresse.getOrt()+ ")");
            }


            Object result = ui.ConsoleOptions("Welchen Wert wollen Sie von diesem Vertragspartner aktualisieren?", vornamenA, nachnameA, ausweisNrA, adresseA, abschliessenA);
            if(result instanceof IVertragspartner) {
                updatedPartner = (IVertragspartner) result;
            }
            if(result instanceof Boolean){
                finished = (boolean) result;
            }

            if(updatedPartner != null){
                List<File> fileList = sXML.getXMLFileList();
                File openedFile = sXML.chooseXML(fileList, "Vertrag");
                Document doc = sXML.readXMLFile(openedFile);

                Element newPartnerKnoten = sXML.newXMLVertragspartnerknoten(updatedPartner);
                doc.getRootElement().setContent(newPartnerKnoten);
            }
        }
    }
    /**
     * Entfernt den Knotenpunkt im XML anhand der übergebenen ID
     * */

    @Override
    public void delete(String id) throws DaoException {
        ServiceXml sXML = ServiceXml.getInstance();
        var jdFile = sXML.idSeachAllXml("Vertragspartner",id);
        Element partnerKnoten = jdFile.element;
        File file = jdFile.file;
        Element root = partnerKnoten.getDocument().getRootElement();
        root.removeContent(partnerKnoten);
        sXML.saveXML(root.getDocument(), file);
    }



    /**
     * Übergibt ein Vertragspartnerknotenelement und baut einen neuen Vertragspartner daraus.
     * */
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
