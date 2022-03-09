package Game;

public class Buildings extends Square {

	private boolean isRentPaid = false;
	private int price;
	private int rentPrice;
		
	
	public Buildings(int xCoord, int yCoord, int width, int height, String labelString, int rotationDegrees) {
		super(xCoord, yCoord, width, height, labelString, rotationDegrees);
		
		
		// TODO Auto-generated constructor stub
	}

	
	
	public int getPrice() {
		return price;
	}

	
	//private String a;

	public void setPrice(int price) {
		
		
		if (super.getRate() == null)
			this.price = price;
		else
			this.price = price * (int)Double.parseDouble(super.getRate());
		//}
		//super.
	}

	public void setUpgradedPrice (int price) {
		this.price = price;
	}
	public void setUpgradedRentPrice (int rentPrice) {
		this.rentPrice = rentPrice;
	}
	public int getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(int rentPrice) {
		if (super.getRate() == null)
			this.rentPrice = rentPrice;
		else
			this.rentPrice = rentPrice * (int)Double.parseDouble(super.getRate());
		//}
		
		
		
	}
	public boolean isRentPaid() {
		return isRentPaid;
	}
	public void setRentPaid(boolean pay) {
		isRentPaid = pay;
	}
	
}
