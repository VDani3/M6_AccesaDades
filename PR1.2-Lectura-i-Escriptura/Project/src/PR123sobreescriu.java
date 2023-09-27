import java.io.FileOutputStream;
import java.util.Scanner;

public class PR123sobreescriu {

    public static void main(String[] args){
        //Sobreescribir el fichero "frasesMatrix"
        Scanner sc = new Scanner(System.in);
        String fichero = "frasesMatrix.txt";
        String contenido = "Yo sólo puedo mostrarte la puerta \nTú eres quien la tiene que atravesar\n";

        try (FileOutputStream archivo = new FileOutputStream(fichero)){
            byte[] bytes = contenido.getBytes();
            archivo.write(bytes);
            System.out.println("Se ha sobreescrito correctamente");
            sc.nextLine();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
