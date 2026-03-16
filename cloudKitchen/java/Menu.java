package cloudKitchen.java;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;



public class Menu {
	private ArrayList<MenuItem> menuItems;
	
	public Menu() {
		this.menuItems = new ArrayList<>();
	}
	
	public void addItem(MenuItem item) {
		if (item != null) {
            menuItems.add(item);
            System.out.println("Added: " + item.getItemName());
        }
		
	}
	public void deleteItem(MenuItem item) {
		if (item != null) {
        menuItems.remove(item);
        System.out.println("Removed: " + item.getItemName());
    }
		else {
        System.out.println("Item not found in menu");
    }
	}
	
	public boolean updateItem(MenuItem oldItem, MenuItem newItem) {
        if (oldItem != null && newItem != null && menuItems.contains(oldItem)) {
            int index = menuItems.indexOf(oldItem);
            menuItems.set(index, newItem);
            System.out.println("Updated: " + newItem.getItemName());
            return true;
        }
        System.out.println("Update failed: Item not found");
        return false;
    }
	


	 public List<MenuItem> getAllItems() {
	        return new LinkedList<>(menuItems);
	    }
	 
	 
	 public List<Food> getFoodItems() {
	        List<Food> foodItems = new LinkedList<>();
	        for (MenuItem item : menuItems) {
	            if (item instanceof Food) {
	                foodItems.add((Food) item);
	            }
	        }
	        return foodItems;
	    }
	 
	 public List<Beverage> getBeverageItems() {
	        List<Beverage> beverageItems = new LinkedList<>();
	        for (MenuItem item : menuItems) {
	            if (item instanceof Beverage) {
	                beverageItems.add((Beverage) item);
	            }
	        }
	        return beverageItems;
	    }
	 
	 public void displayMenu() {
	        System.out.println("===== CLOUD KITCHEN MENU =====");
	        
	        System.out.println("\n--- FOOD ITEMS ---");
	        for (MenuItem item : menuItems) {
	            if (item instanceof Food) {
	                item.displayDetails();
	                System.out.println("------------------------");
	            }
	        }
	        
	        System.out.println("\n--- BEVERAGES ---");
	       
	        for (MenuItem item : menuItems) {
	            if (item instanceof Beverage) {
	                item.displayDetails();
	                System.out.println("------------------------");
	            }
	        }
	        }

	 public MenuItem findItemByName(String name) {
	        for (MenuItem item : menuItems) {
	            if (item.getItemName().equalsIgnoreCase(name)) {
	                return item;
	            }
	        }
	        return null;
	    }
	 
	 
	 
	 public int getItemCount() {
	        return menuItems.size();
	    }
	 
}
