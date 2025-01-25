import java.util.HashMap; //import the HashMap
/*
Name:Henil Patel
Student No: 7084577
Date: 25/3/2024

Assigment  3
 */
public class IngredientMapper {
    private  HashMap<String,Integer> labeltoIdMap;  // Map to store labeltoIdmappings
    private HashMap<Integer,String>idToLabelMap; // Map to store Idtolabel mappings
    private int nxtid;
    public IngredientMapper(){
        labeltoIdMap = new HashMap<>();
        idToLabelMap = new HashMap<>();
        nxtid = 1;
    }
    // Method to add mapping of labeltoid
    public int addMapping(String label){
        if (!labeltoIdMap.containsKey(label)) {
            int id = nxtid++;
            labeltoIdMap.put(label, id);
            idToLabelMap.put(id, label);
            return id;
        }
        return labeltoIdMap.get(label);
    }
}
