import java.io.Serializable;

public class PR132persona implements Serializable{
    private String nom;
    private String cognom;
    private int edat;

    public PR132persona(String n, String c, int e) {
        this.nom = n;
        this.cognom = c;
        this.edat = e;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getCognom() {
        return cognom;
    }
    public void setCognom(String cognom) {
        this.cognom = cognom;
    }
    public int getEdat() {
        return edat;
    }
    public void setEdat(int edat) {
        this.edat = edat;
    }

    @Override
    public String toString() {
        String res = "Nom: "+nom+"   Cognom: "+cognom+"   Edat: "+Integer.toString(edat);
        return res;
    }
    
}
