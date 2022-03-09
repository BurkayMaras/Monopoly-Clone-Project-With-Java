package Game;

import java.awt.BorderLayout;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Game.Board;
import Game.Dice;
import Game.Player;
import javax.swing.JRadioButton;
import java.io.File;  
import java.io.FileNotFoundException; 
import java.util.Random;
import java.util.Scanner;

public class GameMain extends JFrame{

	private JPanel contentIncluder;
	
	private JLayeredPane layeredPane;
	private JPanel PanelGameOver;
	static JTextArea infoConsole;
	private JPanel playerAssetsPanel;
	private CardLayout c1 = new CardLayout();
	private ArrayList<Player> players = new ArrayList<Player>();
	static int turnCounter = 0;
	private JButton btnNextTurn;
	private JButton btnRollDice;
	private JButton btnPayRent;
	private JButton btnBuy;


	private Board gameBoard;
	
	private Player player1;
	private JLabel panelPlayer1Title;
	private JPanel panelPlayer1;
	private JTextArea panelPlayer1TextArea;
	
	private Player player2;	
	private JLabel panelPlayer2Title;
	private JPanel panelPlayer2;
	private JTextArea panelPlayer2TextArea;
	
	
	private Dice dice1;
	private Dice dice2; 
	
	private Boolean doubleDiceForPlayer1 = false;
	private Boolean doubleDiceForPlayer2 = false;
	static int nowPlaying = 0;
	private JTextField txtOyunBitti;
	private JPanel panel;
	
	public GameMain(String player1Name, String player2Name, Color player1Color, Color player2Color) {
		//Settings for the main frame
		//EXIT_ON_CLOSE
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setBounds(100, 100, 450, 300);
		setSize(1080,720);
		setResizable(false);
		setTitle("Monopoly Game");
		contentIncluder = new JPanel();
		contentIncluder.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentIncluder);
		contentIncluder.setLayout(null);

		
		//Left Panel where the gameBoard is
		layeredPane = new JLayeredPane();
		layeredPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		layeredPane.setBounds(6, 6, 632, 630);
		contentIncluder.add(layeredPane);

		gameBoard = new Board(6,6,612,612);
		gameBoard.setBackground(new Color(66, 72, 245));
		layeredPane.add(gameBoard);
		
								

		player1 = new Player(1, player1Color, player1Name);
		players.add(player1);
		layeredPane.add(player1, new Integer(1));

		player2 = new Player(2, player2Color, player2Name);
		players.add(player2);
		layeredPane.add(player2, new Integer(1));

										
		
