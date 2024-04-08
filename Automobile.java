public class Automobile {
	private int invNumber;
	private String make;
	private String model;
	private String color;
	private int year;
	private int mileage;
	
	// Default constructor
	public Automobile() {
	}
	
	// Parameterized constructor
	public Automobile(int invNumber, String make, String model, String color, int year, int mileage) {
		this.invNumber = invNumber;
		this.make = make;
		this.model = model;
		this.color = color;
		this.year = year;
		this.mileage = mileage;
	}
	
	public int getInvNumber() { return invNumber; }
	public String getMake() { return make; }
	public String getModel() { return model; }
	public String getColor() { return color; }
	public int getYear() { return year; }
	public int getMileage() { return mileage; }
	
	public void setInvNumber(int invNumber) {
		this.invNumber = invNumber;
	}
	
	// Update vehicle attributes method
	public String updateVehicleAttributes(int invNumber, String make, String model, String color, int year, int mileage) {
		try {
			this.invNumber = invNumber;
			this.make = make;
			this.model = model;
			this.color = color;
			this.year = year;
			this.mileage = mileage;
			return "Vehicle updated successfully";
		} catch (Exception e) {
			return "Failed to update vehicle";
		}
	}
	
}