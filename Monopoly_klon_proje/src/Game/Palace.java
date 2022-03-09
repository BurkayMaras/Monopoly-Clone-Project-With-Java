package Game;

public class Palace extends Buildings{

	public Palace(int xCoord, int yCoord, int width, int height, String labelString, int rotationDegrees,int price, int rentPrice) {
		super(xCoord, yCoord, width, height, labelString, rotationDegrees);
		// TODO Auto-generated constructor stub
		super.setUpgradedPrice(price * 2);
		super.setUpgradedRentPrice(rentPrice * 2);
	}

}
