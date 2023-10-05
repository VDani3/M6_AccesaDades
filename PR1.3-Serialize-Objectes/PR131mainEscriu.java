import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class PR131mainEscriu {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String filePath = "./PR131HashMapData.ser";
        File file = new File(filePath);
        PR131hashmap persones = new PR131hashmap();
        
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            persones.anyadirVal("Dani", 19);
            persones.anyadirVal("Rodolfo", 77);
            persones.anyadirVal("Tinquiwinqui", 55);
            persones.anyadirVal("Callou", 3);

            FileOutputStream fWrite = new FileOutputStream(filePath);
            ObjectOutputStream dWrite = new ObjectOutputStream(fWrite);

            dWrite.writeObject(persones);
            System.out.println("Escrit correctament");
            sc.nextLine();

        } catch (Exception e) {
            System.out.println("Error desconegut");
        }
    }
}
