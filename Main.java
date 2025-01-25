import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc=new Scanner(System.in);
        Cookbook cb=new Cookbook(100);
        Pantry pantry=new Pantry();
        try{
            cb.loadrecipes("recipie.txt"); //load recipes

        }catch (FileNotFoundException e){
            System.out.println("File not found");
            return;
        }
        loadpantrycpntents(pantry,"pantry.txt"); //load pantry contents
        //Displaying menu options:
        System.out.println("Welcome to Recipe Checker");
        System.out.println("Please choose option:");
        System.out.println("1.Display all the dishes");
        System.out.println("2.Show all the dishes than canbe made");
        System.out.println("3.Show your current Pantry");
        System.out.println("4.add item to Pantry");
        System.out.println("5.Create shopping list");
        System.out.println("6.Display mise en place");
        System.out.println("7.View your shopping list");
        System.out.println("8.Exit");
        while (true) {
            System.out.println("enter your choice please:");
            int choice=sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    displayalldishes(cb);
                    break;
                case 2:
                   dishesthatcanbemade(cb,pantry);
                    break;
                case 3:
                    showpantrystock(pantry);
                    break;
                case 4:
                    additemtopantry(pantry,sc);
                    break;
                case 5:
                    gotoshopping(cb, sc,pantry);
                    break;
                case 6:
                   misenplace(cb,sc);
                    break;
                    case 7:
                        viewshoppinglist();
                        break;
                case 8:
                    System.out.println("Thanks for using this: Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }

    }
// view saved shopping list.
    private static void viewshoppinglist() {
        String filename = "shoppinglist.txt";
        try ( Scanner reader = new Scanner(new File(filename))) {
            while(reader.hasNextLine()) {
                System.out.println("-"+reader.nextLine());
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    //calling out misenplace from cookbook

    private static void misenplace(Cookbook cb, Scanner sc) {
        System.out.println("Enterr desired dish for misen place");
        String dish=sc.nextLine();
        List<String> misenplace=cb.getMiseEnPlace(dish);
        if (!misenplace.isEmpty()) {
            System.out.println("misen place for dish:");
            for(String item:misenplace){
                System.out.println("-"+item);
            }
        } else {
            System.out.println("No misen place for this dish");
        }
    }
//method calling shoppinglist method from cookbook to add the items.
    private static void gotoshopping(Cookbook cb, Scanner sc, Pantry pantry) {
        System.out.println("Enter the name of dish : ");
        String input=sc.nextLine();
        String [] desireddish=input.split(" ,");
        List<String> shplist=cb.createshoppinglist(List.of(desireddish),pantry);

        System.out.println("Shopping list:");
        for (int i = 0, shplistSize = shplist.size(); i < shplistSize; i++) {
            String item = shplist.get(i);
            System.out.println("-" + item);
        }
        saveshoppinglisttofile(shplist);

    }
    //this method will save the shopping list into a file called shoppinglist.txt

    private static void saveshoppinglisttofile(List<String> shplist) {
        String filename="shoppinglist.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String item : shplist) {
                writer.write(item);
                writer.newLine();
            }
            System.out.println(" saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
    //this method will be adding pantry items and user will  have options to add it and if we want to add one by one , seaprate by comma(,)

    private static void additemtopantry(Pantry pantry, Scanner sc) {
        System.out.println("Enter items to add to Pantry (comma with seaprated):");
        String input=sc.nextLine();
        String[] items=input.split(",");
        for(String item:items){
            pantry.additem(item.trim());
        }
        System.out.println("Item added to pantry you can see it with uppercase");
        savetopantryfile(pantry);
    }
    //this method will save the pantry items into a file called pantry.txt

    private static void savetopantryfile(Pantry pantry) {
        String filename="pantry.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            List<String> items=pantry.getitems();
            for (String item : pantry.getitems()) {
                writer.write(item);
                writer.newLine();
            }
            System.out.println(" saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    // This method will be printing out the pantry items i.e. contains of the pantry items
    private static void showpantrystock(Pantry pantry) {
        List<String> pantrycontents=pantry.getitems();
        System.out.println("Your stock:");
        for(String item:pantrycontents){
            System.out.println("-"+item);
        }
    }
    // this method will call dishesthatcanbemade from checking out the current stock of
    // pantry items and comparing it with recipes then it gonna return those name of the dishes

    private static void dishesthatcanbemade(Cookbook cb, Pantry pantry) {
        List<String> alldish = cb.dishesthatcanbemade(pantry);
        if (alldish.isEmpty()) {
            System.out.println("Nothing can be made");
        } else {
            System.out.println("dishes can be made with your stock is:");
            for (int i = 0, alldishSize = alldish.size(); i < alldishSize; i++) {
                String dish = alldish.get(i);
                System.out.println("-" + dish);
            }

        }
    }

    //this method will print all the dishes in the given recipes files.

    private static void displayalldishes(Cookbook cb) {
        List<String> alldish = cb.getalldishes();
        System.out.println("Available Dishes in you file");
        for(String dish: alldish){
            System.out.println("-"+dish);
        }
    }
    //this method will load the pantry items from a file

    private static void loadpantrycpntents(Pantry pantry, String file) throws FileNotFoundException {
        File f=new File(file);
        Scanner scanner=new Scanner(f);
        while (scanner.hasNextLine()){
            String item=scanner.nextLine().trim();
            pantry.additem(item);

        }
        scanner.close();

    }


}
