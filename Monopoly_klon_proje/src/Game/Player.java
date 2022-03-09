package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Player extends JPanel {

	private int playerNumber;
	private String playerName;
	private Color playerColor;


	private int roundTillFree = 0;
	private JLabel lblPlayerNumber;
	static int totalPlayers = 0; 
	static HashMap<Integer, Integer> ledger= new HashMap<>();

	private int currentSquareNumber = 0; // where player is currently located on (0 - 19). initially zero
	private ArrayList<Integer> titleDeeds = new ArrayList<Integer>(); // squares that the player has
	private int wallet = 300000; // initial money
	private boolean wentToDuel = false;
	private boolean answeredCorrect;
	private int answeredQuestion = 0;

	private int hitPoint = 3;
	

	public Player(int xCoord, int yCoord, int width, int height) {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBounds(xCoord, yCoord, 20, 20);
		this.setLayout(null);
	}
			
	public Player(int playerNumber, Color color, String playerName) {
		// TODO Auto-generated constructor stub
		this.playerName = playerName;
		this.playerNumber = playerNumber;
		this.setBackground(color);
		this.playerColor = color;
		lblPlayerNumber = new JLabel(""+playerNumber);
		lblPlayerNumber.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblPlayerNumber.setForeground(Color.WHITE);
		this.add(lblPlayerNumber); 
		this.setBounds(playerNumber*30, 33, 20, 28); 
		totalPlayers++;
	}

	
	public ArrayList<Integer> getTitleDeeds() {
		return titleDeeds;
	}
	
	public int getWallet() {
		return wallet;
	}

	public boolean withdrawFromWallet(int withdrawAmount) {
		if(withdrawAmount > wallet) {
			//setVisible(false);
			
			System.out.println("Player "+ playerNumber + " went bankrupt!");
			return false;
		} else {
			wallet -= withdrawAmount;
			return true;
		}
	}

	public void depositToWallet(int depositAmount) {
		wallet += depositAmount;
	}

	public int getCurrentSquareNumber() {
		return currentSquareNumber;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public boolean hasTitleDeed(int squareNumber) {
		return titleDeeds.contains(squareNumber) ? true : false;
	}

	public void buyTitleDeed(int squareNumber) {
			titleDeeds.add(this.getCurrentSquareNumber());
			ledger.put(squareNumber, this.getPlayerNumber()); // Everytime a player buys a title deed, it is written in ledger, for example square 1 belongs to player 2
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Color getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(Color playerColor) {
		this.playerColor = playerColor;
	}
	
	public int getRoundTillFree() {
		return roundTillFree;
	}

	public void setRoundTillFree(int roundTillFree) {
		this.roundTillFree = roundTillFree;
	}
	
	public boolean isWentToDuel() {
		return wentToDuel;
	}


	public void setWentToDuel(boolean wentToDuel) {
		this.wentToDuel = wentToDuel;
	}

	public int getAnsweredQuestion() {
		return answeredQuestion;
	}

	public void setAnsweredQuestion(int answeredQuestion) {
		this.answeredQuestion = answeredQuestion;
	}

	public boolean isAnsweredCorrect() {
		return answeredCorrect;
	}

	public void setAnsweredCorrect(boolean answeredCorrect) {
		this.answeredCorrect = answeredCorrect;
	}

	public int getHitPoint() {
		return hitPoint;
	}

	public void setHitPoint(int hitPoint) {
		this.hitPoint = hitPoint;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}


	int[] xLocationsOfPlayer1 = {31, 131, 231, 331, 431, 531,  		//Koordinatlarýna göre döndürüyor deðiþtirilebilir
			531, 531, 531, 531, 531,
			431, 331, 231, 131, 31,
			31, 31, 31, 31};

	int[] yLocationsOfPlayer1 = {33, 33, 33, 33, 33, 33,
			133, 233, 333, 433, 533,
			533, 533, 533, 533, 533,
			433, 333, 233, 133};
	
	int[] xLocationsOfPlayer2 = {61, 191, 291, 361, 461, 561,
			561, 561, 561, 561, 561,
			461, 361, 261, 161, 61,
			61, 61, 61, 61};

	int[] yLocationsOfPlayer2 = {33, 33, 33, 33, 33, 33,
			133, 233, 333, 433, 533,
			533, 533, 533, 533, 533,
			433, 333, 233, 133};
	

	public void move(int dicesTotal) {
		if(currentSquareNumber + dicesTotal > 19) {
			depositToWallet(200);							//		Baþlangýç noktasýndan geçtiði an baþlangýç miktarý kadar para veren if bloðu
			//System.out.println("Payday for player "+getPlayerNumber()+". You earned $200!");
		}
		int targetSquare = (currentSquareNumber + dicesTotal) % 20;
		currentSquareNumber = targetSquare;
		
		if(GameMain.nowPlaying == 0) {
			this.setLocation(xLocationsOfPlayer1[targetSquare], yLocationsOfPlayer1[targetSquare]);
		} else {
			this.setLocation(xLocationsOfPlayer2[targetSquare], yLocationsOfPlayer2[targetSquare]);
		}
		
		if(ledger.containsKey(this.getCurrentSquareNumber())) {
			GameMain.infoConsole.setText("This property belongs to player "+ledger.get(this.getCurrentSquareNumber()));
		}
	}

}
