package Game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Board extends JPanel {

	private ArrayList<Square> allSquares = new ArrayList<Square>();
	private ArrayList<Square> unbuyableSquares = new ArrayList<Square>(); // squares like "Go", "Chances" etc...
	
	public ArrayList<Square> getUnbuyableSquares(){
		return unbuyableSquares;
	}
	
	public ArrayList<Square> getAllSquares(){
		return allSquares;
	}
	
	public Square getSquareAtIndex(int location) {
		return allSquares.get(location);
	}

	public Board(int xCoord, int yCoord, int width, int height) {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBounds(xCoord, yCoord, 612, 612);
		this.setLayout(null);
		initializeSquares();
	}

	
	
	
	
	
	private void initializeSquares() {
		// TODO Auto-generated method stub
		String[] squareNames = {
				"Start",				//1
				"Oriental Ave",
				"Community Chest",
				"Vermont Ave",
				"Connecticut Ave",		//5
				"Roll once",
				"St. Charles Place",
				"Duel",
				"States Ave",
				"Virginia Ave",			//10
				"Tax",			//Tax bloðu 10 
				"St. James Place",		//11
				"Community Chest",
				"Tennessee Ave",
				"New York Ave",			//15
				"Jail",
				"Pacific Ave",
				"North Carolina Ave",
				"Duel",
				"Pennsylvania Ave"		//20
		};
		

		// squares on the top
		//Buildings
		Square square00 = new Square(6,6,100,100,squareNames[0],135);
		this.add(square00);
		square00.ChangeBlockPrice();
		allSquares.add(square00);
		unbuyableSquares.add(square00);
		Apartment square01 = new Apartment(106,6,100,100,squareNames[1],180,100,6);
		//Buildings square01 = new Buildings(106,6,100,100,squareNames[1],180);
		this.add(square01);
		allSquares.add(square01);
		
		Square square02 = new Square(206,6,100,100,squareNames[2],180);
		this.add(square02);
		allSquares.add(square02);
		unbuyableSquares.add(square02);
		
		Apartment square03 = new Apartment(306,6,100,100,squareNames[3],180,100,6);
		this.add(square03);
		allSquares.add(square03);
		
		Apartment square04 = new Apartment(406,6,100,100,squareNames[4],180,120,8);
		this.add(square04);
		allSquares.add(square04);
		
		Square square05 = new Square(506,6,100,100,squareNames[5],-135);
		this.add(square05);
		allSquares.add(square05);
		unbuyableSquares.add(square05);

		// squares on the right
		Apartment square06 = new Apartment(506,106,100,100,squareNames[6],-90,140,10);
		this.add(square06);
		allSquares.add(square06);
		
		DuelBlock square07 = new DuelBlock(506,206,100,100,squareNames[7],-90);
		this.add(square07);
		allSquares.add(square07);
		unbuyableSquares.add(square07);
		
		Apartment square08 = new Apartment(506,306,100,100,squareNames[8],-90,140,10);
		this.add(square08);
		allSquares.add(square08);
		
		Apartment square09 = new Apartment(506,406,100,100,squareNames[9],-90,160,12);
		this.add(square09);
		allSquares.add(square09);
		
		Tax square10 = new Tax(506,506,100,100,squareNames[10],-45);
		this.add(square10);
		allSquares.add(square10);
		unbuyableSquares.add(square10);

		// squares on the bottom
		Apartment square11 = new Apartment(406,506,100,100,squareNames[11],0,160,14);
		this.add(square11);
		allSquares.add(square11);
		
		Square square12 = new Square(306,506,100,100,squareNames[12],0);
		this.add(square12);
		allSquares.add(square12);
		unbuyableSquares.add(square12);
		
		Apartment square13 = new Apartment(206,506,100,100,squareNames[13],0,160,14);
		this.add(square13);
		allSquares.add(square13);
		
		Apartment square14 = new Apartment(106,506,100,100,squareNames[14],0,180,16);
		this.add(square14);
		allSquares.add(square14);
		
		
		Jail square15 = new Jail(6,506,100,100,squareNames[15],45);		
		//Square square15 = new Square(6,506,100,100,squareNames[15],45);			//Jail
		this.add(square15);
		allSquares.add(square15);
		unbuyableSquares.add(square15);
		
		// squares on the left
		Apartment square16 = new Apartment(6,406,100,100,squareNames[16],90,300,26);
		this.add(square16);
		allSquares.add(square16);
		
		Apartment square17 = new Apartment(6,306,100,100,squareNames[17],90,300,26);
		this.add(square17);
		allSquares.add(square17);
		
		DuelBlock square18 = new DuelBlock(6,206,100,100,squareNames[18],90);
		this.add(square18);
		allSquares.add(square18);
		unbuyableSquares.add(square18);
		
		Apartment square19 = new Apartment(6,106,100,100,squareNames[19],90,320,28);
		this.add(square19);
		allSquares.add(square19);

		square10.setTaxPrice(20);
		
		

		JLabel lblMonopoly = new JLabel("BOARD GAME"){
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D)g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				AffineTransform aT = g2.getTransform();
				Shape oldshape = g2.getClip();
				double x = getWidth()/2.0;
				double y = getHeight()/2.0;
				aT.rotate(Math.toRadians(0), x, y);   //Cümleyi döndüren fonksiyonu
				g2.setTransform(aT);
				g2.setClip(oldshape);
				super.paintComponent(g);
			}
		};
		lblMonopoly.setForeground(Color.WHITE);
		lblMonopoly.setBackground(Color.RED);
		lblMonopoly.setOpaque(true);
		lblMonopoly.setHorizontalAlignment(SwingConstants.CENTER);
		lblMonopoly.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		lblMonopoly.setBounds(172, 206, 278, 100);
		this.add(lblMonopoly);
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}




}
