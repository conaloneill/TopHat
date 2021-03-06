package monopoly;
/*
 * ---Tophat---
 * Brian O'Leary - 13475468
 * Conal O'Neill - 13315756
 * Daniel Graham - 15319536
 * 
 * This is our main class used for our Monopoly Game.
 * It extends  JFrame as well as an ActionListener to run the game.#
 * The MouseMotionListener is used to enable the player to mouse over tiles for more information.
 * This class contains an instance of our JPanel class 'RenderPanel' called 'boardGraphics' as well as 
 * 4 ArrayLists. One used to hold our 'Tile' Objects, another used to hold our 
 * 'Player' Objects and two for our two decks of cards.
 * The ActionListener is attached to a Timer. This activates the ActionListener 
 * every time the Timer ticks. We use this method to redraw the board on a loop
 * and show objects moving on the board.
 * We also increment a counter called 'ticks' every loop and use this as
 * a reference to redraw the board for mouse tracking and player movement, and to check for the enter key  
 * 
 * This class also implements a KeyListener which is used as an alternative way
 * of entering commands via our 'ENTER' button. The 'ENTER' key may be used to input commands
 * 
 * 
 * currentPlayer(Player) is the current player based on the index position in the Players array (i.e: 0-5)
 * currentPlayerNumber(int) is the human defined number of the Player (i.e: 1-6)
 *
 *
 *  */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;


import cards.Card;
import monopoly.RenderPanel;
import monopoly.UserInputMethods;
import property.PropertyInfo;

@SuppressWarnings("serial")
public class GameScreen extends JFrame implements ActionListener, MouseMotionListener {

	private Timer timer;//Timer used to activate actionListener
	private JFrame frame;
	JTextArea infoPanel, commandPanel;
	private JButton enter;//Main enter button
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();//Size of computer screen, used to set frame position

	Dice dice = new Dice();//Dice object used when 'rolling' dice

	UserInput userInput = new UserInput();//Instance of class containing our userInput method
	UserInputMethods userInputMethods = new UserInputMethods();//Instance of our class containing methods for user actions
	private RenderPanel boardGraphics = new RenderPanel();//Instance of our JPanel class

	public Player currentPlayer;//Used to keep track of player who's current turn it is
	public static GameScreen screen;

	public int mouseX, mouseY, currentTile, numberOfPlayers = 0, taxAmount = 0;
	private int ticks, firstTurn = 0;
	public int currentPlayerNumber, doubleCount=0, rollTurns = 0, numBuildings = 0;
	public static final int  STARTINGBAL = 1500, MINPLAYERS = 2, MAXPLAYERS = 6, TILESIZE = 64, S_WIDTH = 1300, BOARD_WIDTH = TILESIZE*11;

	public boolean inFineOrChanceLoop = false, mouseIsOnATile = false, rollAgain = true, gameOver = false;

	public ArrayList<Tile> Tiles = new ArrayList<Tile>();//Our list of 40 tiles surrounding the board
	public ArrayList<Player> Players = new ArrayList<Player>();//Our list of players in the game
	public ArrayList<Card> ChanceCards = new ArrayList<Card>();//List of chance cards
	public ArrayList<Card> ComChestCards = new ArrayList<Card>();//List of Community chest cards

	public static final String TITLE = "TopHat";//Game Title
	String propertyName = null;
	String choice; //contains user's input throughout the game 
	String helpString = "Type commands on your turn to play the game. (commands are not case-senstive)\n"
			+ "-help : gives list of all available commands \n"
			+ "-roll : rolls both dice and moves player around the board \n"
			+ "-buy : allows player to buy the property they are on if it can be bought \n"
			+ "-build <short name> <number> : build houses on the property using short name and number to build \n"
			+ "-demolish <short name> <number> : demolish houses on the property using short name and number to demolish \n"
			+ "-mortgage <short name> : allows player to mortgage a property to the bank for the amount listed on it's card \n"
			+ "-redeem <short name> : allows player to buy back a mortgaged property from the bank for the mortgage value + 10%\n"
			+ "-property : shows a list of the all the properties owned by the player \n"
			+ "-balance : shows the bank balance of the player \n"
			+ "-done : ends the players turn and allows the next player to start their turn \n"
			+ "-bankrupt : Current player is declared bankrupt and is removed from the game\n"
			+ "-info : displays the short name of the current tile\n"
			+ "-quit : ends the game and display the winner\n"
			+ "-exit : Closes game\n";

