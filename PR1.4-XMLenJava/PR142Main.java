import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import org.w3c.dom.*;

public class PR142Main {
    public static void main(String[] args) {
        String menu = "Menu \n  1)Llistar Cursos tutors i alumnes\n  2)Mostrar moduls dels cursos\n  3)Mostrar alume de una clase\n  4)Afegir alumne\n  5)Eliminar alumne\n  6)Sortir\n Opcio: ";
        String opt = "";
        Scanner sc = new Scanner(System.in);
        String id, nom;

        try {
            String xml = "./cursos.xml";

            while (opt != "6") {
                System.out.print(menu);
                opt = sc.nextLine();

                if (opt.equals("1")) {
                    System.out.println();
                    llistarCurs(xml);
                    sc.nextLine();

                } else if (opt.equals("2")) {
                    System.out.print("Id del curs: ");
                    id = sc.nextLine();
                    System.out.println();
                    mostrarModuls(xml, id);
                    sc.nextLine();

                } else if (opt.equals("3")){
                    System.out.print("Id de la clase: ");
                    id = sc.nextLine();
                    System.out.println();
                    mostrarAlumnes(xml, id);
                    sc.nextLine();
                
                } else if (opt.equals("4")) {
                    System.out.print("Id de la clase: ");
                    id = sc.nextLine();
                    System.out.print("Digues el nom de l'alumne: ");
                    nom = sc.nextLine();
                    System.out.println();
                    afegirAlumne(xml, id, nom);
                    sc.nextLine();

                } else if (opt.equals("5")) {
                    System.out.print("Id de la clase: ");
                    id = sc.nextLine();
                    System.out.print("Nom del alumne a eliminar: ");
                    nom = sc.nextLine();
                    System.out.println();;
                    eliminarAlumne(xml, id, nom);
                    sc.nextLine();

                } else if (opt.equals("6")) {
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void llistarCurs(String xml) throws Exception {
          //'Fa' el document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xml);
          //Prepar el XPath
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
          //Fa com una llista amb els resultats de la busqueda seguent
        String cursoXPath = "/cursos/curs";
        NodeList cursos = (NodeList) xpath.compile(cursoXPath).evaluate(doc, XPathConstants.NODESET);
    
        for (int i = 0; i < cursos.getLength(); i++) {
            Node curso = cursos.item(i);
              //Agafa el id
            String cursoId = ((Element) curso).getAttribute("id");
              //Agafa el nom del tutor
            String tutorName = xpath.compile("tutor").evaluate(curso);
              //Conta el numero d'alumnes
            String numAlumnosXPath = "count(alumnes/alumne)";
            Double numAlumnos = (Double) xpath.compile(numAlumnosXPath).evaluate(curso, XPathConstants.NUMBER);
    
            System.out.println("Curs ID: " + cursoId);
            System.out.println("Tutor: " + tutorName);
            System.out.println("Num d'Alumn: " + numAlumnos.intValue());
            System.out.println("----------");
        }
    }
    

    public static void mostrarModuls(String xml, String cursoId) throws Exception {
          //'Fa' el document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xml);
          //Prepara el XPath
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
          //Crea una "llista" amb tots els elements que te aquell curs
        String cursoXPath = "/cursos/curs[@id='" + cursoId + "']";
        Node curso = (Node) xpath.compile(cursoXPath).evaluate(doc, XPathConstants.NODE);
    
        if (curso != null) {     //Si existeix
            System.out.println("Moduls del curs " + cursoId + ":");
              //Fa una llista amb todos los elmentos del curso que son modulos
            NodeList modulos = (NodeList) xpath.compile("moduls/modul").evaluate(curso, XPathConstants.NODESET);
    
            for (int i = 0; i < modulos.getLength(); i++) {
                Node modulo = modulos.item(i);
                  //Agafa el id del modul i el seu titol
                String moduloId = ((Element) modulo).getAttribute("id");
                String moduloTitulo = xpath.compile("titol").evaluate(modulo);
                System.out.println("Modul: " + moduloId + ", " + moduloTitulo);
            }
        } else {
            System.out.println("Curs " + cursoId + " no trobat.");
        }
    }
    

    public static void mostrarAlumnes(String xml, String cursoId) throws Exception {
          //'Fa' el document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xml);
          //Prepara el XPath
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
          //Agafa el element (com una llista) curs amb l'id especificat
        String cursoXPath = "/cursos/curs[@id='" + cursoId + "']";
        Node curso = (Node) xpath.compile(cursoXPath).evaluate(doc, XPathConstants.NODE);
    
        if (curso != null) {   //Si existeix
            System.out.println("Alumnes del curs " + cursoId + ":");
              //Fa una llista amb tots els elements nom
            NodeList alumnos = (NodeList) xpath.compile("alumnes/alumne").evaluate(curso, XPathConstants.NODESET);
    
            for (int i = 0; i < alumnos.getLength(); i++) {
                  //Agafa cada un dels elements alumne i diu el nom
                Node alumno = alumnos.item(i);
                String alumnoNombre = alumno.getTextContent();
                System.out.println(" "+Integer.toString(i)+": " + alumnoNombre);
            }
        } else {
            System.out.println("Curs " + cursoId + " no trobat...");
        }
    }


    public static void afegirAlumne(String xml, String cursoId, String nombreAlumno) throws Exception {
        try {
              //'Fa' el document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);
            
              //
            String xpath = "/cursos/curs[@id='" + cursoId + "']/alumnes";  
            Node alumnes = (Node) XPathFactory.newInstance().newXPath().evaluate(xpath, doc, XPathConstants.NODE);
    
            if (alumnes != null) {
                Document ownerDocument = alumnes.getOwnerDocument();
                Element nuevoAlumno = ownerDocument.createElement("alumne");
                nuevoAlumno.setTextContent(nombreAlumno);
                alumnes.appendChild(nuevoAlumno);
    
                // Guardar el archivo XML actualizado
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(xml);
                //transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(source, result);
    
                System.out.println("Alumne " + nombreAlumno + " afegit al curs " + cursoId);
            } else {
                System.out.println("Curs " + cursoId + " no trobat o no te una llista d'alumnes...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    


    public static void eliminarAlumne(String xml, String cursoId, String nombreAlumno) throws Exception {
        try {
              //'Fa' el document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);
            
            String xpath = "/cursos/curs[@id='" + cursoId + "']/alumnes/alumne[text()='" + nombreAlumno + "']";
            Node alumno = (Node) XPathFactory.newInstance().newXPath().evaluate(xpath, doc, XPathConstants.NODE);
            
            if (alumno != null) {
                Node parent = alumno.getParentNode();
                parent.removeChild(alumno);
    
                // Guardar el archivo XML actualizado
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(xml);
                transformer.transform(source, result);
    
                System.out.println("Alumne " + nombreAlumno + " eliminat del curs " + cursoId);
            } else {
                System.out.println("Alumne " + nombreAlumno + " no trobat en el curs" + cursoId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    
}
