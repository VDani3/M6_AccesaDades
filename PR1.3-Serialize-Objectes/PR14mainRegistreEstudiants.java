import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class PR14mainRegistreEstudiants {
    private static int numeroRegistroMaximo = 0;
    public static void main(String[] args) {
        String menu = "\n\n\nMenu \n   1) Afegir nou estudiant \n   2) Actualitzar nota d'un estudiant \n   3) Consultar la nota d'un estudiant \n   4) Sortir \nOpcio: ";
        PR14mainRegistreEstudiants ex = new PR14mainRegistreEstudiants();
        int opt = 0;
        Scanner sc = new Scanner(System.in);
        String path = "./PR14registre.txt";
        File f = new File(path);

        if (f.exists()) {
            f.delete();
        }

        while (opt != 4) {
            if (sc.hasNextLine()) {
                sc.nextLine(); // Descarta la entrada actual en el búfer
            }
            System.out.print(menu);
            opt = sc.nextInt();
            if (sc.hasNextLine()) {
                sc.nextLine(); // Descarta la entrada actual en el búfer
            }
            if (opt == 1) {
                try {
                    System.out.println("\n\n\nAfegiment d'estudiants");
                    // Nou numero de registre
                    int numRegistre = ex.getNumRegistre();
                    if (numRegistre == 0) {
                        sc.nextLine();
                        continue;
                    }
                    // Nom
                    System.out.print("   Nom de l'alumne: ");
                    String name = sc.nextLine();
                    name = ex.comprobarNom(name);
                    // Nota
                    System.out.print("   Nota de l'alumne: ");
                    float note = sc.nextFloat();
                    note = ex.corregirNota(note);
                    if (!ex.afegirEstudiant(numRegistre, name, note, path)) {
                        System.out.println("No s'ha pogut afegir");
                        sc.nextLine();
                        continue;
                    }
                    System.out.println(String.format("S'ha creat l'estudiant %s amb la nota %.2f i numero de registre %d", name, note, numRegistre));
                    sc.nextLine();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (opt == 2) {
                try {
                    if (sc.hasNextLine()) {
                        sc.nextLine(); // Descarta la entrada actual en el búfer
                    }
                    int numReg;
                    long posReg;
                    float nota;

                    System.out.println("\n\n\nActualitzar nota");
                    System.out.print("   Num del registre: ");
                    numReg = sc.nextInt();
                    if (sc.hasNextLine()) {
                        sc.nextLine(); // Descarta la entrada actual en el búfer
                    }
                    posReg = ex.findRegistre(numReg, path);

                    if (posReg < 0) {
                        System.out.println("No existeix el registre");
                        sc.nextLine();
                        continue;
                    }

                    System.out.print("   Nota nova: ");
                    nota = sc.nextFloat();
                    if (!ex.modificarNota(posReg, nota, path)) {
                        System.out.println("No s'ha pogut modificar la nota");
                        sc.nextLine();
                        continue;
                    }
                    System.out.println(String.format("La nota de l'alumne amb el numero registre %d ha sigut cambiada per %.2f", numReg, nota));
                    sc.nextLine();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (opt == 3) {
                try {
                    if (sc.hasNextLine()) {
                        sc.nextLine(); // Descarta la entrada actual en el búfer
                    }
                    int numReg;
                    long posReg;
                    String alumneInfo;

                    System.out.println("\n\n\nVisualitzar alumne");
                    System.out.print("   Num del registre: ");
                    numReg = sc.nextInt();
                    if (sc.hasNextLine()) {
                        sc.nextLine(); // Descarta la entrada actual en el búfer
                    }
                    alumneInfo = ex.getInfoEstudiant(numReg, path);
                    if (alumneInfo.equals("")) {
                        System.out.println("No s'ha pogut llegir la info de l'alumne");
                        sc.nextLine();
                        continue;
                    }
                    System.out.println("Dades de l'estudiant:\n" + alumneInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Getters
    private int getNumRegistre() {
        // Incrementa el número de registro máximo
        numeroRegistroMaximo++;
        return numeroRegistroMaximo;
    }

    private String getInfoEstudiant(int numReg, String p) {
        try (RandomAccessFile raf = new RandomAccessFile(p, "r")) {
            int numRegistro;
            while (raf.getFilePointer() < raf.length()) {
                numRegistro = raf.readInt();
                if (numRegistro == numReg) {
                    byte[] nombreBytes = new byte[20];
                    raf.readFully(nombreBytes);
                    String nom = new String(nombreBytes, StandardCharsets.UTF_8).trim();
                    float nota = raf.readFloat();
                    return String.format("  Nom de l'alumne: %s\n  Nota: %.2f", nom, nota);
                } else {
                    // Saltar el nombre y la nota del estudiante no coincidente
                    raf.skipBytes(20);
                    raf.skipBytes(4);
                }
            }
        } catch (IOException e) {
            System.out.println("No s'ha trobat el fitxer");
        } catch (Exception e) {
            System.out.println("Error");
        }
        return "No s'ha trobat l'estudiant amb el número de registre " + numReg;
    }
    

    // Comprobaciones y correcciones
    private String comprobarNom(String n) {
        if (n.length() > 20) {
            n = n.substring(0, 20);
        } else if (n.length() < 20) {
            for (int i = n.length(); i < 20; i++) {
                n += " ";
            }
        }
        return n;
    }

    private int corregirNumReg(int nR) {
        nR = nR & 0xF;
        return nR;
    }

    private float corregirNota(float n) {
        return n;
    }

    // Acciones
    private boolean afegirEstudiant(int numReg, String nom, float nota, String path) {
        try {
            RandomAccessFile raf = new RandomAccessFile(path, "rw");
            raf.seek(raf.length());
            raf.writeInt(numReg);
            byte[] nombreBytes = nom.getBytes(StandardCharsets.UTF_8);
            byte[] nombreFormateado = new byte[20];
            System.arraycopy(nombreBytes, 0, nombreFormateado, 0, Math.min(nombreBytes.length, 20));
            raf.write(nombreFormateado);
            raf.writeFloat(nota);
            raf.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private long findRegistre(int numR, String path) {
        try (RandomAccessFile raf = new RandomAccessFile(path, "r")) {
            int numRegistro;
            while (raf.getFilePointer() < raf.length()) {
                numRegistro = raf.readInt();
                if (numRegistro == numR) {
                    return raf.getFilePointer();
                }
                raf.readFully(new byte[20]);
                raf.skipBytes(4); // Saltar 4 bytes para la nota
            }
            System.out.println("No s'ha trobat el registre");
            return -1;
        } catch (IOException e) {
            System.out.println("No s'ha trobat el fitxer");
        } catch (Exception e) {
            System.out.println("Error");
        }
        return -1;
    }

    private boolean modificarNota(long pos, float n, String file) {
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            raf.seek(pos + 20); // Saltar al campo de nota
            raf.writeFloat(n);
            raf.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error");
        }
        return false;
    }
}
