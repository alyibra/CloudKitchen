package cloudKitchen.java;

/**
 * Base class for all user types in the Cloud Kitchen system
 */
public abstract class User {
    protected String name;
    protected String email;
    protected String password;
    protected int age;
    protected double salary;
    protected char gender;

    /**
     * Default constructor
     */
    public User() {
        this.name = "";
        this.email = "";
        this.password = "";
    }

    /**
     * Basic constructor with essential user information
     */
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

   
    /**
     * Match method to check if credentials match
     */
    public boolean match(String email, int passwordHash) {
        return this.email.equals(email) && this.password.hashCode() == passwordHash;
    }

    /**
     * Login functionality
     */
    public void login() {
        System.out.println(name + " logged in successfully.");
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return name + " " + email + " " + password;
    }
}