package cloudKitchen.java;

public class Beverage extends MenuItem {
	private int volume;
	
	public Beverage(String name, double price, int volume) {
		super(name, price);
		this.volume = volume;
	}
	
	@Override
    public void displayDetails() {
      //  System.out.println(getItemName() + volume + getItemPrice());
        System.out.println("Beverage: " + getItemName());
        System.out.println("Volume: " + volume + " ml");
        System.out.println("Price: $" + String.format("%.2f", getItemPrice()));
    }
	
	public int getVolume() {
        return volume;
    }
	public void setVolume(int volume) {
        this.volume = volume;
    }
	
	 @Override
	    public String toString() {
	        return super.toString() + " (" + volume + " ml)";
	    }

}
