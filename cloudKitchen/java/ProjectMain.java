package cloudKitchen.java;
import java.io.IOException;
import java.util.Scanner;

public class ProjectMain {
	private static SystemDatabase database;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        // Initialize system components
        database = new SystemDatabase();
        scanner = new Scanner(System.in);
        
        // Load data from files
        loadData();
        
        // Display welcome message
        System.out.println("===================================");
        System.out.println("  WELCOME TO CLOUD KITCHEN SYSTEM  ");
        System.out.println("===================================");
        
        // Main application loop
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getUserChoice(1, 4);
            
            switch (choice) {
                case 1: // Customer login/register
                    handleCustomerAccess();
                    break;
                case 2: // Staff login (Admin, Chef, Deliveryman)
                    handleStaffAccess();
                    break;
                case 3: // About the system
                    displayAboutInfo();
                    break;
                case 4: // Exit
                    running = false;
                    saveData();
                    System.out.println("Thank you for using Cloud Kitchen. Goodbye!");
                    break;
            }
        }
        
        scanner.close();
    }
    
    /**
     * Display the main menu options
     */
    private static void displayMainMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1. Customer Access");
        System.out.println("2. Staff Access");
        System.out.println("3. About");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Handle customer login and registration
     */
    private static void handleCustomerAccess() {
        System.out.println("\n===== CUSTOMER ACCESS =====");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = getUserChoice(1, 3);
        
        switch (choice) {
            case 1: // Login
                customerLogin();
                break;
            case 2: // Register
                customerRegister();
                break;
            case 3: // Back
                return;
        }
    }
    
    /**
     * Handle staff login (Admin, Chef, Deliveryman)
     */
    private static void handleStaffAccess() {
        System.out.println("\n===== STAFF ACCESS =====");
        System.out.println("1. Admin Login");
        System.out.println("2. Chef Login");
        System.out.println("3. Delivery Staff Login");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = getUserChoice(1, 4);
        
        switch (choice) {
            case 1: // Admin login
                adminLogin();
                break;
            case 2: // Chef login
                chefLogin();
                break;
            case 3: // Deliveryman login
                deliverymanLogin();
                break;
            case 4: // Back
                return;
        }
    }
    
    /**
     * Customer login functionality
     */
    private static void customerLogin() {
        System.out.println("\n===== CUSTOMER LOGIN =====");
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        // Find customer in database
        Customer loggedInCustomer = null;
        for (Customer customer : database.getCustomers()) {
            if (customer.match(email, password)) {
                loggedInCustomer = customer;
                break;
            }
        }
        
        if (loggedInCustomer != null) {
            System.out.println("Login successful! Welcome, " + loggedInCustomer.getstudentName());
            customerMenu(loggedInCustomer);
        } else {
            System.out.println("Login failed. Invalid email or password.");
        }
    }
    
    /**
     * Customer registration functionality
     */
    private static void customerRegister() {
        Customer newCustomer = new Customer(database);
        newCustomer.register();
    }
    
    /**
     * Customer menu after successful login
     */
    private static void customerMenu(Customer customer) {
        // Create menu for ordering
        Menu menu = createMenu();
        
        boolean customerSession = true;
        while (customerSession) {
            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("1. Place Order");
            System.out.println("2. View Order History");
            System.out.println("3. Edit Profile");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            
            int choice = getUserChoice(1, 4);
            
            switch (choice) {
                case 1: // Place order
                    Order newOrder = customer.placeOrder(menu);
                    // Save the order to the database if it was successfully created
                    if (newOrder != null && newOrder.getOrderStatus().equals("Confirmed")) {
                        database.addOrder(newOrder);
                        System.out.println("Order saved to database successfully!");
                    }
                    break;
                case 2: // View order history
                    customer.viewHistory();
                    break;
                case 3: // Edit profile
                    customer.editProfile();
                    break;
                case 4: // Logout
                    customerSession = false;
                    System.out.println("Logged out successfully.");
                    break;
            }
        }
    }
    
    // Additional methods for staff login and menus would go here
    
    /**
     * Admin login functionality
     */
    private static void adminLogin() {
        System.out.println("\n===== ADMIN LOGIN =====");
        // Skip authentication for demo purposes
        System.out.println("Admin access granted!");
        adminMenu();
    }

    /**
     * Admin menu after login
     */
    private static void adminMenu() {
        Admin admin = new Admin("Admin", "admin@gu.edu", "admin123");
        
        boolean adminSession = true;
        while (adminSession) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. View All Customers");
            System.out.println("2. View All Orders");
            System.out.println("3. View All Menu Items");
            System.out.println("4. View All Chefs");
            System.out.println("5. View All Delivery Staff");
            System.out.println("6. Add Menu Item");
            System.out.println("7. Delete Menu Item");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");
            
            int choice = getUserChoice(1, 8);
            
            switch (choice) {
                case 1: // View all customers
                    admin.viewAllCustomers(database);
                    break;
                case 2: // View all orders
                    admin.viewAllOrders(database);
                    break;
                case 3: // View all menu items
                    admin.viewAllMenuItems(database);
                    break;
                case 4: // View all chefs
                    admin.viewAllChefs(database);
                    break;
                case 5: // View all delivery staff
                    admin.viewAllDeliverymen(database);
                    break;
                case 6: // Add menu item
                    addMenuItem();
                    break;
                case 7: // Delete menu item
                    deleteMenuItem();
                    break;
                case 8: // Logout
                    adminSession = false;
                    System.out.println("Logged out successfully.");
                    break;
            }
        }
    }

    /**
     * Add a new menu item
     */
    private static void addMenuItem() {
        System.out.println("\n===== ADD MENU ITEM =====");
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();
        
        System.out.print("Enter item price: ");
        double price = Double.parseDouble(scanner.nextLine());
        
        System.out.println("Select item type:");
        System.out.println("1. Food");
        System.out.println("2. Beverage");
        System.out.print("Enter choice (1-2): ");
        int typeChoice = getUserChoice(1, 2);
        
        MenuItem newItem;
        
        if (typeChoice == 1) {
            System.out.print("Enter food description: ");
            String description = scanner.nextLine();
            newItem = new Food(itemName, price, description);
        } else {
            System.out.print("Enter beverage volume (ml): ");
            int volume = Integer.parseInt(scanner.nextLine());
            newItem = new Beverage(itemName, price, volume);
        }
        
        database.addMenuItem(itemName, newItem);
        System.out.println("Menu item added successfully!");
    }

    /**
     * Delete a menu item
     */
    private static void deleteMenuItem() {
        System.out.println("\n===== DELETE MENU ITEM =====");
        System.out.print("Enter item name to delete: ");
        String itemName = scanner.nextLine();
        
        database.removeMenuItem(itemName);
        System.out.println("Menu item deleted successfully!");
    }

    /**
     * Chef login functionality
     */
    private static void chefLogin() {
        System.out.println("\n===== CHEF LOGIN =====");
        // Skip authentication for demo purposes
        System.out.println("Chef access granted!");
        chefMenu();
    }

    /**
     * Chef menu after login
     */
    private static void chefMenu() {
        Chef chef = new Chef("Chef", "chef@gu.edu", "chef123");
        
        boolean chefSession = true;
        while (chefSession) {
            System.out.println("\n===== CHEF MENU =====");
            System.out.println("1. View Pending Orders");
            System.out.println("2. Mark Order as Ready");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            
            int choice = getUserChoice(1, 3);
            
            switch (choice) {
                case 1: // View pending orders
                    viewPendingOrders();
                    break;
                case 2: // Mark order as ready
                    markOrderAsReady(chef);
                    break;
                case 3: // Logout
                    chefSession = false;
                    System.out.println("Logged out successfully.");
                    break;
            }
        }
    }

    /**
     * View all pending orders
     */
    private static void viewPendingOrders() {
        System.out.println("\n===== PENDING ORDERS =====");
        boolean foundPending = false;
        
        for (Order order : database.getOrders()) {
            // Check for both "Pending" and "Confirmed" status to show all unprocessed orders
            if (order.getOrderStatus().equalsIgnoreCase("Pending") || 
                order.getOrderStatus().equalsIgnoreCase("Confirmed")) {
                System.out.println("Order #" + order.getOrderId() + 
                                  " - Customer: " + order.getCustomer().getstudentName() +
                                  " - Status: " + order.getOrderStatus());
                
                // Display order items
                System.out.println("Items:");
                for (MenuItem item : order.getOrderedItems()) {
                    System.out.println("  - " + item.getItemName() + ": $" + item.getItemPrice());
                }
                System.out.println("Total: $" + order.getTotalAmount());
                System.out.println("------------------------");
                foundPending = true;
            }
        }
        
        if (!foundPending) {
            System.out.println("No pending orders found.");
        }
    }

    /**
     * Mark an order as ready
     */
    private static void markOrderAsReady(Chef chef) {
        System.out.println("\n===== MARK ORDER AS READY =====");
        
        // First display all pending orders
        viewPendingOrders();
        
        if (database.getOrders().isEmpty()) {
            System.out.println("No orders available to mark as ready.");
            return;
        }
        
        System.out.print("Enter order ID: ");
        String orderId = scanner.nextLine();
        
        // Find the order and update its status
        boolean orderFound = false;
        for (Order order : database.getOrders()) {
            if (order.getOrderId().equals(orderId)) {
                order.setOrderStatus("Ready");
                System.out.println("Order #" + orderId + " marked as Ready.");
                orderFound = true;
                
                // Make sure to save changes to database
                saveData();
                break;
            }
        }
        
        if (!orderFound) {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }

    /**
     * Deliveryman login functionality
     */
    private static void deliverymanLogin() {
        System.out.println("\n===== DELIVERY STAFF LOGIN =====");
        // Skip authentication for demo purposes
        System.out.println("Delivery staff access granted!");
        deliverymanMenu();
    }

    /**
     * Deliveryman menu after login
     */
    private static void deliverymanMenu() {
        Deliveryman deliveryman = new Deliveryman("Delivery", "delivery@gu.edu", "delivery123");
        
        boolean deliverySession = true;
        while (deliverySession) {
            System.out.println("\n===== DELIVERY STAFF MENU =====");
            System.out.println("1. View Ready Orders");
            System.out.println("2. Update Delivery Status");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            
            int choice = getUserChoice(1, 3);
            
            switch (choice) {
                case 1: // View ready orders
                    viewReadyOrders();
                    break;
                case 2: // Update delivery status
                    updateDeliveryStatus(deliveryman);
                    break;
                case 3: // Logout
                    deliverySession = false;
                    System.out.println("Logged out successfully.");
                    break;
            }
        }
    }

    /**
     * View all ready orders
     */
    private static void viewReadyOrders() {
        System.out.println("\n===== READY ORDERS =====");
        boolean foundReady = false;
        
        for (Order order : database.getOrders()) {
            if (order.getOrderStatus().equalsIgnoreCase("Ready")) {
                System.out.println("Order #" + order.getOrderId() + " - Customer: " + 
                                  order.getCustomer().getstudentName() + " - Dorm: " + 
                                  order.getCustomer().getdormName());
                foundReady = true;
            }
        }
        
        if (!foundReady) {
            System.out.println("No ready orders found.");
        }
    }

    /**
     * Update delivery status of an order
     */
    private static void updateDeliveryStatus(Deliveryman deliveryman) {
        System.out.println("\n===== UPDATE DELIVERY STATUS =====");
        System.out.print("Enter order ID: ");
        String orderId = scanner.nextLine();
        
        System.out.println("Select new status:");
        System.out.println("1. Picked Up");
        System.out.println("2. On the Way");
        System.out.println("3. Delivered");
        System.out.print("Enter choice: ");
        
        int statusChoice = getUserChoice(1, 3);
        String newStatus;
        
        switch (statusChoice) {
            case 1:
                newStatus = "Picked Up";
                break;
            case 2:
                newStatus = "On the Way";
                break;
            case 3:
                newStatus = "Delivered";
                break;
            default:
                newStatus = "Unknown";
        }
        
        // Find and update the order in the database
        boolean orderFound = false;
        for (Order order : database.getOrders()) {
            if (order.getOrderId().equals(orderId)) {
                order.setDeliveryStatus(newStatus);
                System.out.println("Order #" + orderId + " delivery status updated to: " + newStatus);
                orderFound = true;
                
                // Make sure to save changes to database
                saveData();
                break;
            }
        }
        
        if (!orderFound) {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }
    
    /**
     * Display information about the system
     */
    private static void displayAboutInfo() {
        System.out.println("\n===== ABOUT CLOUD KITCHEN =====");
        System.out.println("Cloud Kitchen is a food ordering and delivery system");
        System.out.println("designed for university campus environments.");
        System.out.println("\nDeveloped by: Your Team Name");
        System.out.println("Version: 1.0");
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Get user choice with validation
     */
    private static int getUserChoice(int min, int max) {
        int choice = -1;
        while (choice < min || choice > max) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < min || choice > max) {
                    System.out.print("Invalid choice. Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
        return choice;
    }
    
    /**
     * Create a menu with sample items
     */
    private static Menu createMenu() {
        Menu menu = new Menu();
        
        // Add some default items if menu is empty
        if (database.getMenuItems().isEmpty()) {
            // Create food items
            Food burger = new Food("Burger", 8.99, "Delicious beef burger with cheese");
            Food pizza = new Food("Pizza", 12.99, "Pepperoni pizza with extra cheese");
            Food salad = new Food("Salad", 6.99, "Fresh garden salad");
            Food pasta = new Food("Pasta", 10.99, "Spaghetti with meatballs");
            
            // Create beverage items
            Beverage soda = new Beverage("Soda", 1.99, 350);
            Beverage juice = new Beverage("Juice", 2.49, 300);
            Beverage water = new Beverage("Water", 0.99, 500);
            
            // Add to database
            database.addMenuItem("Burger", burger);
            database.addMenuItem("Pizza", pizza);
            database.addMenuItem("Salad", salad);
            database.addMenuItem("Pasta", pasta);
            database.addMenuItem("Soda", soda);
            database.addMenuItem("Juice", juice);
            database.addMenuItem("Water", water);
            
            // Add to menu
            menu.addItem(burger);
            menu.addItem(pizza);
            menu.addItem(salad);
            menu.addItem(pasta);
            menu.addItem(soda);
            menu.addItem(juice);
            menu.addItem(water);
        } else {
            // Add existing items from database to menu
            for (String itemName : database.getMenuItems().keySet()) {
                menu.addItem(database.getMenuItems().get(itemName));
            }
        }
        
        return menu;
    }
    
    /**
     * Load data from files
     */
    private static void loadData() {
        try {
            database.readCustomers("customers.txt");
            database.readMenuItems("menu.txt");
            database.readOrders("orders.txt");
            database.readChefs("chefs.txt");
            database.readAdmins("admins.txt");
            database.readDeliverymen("deliverymen.txt");
            System.out.println("Data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Warning: Could not load some data files. Starting with empty database.");
        }
    }
    
    /**
     * Save data to files
     */
    private static void saveData() {
        try {
            database.writeCustomers("customers.txt");
            database.writeMenuItems("menu.txt");
            database.writeOrders("orders.txt");
            database.writeChefs("chefs.txt");
            database.writeAdmins("admins.txt");
            database.writeDeliverymen("deliverymen.txt");
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error: Could not save data. " + e.getMessage());
        }
    }
}
