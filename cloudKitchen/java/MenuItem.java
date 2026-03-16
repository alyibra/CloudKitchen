package cloudKitchen.java;

public abstract class MenuItem {
	private String itemName;
	private double itemPrice;
	
	public MenuItem(String itemName, double itemPrice) {
		this.itemName = itemName;
		this.itemPrice = itemPrice;
	}
	
	public abstract void displayDetails();

    public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getItemPrice() {
	return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
	this.itemPrice = itemPrice;
		
	}


}
