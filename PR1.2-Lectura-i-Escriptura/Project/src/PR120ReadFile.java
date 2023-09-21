import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PR120ReadFile {
    public static void main(String args[]){
          //Variables
        File fitxer = new File("./PR120ReadFile.java");
        int lineCounter = 1;

        try {
            Scanner sc = new Scanner(fitxer);    //Obre el fitxer
              //Llegim el fitxer
            while(sc.hasNextLine()){             //Mentre tingui alguna linea mes
                lineCounter++;
                String line = sc.nextLine();
                System.out.println(Integer.toString(lineCounter)+"  "+line);
            }
        } catch (FileNotFoundException e){      //Si no troba el fitxer especificat
            e.printStackTrace();
        }
    }
}
