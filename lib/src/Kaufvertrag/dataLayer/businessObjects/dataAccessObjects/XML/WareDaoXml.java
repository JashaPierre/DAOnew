package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.Ware;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.exceptions.DaoException;
import org.jdom2.Document;
import org.jdom2.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Soll IWare später benutzen.
 * */
public class WareDaoXml implements IDao<IWare, Long> {
    @Override
    public IWare create() {
        ConsoleManager ui = ConsoleManager.getInstance();
        String bezeichnung = ui.returnInput("Geben Sie eine Bezeichnung der Ware ein");
        double preis;
        while (true){
            String preisString = ui.returnInput("Geben Sie einen Preis der Ware ein",
                    "^\\d+(,\\d{1,2})?$",
                    "Keine gültige Eingabe für einen Preis.");
            try{
                preis = Double.parseDouble(preisString);
                break;
            }catch (NumberFormatException e){
                System.out.println("Keine gültige Eingabe für einen Preis.");
            }
        }
        return new Ware(bezeichnung, preis);
    }

    @Override
    public void create(IWare objectToInsert) throws DaoException {
        ConsoleManager ui = ConsoleManager.getInstance();
        ServiceXml sXML = ServiceXml.getInstance();

        ConsoleManager.AnswerOption<String> vorhandenA = ui.new AnswerOption<>(()-> {
            File file = sXML.chooseXML(sXML.getXMLFileList(),"Vertrag");
            Document doc = sXML.readXMLFile(file);
            Element root = doc.getRootElement();

            Element newWarenknoten = sXML.newXMLWarenknoten(objectToInsert);
            root.addContent(newWarenknoten);
            sXML.saveXML(doc, file);
            return null;
        }, "Vorhandenem hinzufügen");

        ConsoleManager.AnswerOption<String> neuA = ui.new AnswerOption<>(()-> {
            String fileName = ui.returnInput("Wie soll das neue XML heißen?");
            sXML.newXML(fileName, "Vertrag", sXML.newXMLWarenknoten(objectToInsert));
            return null;
        }, "Neu erstellen");

        ui.ConsoleOptions("Wollen Sie die Ware einem vorhandenen oder einem neuem XML hinzufügen", vorhandenA, neuA);
    }

    @Override
    public IWare read(Long id) throws DaoException {
        ServiceXml sXML = ServiceXml.getInstance();
        if(sXML.getXMLFileList().isEmpty())
            return null;
        for (File file : sXML.getXMLFileList()){
            Document doc = sXML.readXMLFile(file);
            Element root = doc.getRootElement();
            if(root.getChild("Ware").getAttributeValue("ID").equals(Long.toString(id))){
                Element partnerNode = root.getChild("Vertragspartner");
                return parseXMLtoWare(partnerNode);
            }
        }
        return null;
    }

    @Override
    public List<IWare> readAll() throws DaoException {
        List<IWare> warenListe = new ArrayList<>();
        ServiceXml sXML = ServiceXml.getInstance();
        if(sXML.getXMLFileList().isEmpty())
            return null;
        for (File file : sXML.getXMLFileList()){
            Document doc = sXML.readXMLFile(file);
            Element root = doc.getRootElement();
            for(var child : root.getChildren()){
                if(child.getName().equals("Ware")) {
                    Element partnerNode = root.getChild("Ware");
                    IWare ware = parseXMLtoWare(partnerNode);
                    warenListe.add(ware);
                }
            }
        }
        return warenListe;
    }

    @Override
    public void update(IWare objectToUpdate) throws DaoException {
        ServiceXml sXML = ServiceXml.getInstance();
        ConsoleManager ui = ConsoleManager.getInstance();
        ui.updateWareUI(objectToUpdate);

        List<File> fileList = sXML.getXMLFileList();
        File openedFile = sXML.chooseXML(fileList, "Vertrag");
        Document doc = sXML.readXMLFile(openedFile);

        Element newPartnerKnoten = sXML.newXMLWarenknoten(objectToUpdate);
        doc.getRootElement().addContent(newPartnerKnoten);
    }

    @Override
    public void delete(Long id) throws DaoException {
        ServiceXml sXML = ServiceXml.getInstance();
        var jdFile = sXML.idSearchAllXML("Ware", Long.toString(id));
        Element warenKnoten = jdFile.element;
        File file = jdFile.file;
        Element root = warenKnoten.getDocument().getRootElement();
        root.removeContent(warenKnoten);
        sXML.saveXML(root.getDocument(), file);
    }

    public IWare parseXMLtoWare(Element wareKnoten){

        String bezeichnung = wareKnoten.getChild("Bezeichnung").getValue();
        String id = wareKnoten.getAttributeValue("id");
        String beschreibung = Optional.ofNullable(wareKnoten.getChild("Beschreibung")).map(Element::getValue).orElse("");
        String preis = Optional.ofNullable(wareKnoten.getChild("Preis")).map(Element::getValue).orElse("");

        Element besonderheiten = wareKnoten.getChild("Besonderheiten");
        Element maengel = wareKnoten.getChild("Maengel");

        Ware ware = new Ware(bezeichnung, Double.parseDouble(preis));

        if(!id.equals("")){
            ware.setId(Long.parseLong(id));
        }

        if(!beschreibung.equals(""))
            ware.setBeschreibung(beschreibung);

        if (besonderheiten != null) {
            for(var besonderheit : besonderheiten.getChildren()){
                ware.getBesonderheiten().add(besonderheit.getValue());
            }
        }
        if (maengel != null) {
            for(var mangel : maengel.getChildren()){
                ware.getMaengel().add(mangel.getValue());
            }
        }
        return ware;
    }
}
