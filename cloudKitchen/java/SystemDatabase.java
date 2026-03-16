package cloudKitchen.java;

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A database contains customers, admins, chefs, deliverymen, menuItems, orders,
 * and mealPlans.
 */
public class SystemDatabase {
	private HashMap<String, MenuItem> menuItems;
	private ArrayList<Order> orders;
	private ArrayList<Customer> customers;
	private ArrayList<Admin> admins;
	private ArrayList<Chef> chefs;
	private ArrayList<Deliveryman> deliverymen;


	
	/**
	 * Constructs a database with no customers, admins, chefs, deliverymen, menuItems,
	 * orders, and mealPlans.
	 */
	public SystemDatabase() {
		customers = new ArrayList<>();
		admins = new ArrayList<>();
		chefs = new ArrayList<>();
		deliverymen = new ArrayList<>();
		menuItems = new HashMap<>();
		orders = new ArrayList<>();
		
	}
	
	/**
	 * Reads the menu item names and prices.
	 * @param filename the name of the menu item file
	 */
	public void readMenuItems(String filename) throws IOException {
		Scanner in = new Scanner(new File(filename));
		
		while (in.hasNext()) {
			String itemName = in.next();
			double itemPrice = in.nextDouble();
			// Note: This is simplified as we don't have the complete MenuItem hierarchy
			// In a real implementation, you'd need to determine the type of MenuItem
			MenuItem m = new Food(itemName, itemPrice, ""); // Default to Food for simplicity
			addMenuItem(itemName, m);
		}
		
		in.close();
	}
	
	/**
	 * Reads the order IDs, dates, status, delivery status, assigned customers, and total amounts.
	 * @param filename the name of the order file
	 */
	public void readOrders(String filename) throws IOException {
		Scanner in = new Scanner(new File(filename));
		
		while (in.hasNext()) {
			int orderID = in.nextInt();
			String orderDate = in.next();
			String orderStatus = in.next();
			String deliveryStatus = in.next();
			String assignedCustomer = in.next();
			double totalAmount = in.nextDouble();
			Order o = new Order(orderID, orderDate, orderStatus, deliveryStatus,
								assignedCustomer, totalAmount);
			addOrder(o);
		}
		
		in.close();
	}
	
	/**
	 * Reads the customer names, e-mails, passwords, dorm names, phone numbers, and current plans.
	 * @param filename the name of the customer file
	 */
	public void readCustomers(String filename) throws IOException {
		Scanner in = new Scanner(new File(filename));
		
		while (in.hasNext()) {
			String name = in.next();
			String email = in.next();
			String password = in.next();
			String dormName = in.next();
			String phoneNumber = in.next();
			
			Customer c = new Customer();
			c.setstudentName(name);
			c.setstudentEmail(email);
			c.setstudentPassword(password);
			c.setdormName(dormName);
			c.setphoneNumber(phoneNumber);
			addCustomer(c);
		}
		
		in.close();
	}
	
	/**
	 * Reads the admin names, e-mails, passwords, ages, salaries, and genders.
	 * @param filename the name of the admin file
	 */
	public void readAdmins(String filename) throws IOException {
		Scanner in = new Scanner(new File(filename));
		
		while (in.hasNext()) {
			String name = in.next();
			String email = in.next();
			String password = in.next();
			int age = in.nextInt();
			double salary = in.nextDouble();
			char gender = in.next().charAt(0);
			Admin a = new Admin(name, email, password);
			addAdmin(a);
		}
		
		in.close();
	}
	
	/**
	 * Reads the chef names, e-mails, passwords, ages, salaries, and genders.
	 * @param filename the name of the chef file
	 */
	public void readChefs(String filename) throws IOException {
		Scanner in = new Scanner(new File(filename));
		
		while (in.hasNext()) {
			String name = in.next();
			String email = in.next();
			String password = in.next();
			int age = in.nextInt();
			double salary = in.nextDouble();
			char gender = in.next().charAt(0);
			Chef c = new Chef(name, email, password);
			addChef(c);
		}
		
		in.close();
	}
	
