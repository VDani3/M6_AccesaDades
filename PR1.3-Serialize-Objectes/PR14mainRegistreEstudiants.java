import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.Scanner;

public class PR14mainRegistreEstudiants {
    
    public static void main(String[] args) {
        String menu = "Menu \n   1) Afegir nou estudiant \n   2) Actualitzar nota d'un estudiant \n   3) Consultar la nota d'un estudiant \n   4) Sortir \nOpcio: ";
        PR14mainRegistreEstudiants ex = new PR14mainRegistreEstudiants();
        int opt = 0;
        Scanner sc = new Scanner(System.in);  
        String path = "./PR14registre";

        while (opt != 4) {
            if (opt == 1){
                try {
                    System.out.println("\n\n\nAfegiment d'estudiants");
                      //Nou numero de registre
                    int numRegistre = ex.getNumRegistre(path);
                    if (numRegistre == 0){
                        sc.nextLine();
                        continue;
                    }
                      //Nom
                    System.out.print("   Nom de l'alumne: ");
                    String name = sc.nextLine();
                    name = ex.comprobarNom(name);
                      //Nota
                    System.out.print("   Nota de l'alumne: ");
                    float note = sc.nextFloat();
                    note = ex.corregirNota(note);
                      //Afegir estudiant
                    if (!ex.afegirEstudiant(numRegistre, name, note, path)) {
                        System.out.println("No s'ha pogut afegir");
                        sc.nextLine();
                        continue;
                    };
                    System.out.println(String.format("S'ha creat l'estudiant %s amb la nota %s i numero de registre %s", name, Float.toString(note), Integer.toString(numRegistre)));
                    sc.nextLine();
                } catch (Exception e) {e.printStackTrace();}
            }
            else if (opt == 2) {
                try{ 
                    int numReg;
                    long posReg;
                    float nota;

                    System.out.println("\n\n\nActualitzar nota");
                    System.out.print("   Num del registre: ");
                    numReg = sc.nextInt();
                    posReg = ex.findRegistre(numReg, path);

                    if (posReg < 0) {
                        System.out.println("No existeix el registre");
                        sc.nextLine();
                        continue;
                    }

                    System.out.println("   Nota nova: ");
                    nota = sc.nextFloat();

                    if (!ex.modificarNota(posReg, nota, path)) {
                        System.out.println("No s'ha pogut modificar la nota");
                        sc.nextLine();
                        continue;
                    }
                    System.out.println(String.format("La nota de l'alumne amb el numero registre %s ha sigut cambiada per %s", numReg, nota));
                    sc.nextLine();
                } catch (Exception e) { e.printStackTrace(); }
                
            } 
            else if (opt == 3) {
                try {
                    int numReg;
                    long posReg;
                    String alumneInfo;

                    System.out.println("\n\n\nVisualitzar alumne");
                    System.out.print("   Num del registre: ");
                    numReg = sc.nextInt();
                    posReg = ex.findRegistre(numReg, path);
                    if (posReg < 0) {
                        System.out.println("No existeix el registre");
                        sc.nextLine();
                        continue;
                    }
                    alumneInfo = ex.getInfoEstudiant(posReg, path);
                    if (alumneInfo == "") {
                        System.out.println("No s'ha pogut llegir la info de l'alumne");
                        sc.nextLine();
                        continue;
                    }
                } catch (Exception e) {e.printStackTrace();}
            }
        }
    }

      //Getters
    private int getNumRegistre(String p){
        int res = 1;
        long lenFile, lastIniPFile;
        int lenReg = 28;
        try (RandomAccessFile raf = new RandomAccessFile(p, "rw")){
            lenFile = raf.length();
            lastIniPFile = lenFile - lenReg;
              //Si la posicio es negativa (es el primer registre)
            if (lastIniPFile < 0) {
                return 1;
            }
            raf.seek(lastIniPFile);
            res += raf.readInt(); 
            raf.close();
            res = corregirNumReg(res);
            return res;
        } catch (Exception e) {System.out.println("El registre esta mal fet, elimina el document o arregla-ho"); return 0;}
    }

    private String getInfoEstudiant(long pos, String p) {
        String res = "";
        try (RandomAccessFile raf = new RandomAccessFile(p, "r")) {
            raf.seek(pos);
            res = raf.readUTF();
        } catch (Exception e) {System.out.println("Error al agafar la info de l'estudiant");}
        return res;
    }


      //Comprobaciones y correcciones
    private String comprobarNom(String n) {
        if (n.length() > 20) {
            n = n.substring(0, 19);
        } else if (n.length() < 20){
            for (int i = 0; i > 20-n.length(); i++){
                n += " ";
            }
        }
        return n;
    }

    private int corregirNumReg(int nR){
        nR = nR & 0xF;
        return nR;
    }

    private float corregirNota(float n){
        byte[] bytes = ByteBuffer.allocate(4).putFloat(n).array();
        n = ByteBuffer.wrap(bytes).getFloat();
        return n;
    }

      //Acciones
    private boolean afegirEstudiant(int numReg, String nom, float nota, String path){
        //Escribir
        try{
            RandomAccessFile raf = new RandomAccessFile(path, "rw");
            raf.seek(raf.length());
            raf.write(numReg);
            raf.writeBytes(nom);
            raf.writeFloat(nota);
            raf.close();
            return true;
        } catch ( Exception e){ e.printStackTrace();}
        return false;
    }

    private long findRegistre(int numR, String path) {
        try (RandomAccessFile raf = new RandomAccessFile(path, "r")){
            int numRegistro;
            while (raf.getFilePointer() < raf.length()) {
                numRegistro = raf.readInt();
                if (numRegistro == numR) {
                    return raf.getFilePointer();
                }
                raf.readUTF();
                raf.readFloat();
            }
            raf.close();
            System.out.println("No s'ha trobat el registre");
            return -1;
        } catch (IOException e) { System.out.println("No s'ha trobat el ficher");
        } catch (Exception e) {System.out.println("Error");}
        return -1;
    }

    private boolean modificarNota(long pos, float n, String file){
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            raf.seek(pos);
            raf.skipBytes(20);
            raf.writeFloat(n);
            raf.close();
            return true;
        } catch (Exception e) {System.out.println("Error");}

        return false;
    }



}
