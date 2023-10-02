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
        Ware ware = new Ware(bezeichnung, preis);

        ConsoleManager.AnswerOption<Object> jaA = ui.new AnswerOption<>(() ->{
            String bez = ui.returnInput("Geben Sie eine Beschreibung ein.");
            ware.setBeschreibung(bez);
            return null;}, "Ja");
        ConsoleManager.AnswerOption<Object> neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie der Ware eine Beschreibung hinzufügen?",false, jaA, neinA);

        //Frage ob Besondehieten hinzuefügt werden sollen

       jaA = ui.new AnswerOption<>(() ->{
           while(true){
               String bes = ui.returnInput("Geben Sie eine Besonderheit an.");
               ware.getBesonderheiten().add(bes);
               ConsoleManager.AnswerOption<Object> jaA2 = ui.new AnswerOption<>(() -> null ,"ja");
               ConsoleManager.AnswerOption<Boolean> neinA2 = ui.new AnswerOption<>(() -> false ,"nein");
               Object result = ui.ConsoleOptions("Wollen Sie der Ware eine weiter Besonderheit geben?",false, jaA2, neinA2);
               if(result instanceof Boolean && (!(boolean) result))
                   break;
           }
            return null;
           }, "Ja");
        neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie der Ware Besonderheiten hinzufügen?", jaA, neinA);

        //Frage ob Mängel hinzugefügt werden sollen
        jaA = ui.new AnswerOption<>(() ->{
            while(true){
                String man = ui.returnInput("Geben Sie einen Mangel an.");
                ware.getMaengel().add(man);
                ConsoleManager.AnswerOption<Object> jaA2 = ui.new AnswerOption<>(() -> null ,"ja");
                ConsoleManager.AnswerOption<Boolean> neinA2 = ui.new AnswerOption<>(() -> false ,"nein");
                Object result = ui.ConsoleOptions("Wollen Sie der Ware einen weiteren Mangel hinzufügen?",false, jaA2, neinA2);
                if(result instanceof Boolean && (!(boolean) result))
                    break;
            }
            return null;
        }, "Ja");
        neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie der Ware Mängel hinzufügen?", jaA, neinA);

        return ware;
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
        ConsoleManager ui = ConsoleManager.getInstance();
        ServiceXml sXML = ServiceXml.getInstance();

        IWare updatedWare = null;

        while (true){
            ConsoleManager.AnswerOption<Object> bezeichnungA = ui.new AnswerOption<>(() -> {
                String bezeichnung = ui.returnInput(
                        "Wie lautet der Bezeichnung des Vertragspartners?"
                );
                objectToUpdate.setBezeichnung(bezeichnung);
                return null;}, "Bezeichnung (aktueller Wert: " + objectToUpdate.getBezeichnung() + ")");

            ConsoleManager.AnswerOption<Object> beschreibungA = ui.new AnswerOption<>(()->{
                String beschreibung = ui.returnInput(
                        "Was soll die neue Beschreibung sein?"
                );
                objectToUpdate.setBeschreibung(beschreibung);
                return null;}, "Beschreibung (aktueller Wert: "+  objectToUpdate.getBeschreibung() + ")");

            ConsoleManager.AnswerOption<Object> preisA = ui.new AnswerOption<>(()->{
                String preis = ui.returnInput(
                        "Was soll der neue Preis sein?"
                );
                objectToUpdate.setPreis(Long.parseLong(preis));
                return null;}, "Preis (aktueller Wert: "+  objectToUpdate.getPreis() + ")");

            ConsoleManager.AnswerOption<Object> besonderheitenA = ui.new AnswerOption<>(()->{
                boolean finished2 = false;
                while (!finished2){
                    List<ConsoleManager.AnswerOption<String>> besonheitAListe = new ArrayList<>();

                    for(String besonderheit : objectToUpdate.getBesonderheiten()){
                        if(!besonderheit.equals("")){
                            ConsoleManager.AnswerOption<String> besonderheitA = ui.new AnswerOption<>(() ->
                                    besonderheit, besonderheit);
                            besonheitAListe.add(besonderheitA);
                        }
                    }

                    @SuppressWarnings("unchecked")
                    ConsoleManager.AnswerOption<Element>[] array = new ConsoleManager.AnswerOption[besonheitAListe.size()];
                    array = besonheitAListe.toArray(array);
                    String besonderheit = (String) ui.ConsoleOptions("Welche besonderheit möchten Sie bearbeiten?", array);
                    int index = objectToUpdate.getBesonderheiten().indexOf(besonderheit);
                    String neuBesonderheit =  ui.returnInput("Geben Sie eine neue Besonderheit an.");
                    objectToUpdate.getBesonderheiten().set(index, neuBesonderheit);

                    ConsoleManager.AnswerOption<Object> jaA = ui.new AnswerOption<>( null, "ja");
                    ConsoleManager.AnswerOption<Object> neinA = ui.new AnswerOption<>( ()-> true, "ja");

                    finished2 = (Boolean) ui.ConsoleOptions("Möchten Sie eine weitere Besonderheit anpassen?",false, jaA, neinA);

                }
                return null;}, "Besonderheiten");

            ConsoleManager.AnswerOption<Object> maengelA = ui.new AnswerOption<>(()->{
                boolean finished2 = false;
                while (!finished2){
                    List<ConsoleManager.AnswerOption<String>> maengelAListe = new ArrayList<>();

                    for(String mangel : objectToUpdate.getMaengel()){
                        if(!mangel.equals("")){
                            ConsoleManager.AnswerOption<String> mangelA = ui.new AnswerOption<>(() ->
                                    mangel, mangel);
                            maengelAListe.add(mangelA);
                        }
                    }

                    @SuppressWarnings("unchecked")
                    ConsoleManager.AnswerOption<Element>[] array = new ConsoleManager.AnswerOption[maengelAListe.size()];
                    array = maengelAListe.toArray(array);
                    String mangel = (String) ui.ConsoleOptions("Welchen Mangel möchten Sie bearbeiten?", array);
                    int index = objectToUpdate.getMaengel().indexOf(mangel);
                    String neuerMangel =  ui.returnInput("Geben Sie eine neue Mangel an.");
                    objectToUpdate.getMaengel().set(index, neuerMangel);

                    ConsoleManager.AnswerOption<Object> jaA = ui.new AnswerOption<>( null, "ja");
                    ConsoleManager.AnswerOption<Object> neinA = ui.new AnswerOption<>( ()-> true, "ja");

                    finished2 = (Boolean) ui.ConsoleOptions("Möchten Sie eine weitere Mangel anpassen?",false, jaA, neinA);

                }
                return null;}, "Mängel");


            Object result = ui.ConsoleOptions("Welchen Wert wollen Sie von dieser Ware aktualisieren?", bezeichnungA, beschreibungA, preisA, besonderheitenA, maengelA);
            if(result instanceof Boolean && (!(boolean) result)){
                break;
            }

            List<File> fileList = sXML.getXMLFileList();
            File openedFile = sXML.chooseXML(fileList, "Vertrag");
            Document doc = sXML.readXMLFile(openedFile);

            Element newPartnerKnoten = sXML.newXMLWarenknoten(updatedWare);
            doc.getRootElement().addContent(newPartnerKnoten);

        }
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
