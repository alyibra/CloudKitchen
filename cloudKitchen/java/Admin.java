package cloudKitchen.java;

import java.util.ArrayList;

/**
 * Admin class for managing the Cloud Kitchen system
 */
public class Admin extends User {
    /**
     * Default constructor
     */
    public Admin() {
        super();
    }

    /**
     * Basic constructor with essential user information
     */
    public Admin(String name, String email, String password) {
        super(name, email, password);
    }

 
    /**
     * Add a customer to the system database
     */
    public void addUser(SystemDatabase db, Customer user) {
        if (db != null && user != null) {
            db.addCustomer(user);
            System.out.println("Customer " + user.getstudentName() + " added successfully.");
        }
    }

    /**
     * Delete a customer from the system database by email
     */
    public void deleteUser(SystemDatabase db, String email) {
        if (db != null && email != null) {
            ArrayList<Customer> customers = db.getCustomers();
            if (customers != null) {
                for (int i = 0; i < customers.size(); i++) {
                    if (customers.get(i).getstudentEmail().equals(email)) {
                        Customer removedCustomer = customers.get(i);
                        db.removeCustomer(removedCustomer);
                        System.out.println("Customer with email " + email + " deleted successfully.");
                        return;
                    }
                }
                System.out.println("No customer found with email: " + email);
            }
        }
    }

    /**
     * Add a menu item to the system database
     */
    public void addMenuItem(SystemDatabase db, String itemName, MenuItem item) {
        if (db != null && item != null && itemName != null) {
            db.addMenuItem(itemName, item);
            System.out.println("Menu item " + itemName + " added successfully.");
        }
    }

    /**
     * Delete a menu item from the system database by name
     */
    public void deleteMenuItem(SystemDatabase db, String itemName) {
        if (db != null && itemName != null) {
            db.removeMenuItem(itemName);
            System.out.println("Menu item " + itemName + " deleted successfully.");
        }
    }

    /**
     * Add a chef to the system database
     */
    public void addChef(SystemDatabase db, Chef chef) {
        if (db != null && chef != null) {
            db.addChef(chef);
            System.out.println("Chef " + chef.getName() + " added successfully.");
        }
    }

    /**
     * Add a delivery person to the system database
     */
    public void addDeliveryman(SystemDatabase db, Deliveryman deliveryman) {
        if (db != null && deliveryman != null) {
            db.addDeliveryman(deliveryman);
            System.out.println("Deliveryman " + deliveryman.getName() + " added successfully.");
        }
    }

    /**
     * View all customers in the system
     */
    public void viewAllCustomers(SystemDatabase db) {
        if (db != null) {
            ArrayList<Customer> customers = db.getCustomers();
            if (customers != null && !customers.isEmpty()) {
                System.out.println("\n=== All Customers ===");
                for (Customer customer : customers) {
                    System.out.println(customer);
                }
            } else {
                System.out.println("No customers found in the system.");
            }
        }
    }

    /**
     * View all orders in the system
     */
    public void viewAllOrders(SystemDatabase db) {
        if (db != null) {
            ArrayList<Order> orders = db.getOrders();
            if (orders != null && !orders.isEmpty()) {
                System.out.println("\n=== All Orders ===");
                for (Order order : orders) {
                    System.out.println(order);
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("No orders found in the system.");
            }
        }
    }

    /**
     * View all menu items in the system
     */
    public void viewAllMenuItems(SystemDatabase db) {
        if (db != null) {
            var menuItems = db.getMenuItems();
            if (menuItems != null && !menuItems.isEmpty()) {
                System.out.println("\n=== All Menu Items ===");
                for (String itemName : menuItems.keySet()) {
                    MenuItem item = menuItems.get(itemName);
                    System.out.println(itemName + " - $" + item.getItemPrice());
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("No menu items found in the system.");
            }
        }
    }

    /**
     * View all chefs in the system
     */
    public void viewAllChefs(SystemDatabase db) {
        if (db != null) {
            ArrayList<Chef> chefs = db.getChefs();
            if (chefs != null && !chefs.isEmpty()) {
                System.out.println("\n=== All Chefs ===");
                for (Chef chef : chefs) {
                    System.out.println(chef);
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("No chefs found in the system.");
            }
        }
    }

    /**
     * View all delivery personnel in the system
     */
    public void viewAllDeliverymen(SystemDatabase db) {
        if (db != null) {
            ArrayList<Deliveryman> deliverymen = db.getDeliverymen();
            if (deliverymen != null && !deliverymen.isEmpty()) {
                System.out.println("\n=== All Delivery Personnel ===");
                for (Deliveryman deliveryman : deliverymen) {
                    System.out.println(deliveryman);
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("No delivery personnel found in the system.");
            }
        }
    }

    @Override
    public String toString() {
        return getName() + " " + getEmail() + " " + getPassword() + " " + 
               getAge() + " " + getSalary() + " " + getGender();
    }
}