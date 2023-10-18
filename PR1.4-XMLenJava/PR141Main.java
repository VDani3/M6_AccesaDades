import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class PR141Main {
    
    public static void main(String[] args) {
        DocumentBuilderFactory dbf =  DocumentBuilderFactory.newInstance();
        Scanner sc = new Scanner(System.in);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element elmRoot = doc.createElement("biblioteca");
            doc.appendChild(elmRoot);

            Element elmLlibre = doc.createElement("llibre");
            Attr attrId = doc.createAttribute("id");

            elmRoot.appendChild(elmLlibre);
            elmLlibre.setAttributeNode(attrId);
            attrId.setValue("001");

            //Titol
            Element elmTitol = doc.createElement("titol");
            Text titol = doc.createTextNode("El viatge dels venturons");
            elmTitol.appendChild(titol);
            elmLlibre.appendChild(elmTitol);

            //Autor
            Element elmAutor = doc.createElement("autor");
            Text autor = doc.createTextNode("Joan Pla");
            elmAutor.appendChild(autor);
            elmLlibre.appendChild(elmAutor);

            //Any Publicació
            Element elmAny = doc.createElement("anyPublicacio");
            Text nay = doc.createTextNode("1998");
            elmAny.appendChild(nay);
            elmLlibre.appendChild(elmAny);

            //Editorial
            Element elmEd = doc.createElement("editorial");
            Text editorial = doc.createTextNode("Edicions Mar");
            elmEd.appendChild(editorial);
            elmLlibre.appendChild(elmEd);

            //Genere
            Element elmGen = doc.createElement("genere");
            Text genere = doc.createTextNode("Aventura");
            elmGen.appendChild(genere);
            elmLlibre.appendChild(elmGen);

            //Pagines
            Element elmPag =doc.createElement("pagines");
            Text pag = doc.createTextNode("320");
            elmPag.appendChild(pag);
            elmLlibre.appendChild(elmPag);

            //Disponible
            Element elmDisp = doc.createElement("disponible");
            Text disp = doc.createTextNode("true");
            elmDisp.appendChild(disp);
            elmLlibre.appendChild(elmDisp);
            write("./biblioteca.xml", doc);
            System.out.println("Document 'biblioteca.xml' creat correctament");
            sc.nextLine();

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    static public void write(String path, Document doc) throws TransformerException, TransformerConfigurationException, IOException {
        File f = new File(path);
        if (f.exists()) {f.delete();}
        f.createNewFile();

        TransformerFactory transFactory  = TransformerFactory.newInstance();
        Transformer trans = transFactory.newTransformer();

        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult res = new StreamResult(f);
        trans.transform(source, res);
    }
}
