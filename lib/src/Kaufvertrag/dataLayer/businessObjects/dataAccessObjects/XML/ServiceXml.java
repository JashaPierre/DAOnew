package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.Main;
import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.DataLayerManager;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
 public class ServiceXml {
     private static ServiceXml instance;
     private ServiceXml(){}
     public static ServiceXml getInstance(){
         if(instance == null){
             return instance = new ServiceXml();
         }
         return instance;
     }

    public void newGenericXML(String fileName, String rootName, Element... elements) {
        Document document = new Document();
        Element root = new Element(rootName);
        document.setRootElement(root);

        for (var element : elements){
            root.addContent(element);
        }

        try{
            File file = new File(folderPath(),fileName + ".xml" );
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Format format = Format.getCompactFormat();
            format.setIndent("    ");
            XMLOutputter xmlOutputter = new XMLOutputter(format);
            xmlOutputter.output(document, fileOutputStream);
            fileOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void newVertragspartnerXML(IVertragspartner vertragspartner, String fileName){
        Element vornamen = new Element("vornamen");
        Element nachname = new Element("nachname");
        Element ausweisNr = new Element("ausweisNr");
        Element adresse = new Element("adresse");

        vornamen.setText(vertragspartner.getVorname());
        nachname.setText(vertragspartner.getNachname());
        ausweisNr.setText(vertragspartner.getAusweisNr());

        Element strasse = new Element("strasse");
        Element hausNr = new Element("hausNr");
        Element plz = new Element("plz");
        Element ort = new Element("ort");
        if(vertragspartner.getAdresse() != null){
            strasse.setText(vertragspartner.getAdresse().getStrasse());
            strasse.setText(vertragspartner.getAdresse().getHausNr());
            strasse.setText(vertragspartner.getAdresse().getOrt());
            strasse.setText(vertragspartner.getAdresse().getPlz());
        }

        adresse.addContent(strasse);
        adresse.addContent(hausNr);
        adresse.addContent(plz);
        adresse.addContent(ort);

        Element[] elements = {
                vornamen,
                nachname,
                ausweisNr,
                adresse,
        };
        newGenericXML(fileName,"vertragspartner", elements);
    }

    public void readXMLFile(File xmlDatei){
        if(xmlDatei != null){
            try{
                SAXBuilder sb = new SAXBuilder();
                Document xml = sb.build(xmlDatei);
                Element rootElement =  xml.getRootElement();

            }catch (Exception e){
                System.err.println("Konnte kein Root Element finden.");
                e.printStackTrace();
            }
//            return null;
        }
        else {
            System.err.println("xmlDatei was null.");
        }
//        return null;
    }

    private String folderPath(){
       String folderPath = Main.PROJECTPATH + "./xmls";
        // Create the folder if it doesn't exist already
        File folder = new File(folderPath);
        if (!folder.exists()) {
            boolean success = folder.mkdirs();
            if (!success) {
                System.out.println("Failed to create folder " + folderPath);
            }
            return folderPath;
        }
        return folderPath;
    }

    public File getXMLFile(){
        File directory = new File(Main.PROJECTPATH + "./xmls");
        DataLayerManager dlm = DataLayerManager.getInstance();
        List<DataLayerManager.AnswerOption<File>> listOption = new ArrayList<>();
        if(directory.isDirectory()){
            File[] xmlFiles = directory.listFiles(xmlFileNameFilter());
            if(xmlFiles != null) {
                for(var file : xmlFiles){
                    DataLayerManager.AnswerOption<File> fileAt = dlm.new AnswerOption<>(() -> file, file.toString());
                    listOption.add(fileAt);
                }
            }
        }

        @SuppressWarnings("unchecked")
        DataLayerManager.AnswerOption<File>[] array = new DataLayerManager.AnswerOption[listOption.size()];
        array = listOption.toArray(array);

        return DataLayerManager.ConsoleOptions("Welche Datei möchten Sie öffnen?", array);
    }

    private FilenameFilter xmlFileNameFilter(){
        return new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml") || name.endsWith(".XML");
            }
        };
    }

    private Element AddVertragspartner(IVertragspartner iVertragspartner, String partnerType) {
        Element partnerElement = new Element(partnerType);
        partnerElement.setAttribute( "ausweisNr", iVertragspartner.getAusweisNr());

        Element vorname = new Element("vorname");
        vorname.setText(iVertragspartner.getVorname());
        partnerElement.addContent(vorname);

        Element nachname = new Element("nachname");
        nachname.setText(iVertragspartner.getNachname());
        partnerElement.addContent(nachname);

        Element adresse = new Element("adresse");
        Element strasse = new Element("strasse");
        strasse.setText(iVertragspartner.getAdresse().getStrasse());
        adresse.addContent(strasse);

        Element hausNr = new Element("hausNr");
        hausNr.setText(iVertragspartner.getAdresse().getHausNr());
        adresse.addContent(hausNr);

        Element plz = new Element("plz");
        plz.setText(iVertragspartner.getAdresse().getPlz());
        adresse.addContent(plz);

        Element ort = new Element("ort");
        ort.setText(iVertragspartner.getAdresse().getOrt());
        adresse.addContent(ort);
        partnerElement.addContent(adresse);
        return partnerElement;
    }

    private Element AddWare(IWare iWare){
        Element warenElement = new Element("ware");
        warenElement.setAttribute( "bezeichnung", iWare.getBezeichnung());

        Element beschreibung = new Element("beschreibung");
        beschreibung.setText(iWare.getBeschreibung());
        warenElement.addContent(beschreibung);

        Element preis = new Element("preis");
        preis.setText(String.valueOf(iWare.getPreis()));
        warenElement.addContent(preis);

        Element besonderheitenListe = new Element("besonderheitenListe");
        for(var bes : iWare.getBesonderheiten()){
            Element besonderheit = new Element("besonderheit");
            besonderheit.setText(bes);
            besonderheitenListe.addContent(besonderheit);
        }
        warenElement.addContent(besonderheitenListe);

        Element maengelListe = new Element("maengelListe");
        for(var mang : iWare.getMaengel()){
            Element mangel = new Element("mangel");
            mangel.setText(mang);
            maengelListe.addContent(mangel);
        }
        warenElement.addContent(maengelListe);

        return warenElement;
    }

 }
