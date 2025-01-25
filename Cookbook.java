import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//Main class to manage CookBook
/*
Name:Henil Patel
Student No: 7084577
Date: 25/3/2024

Assigment  3
 */
public class Cookbook {
    public Graph graph; //Graph
    public IngredientMapper imapper; //Mapper
    private List<String[]> dishses;  //List of dishes
    private Map<String,List<String>> recipesMap;  //Map of recipes
    public Cookbook(int numofIngridents){
        this.dishses=new ArrayList<>();
        this.recipesMap=new HashMap<>();
        this.graph=new Graph(numofIngridents);
        this.imapper =new IngredientMapper();
    }
    //Method to load recipes and dishes name from the given file , and will make sure after 10('-') it will  be ingredients after that.
    public void loadrecipes(String filename) throws FileNotFoundException{
    File file=new File(filename);
    Scanner sc=new Scanner(file);
    boolean isDish=true;
    while(sc.hasNextLine()){
        String line=sc.nextLine().trim();
        //Chechking if line represent  a seapration between and dishes and ingridents
        if(line.equals("----------")){
            isDish=false;
            continue;
        }
        if(isDish){
            String[] dishPrts=line.split("\t");
            String dishname=dishPrts[0].trim(); //assuming that dishname is at 0th position
            String[] ingredients=dishPrts[1].split(",");
            dishses.add(dishPrts);
            imapper.addMapping(dishname);
            List<String> ingredientslist = Arrays.asList(ingredients);
            recipesMap.put(dishname,ingredientslist);
        }
        else{
            String[] ingredients=line.split(",");
            for(String ingredient : ingredients){
                imapper.addMapping(ingredient.trim());
            }
        }
    }
    sc.close();
    }
    //Method to get all dishesname from the given file
    public List<String>getalldishes(){
        List<String> nameofdishes = new ArrayList<>();
        for(String[] dish : dishses){
            nameofdishes.add(dish[0].trim());
        }
        return nameofdishes;
    }

    //Method to get all ingredients from the given file
    public List<String> getingredients(){
        Set<String> allingredients = new HashSet<>();
        for (int i = 0, dishsesSize = dishses.size(); i < dishsesSize; i++) {
            String[] dishdata = dishses.get(i);
            List<String> ingredients = Arrays.asList(dishdata[1]);
            allingredients.addAll(ingredients);
        }
        return new ArrayList<>(allingredients);
    }
    //Method to get all recipes from the given file i.e. the ingredients
    public List<String> dishesthatcanbemade(Pantry pantry) {
        List<String> dishesWithCurrentStock = new ArrayList<>();
        List<String> pantryItems = pantry.getitems();

        for (String dishName : getalldishes()) {
            List<String> ingredients = recipesMap.get(dishName);
            if (ingredients == null) continue;

            boolean canMakeDish = true;
            for (String ingredient : ingredients) {
                if (!pantryItems.contains(ingredient) && !cangetingrident(ingredient, pantryItems)) {
                    canMakeDish = false;
                    break;
                }
            }

            if (canMakeDish) {
                dishesWithCurrentStock.add(dishName);
            }
        }

        return dishesWithCurrentStock;
    }
    //metho that will check whether it can match up the dishes ingridents with Current Pantry Stock.

    private boolean cangetingrident(String ingredient, List<String> pantryItems) {
        // Check if the ingredient is already a base ingredient
        if (pantryItems.contains(ingredient)) {
            return true;
        }

        // Check if the ingredient can be derived from other ingredients in the pantry
        for (String item : pantryItems) {
            List<String> derivedIngredients = getthespecificingrident(item);
            if (derivedIngredients.contains(ingredient)) {
                return true;
            }
        }

        return false;
    }

    // method to check whether the ingredient can be derived
    private boolean cangetingrident(String ingredient, Set<String> pantryItems) {
        if (pantryItems.contains(ingredient)) {
            return true;
        }
        for (String item : pantryItems) {
            List<String> derivedIngredients = getthespecificingrident(item);
            if (derivedIngredients.contains(ingredient)) {
                return true;
            }
        }

        return false;
    }
    //this method willcheck whether  specified ingredient can be derived from the current pantry item or not
    private List<String> getthespecificingrident(String item) {
        List<String> derivedIngredients = new ArrayList<>();


        for (String[] recipe : dishses) {
            String[] ingredients = recipe[1].split(",");


            if (Arrays.asList(ingredients).contains(item)) {
                for (String ingredient : ingredients) {
                    if (!ingredient.equals(item)) {
                        derivedIngredients.add(ingredient.trim());
                    }
                }
            }
        }

        return derivedIngredients;
    }
    // Method to create a shopping lisr based on the pantry if that pantry is not available it will be created in the shopping list.

public List<String> createshoppinglist(List<String> desiredDishes, Pantry pantry) {
    List<String> shoppingList = new ArrayList<>();


    Set<String> pantryItems = new HashSet<>(pantry.getitems());

    for (String dish : desiredDishes) {
        System.out.println("Processing dish: " + dish);

        List<String> ingredients = recipesMap.get(dish);
        if (ingredients == null) {
            System.out.println("Ingredients not found for dish: " + dish);
            continue;
        }


        for (String ingredient : ingredients) {
            System.out.println("Checking ingredient: " + ingredient);


            if (!pantryItems.contains(ingredient) && !cangetingrident(ingredient, pantryItems)) {

                System.out.println("Adding ingredient to shopping list: " + ingredient);
                shoppingList.add(ingredient);
            } else {
                System.out.println("Ingredient already in pantry or can be derived: " + ingredient);
            }
        }
    }

    return shoppingList;
}
// method to get mise en place for dish :

public List<String> getMiseEnPlace(String dish){
        List<String> misenplace=new ArrayList<>();
        List<String> ingredients=recipesMap.get(dish);
        if(ingredients!=null){
            misenplace.addAll(ingredients);
        }
        else{
            System.out.println("dishes are not in your datafile");

        }
        return misenplace;
}

}
