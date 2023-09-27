import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.Scanner;

public class PR124linies {
    
    public static void main(String[] args){
        Random r = new Random();
        int num;
        File archivo = new File("numeros.txt");
        Scanner sc = new Scanner(System.in);
        
        try{
            if(!archivo.exists()){
                archivo.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i += 1) {
            num = r.nextInt(7777);
            try (FileOutputStream file = new FileOutputStream(archivo, true)){
                String numero = Integer.toString(num)+"\n";
                byte[] bytes = numero.getBytes();
                file.write(bytes);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("10 numeros aleatorios anyadidos correctamente");
        sc.nextLine();

    }
}
