package monopoly;
/*
 * ---Tophat---
 * Brian O'Leary - 13475468
 * Conal O'Neill - 13315756
 * Daniel Graham - 15319536
 * 
 * This is our main class used for our Monopoly Game.
 * It extends  JFrame as well as an ActionListener to run the game.
 * This class contains an instance of our JPanel class 'RenderPanel' called 'boardGraphics' as well as 
 * 2 ArrayLists. One used to hold our 'Tile' Objects and the other used to hold our 
 * 'Player' Objects.
 * The ActionListener is attached to a Timer. This activates the ActionListener 
 * every time the Timer ticks. We use this method to redraw the board on a loop
 * and show objects moving on the board.
 * We also increment a counter called 'ticks' every loop and use this as
 * a reference to redraw the board for mouse tracking and player movement, and to check for the enter key  
 * This class also implements a KeyListener which we intend to use as an alternative way
 * of entering commands via our 'ENTER' button. This is still a work in process, but works 
 * using the space bar.
 * 
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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.text.DefaultCaret;

import cards.Card;
import monopoly.RenderPanel;
import monopoly.UserInputMethods;

@SuppressWarnings("serial")
public class GameScreen extends JFrame implements ActionListener, MouseMotionListener, KeyListener {

	private Timer timer;
	private JFrame frame;
	JTextArea infoPanel, commandPanel;
	private JButton enter;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();//size of computer screen

	Dice dice = new Dice();
	UserInput userInput = new UserInput();
	UserInputMethods userInputMethods = new UserInputMethods();
	private RenderPanel boardGraphics = new RenderPanel();

	public Player currentPlayer;
	public static GameScreen screen;

	public int mouseX, mouseY, currentTile, numberOfPlayers = 0;
	private int ticks, firstTurn = 0;
	public int currentPlayerNumber, doubleCount=0, rollTurns = 0, numBuildings = 0;
	public static final int  STARTINGBAL = 1500, MINPLAYERS = 2, MAXPLAYERS = 6, TILESIZE = 64, S_WIDTH = 1300, BOARD_WIDTH = TILESIZE*11;

	public boolean mouseIsOnATile = false, playerNumberCheck = false, rollAgain = true, gameOver = false;

	public ArrayList<Tile> Tiles = new ArrayList<Tile>();
	public ArrayList<Player> Players = new ArrayList<Player>();
	public ArrayList<Card> ChanceCards = new ArrayList<Card>();
	public ArrayList<Card> ComChestCards = new ArrayList<Card>();

	String propertyName = null;
	String choice; //contains user's input throughout the game 
	String helpString = "type command on your turn to play the game. (commands are not case-senstive)\n"
			+ "-help : gives list of all available commands \n"
			+ "-roll : rolls both dice and moves player around the board \n"
			+ "-buy : allows player to buy the property they are on if it can be bought \n"
			+ "-build <short name> <number> : build houses on the property using short name and number to build \n"
			+ "-demolish <short name <number> : demolish houses on the property using short name and number to demolish \n"
			+ "-mortgage <short name> : allows player to mortgage a property to the bank for the amount listed on it's card \n"
			+ "-redeem <short name> : allows player to buy back a mortgaged property from the bank for the mortgage value + 10%\n"
			+ "-property : shows a list of the all the properties owned by the player \n"
			+ "-balance : shows the bank balance of the player \n"
			+ "-done : ends the players turn and allows the next player to start their turn \n"
			+ "-quit : ends the game and display the winner\n"
			+ "-info : displays the short name of the current tile\n"
			+ "-bankrupt : Current player is declared bankrupt and is removed from the game\n";


	GameScreen() {
		//Sets up Tiles and Players
		init();
		timer = new Timer(20, this);//Params are delay and actionListener

		frame = new JFrame("TopHat");
		frame.setSize(S_WIDTH,BOARD_WIDTH);
		frame.setResizable(false);
		//Set location of the JFrame to the center of the users screen.
		frame.setLocation(dim.width/2 - frame.getWidth()/2, dim.height/2 - frame.getHeight()/2 - 30);

		addComponentsToPane(frame.getContentPane());

		frame.pack(); //Shrinks size to wrap layout
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);
		frame.addMouseMotionListener(this); //Listener for Mouse position
		frame.setVisible(true);

		ticks = 0;
		timer.start();

	}

	//Adds Components to screen (JFrame pane)
	private void addComponentsToPane(Container pane) {
		pane.setLayout(new BorderLayout());

		boardGraphics.setPreferredSize(new Dimension(BOARD_WIDTH+1, BOARD_WIDTH+1));
		pane.add(boardGraphics, BorderLayout.LINE_START);

		//Container to hold text boxes and button (everything on the right)
		Container INFOAREA = new Container();
		INFOAREA.setPreferredSize(new Dimension(S_WIDTH - (BOARD_WIDTH+TILESIZE/2+1), BOARD_WIDTH+1));
		INFOAREA.setLayout(new BorderLayout());

		infoPanel = new JTextArea(37,5);//Parameters are rows and columns
		//set info panel to auto scroll to end of newly appended text
		DefaultCaret caret = (DefaultCaret)infoPanel.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		infoPanel.setText("INFO PANEL\n" + helpString + "\nMouse over property tile spaces for more info on the property\n\n");
		infoPanel.setEditable(false);

		//lines now wrap to next line so only vertical scrolling needed
		infoPanel.setLineWrap(true);
		infoPanel.setWrapStyleWord(true);

		infoPanel.setBackground(boardGraphics.insideGreen);

		//adding a JScrollPane to the info panel to allow it to vertically scroll through all the commands
		JScrollPane infoScrollPane = new JScrollPane(infoPanel);

		infoScrollPane.add(infoPanel);
		infoScrollPane.setViewportView(infoPanel);
		infoScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		INFOAREA.add(infoScrollPane, BorderLayout.NORTH);

		//commandPanel set at JTextArea so line can wrap around and can be made scrollable vertically
		commandPanel = new JTextArea("COMMAND PANEL", 5,3);
		commandPanel.setLineWrap(true);
		commandPanel.setWrapStyleWord(true);

		//adding a JScrollPane to the command panel to allow it to vertically scroll through the new command
		JScrollPane commandScrollPane = new JScrollPane(commandPanel);
		commandScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		INFOAREA.add(commandScrollPane, BorderLayout.CENTER);

		enter = new JButton("Enter");
		enter.setPreferredSize(new Dimension((S_WIDTH - (BOARD_WIDTH+TILESIZE/2+1))/2,50));
		enter.addActionListener(this);
		enter.setActionCommand("ENTER");//Name of action
		INFOAREA.add(enter, BorderLayout.LINE_END);

		pane.add(INFOAREA, BorderLayout.LINE_END);
	}


	//Called once on create. Used to setup game
	private void init() {  


		//Get number of Players
		while(!playerNumberCheck){
			//String n = JOptionPane.showInputDialog("Enter Number of Players (2-6)");
			String[] options = new String[] {"2", "3", "4", "5", "6"};
			numberOfPlayers = JOptionPane.showOptionDialog(null,
					"Enter Number of Players (2-6)",
                    "TopHat",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    null);
			
			//If user clicks 'X'
			if (numberOfPlayers == JOptionPane.CLOSED_OPTION) {
	            System.exit(0);
	        }
			
			//Increment by 2 as '2' is option 0, '3' is option 1 etc. 
			numberOfPlayers +=2;
			
			
			//Check number of players is acceptable
			if(numberOfPlayers >= MINPLAYERS && numberOfPlayers <= MAXPLAYERS){
				playerNumberCheck = true;
			}
		}
		//Create Players in Player ArrayList
		for(int i = 0;i<numberOfPlayers;i++){
			Players.add(new Player(i+1, STARTINGBAL));

			//Ask for player name
			int pnum = i+1;
			String pname = "";
			//Check that something has been entered
			while(pname.equals("")){
				pname = JOptionPane.showInputDialog("Enter Name of Player " + pnum + ":");
				//If user clicks 'X' or 'CANCEL' option
				if (pname == null) {
		            System.exit(0);
		        }
			}
			
			Players.get(i).setName(pname);

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

		//Loops to create Tiles in correct order
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
		ticks++;

		//purely for performance until a better solution is thought of, only repaint the board every 6 ticks 
		//(slight lag in mouse tracking but performance improvements are worth it for now)
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
				infoPanel.append("Roll for first turn: Player " + p.getName() + ": " + p.firstRoll + "\n");
			}

			infoPanel.append(Players.get(currentPlayerNumber-1).getName() + " rolled highest and goes first.\n\n");
			infoPanel.append(Players.get(currentPlayerNumber-1).getName() + " :");

		}

		//If button is pushed
		if ("ENTER".equals(e.getActionCommand())) {

			//If more than 1 Player left and 'quit' command hasn't been called 
			if (!userInputMethods.checkGameOver() && !gameOver) {
				//Check user input and carry out command entered
				userInput.userInput();
			}
			else {
				choice = commandPanel.getText().trim().toLowerCase();
				switch (choice) {
				case "exit":
					System.exit(0);
					break;

				default:
					break;
				}
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
				mouseY > BOARD_WIDTH - TILESIZE*10 &&//BOARD_HEIGHT - TILESIZE*10 + TILESIZE/2 + TILESIZE/2 &&
				mouseY < BOARD_WIDTH - TILESIZE ||
				//or off the board
				mouseX > BOARD_WIDTH - 10) {
			mouseIsOnATile = false;
			currentTile = 100;
		}
	}

	//END OF GAME RUNNING METHODS


	//METHODS



	@Override
	public void mouseMoved(MouseEvent m) {//When mouse is moved
		mouseX = m.getX() - 2;//Get mouse Location
		mouseY = m.getY() - 25;
	}
	//Meant to enable pressing the space key as a button click
	@Override
	public void keyPressed(KeyEvent key) {
		//If 'SPACE' is pressed, click the button
		if(key.getKeyCode() == KeyEvent.VK_SPACE){
			enter.doClick();
		}
		System.out.println(key.getKeyCode());
	}
	@Override
	public void mouseDragged(MouseEvent m) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}