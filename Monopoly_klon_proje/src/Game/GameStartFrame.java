package Game;  //Java dosyalarýnýn olduðu dosya

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;




import java.awt.*;
import javax.swing.*;



import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;




public class GameStartFrame extends JFrame {

	
	private Color player1Color = Color.blue;
	private Color player2Color = Color.red;
	private String Player1Name = "Player1";	
	private String Player2Name = "Player2";
	
	
	
	
	public GameStartFrame() throws HeadlessException {  //Constructor
		super();
		
		getContentPane().setBackground(Color.GRAY);	// Formun Pencere özellikleri
		setBackground(Color.RED);
		setResizable(false);
		setTitle("StartSelector");
		
		setSize(1080,720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		
		//Renk Kýsmý
		
		JTextPane p1color = new JTextPane();							//p1 color
		p1color.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		p1color.setText("P 1 Color");
		p1color.setEditable(false);
		p1color.setBounds(680, 131, 66, 35);
		getContentPane().add(p1color);
		JTextPane p2color = new JTextPane();							//p2 color
		p2color.setText("P 2 Color");
		p2color.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		p2color.setEditable(false);
		p2color.setBounds(680, 305, 66, 35);
		getContentPane().add(p2color);
		
		
		JTextPane player1Name = new JTextPane();
		player1Name.addMouseListener(new MouseAdapter() {
			@Override
			
			public void mouseClicked(MouseEvent e) {
				
				player1Name.setText("");
			}
		});
		player1Name.setText("Please Enter a name");
		player1Name.setBounds(382, 125, 190, 41);
		
		getContentPane().add(player1Name);
		JTextPane player2Name = new JTextPane();
		player2Name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				player2Name.setText("");
			}
		});
		player2Name.setText("Please Enter a name");
		player2Name.setBounds(382, 299, 190, 41);
		getContentPane().add(player2Name);
		
		
		JTextArea txtrText = new JTextArea();
		txtrText.setEditable(false);
		txtrText.setText("Player 1 Name");
		txtrText.setBounds(126, 125, 179, 41);
		getContentPane().add(txtrText);
		
		JButton buttonP1Color = new JButton("Choose Color");
		
		//Will work when clicked on the button
		buttonP1Color.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				player1Color = JColorChooser.showDialog(null, "Choose a color", Color.RED);
				buttonP1Color.setBackground(player1Color);
				p1color.setBackground(player1Color);
			}
		});
		buttonP1Color.setBounds(800, 125, 113, 41);
		getContentPane().add(buttonP1Color);
		
		
		
		//Oyun baþlatma butoni
		JButton buttonStart = new JButton("start");
		buttonStart.setBackground(Color.GREEN);
		buttonStart.setForeground(Color.WHITE);
		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				GameMain a = new GameMain(player1Name.getText(),player2Name.getText(),player1Color,player2Color);  //Oyuncularýn isim ve renklerini (eðer ekleyebilirsek modellerini ya da resim ekleme vb (çok da önemli bir kýsým deðil renk olur) ana frame e atýyore)
				 close();
				a.setVisible(true);
				
			}
		});
		
		
		
		buttonStart.setBounds(534, 427, 190, 71);
		getContentPane().add(buttonStart);
		
		
		JButton buttonP2Color = new JButton("Choose Color");
		
		
		buttonP2Color.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player2Color = JColorChooser.showDialog(null, "Choose a color", Color.RED);
				buttonP2Color.setBackground(player2Color);
				p2color.setBackground(player2Color);
				
			}
		});
		buttonP2Color.setBounds(800, 299, 113, 41);
		getContentPane().add(buttonP2Color);
		
		
		
		JTextArea txtrPlayerName = new JTextArea();
		txtrPlayerName.setEditable(false);
		txtrPlayerName.setText("Player 2 Name");
		txtrPlayerName.setBounds(126, 299, 179, 41);
		getContentPane().add(txtrPlayerName);
		
		JButton buttonExit = new JButton("Exit");
		buttonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		buttonExit.setBackground(Color.RED);
		buttonExit.setBounds(283, 427, 190, 71);
		getContentPane().add(buttonExit);
		
		
		//setBounds(100, 100, 450, 300);
		
		// TODO Auto-generated constructor stub
	}


	public String getPlayer1Name() {
		return Player1Name;
	}

	public void setPlayer1Name(String player1Name) {
		Player1Name = player1Name;
	}

	public String getPlayer2Name() {
		return Player2Name;
	}
	public void setPlayer2Name(String player2Name) {
		Player2Name = player2Name;
	}


	//Function to close the current JFrame and open a new one
	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}
	
	
	public static void errorBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.ERROR_MESSAGE);
	}
	
	
	
	public static void main(String[] args) {
		
		GameStartFrame frame = new GameStartFrame();
		frame.setVisible(true);
		
	}
}





