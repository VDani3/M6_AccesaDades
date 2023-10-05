import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.Scanner;

public class PR131mainLlegeix {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String filePath = "./PR131HashMapData.ser";
        try {
            FileInputStream file = new FileInputStream(filePath);
            ObjectInputStream data = new ObjectInputStream(file);

            PR131hashmap persones = (PR131hashmap) data.readObject();
            for (Map.Entry<String, Integer> entry : persones.p.entrySet()) {
                System.out.print(entry.getKey() + "   " + entry.getValue());
            }
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Error inesperat");
        }
        
    }
}
