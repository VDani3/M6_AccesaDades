import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PR140Main {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("./persones.xml");
            doc.getDocumentElement().normalize();
            NodeList listPersons = doc.getElementsByTagName("persona");

            String[] encabezados = { "Nom", "Cognom", "Edad", "Ciudad" };
            String formato = "| %-15s | %-15s | %-5s | %-15s |%n";
            System.out.println( String.valueOf("-").repeat(63));
            System.out.printf(formato, encabezados);
            System.out.printf(formato, "-------------", "-------------", "-----", "-------------");

            for (int cnt = 0; cnt < listPersons.getLength(); cnt++) {
                Node nodePersona = listPersons.item(cnt);
                if (nodePersona.getNodeType() == Node.ELEMENT_NODE) {
                    Element elm = (Element) nodePersona;
                    String nombre = elm.getElementsByTagName("nom").item(0).getTextContent();
                    String apellido = elm.getElementsByTagName("cognom").item(0).getTextContent();
                    String edad = elm.getElementsByTagName("edat").item(0).getTextContent();
                    String ciudad = elm.getElementsByTagName("ciutat").item(0).getTextContent();

                    System.out.printf(formato, nombre, apellido, edad, ciudad);
                }
            }
            System.out.println( String.valueOf("-").repeat(63));
            sc.nextLine();

        } catch (Exception e) { e.printStackTrace(); }
    }

}