	/**
	 * Reads the deliveryman names, e-mails, passwords, ages, salaries, and genders.
	 * @param filename the name of the deliveryman file
	 */
	public void readDeliverymen(String filename) throws IOException {
		Scanner in = new Scanner(new File(filename));
		
		while (in.hasNext()) {
			String name = in.next();
			String email = in.next();
			String password = in.next();
			String assignedDorm = in.next();
			int age = in.nextInt();
			double salary = in.nextDouble();
			char gender = in.next().charAt(0);
			Deliveryman d = new Deliveryman(name, email, password);
			addDeliveryman(d);
		}
		
		in.close();
	}
	
	/**
	 * Adds a customer to the database.
	 * @param c the customer to add
	 */
	public void addCustomer(Customer c) {
		customers.add(c);
	}
	
	/**
	 * Adds an admin to the database.
	 * @param a the admin to add
	 */
	public void addAdmin(Admin a) {
		admins.add(a);
	}
	
	/**
	 * Adds a chef to the database.
	 * @param c the chef to add
	 */
	public void addChef(Chef c) {
		chefs.add(c);
	}
	
	/**
	 * Adds a deliveryman to the database.
	 * @param d the deliveryman to add
	 */
	public void addDeliveryman(Deliveryman d) {
		deliverymen.add(d);
	}
	
	/**
	 * Adds a MenuItem to the database.
	 * @param s the key for the menu item
	 * @param m the MenuItem to add
	 */
	public void addMenuItem(String s, MenuItem m) {
		menuItems.put(s, m);
	}
	
	/**
	 * Adds an order to the database.
	 * @param o the order to add
	 */
	public void addOrder(Order o) {
		orders.add(o);
	}
	
	/**
	 * Adds a meal plan to the database.
	 * @param m the meal plan to add
	 */

	/**
	 * Removes a customer from the database.
	 * @param c the customer to remove
	 */
	public void removeCustomer(Customer c) {
		customers.remove(c);
	}
	
	/**
	 * Removes an admin from the database.
	 * @param a the admin to remove
	 */
	public void removeAdmin(Admin a) {
		admins.remove(a);
	}
	
	/**
	 * Remove a chef from the database.
	 * @param c the chef to remove
	 */
	public void removeChef(Chef c) {
		chefs.remove(c);
	}
	
	/**
	 * Remove a deliveryman from the database.
	 * @param d the deliveryman to remove
	 */
	public void removeDeliveryman(Deliveryman d) {
		deliverymen.remove(d);
	}
	
	/**
	 * Removes a MenuItem from the database.
	 * @param itemName the name of the MenuItem to remove
	 */
	public void removeMenuItem(String itemName) {
		menuItems.remove(itemName);
	}
	
	/**
	 * Removes an order from the database.
	 * @param o the order to remove
	 */
	public void removeOrder(Order o) {
		orders.remove(o);
	}
	

	
	/**
	 * Finds a customer in the database.
	 * @param anEmail a customer email
	 * @param aPassword a customer password
	 * @return the matching customer, or null if no customer matches
	 */
	public Customer findCustomer(String anEmail, String aPassword) {
	    for (Customer c : customers) {
	        if (c.match(anEmail, aPassword)) {
	            return c;
	        }
	    }
	    return null;
	}
	
	/**
	 * Finds an admin in the database.
	 * @param anEmail an admin email
	 * @param aPassword an admin password
	 * @return the matching admin, or null if no admin matches
	 */
	public Admin findAdmin(String anEmail, int aPassword) {
		for (Admin a : admins)
			if (a.match(anEmail, aPassword))
				return a;
		return null;
	}
	
	/**
	 * Finds a chef in the database.
	 * @param anEmail a chef email
	 * @param aPassword a chef password
	 * @return the matching chef, or null if no chef matches
	 */
	public Chef findChef(String anEmail, int aPassword) {
		for (Chef c : chefs)
			if (c.match(anEmail, aPassword))
				return c;
		return null;
	}
	
