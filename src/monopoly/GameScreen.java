package monopoly;
/*
 * ---Tophat---
 * Brian O'Leary - 134775468
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
 * a reference to move our tokens every 15 'ticks' of the timer.
 * This class also implements a KeyListener which we intend to use as an alternative way
 * of entering commands via our 'ENTER' button. This is still a work in process, but works 
 * using the space bar.
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



import propertyImages.PropertyImages;
import monopoly.RenderPanel;

@SuppressWarnings("serial")
public class GameScreen extends JFrame implements ActionListener, MouseMotionListener, KeyListener {
	public Timer timer;
	public JFrame frame;
	public JTextArea infoPanel, commandPanel;
	public JButton enter;
	public Dice dice = new Dice();
	public RenderPanel rp = new RenderPanel();
	public PropertyImages propertyCards = new PropertyImages();
	public int ticks, tileIndex = 0, mouseX, mouseY, currentTile, numberOfPlayers, maxNumberOfPlayers = 6;
	public int minNumberOfPlayers = 2,currentPlayer=1, doubleCount=0, startingBal = 1500, rollTurns = 0;
	public static final int  TILESIZE = 64, S_WIDTH = 1300, BOARD_WIDTH = TILESIZE*11;
	public Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();//size of computer screen
	public RenderPanel boardGraphics = new RenderPanel();
	public static GameScreen screen;
	public boolean mouseIsOnATile = false, playerNumberCheck = false;
	public ArrayList<Tile> Tiles = new ArrayList<Tile>();
	public ArrayList<Player> Players = new ArrayList<Player>();
	private boolean firstTime = true, rollAgain = false;
	private Player playerName;
	private String helpString = "type command on your turn to play the game. (commands are not case-senstive)\n"
			+ "help : gives list of all available commands \n"
			+ "roll : rolls both dice and moves player around the board \n"
			+ "buy : allows player to buy the property they are on if it can be bought \n"
			+ "pay rent : allows player to pay owed rent to the owner of the property they are on \n"
			+ "property : shows a list of the all the properties owned by the player \n"
			+ "balance : shows the bank balance of the player \n"
			+ "done : ends the players turn and allows the next player to start their turn \n";
	private int firstTurn = 0;

	GameScreen() {
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
		infoPanel.setText("INFO PANEL\n" + helpString + "\n");
		infoPanel.setEditable(false);

		//lines now wrap to next line so only vertical scrolling needed
		infoPanel.setLineWrap(true);
		infoPanel.setWrapStyleWord(true);

		infoPanel.setBackground(boardGraphics.insideGreen);

		//adding a JScrollPane to the info panel to allow it to vertically scroll through all the commands
		JScrollPane infoScrollPane = new JScrollPane(infoPanel);
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
			String n = JOptionPane.showInputDialog("Enter Number of Players (2-6)");
			numberOfPlayers = Integer.parseInt(n);
			//Check number of players is acceptable
			if(numberOfPlayers >= minNumberOfPlayers && numberOfPlayers <= maxNumberOfPlayers){
				playerNumberCheck = true;
			}
		}
		//Create Players in Player ArrayList
		for(int i = 0;i<numberOfPlayers;i++){
			Players.add(new Player(0, i+1, startingBal));

			//Ask for player name
			int pnum = i+1;
			String pname = JOptionPane.showInputDialog("Enter Name of Player " + pnum + ":");
			Players.get(i).setName(pname);
			
			//roll dice and assign to the players
			dice.roll();
			Players.get(i).firstRoll = dice.getValue();
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
			Tiles.add(new Tile(row,0,100,x,y));
			x-= TILESIZE;
		}
		x+=TILESIZE;
		y-= TILESIZE;
		for(int col = 0;col<9;col++){//LEFT COL
			Tiles.add(new Tile(col + 11,0,100,x,y));
			y-= TILESIZE;
		}
		for(int row = 0;row<11;row++){//TOP ROW
			Tiles.add(new Tile(row + 20,0,100,x,y));
			x+= TILESIZE;
		}
		x-=TILESIZE;
		y+= TILESIZE;
		for(int col = 0;col<9;col++){//RIGHT COL
			Tiles.add(new Tile(col + 31,0,100,x,y));
			y+= TILESIZE;
		}	
		
		//find the player with the largest first roll
		for (Player p : Players) {
			if (p.firstRoll == firstTurn) {
				currentPlayer = p.playerNumber;
			}
			//print for testing check
			System.out.println("firstRoll, Player " + p.getName() + ": " + p.firstRoll);
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
		if (firstTime) {	
			infoPanel.append(Players.get(currentPlayer-1).getName() + " :");
			//Draw board before starting to move players
			boardGraphics.repaint();
			firstTime = false;
		}

		//If button is pushed, add command panel text to the info panel
		if ("ENTER".equals(e.getActionCommand())) {

			playerName = Players.get(currentPlayer-1);
			String choice = commandPanel.getText();
			choice = choice.toLowerCase();
			infoPanel.append(choice + "\n"); //add text to info panel


			switch(choice) {
			case "help":
				infoPanel.append(helpString);
				break;
			case "roll":
				dice.roll();

				if (rollTurns == 0) {
					movePlayer();
				}
				else if (rollTurns < 3 && rollTurns > 0 && rollAgain) {
					movePlayer();
				}
				else {
					infoPanel.append("Error you cant roll again this turn. Please end turn with 'done'\nor type 'help' for the other options\n");
				}
				break;
			case "balance":
				infoPanel.append("Player " + playerName.getName() + " has a balance of: " + playerName.getBalance());
				break;
			case "buy":
				//implement buying current tile + error handling for non-buyable tile
				infoPanel.append("buy condition, but no method yet");
				break;
			case "property":
				String properties = "Property owned by " + playerName.getName() + " :";
				for(Tile o : Tiles){
					if(o.getOwnerNumber() == currentPlayer){
						properties += o.getName() + ", ";
					}
				}
				//implement showing all properties owned by player
				infoPanel.append(properties);
				break;
			case "pay rent":
				//implement paying of rent of unmortaged properties to property owner
				infoPanel.append("pay rent condition, but no method yet");
				break;
			case "done":
				if(rollTurns>0) {
					done();
				}
				else {
					infoPanel.append("\nError: Must roll before turn can end\n");
				}
				break;
			case "quit":
				quitGame();
				infoPanel.append("quit condition, method not written");
				break;
			default:
				infoPanel.append("\nError: Invalid command\n");
				break;
			
			}
			
			infoPanel.append("\n" + playerName.getName() + " :");  //Asks the next player for input
		}


		//Idea for a popup to appear on the screen containing tile information for whatever tile mouse is on
		for(Tile o : Tiles) { //figure out what tile the mouse is on
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

	private void quitGame() {
		// TODO Auto-generated method stub
		
		//balances into array and sort array(only ascending with API - descending with custom implementation) and reverse take. 
		//how to get player num in order as well.
		
	}
	
	
	private void done() {
		if (currentPlayer >= numberOfPlayers) { //If every player has had a turn, resets to player 1
			currentPlayer = 1;
			playerName = Players.get(currentPlayer-1);
		} else { //Moves on to the next player
			currentPlayer++;
			playerName = Players.get(currentPlayer-1);
		}
		rollTurns = 0;
		rollAgain = false;
	}



	//This method moves the players around the board based on player x/y position and value of the dice. 
	//game breaks when command panel gets set to null after reading roll to allow player a choice in between double roll moving.
	private void movePlayer() {

		for(int i = 1; i <= dice.getValue(); i++) {
			//While on the bottom squares, players move to the left
			if(Players.get(currentPlayer-1).currentTile <= 9) { 
				Players.get(currentPlayer-1).xPosition -= TILESIZE;
				Players.get(currentPlayer-1).currentTile++;
			}
			//While along the left side of the board, players move upwards
			else if(Players.get(currentPlayer-1).currentTile > 9 && Players.get(currentPlayer-1).currentTile <= 19) {  
				Players.get(currentPlayer-1).yPosition -= TILESIZE;
				Players.get(currentPlayer-1).currentTile++;
			}
			//While along the top squares, players move to the right
			else if(Players.get(currentPlayer-1).currentTile > 19 && Players.get(currentPlayer-1).currentTile <= 29) {  
				Players.get(currentPlayer-1).xPosition += TILESIZE;
				Players.get(currentPlayer-1).currentTile++;
			}
			//While along the left side of the board, players move downwards
			else if(Players.get(currentPlayer-1).currentTile > 29 && Players.get(currentPlayer-1).currentTile <= 39) { 
				Players.get(currentPlayer-1).yPosition += TILESIZE;
				Players.get(currentPlayer-1).currentTile++;

				//Resets their current tile number to zero when they reach the "go" square
				if(Players.get(currentPlayer-1).currentTile >= Tiles.size()) {  
					Players.get(currentPlayer-1).currentTile = 0;

					//Pass go, collect 200
					Players.get(currentPlayer-1).deposit(200);
				}
			}
		}
		rollAgain = false;

		infoPanel.append(Players.get(currentPlayer-1).getName() + " rolled " + dice.getDice1() + " and " + dice.getDice2() + ". Moved " + dice.getValue() + " squares\n"); //Says how many squares a player has moved

		if (dice.checkDouble() && doubleCount < 3) {
			infoPanel.append("Doubles! Roll again!\n"); //add text to info pane
			doubleCount++;
			rollAgain = true;
			rollTurns++;
		}
		else {
			rollTurns = 1;
			rollAgain = false;
			doubleCount = 0;
		}

	}



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