		//Right panel for the buttons and infoConsole
		
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.LIGHT_GRAY);
		rightPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		rightPanel.setBounds(634, 6, 419, 600);
		contentIncluder.add(rightPanel);
		rightPanel.setLayout(null);

		
		
		//Buy button with the actionlistener function
		//Checks if the player owns the square and if the player owns the square it will check (using instanceof) if its an hotel or apartment to change the buy function
		btnBuy = new JButton("Buy");
		btnBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 if (Player.ledger.containsKey(players.get(nowPlaying).getCurrentSquareNumber()) && Player.ledger.get(players.get(nowPlaying).getCurrentSquareNumber()) == players.get(nowPlaying).getPlayerNumber()) {  // Checks if the player wants to upgrade or going to buy a new place
					 int withdrawAmount = ((Buildings)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getPrice() * 2;
						if ( players.get(nowPlaying).withdrawFromWallet(withdrawAmount)) {
							int[] array = new int[7];
							array[0] = gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()).getxCoord();
							array[1] = gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()).getyCoord();
							array[2] = gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()).getWidth();
							array[3] = gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()).getHeight();
							String name;  //= gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()).getName() + "(Hotel)";
							array[4] = gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()).getRotationDegrees();
							array[5] = ((Buildings)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getPrice();
							array[6] = ((Buildings)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getRentPrice();
							// this gets the current square and you can use set method gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())
							if (gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()) instanceof Apartment) {
								name = gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()).getName() + "(Hotel)";
								Hotel a = new Hotel(array[0],array[1],array[2],array[3],name,array[4],array[5],array[6]);
								gameBoard.getAllSquares().set(players.get(nowPlaying).getCurrentSquareNumber(), a);
								/*Hotel a = new Hotel (gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()).getxCoord(), gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()).getyCoord()
										gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()).getWidth(),gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()).getHeight(),gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()).getName()
								
										);*/
								if (gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()) instanceof Hotel) {
								infoConsole.setText("Upgraded to Hotel new Price" + ((Buildings)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getPrice() + 
										"New rent price"+ ((Buildings)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getRentPrice() );
								
								}
								
							}
							else if (gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()) instanceof Hotel) {
								name = gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()).getName() + "(Palace)";
								Palace palace = new Palace(array[0],array[1],array[2],array[3],name,array[4],array[5],array[6]);
								gameBoard.getAllSquares().set(players.get(nowPlaying).getCurrentSquareNumber(), palace);
								if (gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()) instanceof Palace) {
									infoConsole.setText("Upgraded to Palace new Price" + ((Buildings)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getPrice() + 
											"New rent price"+ ((Buildings)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getRentPrice() );
									
									}
							}
							
							
						}
						
					 btnBuy.setEnabled(false);
					 btnBuy.setText("Buy");
					 updatePanelPlayer1TextArea();
					 updatePanelPlayer2TextArea();
				 }
				 else { 		//If the square is'nt owned by anyone then the player can buy the square with this else block
					Player currentPlayer = players.get(nowPlaying);		//Gets the current playing player
					int withdrawAmount = ((Buildings)gameBoard.getAllSquares().get(currentPlayer.getCurrentSquareNumber())).getPrice(); //Gets the price of the square
					if (currentPlayer.withdrawFromWallet(withdrawAmount)) {
					infoConsole.setText("You bought "+gameBoard.getAllSquares().get(currentPlayer.getCurrentSquareNumber()).getName());
					gameBoard.getAllSquares().get(currentPlayer.getCurrentSquareNumber()).setColor(players.get(nowPlaying).getPlayerColor());
					currentPlayer.buyTitleDeed(currentPlayer.getCurrentSquareNumber());
					btnBuy.setEnabled(false);
					layeredPane.remove(gameBoard);
					layeredPane.add(gameBoard);
					updatePanelPlayer1TextArea();
					updatePanelPlayer2TextArea();
					}
					else {					
						System.out.print("Cant buy this place not enough money"); //info consoluna eklenecek  --- Eklendi silinebilir
						infoConsole.setText("You cannot buy "+gameBoard.getAllSquares().get(currentPlayer.getCurrentSquareNumber()).getName() + " \nyou don't have enough money");
						btnBuy.setEnabled(false);
					}
				 }
			}
		});
		btnBuy.setBounds(81, 420, 260, 29);
		rightPanel.add(btnBuy);
		btnBuy.setEnabled(false);

		btnPayRent = new JButton("Pay Rent");
		btnPayRent.addActionListener(new ActionListener() {		//Pay rent or pay tax function

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// turnCounter--;
				Player currentPlayer = players.get(nowPlaying);
				if (gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber()) instanceof Tax) {  	//Checks if the player is in a tax square 
					
					
					Tax a = (Tax)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber());
					System.out.print("\n\nTax price \n\n" + a.getTaxPrice());
					if (players.get(nowPlaying).withdrawFromWallet(((Tax)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getTaxPrice())) {	//vergiyi ödeyebiliyorsa
						infoConsole.setText("You paid "+ ((Tax)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getTaxPrice() + "amount tax");
						btnNextTurn.setEnabled(true);
						btnPayRent.setText("Pay Rent");
						btnPayRent.setEnabled(false);
						btnNextTurn.setEnabled(true);
						//Updates infoConsole
						updatePanelPlayer1TextArea();
						updatePanelPlayer2TextArea();
						
					}
					else {		//ödeyemezse oyun biter
						endGame(players.get(nowPlaying));		//If the player cant pay tax then the game will be over
					}
					
				}
				else {											//Current player pays rent to the other player
					//Player ownerOfTheSquare = players.get((Player.ledger.get(currentPlayer.getCurrentSquareNumber()))==1?0:1);
					infoConsole.setText("You paid to the player "+players.get((Player.ledger.get(currentPlayer.getCurrentSquareNumber()))==1?0:1).getPlayerNumber());
					
					int withdrawAmount = ((Buildings)gameBoard.getAllSquares().get(currentPlayer.getCurrentSquareNumber())).getRentPrice();
					System.out.println(withdrawAmount);
					if (players.get(nowPlaying).withdrawFromWallet(withdrawAmount)) {			//Eðer kirayý ödeyebiliyorsa
						System.out.print("\n player wallet " +players.get((Player.ledger.get(currentPlayer.getCurrentSquareNumber()))==1?0:1).getWallet());
					players.get((Player.ledger.get(currentPlayer.getCurrentSquareNumber()))==1?0:1).depositToWallet(withdrawAmount);
					System.out.print("\n player wallet " +players.get((Player.ledger.get(currentPlayer.getCurrentSquareNumber()))==1?0:1).getWallet());
					btnNextTurn.setEnabled(true);
					btnPayRent.setEnabled(false);
					//currentPlayer.withdrawFromWallet(withdrawAmount);
					updatePanelPlayer1TextArea();
					updatePanelPlayer2TextArea();
					//turnCounter++;
					//gameBoard.getAllSquares().get(player1.getCurrentSquareNumber()).setRentPaid(true);			
					}
					else {				//kirayý ödeyemezse oyun bitiyor
						endGame(players.get(nowPlaying));			
					}				
				}				
			}

		});
		btnPayRent.setBounds(81, 465, 260, 29);
		rightPanel.add(btnPayRent);
		btnPayRent.setEnabled(false);

		dice1 = new Dice(244, 406, 40, 40);		//Adding dices to the layeredPane
		layeredPane.add(dice1);

		dice2 = new Dice(333, 406, 40, 40);
		layeredPane.add(dice2);

		btnRollDice = new JButton("Roll Dice");
		btnRollDice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				if (players.get(nowPlaying).getRoundTillFree() > 0) {
					dice1.rollDice();
					dice2.rollDice();
					if(dice1.getFaceValue() == 6 && dice2.getFaceValue() == 6) {		//Eðer çift zar gelirse iki kez kaçma turu 0 olarak ayarlanýyor
						players.get(nowPlaying).setRoundTillFree(0);
						infoConsole.setText("You rolled 2 6's you can escape from the jail next turn!");
					} else {
						infoConsole.setText("Escape Failed!");
					}
					btnRollDice.setEnabled(false);
					btnNextTurn.setEnabled(true);
				}
				
				else {
					if(nowPlaying == 0) {
						// player1's turn
						dice1.rollDice();
						dice2.rollDice();
						int dicesTotal = dice1.getFaceValue() + dice2.getFaceValue();
						if(dice1.getFaceValue() == dice2.getFaceValue()) {		//Eðer çift zar gelirse iki kez
							doubleDiceForPlayer1 = true;
						} else {
							doubleDiceForPlayer1 = false;
						}
						player1.move(dicesTotal); 		//Tahtada hareket etmesini saðlýyor
						
						
						
						if(Player.ledger.containsKey(player1.getCurrentSquareNumber()) // if bought by someone			//This is where buyButton will be added and player may be able to buy it from the other player
								&& Player.ledger.get(player1.getCurrentSquareNumber()) != player1.getPlayerNumber() // not by itself
								) {
							btnBuy.setEnabled(false);
							btnRollDice.setEnabled(false);
							btnNextTurn.setEnabled(false);
							btnPayRent.setEnabled(true);
							btnPayRent.setText("Pay Rent = " + ((Buildings)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getRentPrice() );
						} 
						else if (Player.ledger.containsKey(player1.getCurrentSquareNumber()) // if bought by someone 
								&& Player.ledger.get(player1.getCurrentSquareNumber()) == player1.getPlayerNumber()) { // and by itself
							btnBuy.setEnabled(false);
						
							if (gameBoard.getAllSquares().get(player1.getCurrentSquareNumber()) instanceof Apartment) {			//Tax kýsmý
								btnBuy.setEnabled(true);
								btnBuy.setText("Buy upgrade to Hotel =" + ((Buildings)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getPrice() * 2);
							}
							else if (gameBoard.getAllSquares().get(player1.getCurrentSquareNumber()) instanceof Hotel) {			//Tax kýsmý
								btnBuy.setEnabled(true);
								btnBuy.setText("Buy upgrade to Palace =" + ((Buildings)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getPrice() * 2);
							}
							btnPayRent.setEnabled(false);
							btnNextTurn.setEnabled(true);
						}
						if(gameBoard.getUnbuyableSquares().contains(gameBoard.getAllSquares().get(player1.getCurrentSquareNumber()))) {		//Satýn alýnamayan blok kýsýmlarý için
							if (gameBoard.getAllSquares().get(player1.getCurrentSquareNumber()) instanceof Tax) {			//Tax kýsmý
								System.out.print("Tax bloðu");
								btnPayRent.setEnabled(true);
								btnPayRent.setText("Pay Tax = " + ((Tax)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getTaxPrice());			//Rent tuþunun pay tax tuþuna dönüþtürerek o butondan iþi hallediyoruz
							}
							else if (gameBoard.getAllSquares().get(player1.getCurrentSquareNumber()) instanceof Jail) {			//Jail kýsmý
								infoConsole.setText("You Are in Jail to get Out you need to roll two 6s or wait 3 turns");
								doubleDiceForPlayer1 = false;
								players.get(nowPlaying).setRoundTillFree(4);	//3 tur da kaçabiliyor (burada 4 olarak ayarlanýyor çünkü her next turn dendiðinde 1 azaltacak)
								btnNextTurn.setEnabled(true);
							}
							else if (gameBoard.getAllSquares().get(player1.getCurrentSquareNumber()) instanceof DuelBlock) {
								if (!players.get(nowPlaying).isWentToDuel())  {//if the player didnt went to duel block then it will see the duel frame
									players.get(nowPlaying).setWentToDuel(true);
									Duel();		
									
								}
								else {
									infoConsole.setText("Click Next Turn you already answered the question!");
									btnNextTurn.setEnabled(true);
								}
							}
							else {		//Boþ bloklar için
							btnBuy.setEnabled(false);
							btnNextTurn.setEnabled(true);
							}
						} else if (!Player.ledger.containsKey(player1.getCurrentSquareNumber())) { // if not bought by someone //Bu kýsým hiç bir kiþi tarafýndan alýnmadýðýnda çalýþýyor (satýn alýnabilen bir blok)
							btnBuy.setEnabled(true);
							btnBuy.setText("Buy Price = " + ((Buildings)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getPrice() );
							btnNextTurn.setEnabled(true);
							btnPayRent.setEnabled(false);
						} 
						
		
					} 
					else {
						// player2's turn
						dice1.rollDice();
						dice2.rollDice();
						int dicesTotal = dice1.getFaceValue() + dice2.getFaceValue();
						if(dice1.getFaceValue() == dice2.getFaceValue()) {
							doubleDiceForPlayer2 = true;
						} else {
							doubleDiceForPlayer2 = false;
						}
						player2.move(dicesTotal);
						if(Player.ledger.containsKey(player2.getCurrentSquareNumber()) // if bought by someone				
								&& Player.ledger.get(player2.getCurrentSquareNumber()) != player2.getPlayerNumber() // not by itself
								) {
							btnBuy.setEnabled(false);
							btnRollDice.setEnabled(false);
							btnNextTurn.setEnabled(false);
							btnPayRent.setEnabled(true);
							btnPayRent.setText("Pay Rent = " + ((Buildings)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getRentPrice() );
						}
						else if(Player.ledger.containsKey(player2.getCurrentSquareNumber()) // if bought by someone 
								&& Player.ledger.get(player2.getCurrentSquareNumber()) == player2.getPlayerNumber()) { // and by itself
							btnBuy.setEnabled(false);
							if (gameBoard.getAllSquares().get(player2.getCurrentSquareNumber()) instanceof Apartment) {			//Tax kýsmý
								btnBuy.setEnabled(true);
								btnBuy.setText("Buy upgrade to Hotel =" + ((Buildings)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getPrice() * 2);
							}
							else if (gameBoard.getAllSquares().get(player1.getCurrentSquareNumber()) instanceof Hotel) {			//Tax kýsmý
								btnBuy.setEnabled(true);
								btnBuy.setText("Buy upgrade to Palace =" + ((Buildings)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getPrice() * 2);
							}
							btnPayRent.setEnabled(false);
							btnNextTurn.setEnabled(true);
	
						}
						if(gameBoard.getUnbuyableSquares().contains(gameBoard.getAllSquares().get(player2.getCurrentSquareNumber()))) {
							if (gameBoard.getAllSquares().get(player2.getCurrentSquareNumber()) instanceof Tax) {
								System.out.print("Tax bloðu");
								btnPayRent.setEnabled(true);
								btnPayRent.setText("Pay Tax = " + ((Tax)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getTaxPrice());	
							}
							else if (gameBoard.getAllSquares().get(player2.getCurrentSquareNumber()) instanceof Jail) {
								infoConsole.setText("You Are in Jail to get Out you need to roll two 6s or wait 3 turns");
								doubleDiceForPlayer2 = false;
								players.get(nowPlaying).setRoundTillFree(4);
								btnNextTurn.setEnabled(true);
							}
							else if (gameBoard.getAllSquares().get(player2.getCurrentSquareNumber()) instanceof DuelBlock) {
								if (!players.get(nowPlaying).isWentToDuel())  {//if the player didnt went to duel block then it will see the duel frame
									players.get(nowPlaying).setWentToDuel(true);
									Duel();		
									//DuelCheck();
								}
								else {
									infoConsole.setText("Click Next Turn you already answered the question!");
									btnNextTurn.setEnabled(true);
								}
							}
							else {
							btnBuy.setEnabled(false);
							btnNextTurn.setEnabled(true);
							}
						} 
						else if (!Player.ledger.containsKey(player2.getCurrentSquareNumber())) { // if not bought by someone
							btnBuy.setEnabled(true);
							btnBuy.setText("Buy Price = " + ((Buildings)gameBoard.getAllSquares().get(players.get(nowPlaying).getCurrentSquareNumber())).getPrice() );
							btnNextTurn.setEnabled(true);
							btnPayRent.setEnabled(false);
						}
	
					}
					btnRollDice.setEnabled(false);
					if(doubleDiceForPlayer1 || doubleDiceForPlayer2) {			//if bloðu silinecek ve yerine alttaki fonksiyon kalacak 
						infoConsole.setText("Click Next Turn to allow player "+ (nowPlaying==0 ? 1 : 2) +" to Roll Dice!");
					} else {
						infoConsole.setText("Click Next Turn to allow player "+ (nowPlaying==0 ? 2 : 1) +" to Roll Dice!");
					}
				}

				
				

				// we have to add below 2 lines to avoid some GUI breakdowns.
				layeredPane.remove(gameBoard);
				layeredPane.add(gameBoard);
				//layeredPane.add(gameBoard, new Integer(0)); integer 0 gerekmiyor java 9dan beri
				
				updatePanelPlayer1TextArea();
				updatePanelPlayer2TextArea();

			
			}
		});
		btnRollDice.setBounds(81, 366, 260, 42);
		rightPanel.add(btnRollDice);

		btnNextTurn = new JButton("Next Turn");
		btnNextTurn.addActionListener(new ActionListener() {  //Sonraki tura geçebilmek için 

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				btnRollDice.setEnabled(true);
				btnBuy.setEnabled(false);
				btnPayRent.setEnabled(false);
				btnNextTurn.setEnabled(false);
				btnBuy.setText("Buy");
				btnPayRent.setText("Pay Rent");
				if (players.get(nowPlaying).getRoundTillFree() > 0 )							//Hapiste ise yeni turda öbür oyuncuya geçmeden bekleme turunu bir azaltýyor
					players.get(nowPlaying).setRoundTillFree(players.get(nowPlaying).getRoundTillFree() - 1);
				if(nowPlaying == 0 && doubleDiceForPlayer1) {
					nowPlaying = 0;
					doubleDiceForPlayer1 = false;
				} else if(nowPlaying == 1 && doubleDiceForPlayer2) {
					nowPlaying = 1;
					doubleDiceForPlayer2 = false;
				} else if(!doubleDiceForPlayer1 && !doubleDiceForPlayer2) {
					nowPlaying = (nowPlaying + 1) % 2;
				}
				
				
				c1.show(playerAssetsPanel, ""+(nowPlaying==0 ? 1 : 2)); // maps 0 to 1 and 1 to 2
				updatePanelPlayer1TextArea();
				updatePanelPlayer2TextArea();
				infoConsole.setText("It's now player "+(nowPlaying==0 ? 1 : 2)+"'s turn!");
				
			}

		

		});
		
		btnNextTurn.setBounds(81, 503, 260, 42);
		rightPanel.add(btnNextTurn);
		btnNextTurn.setEnabled(false);

		playerAssetsPanel = new JPanel();
		playerAssetsPanel.setBounds(81, 28, 246, 189);
		rightPanel.add(playerAssetsPanel);
		playerAssetsPanel.setLayout(c1);

		panelPlayer1 = new JPanel();
		panelPlayer1.setBackground(player1.getPlayerColor());
		playerAssetsPanel.add(panelPlayer1, "1");
		panelPlayer1.setLayout(null);

		panelPlayer1Title = new JLabel(player1.getPlayerName() + "'s all owned buildings");
		panelPlayer1Title.setForeground(Color.WHITE);
		panelPlayer1Title.setHorizontalAlignment(SwingConstants.CENTER);
		panelPlayer1Title.setBounds(0, 6, 240, 16);
		panelPlayer1.add(panelPlayer1Title);

		panelPlayer1TextArea = new JTextArea();
		panelPlayer1TextArea.setBounds(10, 34, 230, 149);
		panelPlayer1.add(panelPlayer1TextArea);

		panelPlayer2 = new JPanel();
		panelPlayer2.setBackground(player2.getPlayerColor());
		playerAssetsPanel.add(panelPlayer2, "2");
		panelPlayer2.setLayout(null);
		c1.show(playerAssetsPanel, ""+nowPlaying);

		panelPlayer2Title = new JLabel(player2.getPlayerName() + "'s all owned buildings");
		panelPlayer2Title.setForeground(Color.WHITE);
		panelPlayer2Title.setHorizontalAlignment(SwingConstants.CENTER);
		panelPlayer2Title.setBounds(0, 6, 240, 16);
		panelPlayer2.add(panelPlayer2Title);

		panelPlayer2TextArea = new JTextArea();
		panelPlayer2TextArea.setBounds(10, 34, 230, 149);
		panelPlayer2.add(panelPlayer2TextArea);
		
		updatePanelPlayer1TextArea();
		updatePanelPlayer2TextArea();
		infoConsole = new JTextArea();
		infoConsole.setBounds(81, 250, 260, 68);
		rightPanel.add(infoConsole);
		infoConsole.setColumns(20);
		infoConsole.setRows(5);
		infoConsole.setLineWrap(true);
		infoConsole.setText("PLayer 1 starts the game by clicking Roll Dice!");				
				
		panel = new JPanel();
		panel.setBounds(919, 660, -158, -54);
		contentIncluder.add(panel);
		
		//Oyun Bitiþi Panel testi için 
		//endGame(player1);
	}
	
	
	private String result;
	private JButton btnExit;
	private JButton btnNewGame;
	
	public void updatePanelPlayer2TextArea() {
		// TODO Auto-generated method stub
		result = "";
		result += "Current Balance: "+player2.getWallet()+"\n";
		
		result += "Title Deeds: \n";
		for(int i = 0; i < player2.getTitleDeeds().size(); i++) {
			result += " - "+gameBoard.getAllSquares().get(player2.getTitleDeeds().get(i)).getName()+"\n";
		}
		
		panelPlayer2TextArea.setText(result);
	}

	public void updatePanelPlayer1TextArea() {
		// TODO Auto-generated method stub
		result = "";
		result += "Current Balance: "+player1.getWallet()+"\n";
		
		result += "Title Deeds: \n";
		for(int i = 0; i < player1.getTitleDeeds().size(); i++) {
			result += " - "+gameBoard.getAllSquares().get(player1.getTitleDeeds().get(i)).getName()+"\n";
		}
		
		
		panelPlayer1TextArea.setText(result);
	}
	
	
	private int questionHolder;
	private int correctAnswer = 0;
	public void Duel () {
		PanelGameOver = new JPanel();
		PanelGameOver.setBounds(40, 72, 562, 450);
		PanelGameOver.setBackground(Color.green);
		layeredPane.add(PanelGameOver);
		PanelGameOver.setLayout(null);
		JTextField questionArea = new JTextField();
		questionArea.setBounds(10, 104, 494, 168);
		questionArea.setText("Oyun Bitti");
		PanelGameOver.add(questionArea);
		questionArea.setColumns(10);
		
		
		 JRadioButton AButton = new JRadioButton("A- ");
		    AButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		    AButton.setBounds(10, 367, 155, 56);
		    //.getContentPane().add(AButton);
		    PanelGameOver.add(AButton);
		    JRadioButton BButton = new JRadioButton("B- ");
		    BButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		    BButton.setBounds(189, 367, 170, 56);
		    //f.getContentPane().add(BButton);
		    PanelGameOver.add(BButton);
		    JRadioButton CButton = new JRadioButton("C- ");
		    CButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		    CButton.setBounds(375, 367, 170, 56);
		   // f.getContentPane().add(CButton);
		    PanelGameOver.add(CButton);
		    String data2 = "";

		    int i=0, x=0;
		
		    if (players.get((nowPlaying==0 ? 1 : 0)).isWentToDuel()) 		//Ayný soruyu görmesini saðlayan kýsým
		    {
		    	x = players.get((nowPlaying==0 ? 1 : 0)).getAnsweredQuestion();
		    }
		    else {
		    Random rnd = new Random();
		    x=rnd.nextInt(3)+1;
		    x=x+3*(x-1);
		    players.get(nowPlaying).setAnsweredQuestion(x);
		    }
		    try {
		    	//player1.setEnabled(false);
		    	player1.setVisible(false);
		    	player2.setVisible(false);
		    	dice1.setVisible(false);
		    	dice2.setVisible(false);
		        File myObj = new File(".\\quiz.txt");
		        Scanner myReader = new Scanner(myObj);
		        while (myReader.hasNextLine()) {
		          String data = myReader.nextLine();
		          i++;
		          
		          if(i==x || i==x+1 || i==x+2 || i==x+3) {
		          if(i==x) {
		          data2 = data + "\n";
		          questionArea.setText(data2);
		          }
		          if(i==x+1) {
		        	  if (data.contains("+")) {
		        		  correctAnswer = 1;
		        		  data = data.substring(1);
		        	  }
		              data2 = data + "\n";
		              AButton.setText("A- "+data2);
		          }
		          if(i==x+2) {
		        	  if (data.contains("+")) {
		        		  correctAnswer = 2;
		        		  data = data.substring(1);
		        	  }
		              data2 = data + "\n";
		              BButton.setText("B- "+data2);
		          }
		          if(i==x+3) {
		        	  if (data.contains("+")) {
		        		  correctAnswer = 3;
		        		  data = data.substring(1);
		        	  }
		              data2 = data + "\n";
		              CButton.setText("C- "+data2);
		          }
		          }
		          
		        }
		        myReader.close();
		      } catch (FileNotFoundException e) {
		        System.out.println("An error occurred.");
		        e.printStackTrace();
		      }   
		    
		    
		    
		AButton.addActionListener(new ActionListener () { 
			public void actionPerformed(ActionEvent e) { 
				if (correctAnswer == 1) {
					players.get(nowPlaying).setAnsweredCorrect(true);
				}
				else
					players.get(nowPlaying).setAnsweredCorrect(false);
				
				player1.setVisible(true);
		    	player2.setVisible(true);
		    	dice1.setVisible(true);
		    	dice2.setVisible(true);
		    	
		    	layeredPane.remove(PanelGameOver);
		    	layeredPane.remove(gameBoard);
				layeredPane.add(gameBoard);
				btnNextTurn.setEnabled(true);
				DuelCheck();
			}
			
			
		});
		
		BButton.addActionListener(new ActionListener () { 
			public void actionPerformed(ActionEvent e) { 
				if (correctAnswer == 2) {
					players.get(nowPlaying).setAnsweredCorrect(true);
				}
				else
					players.get(nowPlaying).setAnsweredCorrect(false);
				player1.setVisible(true);
		    	player2.setVisible(true);
		    	dice1.setVisible(true);
		    	dice2.setVisible(true);
		    	//DuelCheck();
		    	layeredPane.remove(PanelGameOver);
		    	layeredPane.remove(gameBoard);
				layeredPane.add(gameBoard);
				btnNextTurn.setEnabled(true);
				DuelCheck();
			}

			
		});
		
		CButton.addActionListener(new ActionListener () { 
			public void actionPerformed(ActionEvent e) { 
				
				if (correctAnswer == 3) {
					players.get(nowPlaying).setAnsweredCorrect(true);
				}
				else
					players.get(nowPlaying).setAnsweredCorrect(false);
				player1.setVisible(true);
		    	player2.setVisible(true);
		    	dice1.setVisible(true);
		    	dice2.setVisible(true);
		    	//DuelCheck();
		    	layeredPane.remove(PanelGameOver);
		    	layeredPane.remove(gameBoard);
				layeredPane.add(gameBoard);
				btnNextTurn.setEnabled(true);
				DuelCheck();
			}

		});
			
	}
	 
	
	
	public void DuelCheck() {
		int otherPlayer = nowPlaying==0 ? 1 : 0;
		if (players.get((nowPlaying==0 ? 1 : 0)).isWentToDuel()) { //If both players went then it will choose who won
			if (players.get(nowPlaying).isAnsweredCorrect() == true && players.get((nowPlaying==0 ? 1 : 0)).isAnsweredCorrect() == true ) { //if both answered correct no hp will be down
				players.get((nowPlaying==0 ? 1 : 0)).setWentToDuel(false);
				players.get(nowPlaying).setWentToDuel(false);
			}
			else if (players.get(nowPlaying).isAnsweredCorrect() == false && players.get((nowPlaying==0 ? 1 : 0)).isAnsweredCorrect() == false ) {
				players.get((nowPlaying==0 ? 1 : 0)).setWentToDuel(false);
				players.get(nowPlaying).setWentToDuel(false);
			}
			
			else if (players.get(nowPlaying).isAnsweredCorrect() == false) {
				if (players.get(nowPlaying).getHitPoint() == 1)
					endGame(players.get(nowPlaying));
				else {
				players.get(nowPlaying).setHitPoint(players.get(nowPlaying).getHitPoint() - 1);
				players.get((nowPlaying==0 ? 1 : 0)).setWentToDuel(false);
				players.get(nowPlaying).setWentToDuel(false);
				}					
			}
			else {
				if (players.get((nowPlaying==0 ? 1 : 0)).getHitPoint() == 1)
					endGame(players.get((nowPlaying==0 ? 1 : 0)));	
				else {
				players.get(otherPlayer).setHitPoint((players.get(otherPlayer).getHitPoint() - 1));
				players.get((nowPlaying==0 ? 1 : 0)).setWentToDuel(false);
				players.get(nowPlaying).setWentToDuel(false);			
				}			
			}
			System.out.print(nowPlaying +"  hp " +players.get(nowPlaying).getHitPoint() + " other player hp " + players.get((nowPlaying==0 ? 1 : 0)).getHitPoint());
			
		}
		
	}
	
	public void endGame(Player currentPlayer) { 		//Function to handle after game over and get the user choice for a new game or to terminate the program
		PanelGameOver = new JPanel();
		PanelGameOver.setBounds(110, 129, 399, 261);
		layeredPane.add(PanelGameOver);
		PanelGameOver.setLayout(null);
		txtOyunBitti = new JTextField();
		txtOyunBitti.setBounds(10, 30, 379, 74);
		txtOyunBitti.setText("Oyun Bitti");
		PanelGameOver.add(txtOyunBitti);
		txtOyunBitti.setColumns(10);
		txtOyunBitti.setText("Game Over" + currentPlayer.getPlayerName() + " went bankrupt");
		
		btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(Color.RED);
		btnExit.setBounds(27, 157, 155, 60);
		PanelGameOver.add(btnExit);
		
		btnNewGame = new JButton("New Game");
		btnNewGame.setBackground(Color.GREEN);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				GameStartFrame a = new GameStartFrame();
				a.setVisible(true);	
				close();
		
			}
		});
		btnNewGame.setBounds(208, 157, 167, 60);
		PanelGameOver.add(btnNewGame);
		
		gameBoard.setVisible(false);
		player1.setVisible(false);
		player2.setVisible(false);
		dice1.setVisible(false);
		dice2.setVisible(false);
		//gameBoard.add(PanelGameOver);
		
		btnBuy.setEnabled(false);
		btnRollDice.setEnabled(false);
		btnNextTurn.setEnabled(false);
		btnPayRent.setEnabled(false);		
	}
	
	
	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}
	public static void errorBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.ERROR_MESSAGE);
	}
}