	//Constructor called when this class is instantiated
	GameScreen() {
		//Sets up Tiles and Players
		init();
		timer = new Timer(20, this);//Parameters are delay and actionListener

		frame = new JFrame(TITLE);//Create JFrame and set title
		frame.setSize(S_WIDTH,BOARD_WIDTH);//Set size
		frame.setResizable(false);//Disable the user resizing the screen
		frame.setLocation(dim.width/2 - frame.getWidth()/2, dim.height/2 - frame.getHeight()/2 - 30);//Set location of the JFrame to the center of the users screen.

		addComponentsToPane(frame.getContentPane());//Add elements to screen

		frame.pack(); //Shrinks size to wrap layout
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseMotionListener(this); //Listener for Mouse position
		frame.setVisible(true);
		ticks = 0;//Ticks counts timer ticks. Initially set to 0
		timer.start();//Begin game

	}

	//Adds Components to screen (JFrame pane)
	private void addComponentsToPane(Container pane) {
		pane.setLayout(new BorderLayout());//Use border layout in JFrame

		boardGraphics.setPreferredSize(new Dimension(BOARD_WIDTH+1, BOARD_WIDTH+1));//Set size of our JPanel class
		pane.add(boardGraphics, BorderLayout.LINE_START);//Add JPanel to JFrame

		//Container to hold text boxes and button (everything on the right)
		Container INFOAREA = new Container();
		INFOAREA.setPreferredSize(new Dimension(S_WIDTH - (BOARD_WIDTH+TILESIZE/2+1), BOARD_WIDTH+1));
		INFOAREA.setLayout(new BorderLayout());

		infoPanel = new JTextArea(37,5);//Parameters are rows and columns
		//Set info panel to auto scroll to end of newly appended text
		DefaultCaret caret = (DefaultCaret)infoPanel.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		infoPanel.setText("INFO PANEL\n" + helpString + 
				"\nMouse over property tile spaces for more info on the property"
				+ "\nEnter Key may be used to enter commands.\n\n");
		infoPanel.setEditable(false);

		//Lines now wrap to next line so only vertical scrolling needed
		infoPanel.setLineWrap(true);
		infoPanel.setWrapStyleWord(true);

		infoPanel.setBackground(boardGraphics.insideGreen);//Set color of info panel

		//Add a JScrollPane to the info panel to allow it to vertically scroll through all the commands
		JScrollPane infoScrollPane = new JScrollPane(infoPanel);

		infoScrollPane.add(infoPanel);//Add info panel to JScrollpane
		infoScrollPane.setViewportView(infoPanel);
		infoScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		INFOAREA.add(infoScrollPane, BorderLayout.NORTH);//Add JScrollpane containing info panel to JFrame

		//commandPanel set at JTextArea so line can wrap around and can be made scrollable vertically
		commandPanel = new JTextArea("COMMAND PANEL", 5,3);
		commandPanel.setLineWrap(true);
		commandPanel.setWrapStyleWord(true);
		//Enable pressing enter key as a button click
		commandPanel.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent key) {
				//If 'ENTER' is pressed, click the button
				if(key.getKeyCode() == KeyEvent.VK_ENTER){
					key.consume();//Don't type enter into command panel
					enter.doClick();//Activate button
				}
			}
			//Unused mandatory methods
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {} 
		});
		//Add a JScrollPane to the command panel to allow it to vertically scroll through the new command
		JScrollPane commandScrollPane = new JScrollPane(commandPanel);
		commandScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		INFOAREA.add(commandScrollPane, BorderLayout.CENTER);//Add JScrollpane containing command panel to JFrame

		enter = new JButton("Enter");//Create main enter button
		enter.setPreferredSize(new Dimension((S_WIDTH - (BOARD_WIDTH+TILESIZE/2+1))/2,50));//Set size
		enter.addActionListener(this);//Add this class's actionListener
		enter.setActionCommand("ENTER");//Name of action
		INFOAREA.add(enter, BorderLayout.LINE_END);

		pane.add(INFOAREA, BorderLayout.LINE_END);//Add container to JFrame using border layout
	}


	//Called once on create. Used to setup game
	private void init() {  
		//Set background of JOptionPanes to green
		UIManager.put("OptionPane.background", boardGraphics.insideGreen);

		//Icons used in JOptionPanes
		ImageIcon icon = new ImageIcon(getClass().getResource("drawable/monopolyIcon.png"));
		ImageIcon welcomeIcon = new ImageIcon(getClass().getResource("drawable/welcome.gif"));

		//Options for JOptionPane
		String[] options = new String[] {"  2  ", "  3  ", "  4  ", "  5  ", "  6  "};
		//Show JOptionPane
		int playerOption = JOptionPane.showOptionDialog(null,
				"Select Number of Players : ",
				TITLE,
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				welcomeIcon,
				options,
				null);

		//If user clicks 'X'
		if (playerOption == JOptionPane.CLOSED_OPTION) {
			System.exit(0);
		}

		//Increment by 2 as '2' is option 0, '3' is option 1 etc. 
		numberOfPlayers = playerOption + 2;

		//JPanel used for collecting player names
		JPanel namesPanel = new JPanel();
		//Set background Color to green
		namesPanel.setBackground(boardGraphics.insideGreen);

		//Set to grid Layout dependent on number of players
		if(numberOfPlayers <=3)
			namesPanel.setLayout(new GridLayout(0,2));
		else
			namesPanel.setLayout(new GridLayout(0,4));

		//List of JTextfields for holding player names
		ArrayList<JTextField> playerNames = new ArrayList<JTextField>();
		//Check for empty names
		boolean aNameIsEmpty = true;

		//Create TextFields for name input
		for(int i = 0;i<numberOfPlayers;i++){
			int playerNumber = i+1;//Current player number
			JLabel label = new JLabel(" Name of Player " + playerNumber + ": ", JLabel.TRAILING);//Create new Label
			namesPanel.add(label);//Add new Label to JPanel
			JTextField textField = new JTextField(5);//Create new JTextfield
			String defaultPlayerName = "Player " + playerNumber; //Default player name
			textField.setText(defaultPlayerName);//Set default player name
			playerNames.add(textField);//Add new JTextfield
			label.setLabelFor(textField);//Set new Label as label for new JTextfield
			namesPanel.add(textField);//Add new JTextField to JPanel
		}
		//Get player names
		while(aNameIsEmpty){
			
			//JOptionPane containing JPanel 'namesPanel'
			int namesOptionPane = JOptionPane.showConfirmDialog(null,
					namesPanel,
					TITLE,
					JOptionPane.OK_CANCEL_OPTION, 
					0,
					icon
					);
				
			//If user clicks 'X' or Cancel
			if (namesOptionPane == 2 || namesOptionPane == -1) {
				System.exit(0);
			}
			//Count empty names entered
			int emptyNames = 0;
			for(JTextField text : playerNames){
				//If a name is empty, increment counter
				if(text.getText().trim().equals("")){
					emptyNames++;
				}
			}
			aNameIsEmpty = emptyNames != 0; //Update check for empty names
		}

		//Create Players in Player ArrayList
		for(int i = 0;i<numberOfPlayers;i++){

			//Create player and set number, balance and name
			Players.add(new Player(i+1, STARTINGBAL, playerNames.get(i).getText()));

			//roll dice and assign to the players
			dice.roll();
			Players.get(i).firstRoll = dice.getValue();
			//Find highest first roll
			if (Players.get(i).firstRoll > firstTurn ) {
				firstTurn = Players.get(i).firstRoll;
			}
		}

		//Sets the starting position and color for each individual player
		Players.get(0).xPosition=650;
		Players.get(0).yPosition=645;
		Players.get(0).setColour(Color.magenta);

		Players.get(1).xPosition=675;
		Players.get(1).yPosition=645;
		Players.get(1).setColour(Color.blue);

		if(numberOfPlayers>=3){
			Players.get(2).xPosition=650;
			Players.get(2).yPosition=665;
			Players.get(2).setColour(Color.yellow);
		}

		if(numberOfPlayers>=4){
			Players.get(3).xPosition=675;
			Players.get(3).yPosition=665;
			Players.get(3).setColour(Color.green);
		}

		if(numberOfPlayers>=5){
			Players.get(4).xPosition=650;
			Players.get(4).yPosition=685;
			Players.get(4).setColour(Color.orange);
		}

		if(numberOfPlayers>=6){
			Players.get(5).xPosition=675;
			Players.get(5).yPosition=685;
			Players.get(5).setColour(Color.pink);
		}

		//Loops to create Tiles in correct order around the board
		int x = BOARD_WIDTH - TILESIZE/2;
		int y = BOARD_WIDTH - TILESIZE/2;
		for(int row = 0;row<11;row++){//BOTTOM ROW
			Tiles.add(new Tile(row,x,y));
			x-= TILESIZE;
		}
		x+=TILESIZE;
		y-= TILESIZE;
		for(int col = 0;col<9;col++){//LEFT COL
			Tiles.add(new Tile(col + 11,x,y));
			y-= TILESIZE;
		}
		for(int row = 0;row<11;row++){//TOP ROW
			Tiles.add(new Tile(row + 20,x,y));
			x+= TILESIZE;
		}
		x-=TILESIZE;
		y+= TILESIZE;
		for(int col = 0;col<9;col++){//RIGHT COL
			Tiles.add(new Tile(col + 31,x,y));
			y+= TILESIZE;
		}
	}


	public static void main(String[] args) {
		//Create an instance of our main class
		screen = new GameScreen();

	}

	@Override  //MAIN LOOP, gets called when timer ticks
	public void actionPerformed(ActionEvent e) {  
		ticks++;//Counts ticks of the timer

		//For performance, only repaint the board every 6 ticks 
		//(slight lag in mouse tracking but performance improvements are worth it)
		if (ticks%6==0) {
			boardGraphics.repaint();
		}

		//Only happens on first call of this method to have board drawn before players move
		if (ticks==1) {	
			//Draw board before starting to move players
			boardGraphics.repaint();

			//find the player with the largest first roll
			for (Player p : Players) {
				if (p.firstRoll == firstTurn) {
					currentPlayerNumber = p.playerNumber;
				}
				//print for testing check
				infoPanel.append("Roll for first turn: " + p.getName() + ": " + p.firstRoll + "\n");
			}

			infoPanel.append(Players.get(currentPlayerNumber-1).getName() + " rolled highest and goes first.\n\n");
			infoPanel.append(Players.get(currentPlayerNumber-1).getName() + " :");

		}

		//If button is pushed
		if ("ENTER".equals(e.getActionCommand())) {

			//If more than 1 Player left and 'quit' command hasn't been called 
			if (!userInputMethods.checkGameOver() && !gameOver && !inFineOrChanceLoop) {
				//Check user input and carry out command entered
				userInput.userInput();
			}
			//If game is over
			else if((userInputMethods.checkGameOver() || gameOver) && !inFineOrChanceLoop) {
				gameOver = true;
				//If Only 1 player left
				if(Players.size() <= 1){
					//Get last players assets
					Players.get(Players.size()-1).calculateAssetValue(Tiles, Players.get(Players.size()-1).playerNumber );
					//GameOver message
					infoPanel.append("\nGame Over! Only one player remaining.\nWinner is " + Players.get(Players.size()-1).getName()
							+ " with assets worth " + Players.get(Players.size()-1).getAssetValue()
							+ "\nUse 'exit' to exit game");
				//If multiple players left
				}else{
					userInputMethods.quitGame();
				}
				
				//Game is over, Only available command is 'exit'
				choice = commandPanel.getText().trim().toLowerCase();
				switch (choice) {
				case "exit":
					System.exit(0);
					break;

				default:
					break;
				}
			}
			//If a player has drawn a 'Fine or Chance' Card
			else if(inFineOrChanceLoop){
				choice = commandPanel.getText().trim().toLowerCase();
				switch (choice) {
				case "fine":
					infoPanel.append("\n" + currentPlayer.getName() +  " spent " + taxAmount + ".");
					currentPlayer.spend(taxAmount);//Deduct from players balance
					inFineOrChanceLoop = false;//Get out of loop
					break;
				case "chance":
					infoPanel.append("\n" + currentPlayer.getName() + " drew  a chance card.");
					userInputMethods.drawCard(PropertyInfo.TYPE_CHANCE);//Draw a chance card
					inFineOrChanceLoop = false;//Get out of loop
					break;
				default:
					//Default message is shown for invalid input
					infoPanel.append("\nYou must enter 'fine' to pay the fine or enter 'chance' to take a chance card.");
				}
				//Asks the next player for input
				infoPanel.append("\n" + currentPlayer.getName() + " :"); 
				//Clear text from command panel
				commandPanel.setText("");
			}
		}

		//Idea for a pop-up to appear on the screen containing tile information for whatever tile mouse is on
		//If mouse is on a tile, get tile number
		for(Tile o : Tiles) { 
			if(mouseX > o.x - TILESIZE/2 && mouseX < o.x + TILESIZE/2 && mouseY > o.y - TILESIZE/2 && mouseY < o.y + TILESIZE/2){
				mouseIsOnATile = true;
				currentTile =  o.getTileNum();
			}
		}
		//If mouse is in the center of the board ( not a tile )
		if(mouseX > BOARD_WIDTH - TILESIZE*10 && 
				mouseX <  BOARD_WIDTH - TILESIZE &&
				mouseY > BOARD_WIDTH - TILESIZE*10 &&
				mouseY < BOARD_WIDTH - TILESIZE ||
				//or off the board
				mouseX > BOARD_WIDTH - 10) {
			mouseIsOnATile = false;
			currentTile = 100;
		}
	}

	//MouseMotionListener Methods
	@Override
	public void mouseMoved(MouseEvent m) {//When mouse is moved
		mouseX = m.getX() - 2;//Get mouse Location
		mouseY = m.getY() - 25;
	}
	@Override
	public void mouseDragged(MouseEvent m) {}
}