package cloudKitchen.java;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Customer {
    private String studentEmail;
    private String studentPassword;
    private String studentName;
    private String dormName;
    private String phoneNumber;
    private SystemDatabase database;
    private List<Order> orderHistory;

    /**
     * Default constructor for testing purposes
     */
    public Customer() {
        this.orderHistory = new ArrayList<>();
    }
    
    /**
     * Constructor with name and email for quick creation
     */
    public Customer(String name, String email) {
        this.studentName = name;
        this.studentEmail = email;
        this.orderHistory = new ArrayList<>();
    }

    /**
     * Full constructor with database connection
     */
    public Customer(SystemDatabase database) {
        this.database = database;
        this.orderHistory = new ArrayList<>();
    }

    /**
     * Register a new customer with validated information
     */
    public void register() {
        Scanner scanner = new Scanner(System.in);
        
        // Collect and validate student email
        while (true) {
            System.out.print("Enter your student email: ");
            String email = scanner.nextLine();
            if (isValidEmail(email)) {
                this.studentEmail = email;
                break;
            } else {
                System.out.println("Invalid email format. Please use your university email (e.g., name@gu.edu)");
            }
        }
        
        // Collect and validate password
        while (true) {
            System.out.print("Create a password (min 8 chars, at least 1 number and 1 special char): ");
            String password = scanner.nextLine();
            if (isValidPassword(password)) {
                this.studentPassword = password;
                break;
            } else {
                System.out.println("Password doesn't meet requirements. Try again.");
            }
        }
        
        // Collect personal information
        System.out.print("Enter your full name: ");
        this.studentName = scanner.nextLine();
        
        System.out.print("Enter your dorm name: (e.g., A 2222) ");
        this.dormName = scanner.nextLine();
        
        while (true) {
            System.out.print("Enter your phone number: ");
            String phoneNumber = scanner.nextLine();
            if (isValidPhoneNumber(phoneNumber)) {
                this.phoneNumber = phoneNumber;
                break;
            } else {
                System.out.println("PhoneNumber Invalid. Try again.");
            }
        }
        
        // Save to database if available
        if (database != null) {
            if (saveToDatabase()) {
                System.out.println("Registration successful! Welcome, " + this.studentName);
            } else {
                System.out.println("Registration failed. Please try again.");
            }
        } else {
            System.out.println("Registration successful! (Note: Database not connected)");
        }
    }
    
    /**
     * Validate email format
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@gu\\.edu$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
    
    /**
     * Validate password format
     */
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$";
        return Pattern.compile(passwordRegex).matcher(password).matches();
    }
    
    /**
     * Validate phone number format
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^(\\d{11})$";
        return Pattern.compile(phoneRegex).matcher(phoneNumber).matches();
    }

    /**
     * Save customer to database
     */
    private boolean saveToDatabase() {
        try {
            if (database != null) {
                database.addCustomer(this);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Login a customer with email and password validation
     */
    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        boolean loginSuccess = false;

        System.out.print("Enter your student email: ");
        String email = scanner.nextLine();
        
        if (!email.equalsIgnoreCase(studentEmail)) {
            System.out.println("Invalid email. Please register!");
            return false;
        }
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        if (password.equals(studentPassword)) {
            System.out.println("Password Correct! You are logged in!");
            loginSuccess = true;
        } else {
            System.out.println("Incorrect password. Login failed.");
        }
        
        return loginSuccess;
    }

    
    
    public boolean match(String email, String password) {
        return this.studentEmail.equalsIgnoreCase(email) && 
               this.studentPassword.equals(password);
    }

    /**
     * Place a new order using the menu
     */
    public Order placeOrder(Menu menu) {
        if (menu == null) {
            System.out.println("Error: Menu not available");
            return null;
        }
        
        // Create a new order for this customer
        Order newOrder = new Order(this);
        
        // Display menu and allow customer to select items
        menu.displayMenu();
        
        // Start order process
        newOrder.placeOrder(menu);
        
        // Add to order history if order was completed
        if (newOrder.getOrderStatus().equals("Confirmed")) {
            orderHistory.add(newOrder);
        }
        
        return newOrder;
    }

 
    /**
     * Track a specific order by ID
     */
    public void trackOrder(String orderId) {
        boolean found = false;
        
        for (Order order : orderHistory) {
            if (order.getOrderId().equals(orderId)) {
                System.out.println("Order " + orderId + " status: " + order.getOrderStatus());
                System.out.println("Delivery status: " + order.getDeliveryStatus());
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("Order not found. Please check the order ID.");
        }
    }

    /**
     * View order history
     */
    public void viewHistory() {
        if (orderHistory.isEmpty()) {
            System.out.println("No order history found.");
            return;
        }
        
        System.out.println("\n=== Order History for " + studentName + " ===");
        for (Order order : orderHistory) {
            System.out.println("----------------------------------------");
            System.out.println(order.toString());
        }
    }

    /**
     * Edit customer profile information
     */
    public void editProfile() {
        Scanner scanner = new Scanner(System.in);
        boolean editing = true;
        
        while (editing) {
            System.out.println("\n=== Edit Profile ===");
            System.out.println("1. Change Name");
            System.out.println("2. Change Password");
            System.out.println("3. Change Dorm");
            System.out.println("4. Change Phone Number");
            System.out.println("0. Back to Main Menu");
            
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    this.studentName = scanner.nextLine();
                    System.out.println("Name updated successfully!");
                    break;
                case 2:
                    while (true) {
                        System.out.print("Enter new password: ");
                        String newPassword = scanner.nextLine();
                        if (isValidPassword(newPassword)) {
                            this.studentPassword = newPassword;
                            System.out.println("Password updated successfully!");
                            break;
                        } else {
                            System.out.println("Invalid password format. Try again.");
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter new dorm: ");
                    this.dormName = scanner.nextLine();
                    System.out.println("Dorm updated successfully!");
                    break;
                case 4:
                    while (true) {
                        System.out.print("Enter new phone number: ");
                        String newPhone = scanner.nextLine();
                        if (isValidPhoneNumber(newPhone)) {
                            this.phoneNumber = newPhone;
                            System.out.println("Phone number updated successfully!");
                            break;
                        } else {
                            System.out.println("Invalid phone number format. Try again.");
                        }
                    }
                    break;
                case 0:
                    editing = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        // Update database if available
        if (database != null) {
            saveToDatabase();
        }
    }

    /**
     * Process payment for an order
     */
    public boolean pay(double amount) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n=== Payment Processing ===");
        System.out.println("Amount to pay: $" + String.format("%.2f", amount));
        System.out.println("Select payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. Cash on Delivery");
        
        System.out.print("Enter your choice (1-3): ");
        int paymentChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (paymentChoice < 1 || paymentChoice > 3) {
            System.out.println("Invalid payment option selected!");
            return false;
        }
        
        // Process payment based on choice
        switch (paymentChoice) {
            case 1: // Credit Card
            case 2: // Debit Card
                System.out.print("Enter card number: ");
                String cardNumber = scanner.nextLine();
                System.out.print("Enter expiration date (MM/YY): ");
                String expDate = scanner.nextLine();
                System.out.print("Enter CVV: ");
                String cvv = scanner.nextLine();
                
                // Basic validation
                if (cardNumber.length() < 16 || expDate.length() < 5 || cvv.length() < 3) {
                    System.out.println("Invalid card information!");
                    return false;
                }
                break;
                
            case 3: // Cash on Delivery
                System.out.println("You selected Cash on Delivery. Have exact change ready!");
                break;
        }
        
        // Simulate payment processing
        System.out.println("Processing payment...");
        try {
            Thread.sleep(1500); // Simulate processing delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // In a real app, this would connect to payment gateway
        System.out.println("Payment successful!");
        return true;
    }

    // Getters and setters
    public String getstudentEmail() {
        return studentEmail;
    }

    public void setstudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getstudentPassword() {
        return studentPassword;
    }

    public void setstudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public String getstudentName() {
        return studentName;
    }

    public void setstudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getdormName() {
        return dormName;
    }

    public void setdormName(String dormName) {
        this.dormName = dormName;
    }

    public String getphoneNumber() {
        return phoneNumber;
    }

    public void setphoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

  

    /**
     * Add an order to this customer's history
     */
    public void addOrder(Order order) {
        if (order != null) {
            orderHistory.add(order);
        }
    }
    
    /**
     * Get all orders for this customer
     */
    public List<Order> getOrderHistory() {
        return orderHistory;
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "name='" + studentName + '\'' +
                ", email='" + studentEmail + '\'' +
                ", dorm='" + dormName + '\'' +
                ", phone='" + phoneNumber + '\'' +
                '}';
    }
    
    public static void main(String[] args) {
        // Create a menu for testing
        Menu menu = new Menu();
        
        // Add some food items to the menu
        Food burger = new Food("Burger", 5.99, "Delicious beef burger with cheese");
        Food pizza = new Food("Pizza", 8.99, "Pepperoni pizza with extra cheese");
        Food pasta = new Food("Pasta", 7.49, "Spaghetti with meatballs");
        Food salad = new Food("Salad", 6.99, "Fresh garden salad");
        
        menu.addItem(burger);
        menu.addItem(pizza);
        menu.addItem(pasta);
        menu.addItem(salad);
        
        // Add some beverages to the menu
        Beverage soda = new Beverage("Soda", 1.99, 350);
        Beverage juice = new Beverage("Juice", 2.49, 300);
        Beverage water = new Beverage("Water", 0.99, 500);
        
        menu.addItem(soda);
        menu.addItem(juice);
        menu.addItem(water);
        
        // Create a new customer for testing
        Customer newCustomer = new Customer();
        newCustomer.register();
        
        if (newCustomer.login()) {
            // Place an order
            newCustomer.placeOrder(menu);
            
            // View order history
            newCustomer.viewHistory();
            
            // Edit profile
            newCustomer.editProfile();
        }
    }
}