import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;

public class Main {
    private static final String DEFAULT_FILE_PATH = getDefaultFilePath();
    
    private static String getDefaultFilePath() {
        String os = System.getProperty("os.name").toLowerCase();
        String documentsPath;
        
        if (os.contains("win")) {
            // Windows typically stores user documents in the 'Documents' folder within 'USERPROFILE'
            documentsPath = System.getProperty("user.home") + "\\Documents\\vehicle_inventory.csv";
        } else if (os.contains("mac")) {
            // macOS also typically stores documents in the 'Documents' folder within the user's home directory
            documentsPath = System.getProperty("user.home") + "/Documents/vehicle_inventory.csv";
        } else {
            // For other OS like Linux, adjust accordingly or use a generic path within the user's home directory
            documentsPath = System.getProperty("user.home") + "/Documents/vehicle_inventory.csv";
        }
        
        return documentsPath;
    }
    
    // Enable loading the vehicle inventory from the saved file
    private static void loadFromFile(String filePath, AutomobileInventory inventory) {
        File file = new File(filePath);
        try (Scanner fileScanner = new Scanner(file)) {
            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine();  // Skip the header line
            }
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(", ");
                if (data.length == 6) {
                    try {
                        int invNumber = Integer.parseInt(data[0].trim());
                        String make = data[1].trim();
                        String model = data[2].trim();
                        String color = data[3].trim();
                        int year = Integer.parseInt(data[4].trim());
                        int mileage = Integer.parseInt(data[5].trim());
                        Automobile vehicle = new Automobile(invNumber, make, model, color, year, mileage);
                        inventory.addVehicle(vehicle);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing number from file: " + e.getMessage());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous data file found. Starting with an empty inventory.");
        }
    }
    
    private static boolean promptForFileSave() {
        System.out.println("Do you want to update the inventory file before exiting? (yes/no)");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.startsWith("y");  // Return true if response starts with 'y' or 'Y'
    }
    
    // Used to simplify exception handling with strings
    private static String readStringFromUser(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim(); // Read the input and trim whitespace
        
        // Check if the input is not empty
        while (input.isEmpty()) {
            System.out.println("Invalid input. Please enter a valid text.");
            System.out.print(prompt);
            input = scanner.nextLine().trim(); // Re-prompt and trim whitespace
        }
        return input;
    }
    
