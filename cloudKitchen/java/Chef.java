package cloudKitchen.java;

import java.util.ArrayList;

/**
 * Chef class for handling kitchen operations in the Cloud Kitchen system
 */
public class Chef extends User {
    private ArrayList<Order> orders;
    private double totalRating;
    private int numberOfRatings;

    /**
     * Default constructor
     */
    public Chef() {
        super();
        this.orders = new ArrayList<>();
        this.totalRating = 0;
        this.numberOfRatings = 0;
    }

    /**
     * Basic constructor with essential information
     */
    public Chef(String name, String email, String password) {
        super(name, email, password);
        this.orders = new ArrayList<>();
        this.totalRating = 0;
        this.numberOfRatings = 0;
    }

    

    /**
     * View all pending orders assigned to this chef
     */
    public void viewPendingOrders() {
        System.out.println("\n=== Pending Orders ===");
        boolean hasPendingOrders = false;
        
        for (Order order : orders) {
            if (!order.getOrderStatus().equalsIgnoreCase("Ready")) {
                System.out.println(order);
                hasPendingOrders = true;
            }
        }
        
        if (!hasPendingOrders) {
            System.out.println("No pending orders found.");
        }
    }

    /**
     * Mark a specific order as ready
     */
    public void markOrderAsReady(int orderId) {
        boolean orderFound = false;
        
        for (Order order : orders) {
            if (order.getOrderId().equals(String.valueOf(orderId))) {
                if (!order.getOrderStatus().equalsIgnoreCase("Ready")) {
                    order.setOrderStatus("Ready");
                    System.out.println("Order #" + orderId + " marked as Ready.");
                } else {
                    System.out.println("Order #" + orderId + " is already Ready.");
                }
                orderFound = true;
                break;
            }
        }
        
        if (!orderFound) {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }

    /**
     * Rate the chef with a rating from 0 to 5
     */
    public void rateChef(double rating) {
        if (rating >= 0 && rating <= 5) {
            totalRating += rating;
            numberOfRatings++;
            System.out.println("Chef rated successfully.");
        } else {
            System.out.println("Rating must be between 0 and 5.");
        }
    }

    /**
     * Get the average rating of the chef
     */
    public double getAverageRating() {
        if (numberOfRatings == 0) return 0;
        return totalRating / numberOfRatings;
    }

    /**
     * Display the chef's rating
     */
    public void showRating() {
        System.out.printf("Chef's average rating: %.2f out of 5\n", getAverageRating());
    }

    /**
     * Assign orders to this chef
     */
    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    /**
     * Get the orders assigned to this chef
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Get the total rating sum
     */
    public double getTotalRating() {
        return totalRating;
    }

    /**
     * Get the number of ratings received
     */
    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    @Override
    public String toString() {
        return getName() + " " + getEmail() + " " + getPassword() + " " + 
               getAge() + " " + getSalary() + " " + getGender();
    }
}