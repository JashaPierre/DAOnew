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
import java.util.*;

public class VertragspartnerDaoXml implements IDao<IVertragspartner, String> {

    //Das Vertragspartner Object bräuchte dringend auch ein ID feld.
    //Hier eingebaut, weil es im Plan keine genauen vorgaben zu dieser Klasse gibt.
    public Map<IVertragspartner, String> idStore = new HashMap<>();

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
        return new Vertragspartner(vorname, nachname);
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
            root.addContent(newPartnerKnoten);
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
        idStore.clear();
        if(sXML.getXMLFileList().isEmpty())
            return null;
        for (File file : sXML.getXMLFileList()){
           Document doc = sXML.readXMLFile(file);
           Element root = doc.getRootElement();
           for(var child : root.getChildren()){
               if(child.getName().equals("Vertragspartner")) {
                   Element partnerNode = root.getChild("Vertragspartner");
                   IVertragspartner partner = parseXMLtoPartner(partnerNode);
                   partnerList.add(partner);
                   idStore.put(partner, partnerNode.getAttributeValue("id"));
               }
           }
        }
        return partnerList;
    }

    @Override
    public void update(IVertragspartner objectToUpdate) throws DaoException {
        ServiceXml sXML = ServiceXml.getInstance();
        ConsoleManager ui = ConsoleManager.getInstance();
        String id = idStore.get(objectToUpdate);
        ui.updateVertragspartnerUI(objectToUpdate);

        List<File> fileList = sXML.getXMLFileList();
        File openedFile = sXML.chooseXML(fileList, "Vertrag");
        Document doc = sXML.readXMLFile(openedFile);

        Element newPartnerKnoten = sXML.newXMLVertragspartnerknoten(objectToUpdate);
        doc.getRootElement().addContent(newPartnerKnoten);
        delete(id);
        sXML.saveXML(doc,openedFile);
    }

    /**
     * Entfernt den Knotenpunkt im XML anhand der übergebenen ID
     * */
    @Override
    public void delete(String id) throws DaoException {
        ServiceXml sXML = ServiceXml.getInstance();
        var jdFile = sXML.idSearchAllXML("Vertragspartner", id);
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
