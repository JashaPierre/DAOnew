package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.Main;
import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
 public class ServiceXml {

    public void newVertragXML(IDao<Object,Object> idao, String fileName) throws IOException {
        Document document = new Document();
        Element root = new Element("kaufvertrag");
        document.setRootElement(root);

        //Element zahlung = new Element("zahlung");
        //zahlung.setText("Privater Barverkauf");

        // ____ Muss wo anders hin ________
        //document.getRootElement().addContent(kaeufer);
        //document.getRootElement().addContent(verkaeufer);
        //document.getRootElement().addContent(AddWare);
        //document.getRootElement().addContent(zahlung);

        String datei =  Main.PROJECTPATH + fileName + ".xml";
        FileOutputStream fileOutputStream = new FileOutputStream(datei);
        Format format = Format.getCompactFormat();
        format.setIndent("    ");
        XMLOutputter xmlOutputter = new XMLOutputter(format);
        xmlOutputter.output(document, fileOutputStream);
        fileOutputStream.close();
    }

   /* public void newVertragXML(IDao<Object,Object> idao)  throws IOException{
        newVertragXML(idao, "");
    }*/

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

    public File getXMLFile(){
        File directory = new File(Main.PROJECTPATH + "/xmls");
        StringBuilder filesString = new StringBuilder();
        Map<File, String> fileOptions = new HashMap<>();
        if(directory.isDirectory()){
            File[] xmlFiles = directory.listFiles(xmlFileNameFilter());
            if(xmlFiles != null) {
                for(int i = 0; i < xmlFiles.length; i++){
                    File file = xmlFiles[i];
                    String option = Integer.toString(i);
                    fileOptions.put(file, option);
                    filesString.append(xmlFiles[i].getName()).append(" (").append(i).append(") ");
                }
            }
        }
        System.out.println("Welche Datei möchten Sie öffnen?");
        System.out.println(filesString);
        Scanner sc = new Scanner(System.in);
        File foundFile = null;
            if(!fileOptions.isEmpty()){
                do{
                    for (var entry : fileOptions.entrySet()){
                        if (entry != null){
                            if(sc.nextLine().equals(entry.getValue())){
                                foundFile = entry.getKey();
                                System.out.println("Selected File: " + foundFile.toString());
                            }
                            else {
                                System.out.println("Ungültige Eingabe");
                            }
                        }
                    }
                }while (foundFile == null);
            }
            sc.close();
        return foundFile;
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
