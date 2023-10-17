import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import org.w3c.dom.*;

public class PR142Main {
    
    public static void main(String[] args) {
        try {
            String xml = "./cursos.xml"; // Tu cadena XML aquí
            listarCursosTutoresAlumnos(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void listarCursosTutoresAlumnos(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xml);
    
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
    
        String cursoXPath = "/cursos/curs";
        NodeList cursos = (NodeList) xpath.compile(cursoXPath).evaluate(doc, XPathConstants.NODESET);
    
        for (int i = 0; i < cursos.getLength(); i++) {
            Node curso = cursos.item(i);
            String cursoId = ((Element) curso).getAttribute("id");
            String tutorName = xpath.compile("tutor").evaluate(curso);
            String numAlumnosXPath = "count(alumnes/alumne)";
            Double numAlumnos = (Double) xpath.compile(numAlumnosXPath).evaluate(curso, XPathConstants.NUMBER);
    
            System.out.println("Curs ID: " + cursoId);
            System.out.println("Tutor: " + tutorName);
            System.out.println("Num d'Alumn: " + numAlumnos.intValue());
            System.out.println("----------");
        }
    }
    
    public static void mostrarModulosDeCurso(String xml, String cursoId) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xml);
    
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
    
        String cursoXPath = "/cursos/curs[@id='" + cursoId + "']";
        Node curso = (Node) xpath.compile(cursoXPath).evaluate(doc, XPathConstants.NODE);
    
        if (curso != null) {
            System.out.println("Módulos del curso con ID " + cursoId + ":");
            NodeList modulos = (NodeList) xpath.compile("moduls/modul").evaluate(curso, XPathConstants.NODESET);
    
            for (int i = 0; i < modulos.getLength(); i++) {
                Node modulo = modulos.item(i);
                String moduloId = ((Element) modulo).getAttribute("id");
                String moduloTitulo = xpath.compile("titol").evaluate(modulo);
                System.out.println("ID: " + moduloId + ", Título: " + moduloTitulo);
            }
        } else {
            System.out.println("Curso con ID " + cursoId + " no encontrado.");
        }
    }
    

    public static void mostrarAlumnosDeCurso(String xml, String cursoId) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xml);
    
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
    
        String cursoXPath = "/cursos/curs[@id='" + cursoId + "']";
        Node curso = (Node) xpath.compile(cursoXPath).evaluate(doc, XPathConstants.NODE);
    
        if (curso != null) {
            System.out.println("Alumnos del curso con ID " + cursoId + ":");
    
            NodeList alumnos = (NodeList) xpath.compile("alumnes/alumne").evaluate(curso, XPathConstants.NODESET);
    
            for (int i = 0; i < alumnos.getLength(); i++) {
                Node alumno = alumnos.item(i);
                String alumnoNombre = alumno.getTextContent();
                System.out.println("Nombre: " + alumnoNombre);
            }
        } else {
            System.out.println("Curso con ID " + cursoId + " no encontrado.");
        }
    }


    public static void agregarAlumnoACurso(String xml, String cursoId, String nombreAlumno) throws Exception {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);

            String xpath = "/cursos/curs[@id='" + cursoId + "']";
            Node curso = doc.getElementsByTagName("curs").item(0);

            if (curso != null) {
                Document ownerDocument = curso.getOwnerDocument();
                Element nuevoAlumno = ownerDocument.createElement("alumne");
                nuevoAlumno.setTextContent(nombreAlumno);
                curso.appendChild(nuevoAlumno);

                // Guardar el archivo XML actualizado
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(xml);
                transformer.transform(source, result);

                System.out.println("Alumno " + nombreAlumno + " añadido al curso con ID " + cursoId);
            } else {
                System.out.println("Curso con ID " + cursoId + " no encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void eliminarAlumnoDeCurso(String xml, String cursoId, String nombreAlumno) throws Exception {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);
    
            String xpathCurso = "/cursos/curs[@id='" + cursoId + "']";
            Node curso = doc.getElementsByTagName("curs").item(0);
    
            if (curso != null) {
                NodeList alumnos = ((Document) curso).getElementsByTagName("alumne");
                for (int i = 0; i < alumnos.getLength(); i++) {
                    Element alumno = (Element) alumnos.item(i);
                    if (alumno.getTextContent().equals(nombreAlumno)) {
                        curso.removeChild(alumno);
    
                        // Guardar el archivo XML actualizado
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(xml);
                        transformer.transform(source, result);
    
                        System.out.println("Alumno " + nombreAlumno + " eliminado del curso con ID " + cursoId);
                        return; // Salimos del bucle si encontramos y eliminamos al alumno
                    }
                }
                System.out.println("Alumno " + nombreAlumno + " no encontrado en el curso con ID " + cursoId);
            } else {
                System.out.println("Curso con ID " + cursoId + " no encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
}
