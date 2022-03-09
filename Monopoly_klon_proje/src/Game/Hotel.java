package Game;

public class Hotel extends Buildings{

	public Hotel(int xCoord, int yCoord, int width, int height, String labelString, int rotationDegrees, int price, int rentPrice) {
		super(xCoord, yCoord, width, height, labelString, rotationDegrees);
		//super.setPrice(price * 2);
		super.setUpgradedPrice(price * 2);
		super.setUpgradedRentPrice(rentPrice * 2);
	}
}
