import java.util.ArrayList;
import java.util.List;

//Class represnting a pantry to store item
public class Pantry {

    private List<String> items; //list to store pantry items.

    public Pantry() {
        this.items = new ArrayList<>();
    }
    //method to add an item to pantry
    public void additem(String item) {
        items.add(item);
    }
    // method to get items from the pantry.
    public List<String> getitems() {
        return items;
    }
}
