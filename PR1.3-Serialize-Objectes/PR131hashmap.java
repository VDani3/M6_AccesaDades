import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

public class PR131hashmap implements Serializable{
    public HashMap<String, Integer> p = new HashMap<String, Integer>();

    public PR131hashmap() {}

    public void anyadirVal(String n, int e) {
        p.put(n, e);
    }
}
