import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class PR122cat {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.print("Digues el nom o la ruta del fitxer: ");
        String source = sc.nextLine();
        PR122cat ex = new PR122cat();
        ex.openFile(source);
    }

    private void openFile(String route) {
        Scanner sc = new Scanner(System.in);
        File file = new File(route);
        if (file.exists()){
             if (file.isDirectory()){
                System.out.println("La ruta especificada es un directori");
             } else {
                    try (BufferedReader br = new BufferedReader(new FileReader(route))){
                        String linea;
                        System.out.println("Text del fitxer: \n");
                        while ((linea = br.readLine()) != null) {
                            System.out.println(linea);
                        }
                    } catch (Exception e) {
                        System.out.println("No s'ha pogut llegir el fitxer\n");
                    }
             }
        } else {
            System.out.println("No existeix aquest arxiu");
        }
        sc.nextLine();
    }
}
