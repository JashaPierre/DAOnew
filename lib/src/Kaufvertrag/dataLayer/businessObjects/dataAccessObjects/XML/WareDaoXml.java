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
        ui.ConsoleOptions("Möchten Sie der Ware eine Beschreibung hinzufügen?", jaA, neinA);

        //Frage ob Besondehieten hinzuefügt werden sollen

       jaA = ui.new AnswerOption<>(() ->{
           boolean finished = false;
           while(!finished){
               String bes = ui.returnInput("Geben Sie eine Besonderheit an.");
               ware.getBesonderheiten().add(bes);
               ConsoleManager.AnswerOption<Object> jaA2 = ui.new AnswerOption<>(() -> false ,"ja");
               ConsoleManager.AnswerOption<Boolean> neinA2 = ui.new AnswerOption<>(() -> true ,"nein");
               finished = (Boolean) ui.ConsoleOptions("Wollen Sie der Ware eine weiter Besonderheit geben?", jaA2, neinA2);
           }
            return null;
           }, "Ja");
        neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie der Ware Besonderheiten hinzufügen?", jaA, neinA);

        //Frage ob Mängel hinzugefügt werden sollen
        jaA = ui.new AnswerOption<>(() ->{
            boolean finished = false;
            while(!finished){
                String man = ui.returnInput("Geben Sie einen Mangel an.");
                ware.getMaengel().add(man);
                ConsoleManager.AnswerOption<Object> jaA2 = ui.new AnswerOption<>(() -> false ,"ja");
                ConsoleManager.AnswerOption<Boolean> neinA2 = ui.new AnswerOption<>(() -> true ,"nein");
                finished = (Boolean) ui.ConsoleOptions("Wollen Sie der Ware eine weiter Besonderheit geben?", jaA2, neinA2);
            }
            return null;
        }, "Ja");
        neinA = ui.new AnswerOption<>(null, "Nein");
        ui.ConsoleOptions("Möchten Sie der Ware Mängel hinzufügen?", jaA, neinA);

        return ware;
    }

    @Override
    public void create(IWare objectToInsert) throws DaoException {
        ServiceXml sXML = ServiceXml.getInstance();
        Object[] docAndFile = sXML.nameSeachAllXml(objectToInsert.getBezeichnung());
        Element oldPartnerknoten = (Element) docAndFile[0];
        File file = (File) docAndFile[1];
        Element root = oldPartnerknoten.getParentElement();
        objectToInsert = create();
        Element newPartnerKnoten = sXML.newXMLWarenknoten((Ware) objectToInsert);

        root.setContent(newPartnerKnoten);
        root.removeContent(oldPartnerknoten);

        sXML.saveXML(root.getDocument(), file);
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
        List<IWare> partnerList = new ArrayList<>();
        ServiceXml sXML = ServiceXml.getInstance();
        if(sXML.getXMLFileList().isEmpty())
            return null;
        for (File file : sXML.getXMLFileList()){
            Document doc = sXML.readXMLFile(file);
            Element root = doc.getRootElement();
            if(root.getChild("Ware") != null){
                Element warenKnoten = root.getChild("Ware");
                partnerList.add(parseXMLtoWare(warenKnoten));
            }
        }
        return partnerList;
    }

    @Override
    public void update(IWare objectToUpdate) throws DaoException {
        ConsoleManager ui = ConsoleManager.getInstance();

        boolean finished = false;
        while (!finished){
            ConsoleManager.AnswerOption<Object> abschliessenA = ui.new AnswerOption<>(()-> true, "Abschließen");
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

                    finished2 = (Boolean) ui.ConsoleOptions("Möchten Sie eine weitere Besonderheit anpassen?", jaA, neinA);

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

                    finished2 = (Boolean) ui.ConsoleOptions("Möchten Sie eine weitere Mangel anpassen?", jaA, neinA);

                }
                return null;}, "Mängel");


            Object result = ui.ConsoleOptions("Welchen Wert wollen Sie von diesem Vertragspartner aktualisieren?", bezeichnungA, beschreibungA, preisA, besonderheitenA, maengelA, abschliessenA);
            if(result instanceof Boolean){
                finished = (boolean) result;
            }
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        ServiceXml sXML = ServiceXml.getInstance();
        Object[] docAndFile = sXML.idSeachAllXml(Long.toString(id));
        Element warenKnoten = (Element) docAndFile[0];
        File file = (File) docAndFile[1];
        Element root = warenKnoten.getParentElement();

        root.removeContent(warenKnoten);

        sXML.saveXML(root.getDocument(), file);
    }

    public IWare parseXMLtoWare(Element wareKnoten){

        String bezeichnung = wareKnoten.getChild("Bezeichnung").getValue();
        String beschreibung = Optional.ofNullable(wareKnoten.getChild("Beschreibung")).map(Element::getValue).orElse("");
//        String id = Optional.ofNullable(wareKnoten.getChild("ID")).map(Element::getValue).orElse("");
        String preis = Optional.ofNullable(wareKnoten.getChild("Preis")).map(Element::getValue).orElse("");

        Element besonderheiten = wareKnoten.getChild("Besonderheiten");
        Element maengel = wareKnoten.getChild("Maengel");

        Ware ware = new Ware(bezeichnung, Long.parseLong(preis));

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
