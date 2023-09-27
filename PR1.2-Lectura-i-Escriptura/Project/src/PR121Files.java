import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PR121Files {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        PR121Files ex = new PR121Files();
        ex.crearCarpeta("./myFiles");
        ex.crearArxiuTxt("./myFiles", "file1");
        ex.crearArxiuTxt("./myFiles", "file2");
        ex.renombrarArxiu("./myFiles", "file2", "renamedFile");
        System.out.println(ex.mostrarFitxers("./myFiles"));
        ex.eliminarFitxer("./myFiles/file1.txt");
        System.out.println(ex.mostrarFitxers("./myFiles"));
        sc.nextLine();
    }


      //Metodes
    private void crearCarpeta(String route){
        File dir = new File(route);
          //Comproba la existencia del directori, i si no existeix ja el crea
        if(!dir.exists()) {
            if(dir.mkdirs()){
                System.out.println("S'ha creat el directori.");
            } else { System.out.println("No s'ha creat el directori."); System.exit(0);;} //Si no es pot crear i no existeix acaba.
        } else { System.out.println("Ja existeix el directori");}
    }

    private void crearArxiuTxt(String route, String fileName) {
        File txt = new File(route+"/"+fileName+".txt");
        try{
        if (!txt.exists()) {                                     //Si el fitxer no existeix el crea
            if (txt.createNewFile()){
                System.out.println("Fitxer "+fileName+".txt creat.");
            } else {System.out.println("Error al crear el fitxer"); System.exit(0);}
        } else {System.out.println("Ja existeix el fitxer");}
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void renombrarArxiu(String route, String fileName, String newFileName){
        File fitxer = new File(route+"/"+fileName+".txt");
        File newFitxer = new File(route+"/"+newFileName+".txt");
        if (fitxer.exists()) {
            if(fitxer.renameTo(newFitxer)){
                System.out.println("Renombrat Correctament");
            } else { System.out.println("Error al renombrar el fitxer"); System.exit(0);}
        } else { System.out.println("No existeix aquest arxiu");}
    }

    private String mostrarFitxers(String route){
        File folder = new File(route);
        String fitxers = "Els arxius de la carpeta son: ";

        if (folder.isDirectory()) {
            File[] arxius = folder.listFiles();
            if (arxius != null){
                for(File arxiu : arxius){
                    fitxers += arxiu.getName()+", ";
                }
            } else { fitxers += "No n'hi han";}
            return fitxers;
        } else {return "No existeix la carpeta especificada";}
    }

    private void eliminarFitxer(String route) {
        File f = new File(route);
        if (f.exists()) {
            f.delete();
            System.out.println("Fitxer eliminat");
        } else { System.out.println("No existeix el fitxer"); }
    }
}
