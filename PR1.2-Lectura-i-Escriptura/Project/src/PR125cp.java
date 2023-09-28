//Fet per Daniel Villa
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.io.*;

public class PR125cp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingresa la ruta del archivo que deseas copiar: ");
        String ruta = sc.nextLine();
        File original = new File(ruta);
        System.out.print("Ingresa la ruta donde deseas guardar el archivo copiado: ");
        String rutaNew = sc.nextLine();

        try {
            //Si existeix l'arxiu l'eliminara i el creara
            File file = new File(rutaNew);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            Scanner fileRead = new Scanner(original);
            FileWriter fw = new FileWriter(file); 

            while (fileRead.hasNextLine()) {
                String linea = fileRead.nextLine();
                fw.write(linea + "\n"); 
            }

            // Tancar els arxius (encara que no seria necesari en aquest cas)
            fileRead.close();
            fw.close();

            System.out.println("Se ha copiado correctamente");
            sc.nextLine();
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        } 
    }
}
