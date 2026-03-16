package cloudKitchen.java;

import java.util.ArrayList;

/**
 * Deliveryman class for handling delivery operations in the Cloud Kitchen system
 */
public class Deliveryman extends User {
    private ArrayList<Order> deliveryOrders;
    private String assignedDorm; // Added for dormitory assignment
    
    /**
     * Default constructor
     */
    public Deliveryman() {
        super();
        this.deliveryOrders = new ArrayList<>();
    }
    
    /**
     * Basic constructor with essential information
     */
    public Deliveryman(String name, String email, String password) {
        super(name, email, password);
        this.deliveryOrders = new ArrayList<>();
    }
    

    
    /**
     * View all delivery orders assigned to this delivery person
     */
    public void viewDeliveries() {
        System.out.println("\n=== Delivery Orders ===");
        if (deliveryOrders.isEmpty()) {
            System.out.println("No delivery orders assigned.");
            return;
        }
        
        for (Order order : deliveryOrders) {
            System.out.println(order);
            System.out.println("------------------------");
        }
    }
    
    /**
     * Update the delivery status of a specific order
     */
    public void updateDeliveryStatus(int orderId, String status) {
        boolean orderFound = false;
        
        for (Order order : deliveryOrders) {
            if (order.getOrderId().equals(String.valueOf(orderId))) {
                order.setDeliveryStatus(status);
                System.out.println("Order #" + orderId + " status updated to: " + status);
                orderFound = true;
                break;
            }
        }
        
        if (!orderFound) {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }
    
    /**
     * Assign an order to this delivery person
     */
    public void assignOrder(Order order) {
        if (order != null) {
            deliveryOrders.add(order);
            System.out.println("Order #" + order.getOrderId() + " assigned to " + getName());
        }
    }
    
    /**
     * Remove an order from this delivery person's list
     */
    public void removeOrder(String orderId) {
        deliveryOrders.removeIf(order -> order.getOrderId().equals(orderId));
    }
    
    /**
     * Get the dorm assigned to this delivery person
     */
    public String getAssignedDorm() {
        return assignedDorm;
    }
    
    /**
     * Set the dorm assigned to this delivery person
     */
    public void setAssignedDorm(String assignedDorm) {
        this.assignedDorm = assignedDorm;
    }
    
    /**
     * Get all orders assigned to this delivery person
     */
    public ArrayList<Order> getDeliveryOrders() {
        return deliveryOrders;
    }
    
    /**
     * Set orders to this delivery person
     */
    public void setDeliveryOrders(ArrayList<Order> deliveryOrders) {
        this.deliveryOrders = deliveryOrders;
    }
    
    @Override
    public String toString() {
        return getName() + " " + getEmail() + " " + getPassword() + " " + 
               getAssignedDorm() + " " + getAge() + " " + getSalary() + " " + getGender();
    }
}