	/**
	 * Finds a deliveryman in the database.
	 * @param anEmail a deliveryman email
	 * @param aPassword a deliveryman password
	 * @return the matching deliveryman, or null if no deliveryman matches
	 */
	public Deliveryman findDeliveryman(String anEmail, int aPassword) {
		for (Deliveryman d : deliverymen)
			if (d.match(anEmail, aPassword))
				return d;
		return null;
	}
	
	/**
	 * Finds plan items in the database.
	 * @param listOfNames plan items names
	 * @return the matching plan items
	 */
	public ArrayList<MenuItem> findPlanItems(ArrayList<String> listOfNames) {
		ArrayList<MenuItem> matchedItems = new ArrayList<>();
		for (String s : listOfNames) {
			MenuItem item = menuItems.get(s);
			if (item != null) {
				matchedItems.add(item);
			}
		}
		return matchedItems;
	}
	
	/**
	 * Get all customers in the database
	 * @return ArrayList of all customers
	 */
	public ArrayList<Customer> getCustomers() {
		return customers;
	}
	
	/**
	 * Get all admins in the database
	 * @return ArrayList of all admins
	 */
	public ArrayList<Admin> getAdmins() {
		return admins;
	}
	
	/**
	 * Get all chefs in the database
	 * @return ArrayList of all chefs
	 */
	public ArrayList<Chef> getChefs() {
		return chefs;
	}
	
	/**
	 * Get all deliverymen in the database
	 * @return ArrayList of all deliverymen
	 */
	public ArrayList<Deliveryman> getDeliverymen() {
		return deliverymen;
	}
	
	/**
	 * Get all menu items in the database
	 * @return HashMap of all menu items
	 */
	public HashMap<String, MenuItem> getMenuItems() {
		return menuItems;
	}
	
	/**
	 * Get all orders in the database
	 * @return ArrayList of all orders
	 */
	public ArrayList<Order> getOrders() {
		return orders;
	}
	
	/**
	 * Get all meal plans in the database
	 * @return ArrayList of all meal plans
	 */

	/**
	 * Writes the menu item names and prices.
	 * @param filename the name of the menu item file
	 */
	public void writeMenuItems(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new File(filename));
		
		for (String key : menuItems.keySet()) {
			MenuItem m = menuItems.get(key);
			out.println(key + " " + m.getItemPrice());
		}
		
		out.close();
	}
	
	/**
	 * Writes the order IDs, dates, status, delivery status, assigned customers, and total amounts.
	 * @param filename the name of the order file
	 */
	public void writeOrders(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new File(filename));
		
		for (Order o : orders)
			out.println(o);
		
		out.close();
	}
	
	/**
	 * Writes the customer names, e-mails, passwords, dorm names, phone numbers, and current plans.
	 * @param filename the name of the customer file
	 */
	public void writeCustomers(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new File(filename));
		
		for (Customer c : customers)
			out.println(c);
		
		out.close();
	}
	
	/**
	 * Writes the admin names, e-mails, passwords, ages, salaries, and genders.
	 * @param filename the name of the admin file
	 */
	public void writeAdmins(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new File(filename));
		
		for (Admin a : admins)
			out.println(a);
		
		out.close();
	}
	
	/**
	 * Writes the chef names, e-mails, passwords, ages, salaries, and genders.
	 * @param filename the name of the chef file
	 */
	public void writeChefs(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new File(filename));
		
		for (Chef c : chefs)
			out.println(c);
		
		out.close();
	}
	
	/**
	 * Writes the deliveryman names, e-mails, passwords, ages, salaries, and genders.
	 * @param filename the name of the deliveryman file
	 */
	public void writeDeliverymen(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new File(filename));
		
		for (Deliveryman d : deliverymen)
			out.println(d);
		
		out.close();
	}
	

}
