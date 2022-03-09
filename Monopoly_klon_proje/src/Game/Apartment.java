package Game;

public class Apartment extends Buildings{

	public Apartment(int xCoord, int yCoord, int width, int height, String labelString, int rotationDegrees, int price, int rentPrice) {
		super(xCoord, yCoord, width, height, labelString, rotationDegrees);
		// TODO Auto-generated constructor stub
		super.setPrice(price);
		super.setRentPrice(rentPrice);
	}
	
}
