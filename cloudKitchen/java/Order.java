package cloudKitchen.java;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Order {
    private String orderId;
    private List<MenuItem> orderedItems;
    private LocalDateTime orderDate;
    private String orderStatus;
    private String deliveryStatus;
    private double totalAmount;
    private Customer customer;
    
    /**
     * Constructor with customer parameter
     */
    public Order(Customer customer) {
        this.customer = customer;
        this.orderedItems = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
        this.orderStatus = "Pending";
        this.deliveryStatus = "Not Started";
        this.totalAmount = 0.0;
        this.orderId = generateOrderId();
    }
    
    /**
     * Constructor with all parameters (for SystemDatabase use)
     */
    public Order(int orderID, String orderDate, String orderStatus, String deliveryStatus,
                String assignedCustomer, double totalAmount) {
        this.orderId = String.valueOf(orderID);
        this.orderDate = LocalDateTime.parse(orderDate);
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
        this.totalAmount = totalAmount;
        this.orderedItems = new ArrayList<>();
        // Note: This constructor would typically link to a customer by email or ID
    }

    /**
     * Place an order using the available menu
     */
    public void placeOrder(Menu menu) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("\n=== Place Your Order ===");
            
            // 1. Select food items
            selectFoodItems(scanner, menu);
            
            // 2. Select beverages
            selectBeverageItems(scanner, menu);
            
            // 3. Calculate total
            calculateTotal();
            
            // 4. Confirm order
            if (confirmOrder(scanner)) {
                // 5. Process payment
                if (processPayment(scanner)) {
                    // 6. Save order
                    saveOrder();
                    System.out.println("\nOrder placed successfully! Order ID: " + orderId);
                    System.out.println("Estimated delivery time: 30 minutes");
                } else {
                    System.out.println("Payment failed. Order cancelled.");
                    resetOrder();
                }
            } else {
                System.out.println("Order cancelled.");
                resetOrder();
            }
        } catch (Exception e) {
            System.err.println("Error placing order: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Select food items from the menu
     */
    private void selectFoodItems(Scanner scanner, Menu menu) {
        System.out.println("\nAvailable Food Items:");
        List<Food> foodItems = menu.getFoodItems();
        
        if (foodItems.isEmpty()) {
            System.out.println("No food items available on the menu.");
            return;
        }
        
        while (true) {
            // Display menu
            for (int i = 0; i < foodItems.size(); i++) {
                Food item = foodItems.get(i);
                System.out.println((i+1) + ". " + item.getItemName() + " ($" + 
                                   String.format("%.2f", item.getItemPrice()) + ") - " + 
                                   item.getDescription());
            }
            System.out.println("0. Finish food selection");
            
            System.out.print("Select item (1-" + foodItems.size() + ") or 0 to finish: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (choice == 0) break;
            if (choice < 0 || choice > foodItems.size()) {
                System.out.println("Invalid choice!");
                continue;
            }
            
            MenuItem selectedItem = foodItems.get(choice-1);
            orderedItems.add(selectedItem);
            System.out.println(selectedItem.getItemName() + " added to order");
        }
    }

    /**
     * Select beverage items from the menu
     */
    private void selectBeverageItems(Scanner scanner, Menu menu) {
        System.out.println("\nAvailable Beverages:");
        List<Beverage> beverageItems = menu.getBeverageItems();
        
        if (beverageItems.isEmpty()) {
            System.out.println("No beverages available on the menu.");
            return;
        }
        
        while (true) {
            // Display menu
            for (int i = 0; i < beverageItems.size(); i++) {
                Beverage item = beverageItems.get(i);
                System.out.println((i+1) + ". " + item.getItemName() + " ($" + 
                                   String.format("%.2f", item.getItemPrice()) + ") - " + 
                                   item.getVolume() + "ml");
            }
            System.out.println("0. Finish beverage selection");
            
            System.out.print("Select item (1-" + beverageItems.size() + ") or 0 to finish: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (choice == 0) break;
            if (choice < 0 || choice > beverageItems.size()) {
                System.out.println("Invalid choice!");
                continue;
            }
            
            MenuItem selectedItem = beverageItems.get(choice-1);
            orderedItems.add(selectedItem);
            System.out.println(selectedItem.getItemName() + " added to order");
        }
    }
    
    /**
     * Calculate total cost of the order
     */
    private void calculateTotal() {
        totalAmount = 0.0;
        
        // Calculate items total
        for (MenuItem item : orderedItems) {
            totalAmount += item.getItemPrice();
        }
        
        // Apply delivery fee
        double deliveryFee = 2.50; // Fixed delivery fee
        totalAmount += deliveryFee;
        
        // Display order summary
        System.out.println("\n=== Order Summary ===");
        System.out.println("Ordered Items:");
        
        for (MenuItem item : orderedItems) {
            if (item instanceof Food) {
                Food food = (Food) item;
                System.out.println("- " + food.getItemName() + " ($" + 
                                   String.format("%.2f", food.getItemPrice()) + ") - " + 
                                   food.getDescription());
            } else if (item instanceof Beverage) {
                Beverage beverage = (Beverage) item;
                System.out.println("- " + beverage.getItemName() + " ($" + 
                                   String.format("%.2f", beverage.getItemPrice()) + ") - " + 
                                   beverage.getVolume() + "ml");
            }
        }
        
        System.out.println("\nDelivery Fee: $" + String.format("%.2f", deliveryFee));
        System.out.println("Total Amount: $" + String.format("%.2f", totalAmount));
    }
    
    /**
     * Confirm the order with customer
     */
    private boolean confirmOrder(Scanner scanner) {
        System.out.print("\nConfirm your order? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        return confirmation.equals("y") || confirmation.equals("yes");
    }
    
    /**
     * Process payment for the order
     */
    private boolean processPayment(Scanner scanner) {
        // Use customer's payment method if possible
        if (customer != null) {
            return customer.pay(totalAmount);
        }
        
        // Fallback payment processing if customer object doesn't support payment
        System.out.println("\n=== Payment Processing ===");
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
        orderStatus = "Paid";
        return true;
    }
    
    /**
     * Save the order in the system
     */
    private void saveOrder() {
        // Update order status
        orderStatus = "Confirmed";
        deliveryStatus = "Preparing";
        
        // Add to customer's order history if customer object is available
        if (customer != null) {
            customer.addOrder(this);
        }
        
        // Notify kitchen
        notifyKitchen();
    }
    
    /**
     * Reset the order (when cancelled)
     */
    private void resetOrder() {
        orderedItems.clear();
        totalAmount = 0.0;
        orderStatus = "Cancelled";
    }
    
    /**
     * Notify kitchen about the new order
     */
    private void notifyKitchen() {
        System.out.println("Order " + orderId + " has been sent to the kitchen!");
        // In a real application, this would notify Chef objects
    }
    
    /**
     * Generate a unique order ID
     */
    private String generateOrderId() {
        // Generate unique order ID (timestamp + random number)
        long timestamp = System.currentTimeMillis();
        int randomDigits = new Random().nextInt(1000);
        return "ORD-" + timestamp % 10000 + "-" + randomDigits;
    }
    
    // Order tracking methods
    /**
     * Update the order status
     */
    public void updateOrderStatus(String status) {
        this.orderStatus = status;
        System.out.println("Order " + orderId + " status updated to: " + status);
    }
    
    /**
     * Update the delivery status
     */
    public void updateDeliveryStatus(String status) {
        this.deliveryStatus = status;
        System.out.println("Order " + orderId + " delivery status updated to: " + status);
    }
    
    // Getters and setters
    public String getOrderId() {
        return orderId;
    }
    
    public List<MenuItem> getOrderedItems() {
        return orderedItems;
    }
    
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    
    public String getOrderStatus() {
        return orderStatus;
    }
    
    public String getDeliveryStatus() {
        return deliveryStatus;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
    
    /**
     * Get formatted order date string
     */
    public String getFormattedOrderDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return orderDate.format(formatter);
    }
    
    /**
     * Add an item to the order
     */
    public void addItem(MenuItem item) {
        if (item != null) {
            orderedItems.add(item);
            // Recalculate total
            totalAmount += item.getItemPrice();
        }
    }
    
    /**
     * Remove an item from the order
     */
    public boolean removeItem(MenuItem item) {
        if (item != null && orderedItems.contains(item)) {
            orderedItems.remove(item);
            // Recalculate total
            totalAmount -= item.getItemPrice();
            return true;
        }
        return false;
    }
    
    // Method to get order summary
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Order ID: ").append(orderId)
               .append("\nOrder Date: ").append(getFormattedOrderDate())
               .append("\nCustomer: ").append(customer != null ? customer.getstudentName() : "N/A")
               .append("\nOrder Status: ").append(orderStatus)
               .append("\nDelivery Status: ").append(deliveryStatus)
               .append("\nTotal Amount: $").append(String.format("%.2f", totalAmount));
        
        if (!orderedItems.isEmpty()) {
            builder.append("\n\nOrdered Items:");
            for (MenuItem item : orderedItems) {
                builder.append("\n- ").append(item.getItemName())
                       .append(" ($").append(String.format("%.2f", item.getItemPrice())).append(")");
            }
        }
        
        return builder.toString();
    }
    
    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        // Create a menu for testing
        Menu menu = new Menu();
        
        // Add some food items to the menu
        Food burger = new Food("Burger", 5.99, "Delicious beef burger with cheese");
        Food pizza = new Food("Pizza", 8.99, "Pepperoni pizza with extra cheese");
        
        menu.addItem(burger);
        menu.addItem(pizza);
        
        // Add some beverages to the menu
        Beverage soda = new Beverage("Soda", 1.99, 350);
        Beverage juice = new Beverage("Juice", 2.49, 300);
        
        menu.addItem(soda);
        menu.addItem(juice);
        
        // Create a test customer
        Customer testCustomer = new Customer();
        testCustomer.setstudentName("Test Student");
        testCustomer.setstudentEmail("test@gu.edu");
        
        // Create and place an order
        Order order = new Order(testCustomer);
        order.placeOrder(menu);
    }
}