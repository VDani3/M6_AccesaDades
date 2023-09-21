import java.io.File;
import java.io.IOException;

public class PR121Files {
    public static void main(String args[]){
        PR121Files ex = new PR121Files();
        ex.crearCarpeta("myFiles");
        ex.crearArxiuTxt("myFiles", "file1");
        ex.crearArxiuTxt("myFiles", "file2");
    }


      //Metodes
    private void crearCarpeta(String name){
        File dir = new File("./"+name);
          //Comproba la existencia del directori, i si no existeix ja el crea
        if(!dir.exists()) {
            if(dir.mkdirs()){
                System.out.println("S'ha creat el directori.");
            } else { System.out.println("No s'ha creat el directori."); System.exit(0);;} //Si no es pot crear i no existeix acaba.
        } else { System.out.println("Ja existeix el directori");}
    }

    private void crearArxiuTxt(String dir, String name) {
        File txt = new File("./"+dir+"/"+name+".txt");
        try {
            if (!txt.exists()) {                                     //Si el fitxer no existeix el crea
                txt.mkdir();
                System.out.println("Fitxer "+name+".txt creat.");
            } else {System.out.println("Ja existeix el fitxer");}

        } catch (IOException e) {                                    //Si dona un error del sistema s'acaba el programa
            System.out.println("Error en la creació del fitxer "+name+".txt");
            System.exit(0);
        }
    }
}
