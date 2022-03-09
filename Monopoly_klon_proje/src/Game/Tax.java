package Game;
//bam




public class Tax extends Square {

	private int taxPrice;
	

	public Tax(int xCoord, int yCoord, int width, int height, String labelString, int rotationDegrees) {
		super(xCoord, yCoord, width, height, labelString, rotationDegrees);
		// TODO Auto-generated constructor stub
	}

	
	public int getTaxPrice() {
		return taxPrice;
	}

	public void setTaxPrice(int taxPrice) {
		if (super.getRate() == null)
			this.taxPrice = taxPrice;
		else
			this.taxPrice = taxPrice * (int)Double.parseDouble(super.getRate());
		
		//super.setRentPrice(taxPrice);
	}

	
	
}
