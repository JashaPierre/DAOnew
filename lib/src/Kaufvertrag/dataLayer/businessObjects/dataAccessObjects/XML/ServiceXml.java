package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.Main;
import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class ServiceXml {
     private static ServiceXml instance;
     private final Path XMLFOLDERPATH = Paths.get(Main.PROJECTPATH ).resolve("xmls");
     private ServiceXml(){}
     public static ServiceXml getInstance(){
         if(instance == null){
             return instance = new ServiceXml();
         }
         return instance;
     }

     /**
      * Gibt den XML-Ordner Pfad zurück. Sollte er nicht existieren, wird ein neuer Ordner erstellt.
      * */
     public Path checkForXMLsFolder(){
         if(Files.exists(XMLFOLDERPATH) && Files.isDirectory(XMLFOLDERPATH)){
             return XMLFOLDERPATH;
         }
         else{
             try {
                 System.out.println("Ordner Hinzugefügt");
                 return Files.createDirectory(XMLFOLDERPATH);
             } catch (IOException e) {
                 System.err.println("Fehler beim erstellen des Ordners: " + e.getMessage());
             }
         }
         return null;
     }


     /**
      * Einfaches XML file mit Rootknoten aufsetzen
      * */
    public void newXML(String fileName, String rootName, Element... elements) {
        Document document = new Document();
        Element root = new Element(rootName);
        document.setRootElement(root);

        for(var element : elements){
            root.addContent(element);
        }

        Path folderPath = checkForXMLsFolder();
        Path filePath = folderPath.resolve(fileName + ".xml");
        File file = filePath.toFile();

        saveXML(document, file);
    }

     public void saveXML(Document document, File file) {
         createUniqueID(document);

         try{
             FileOutputStream fileOutputStream = new FileOutputStream(file);
             Format format = Format.getCompactFormat();
             format.setIndent("    ");
             XMLOutputter xmlOutputter = new XMLOutputter(format);
             xmlOutputter.output(document, fileOutputStream);
             fileOutputStream.close();

             System.out.println("XML Document gespeichert unter: " + checkForXMLsFolder().toString());
         }catch (Exception e){
             e.printStackTrace();
         }
     }

     /**
     * Einen Vertragspartnerknoten hinzufügen
     * */
     public Element newXMLVertragspartnerknoten(IVertragspartner vertragspartner){
         Element partner = new Element("Vertragspartner");

         Element vorname = new Element("Vorname");
         Element nachname = new Element("Nachname");
         Element ausweisNr = new Element("AusweisNr");
         Element adresse = new Element("Adresse");

         vorname.setText(vertragspartner.getVorname());
         nachname.setText(vertragspartner.getNachname());
         ausweisNr.setText(vertragspartner.getAusweisNr());

         Element strasse = new Element("Strasse");
         Element hausNr = new Element("HausNr");
         Element plz = new Element("PLZ");
         Element ort = new Element("Ort");
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

         partner.addContent(vorname);
         partner.addContent(nachname);
         partner.addContent(ausweisNr);
         partner.addContent(adresse);
         return partner;
    }

     /**
      * Einen Warenknoten hinzufügen
      * */
    public Element newXMLWarenknoten(IWare ware){
        Element warenElement = new Element("Ware");
        Attribute attr = new Attribute("id", Long.toString(ware.getId()));
        warenElement.setAttribute(attr);


        Element bezeichnung = new Element("Bezeichnung");
        Element beschreibung = new Element("Beschreibung");
        Element ID = new Element("ID");
        Element preis = new Element("Preis");

        bezeichnung.setText(ware.getBezeichnung());
        beschreibung.setText(ware.getBeschreibung());
        ID.setText(Long.toString(ware.getId()));
        preis.setText(Double.toString(ware.getPreis()));


        Element besonderheiten = new Element("Besonderheiten");
        for (var besonderheit : ware.getBesonderheiten()){
            Element element = new Element("li");
            element.setText(besonderheit);
            besonderheiten.setContent(element);
        }
        Element maengel = new Element("Maengel");
        for (var mangel : ware.getMaengel()){
            Element element = new Element("li");
            element.setText(mangel);
            maengel.setContent(element);
        }

        return warenElement;
    }
     public void addNodesToXML(Document file, Element[]... elements){
         try{
             Element root = file.getRootElement();
             for (var arr : elements){
                 for(var element : arr){
                     root.addContent(element);
                 }
             }
         }catch (Exception e){
             e.printStackTrace();
         }
     }
    public Document readXMLFile(File xmlDatei){
        if(xmlDatei != null){
            try{
                SAXBuilder sb = new SAXBuilder();
                return sb.build(xmlDatei);
            }catch (Exception e){
                System.err.println("Konnte kein Root Element finden.");
                e.printStackTrace();
            }
        }
        else {
            System.err.println("xmlDatei was null.");
        }
        return null;
    }

    public List<File> getXMLFileList(){
        Path folderPath = checkForXMLsFolder();
        List<File> xmlFileList = new ArrayList<>();
        if(Files.isDirectory(folderPath)){
            try {
                Files.walkFileTree(folderPath, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        if (xmlFileFilter.accept(file.toFile())) {
                            xmlFileList.add(file.toFile());
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return xmlFileList;
    }

     public class JdomFile {
         public Element element;
         public File file;

         public JdomFile(Element element, File file){
             this.element = element;
             this.file = file;
         }
     }

     private void createUniqueID(Document doc){
         int randomVal;
         for (Element child : doc.getRootElement().getChildren()){
             if(child.getAttribute("id") == null){
                 while(true) {
                     int lenght = 8;
                     int idMAxValue = (int) Math.pow(10, lenght) - 1;
                     Random random = new Random();
                     randomVal =  random.nextInt(idMAxValue);
                     var file = idSearchAllXml("", Integer.toString(randomVal));
                     if(file.element == null)
                         break;
                 }
                 child.setAttribute("id", String.valueOf(randomVal));
             }
         }
     }

    public JdomFile idSearchAllXml(String nodeName, String id){
        var fileList = getXMLFileList();
        for(var file :fileList){
            Document doc = readXMLFile(file);
            Element element = searchRecursively(doc.getRootElement(),true, nodeName,"id", id);
            return new JdomFile(element, file);
        }
        return null;
    }

     public Element searchRecursively(Element element, boolean attribute, String nodeName, String attName, String value) {
         if(attribute) {
             if ((element.getName().equals(nodeName) && element.getAttributeValue(attName) != null && element.getAttributeValue(attName).equals(value))
                     || (nodeName.equals("") && element.getAttributeValue(attName) != null && element.getAttributeValue(attName).equals(value)))
                 return element;
         }
         else {
             if ((element.getName().equals(nodeName) && element.getValue().equals(value))
              || (element.getName().equals(nodeName) && value.equals("")))
                 return element;
         }
         for (Element child : element.getChildren()) {
             Element found = searchRecursively(child, attribute, nodeName, attName, value);
             if (found != null) {
                 return found;
             }
         }
         return null;
     }

     public File chooseXML(List<File> xmlFiles, String type){
         return chooseXML(xmlFiles, type, "");
     }

    public File chooseXML(List<File> xmlFiles, String type, String frage){
        ConsoleManager ui = ConsoleManager.getInstance();
        List<ConsoleManager.AnswerOption<File>> listOption = new ArrayList<>();

        if(!xmlFiles.isEmpty()) {
            for(File file : xmlFiles){
                if(xmlFileContainsName(file, type)) {
                    ConsoleManager.AnswerOption<File> fileA = ui.new AnswerOption<>(() -> file, "\n\t" + file.getName());
                    listOption.add(fileA);
                }
            }
        }

        @SuppressWarnings("unchecked")
        ConsoleManager.AnswerOption<File>[] array = new ConsoleManager.AnswerOption[listOption.size()];
        array = listOption.toArray(array);
        if(frage.equals(""))
            frage = "Welche Datei möchten Sie öffnen? ";
        Object result = ui.ConsoleOptions(frage, array);
        if(result instanceof File file)
            return file;
        else
            return null;
    }

    private boolean xmlFileContainsName (File file, String name){
        return searchRecursively(readXMLFile(file).getRootElement(), false, name, "", "") != null;
    }

    public Element UnterKnotenAuswahlen(Document document, String typen, String tyeChild, String frage){
        ConsoleManager ui = ConsoleManager.getInstance();
        Element root = document.getRootElement();
        List<ConsoleManager.AnswerOption<Element>> knoten = new ArrayList<>();

        for(var element : root.getChildren()){
            if(element.getName().equals(typen) && !typen.equals("")){
                ConsoleManager.AnswerOption<Element> elementA = ui.new AnswerOption<>(() -> element, element.getChild(tyeChild).getValue() + " id: " + element.getAttributeValue("id"));
                knoten.add(elementA);
            }
        }

        @SuppressWarnings("unchecked")
        ConsoleManager.AnswerOption<Element>[] array = new ConsoleManager.AnswerOption[knoten.size()];
        array = knoten.toArray(array);
        if(frage.isBlank())
            frage = "Welchen Knotenpunkt möchten Sie öffnen?";
        return (Element) ui.ConsoleOptions(frage, array);
    }

     FileFilter xmlFileFilter = new FileFilter() {
         @Override
         public boolean accept(File pathname) {
             return pathname.isFile() && pathname.getName().toLowerCase().endsWith(".xml");
         }
     };
 }
