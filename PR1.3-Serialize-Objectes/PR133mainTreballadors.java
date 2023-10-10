import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PR133mainTreballadors {
    
    public static void main(String[] args) {
        String filePath = "./PR133treballadors.csv";
        List<String> csv = UtilsCSV.read(filePath);
        String[] columnes = UtilsCSV.getKeys(csv);
        String[] arrLine;
        columnes[0] = columnes[0].substring(1);
        Scanner sc = new Scanner(System.in);
        String opt, strLinea, oldData, newData;
        int numlinea, posOpt, opt2;
        
        try {
            System.out.print("Id de la persona: ");
            opt2 = Integer.parseInt(sc.nextLine());
            System.out.print("Que dada vols modificar?: ");
            opt = sc.nextLine();
            opt = opt.toLowerCase();
            System.out.print("Nou valor: ");
            newData = sc.nextLine();

            numlinea = getLineNumber(csv, Integer.toString(opt2));  //He tingut que fer el meu propi ^^
            strLinea = csv.get(numlinea);
            arrLine = UtilsCSV.getLineArray(strLinea);
            posOpt = UtilsCSV.csvGetColumnPosition(csv, opt);
            oldData = arrLine[posOpt];
            
            UtilsCSV.update(csv, numlinea, opt, newData);
            UtilsCSV.write(filePath, csv);
            System.out.println(String.format("S'ha canviat el %s de la persona de %s a %s",opt, oldData, newData));
            sc.nextLine();

        } catch (Exception e){ System.out.println("Error");}
    }

    public static int getLineNumber(List<String> ar, String id){
        int res = 0;
        String num;
        for (String elemento : ar) {
            num = "";
            for (int o = 0; o < elemento.length(); o++) {
                char caracter = elemento.charAt(o);
                if (caracter == ';' || caracter == ',') {
                    break;
                } else {
                    num += caracter;
                }
            }
            if (num.equals(id)) {
                return ar.indexOf(elemento);
            }
        }
        return res;
    }
}
