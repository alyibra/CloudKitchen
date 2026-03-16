package cloudKitchen.java;

public class Food extends MenuItem {
	private String description;
	
	public Food(String name, double price, String description) {
		super(name, price);
		this.description = description;
		}
	@Override
    public void displayDetails() {
       // System.out.println(getItemName() + description + getItemPrice()); 
        System.out.println("Food Item: " + getItemName());
        System.out.println("Description: " + description);
        System.out.println("Price: $" + String.format("%.2f", getItemPrice()));
    }
	
	public String getDescription() {
        return description;
    }
	public void setDescription(String description) {
        this.description = description;
    }
	
	 @Override
	    public String toString() {
	        return super.toString() + " - " + description;
	    }
	 
}
