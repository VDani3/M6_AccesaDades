import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class PR130mainPersonesHashmap {
    
    public static void main(String[] args){
        //       Nom     Edat
        HashMap<String, Integer> persones = new HashMap<String, Integer>();
        PR130mainPersonesHashmap ex = new PR130mainPersonesHashmap();
        String filePath = "./PR130.dat";

        persones.put("Dani", 19);
        persones.put("Akane", 21);
        persones.put("Victor", 31);
        persones.put("Janete", 19);
        persones.put("Rosana", 45);
        ex.crearArchivo(filePath);

        try{
            FileOutputStream file = new FileOutputStream(filePath);
            DataOutputStream data = new DataOutputStream(file);
              //Recorremos el Hashmap con el for each :)
            for (Map.Entry<String, Integer> entry : persones.entrySet()) {
                data.writeUTF(entry.getKey());
                data.writeInt(entry.getValue());
            }
            file.close();
            data.close();

              //Leer el fichero
            ex.leerArchivo(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void crearArchivo(String file) {
        File f = new File(file);
        if (f.exists()) {
            f.delete();
        }
        try{
            f.createNewFile();
        } catch (Exception e) {
            System.out.println("Error inesperat");
        }
    }

    public void leerArchivo(String path){
        try{
            FileInputStream f = new FileInputStream(path);
            DataInputStream d = new DataInputStream(f);
            for (int i = 0; i < 5; i++) {
                System.out.print(d.readUTF()+"   ");
                System.out.println(d.readInt());
            }

            f.close();
            d.close();
        } catch (Exception e) {
            System.out.println("Error al llegir l'arxiu");
        }

    }
}
