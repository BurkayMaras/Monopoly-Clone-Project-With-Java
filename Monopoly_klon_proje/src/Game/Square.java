package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.net.*;
import java.io.*;


public class Square extends JPanel implements ExchangeRate{

	private int number;
	private String name;
	private String description;
	private JLabel nameLabel;
	private static int totalSquares = 0;
	//private int price;					--> Price ve rentPricelar buildings'e alındı 
	//private int rentPrice;
	private int xCoord,yCoord,width,height,rotationDegrees;
	private Color color;
	public void setColor(Color color) {
		this.color = color;
	}



	public Square(int xCoord, int yCoord, int width, int height, String labelString, int rotationDegrees) {
		number = totalSquares;
		totalSquares++;
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBounds(xCoord, yCoord, width, height);
		name = labelString;
		this.setLayout(null);
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.width = width;
		this.height = height;
		this.rotationDegrees = rotationDegrees;
		
		if(rotationDegrees == 0) {							//Used for rotating the name of the square
			nameLabel = new JLabel(labelString);
			nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
			nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			nameLabel.setBounds(0,20,this.getWidth(),20);
			this.add(nameLabel);
		} else {	
						
			nameLabel = new JLabel(labelString) {		//Block ismini döndürmeye yarayan kısım
				protected void paintComponent(Graphics g) {
					Graphics2D g2 = (Graphics2D)g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
					AffineTransform aT = g2.getTransform();
					Shape oldshape = g2.getClip();
					double x = getWidth()/2.0;
					double y = getHeight()/2.0;
					aT.rotate(Math.toRadians(rotationDegrees), x, y);
					g2.setTransform(aT);
					g2.setClip(oldshape);

					super.paintComponent(g);
					//paintComponent(g);
				}
			};
			if(rotationDegrees == 90) {
				nameLabel.setBounds(20, 0, this.getWidth(), this.getHeight());
			}
			if(rotationDegrees == -90) {
				nameLabel.setBounds(-10, 0, this.getWidth(), this.getHeight());
			}
			if(rotationDegrees == 180) {
				nameLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
			}
			if(rotationDegrees == 135 || rotationDegrees == -135 || rotationDegrees == -45 || rotationDegrees == 45) {
				nameLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
			}
			nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
			nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			
			this.add(nameLabel);
		}

	}

	
	
	public int getxCoord() {
		return xCoord;
	}



	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}



	public int getyCoord() {
		return yCoord;
	}



	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}



	public int getWidth() {
		return width;
	}



	public void setWidth(int width) {
		this.width = width;
	}



	public int getHeight() {
		return height;
	}



	public void setHeight(int height) {
		this.height = height;
	}



	public String getName() {
		return name;
	}
	
	public int getRotationDegrees() {
		return rotationDegrees;
	}


	public void setRotationDegrees(int rotationDegrees) {
		this.rotationDegrees = rotationDegrees;
	}

	
	
	static String rate;
	public String getRate() {
		return rate;
	}



	public void setRate(String rate) {
		this.rate = rate;
	}



	public void ChangeBlockPrice() {
		
		try {
		rate = ConvertRate();
		rate = rate.replace(",", ".");
		}
		catch (Exception ex) {
			rate = null;
		}
		
	}
	
	
	private URL oracle;
	private URLConnection newConnection;
	private BufferedReader input;
	
	
	public String ConvertRate() throws Exception{
		oracle = new URL("https://dovizborsa.com/doviz/dolar");
        newConnection = oracle.openConnection();
        input = new BufferedReader(new InputStreamReader(newConnection.getInputStream()));
        String inputLine;
        inputLine = input.readLine(); // bütün sitenin html kodu tek stringde þuan
        
        int a=inputLine.indexOf("_x19");
        
        char dolar[]= new char[6];
        for(int i=0;i<dolar.length;i++) {
        	dolar[i]=inputLine.charAt(a+6+i);//a 
        }
		
		//for (int i = 0; rate)
		rate = String.valueOf(dolar);
		return rate;
	}
	

	public void paintComponent(Graphics g) {											//Function to draw a line to impose who owns the square by changing the squares color
		//Boyayan fonksiyonu düzeltmek gerekecek Satın alan kişinin  rengi örneğin
		
		super.paintComponent(g);
		if (color == null) {
			
			if(this.number == 1 || this.number == 3 || this.number == 4) {
				g.drawRect(0, this.getHeight()-20, this.getWidth(), 20);
				//g.setColor(Color.BLUE);
				g.fillRect(0, this.getHeight()-20, this.getWidth(), 20);
			}
			if(this.number == 6 || this.number == 8 || this.number == 9) {
				g.drawRect(0, 0, 20, this.getHeight());
				//g.setColor(Color.PINK);
				g.fillRect(0, 0, 20, this.getHeight());
			}
			if(this.number == 11 || this.number == 13 || this.number == 14) {
				g.drawRect(0, 0, this.getWidth(), 20);
				//g.setColor(Color.ORANGE);
				g.fillRect(0, 0, this.getWidth(), 20);
			}
			if(this.number == 16 || this.number == 17 || this.number == 19) {
				g.drawRect(this.getWidth()-20, 0, 20, this.getHeight());   		//Çizgi çekilmesini sağlıyor
				//g.setColor(Color.GREEN);										//O çizginin rengi
				g.fillRect(this.getWidth()-20, 0, 20, this.getHeight());		//Çizginin dolmasını sağlıyor
			}
		}
		else {
			if(this.number == 1 || this.number == 3 || this.number == 4) {
				g.drawRect(0, this.getHeight()-20, this.getWidth(), 20);
				g.setColor(color);
				g.fillRect(0, this.getHeight()-20, this.getWidth(), 20);
			}
			if(this.number == 6 || this.number == 8 || this.number == 9) {
				g.drawRect(0, 0, 20, this.getHeight());
				g.setColor(color);
				g.fillRect(0, 0, 20, this.getHeight());
			}
			if(this.number == 11 || this.number == 13 || this.number == 14) {
				g.drawRect(0, 0, this.getWidth(), 20);
				g.setColor(color);
				g.fillRect(0, 0, this.getWidth(), 20);
			}
			if(this.number == 16 || this.number == 17 || this.number == 19) {
				g.drawRect(this.getWidth()-20, 0, 20, this.getHeight());   		//Çizgi çekilmesini sağlıyor
				g.setColor(color);								//O çizginin rengi
				g.fillRect(this.getWidth()-20, 0, 20, this.getHeight());		//Çizginin dolmasını sağlıyor
			}
			//g.setColor(color);
		}
	}



}
