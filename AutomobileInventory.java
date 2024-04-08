import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AutomobileInventory {
	private List<Automobile> vehicles = new ArrayList<>();
	
	// Method to generate a unique inventory number
	private int generateUniqueInvNumber() {
		int highestInvNumber = 0;
		for (Automobile vehicle : vehicles) {
			if (vehicle.getInvNumber() > highestInvNumber) {
				highestInvNumber = vehicle.getInvNumber();
			}
		}
		// The new unique invNumber is one more than the highest current invNumber
		return highestInvNumber + 1;
	}
	
	// Method of add a new vehicle
	public String addVehicle(Automobile newVehicle) {
		try {
			for (Automobile vehicle : vehicles) {
				if (vehicle.getInvNumber() == newVehicle.getInvNumber()) {
					return "Failed to add vehicle: Inventory number must be unique.";
				}
			}
			vehicles.add(newVehicle);
			return "Vehicle added successfully";
		} catch (Exception e) {
			return "Failed to add vehicle due to unexpected error: " + e.getMessage();
		}
	}
	
	
	// Method to remove a vehicle by inventory number
	public boolean removeVehicleByInvNumber(int invNumber) {
		try {
			Iterator<Automobile> iterator = vehicles.iterator();
			while (iterator.hasNext()) {
				Automobile vehicle = iterator.next();
				if (vehicle.getInvNumber() == invNumber) {
					iterator.remove();
					return true; // Vehicle found and removed
				}
			}
			return false; // Vehicle not found
		} catch (Exception e) {
			System.out.println("Failed to remove vehicle: " + e.getMessage());
			return false;
		}
	}
	
	public List<Automobile> listVehicles() {
		// Return a new ArrayList that contains all vehicles in the inventory.
		// This ensures that operations on the returned list do not affect the original list.
		return new ArrayList<>(vehicles);
	}
	
	
	// Method for printing inventory in a table format
	public void printVehicleTable() {
		if (vehicles.isEmpty()) {
			System.out.println("No vehicles in inventory.");
			return;
		}
		
		try {
			// Determine the maximum width needed for each column
			int maxInvNumWidth = "Inventory #".length();
			int maxMakeWidth = "Make".length();
			int maxModelWidth = "Model".length();
			int maxColorWidth = "Color".length();
			int maxYearWidth = "Year".length();
			int maxMileageWidth = "Mileage".length();
			
			for (Automobile vehicle : vehicles) {
				maxInvNumWidth = Math.max(maxInvNumWidth, Integer.toString(vehicle.getInvNumber()).length());
				maxMakeWidth = Math.max(maxMakeWidth, vehicle.getMake() != null ? vehicle.getMake().length() : 0);
				maxModelWidth = Math.max(maxModelWidth, vehicle.getModel() != null ? vehicle.getModel().length() : 0);
				maxColorWidth = Math.max(maxColorWidth, vehicle.getColor() != null ? vehicle.getColor().length() : 0);
				maxYearWidth = Math.max(maxYearWidth, Integer.toString(vehicle.getYear()).length());
				maxMileageWidth = Math.max(maxMileageWidth, Integer.toString(vehicle.getMileage()).length());
			}
			
			// Print header
			System.out.printf("%-" + maxInvNumWidth + "s %-" + maxMakeWidth + "s %-" + maxModelWidth + "s %-" + maxColorWidth + "s %-" + maxYearWidth + "s %-" + maxMileageWidth + "s\n",
				"Inventory #", "Make", "Model", "Color", "Year", "Mileage");
			
			// Print each vehicle row
			for (Automobile vehicle : vehicles) {
				System.out.printf("%-" + maxInvNumWidth + "d %-" + maxMakeWidth + "s %-" + maxModelWidth + "s %-" + maxColorWidth + "s %-" + maxYearWidth + "d %-" + maxMileageWidth + "d\n",
					vehicle.getInvNumber(),
					vehicle.getMake(),
					vehicle.getModel(),
					vehicle.getColor(),
					vehicle.getYear(),
					vehicle.getMileage());
			}
		} catch (Exception e) {
			System.out.println("Error printing vehicle table: " + e.getMessage());
		}
	}
	
	// Method to update a vehicle in the list
	public boolean updateVehicle(int invNumber, String make, String model, String color, int year, int mileage) {
		try {
			for (Automobile vehicle : vehicles) {
				if (vehicle.getInvNumber() == invNumber) {
					vehicle.updateVehicleAttributes(invNumber, make, model, color, year, mileage);
					return true;  // Update successful
				}
			}
			return false;  // Update unsuccessful, vehicle not found
		} catch (Exception e) {
			System.out.println("Failed to update vehicle: " + e.getMessage());
			return false;
		}
	}
}