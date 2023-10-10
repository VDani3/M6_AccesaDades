import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class PR132main {
    public static void main(String[] args) {
        PR132persona maria = new PR132persona("Maria", "López", 36);
        PR132persona gustavo = new PR132persona("Gustavo", "Ponts", 63);
        PR132persona irene = new PR132persona("Irene", "Sales", 54);
        String path = "./PR132people.dat";
        File createFile = new File(path);
        Scanner sc = new Scanner(System.in);

        try {
            if (createFile.exists()){
                createFile.delete();
            }
            createFile.createNewFile();
            FileOutputStream file = new FileOutputStream(path);
            ObjectOutputStream data = new ObjectOutputStream(file);

            data.writeObject(maria);
            data.writeObject(gustavo);
            data.writeObject(irene);

            FileInputStream f = new FileInputStream(path);
            ObjectInputStream showData = new ObjectInputStream(f);

            PR132persona p1 = (PR132persona) showData.readObject();
            PR132persona p2 = (PR132persona) showData.readObject();
            PR132persona p3 = (PR132persona) showData.readObject();

            System.out.println(p1);
            System.out.println(p2);
            System.out.println(p3);
            sc.nextLine();
        } catch (Exception e) {System.out.println("Error inesperat");}
    }
}