    // Used to simplify exception handling with integers
    private static int readIntFromUser(String prompt, boolean allowNegative) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next(); // consume the non-integer input
            System.out.println("Invalid input. Please enter a valid number.");
            System.out.print(prompt);
        }
        int number = scanner.nextInt();
        scanner.nextLine(); // consume the newline
        
        // Handle negative number input based on the allowNegative flag
        while (!allowNegative && number < 0) {
            System.out.println("Negative numbers are not allowed. Please enter a valid positive number.");
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                scanner.next(); // consume the non-integer input
                System.out.println("Invalid input. Please enter a valid number.");
                System.out.print(prompt);
            }
            number = scanner.nextInt();
            scanner.nextLine(); // consume the newline
        }
        return number;
    }
    
    // Allows for exception handling of years
    private static int readYearFromUser(String prompt) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int year = readIntFromUser(prompt, false);
        while (year < 1900 || year > currentYear) {
            System.out.println("Invalid year. Please enter a year between 1900 and " + currentYear + ".");
            year = readIntFromUser(prompt + " (valid range is 1900 - " + currentYear + "): ", false);
        }
        return year;
    }
    // Used for input handling of negative numbers in mileage
    private static int readMileageFromUser(String prompt) {
        int mileage = readIntFromUser(prompt, false);
        while (mileage < 0) {
            System.out.println("Invalid mileage. Mileage cannot be negative.");
            mileage = readIntFromUser(prompt + " (must be non-negative): ", false);
        }
        return mileage;
    }
    
    // Needed for blank input for default filepath in file save function
    private static String readStringOrUseDefault(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();  // Directly use scanner to allow empty input
        
        // If input is empty, return the default value; otherwise, return the input
        return input.isEmpty() ? defaultValue : input;
    }
    
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final AutomobileInventory inventory = new AutomobileInventory();
    
    public static void main(String[] args) {

        String filePath = getDefaultFilePath();
        loadFromFile(filePath, inventory);  // Load existing data
        
        // Menu input
        while (true) {
            System.out.println("\n--- Car Inventory Management System ---");
            System.out.println("1. Add a new vehicle");
            System.out.println("2. Remove a vehicle");
            System.out.println("3. Update a vehicle");
            System.out.println("4. List all vehicles");
            System.out.println("5. Write to file");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over
            
            switch (choice) {
                case 1:
                    addVehicle();
                    break;
                case 2:
                    removeVehicle();
                    break;
                case 3:
                    updateVehicle();
                    break;
                case 4:
                    printVehicleTable();
                    break;
                case 5:
                    writeToFile(inventory.listVehicles());
                    break;
                case 6:
                    if (promptForFileSave()) {  // Ask user if they want to save changes upon exit
                        writeToFile(inventory.listVehicles());  // Save to file
                    }
                    System.out.println("Exiting program.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please enter a number between 1 and 6.");
            }
        }
    }
        // Add vehicle method
        private static void addVehicle() {
        System.out.println("Enter vehicle details to add:");
        int invNumber = readIntFromUser("Inventory Number: ", true); 
        String make = readStringFromUser("Make: ");
        String model = readStringFromUser("Model: ");
        String color = readStringFromUser("Color: ");
        int year = readYearFromUser("Year: ");
        int mileage = readMileageFromUser("Mileage: ");
        
        Automobile newVehicle = new Automobile(invNumber, make, model, color, year, mileage);
        inventory.addVehicle(newVehicle);
        System.out.println("Vehicle added successfully.");
    }
    
    // Remove vehicle method
    private static void removeVehicle() {
        System.out.print("Enter inventory number of vehicle to remove: ");
        // Assuming the inventory number is an integer
        int invNumber;
        try {
            invNumber = Integer.parseInt(scanner.nextLine());  // Convert the string input to integer
            if (inventory.removeVehicleByInvNumber(invNumber)) {
                System.out.println("Vehicle removed successfully.");
            } else {
                System.out.println("Vehicle not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Inventory number must be an integer.");
        }
    }
    
    // Update vehicle method
    private static void updateVehicle() {
        int invNumber = readIntFromUser("Enter the inventory number of the vehicle you want to update: ", true);
        String make = readStringFromUser("Enter new make: ");
        String model = readStringFromUser("Enter new model: ");
        String color = readStringFromUser("Enter new color: ");
        int year = readYearFromUser("Enter new year: ");
        int mileage = readMileageFromUser("Enter new mileage: ");
        
        boolean success = inventory.updateVehicle(invNumber, make, model, color, year, mileage);
        if (success) {
            System.out.println("Vehicle updated successfully.");
        } else {
            System.out.println("Vehicle not found or update failed.");
        }
    }
    
    
    // Prints vehicle inventory in a table format
    private static void printVehicleTable() {
        inventory.printVehicleTable();
    }
    
    // Saves vehicle inventory to a file
    private static void writeToFile(List<Automobile> vehicles) {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles to save.");
            return;
        }
        System.out.print("Enter the file path to write vehicle details or press Enter to use default (" + DEFAULT_FILE_PATH + "): ");
        String filePath = readStringOrUseDefault("Enter the file path or press Enter for default: ", DEFAULT_FILE_PATH);
        
        try (PrintWriter writer = new PrintWriter(new File(filePath))) {
            writer.println("Inventory #, Make, Model, Color, Year, Mileage");
            for (Automobile vehicle : vehicles) {
                writer.printf("%d, %s, %s, %s, %d, %d%n",
                    vehicle.getInvNumber(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getColor(),
                    vehicle.getYear(),
                    vehicle.getMileage());
            }
            System.out.println("Vehicle information has been successfully written to " + filePath);
        } catch (FileNotFoundException e) {
            System.out.println("Error: The file path provided could not be found or could not be created. Please check the path and try again.");
        }
    }
}