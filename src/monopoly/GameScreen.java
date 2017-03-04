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
import java.util.Collections;
import java.util.Comparator;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.text.DefaultCaret;

import propertyImages.PropertyImages;
import monopoly.RenderPanel;

@SuppressWarnings("serial")
public class GameScreen extends JFrame implements ActionListener, MouseMotionListener, KeyListener {

	private Timer timer;
	private JFrame frame;
	private JTextArea infoPanel, commandPanel;
	private JButton enter;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();//size of computer screen

	private Dice dice = new Dice();
	private RenderPanel boardGraphics = new RenderPanel();
	private Player currentPlayer;
	public static GameScreen screen;

	public int mouseX, mouseY, currentTile, numberOfPlayers = 0;
	private int ticks, firstTurn = 0, currentPlayerNumber, doubleCount=0 , rollTurns = 0;
	public static final int  STARTINGBAL = 1500, MINPLAYERS = 2, MAXPLAYERS = 6, TILESIZE = 64, S_WIDTH = 1300, BOARD_WIDTH = TILESIZE*11;

	public boolean mouseIsOnATile = false, playerNumberCheck = false, rollAgain = true, gameOver = false;

	public ArrayList<Tile> Tiles = new ArrayList<Tile>();
	public ArrayList<Player> Players = new ArrayList<Player>();

	private String choice; //contains user's input throughout the game 
	private String helpString = "type command on your turn to play the game. (commands are not case-senstive)\n"
			+ "-help : gives list of all available commands \n"
			+ "-roll : rolls both dice and moves player around the board \n"
			+ "-buy : allows player to buy the property they are on if it can be bought \n"
			+ "-build <short name> <number> : build houses on the property using short name and number to build \n"
			+ "-pay rent : allows player to pay owed rent to the owner of the property they are on \n"
			+ "-mortgage <short name> : allows player to mortgage a property to the bank for the amount listed on it's card \n"
			+ "-redeem <short name> : allows player to buy back a mortgaged property from the bank for the mortgage value + 10%"
			+ "-property : shows a list of the all the properties owned by the player \n"
			+ "-balance : shows the bank balance of the player \n"
			+ "-done : ends the players turn and allows the next player to start their turn \n"
			+ "-quit : ends the game and display the winner\n";
	private String propertyName;
	private int numBuildings;


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
			String n = JOptionPane.showInputDialog("Enter Number of Players (2-6)");

			//Check if int. Else throw error and try again
			try {
				numberOfPlayers = Integer.parseInt(n);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.out.println("Error. Number expected");
			}

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
			if (!checkGameOver() && !gameOver) {
				//Check user input and carry out command entered
				userInput();
			}
			//If Game has ended exit the program on user command
			else {
				infoPanel.append("\nError cant continue as Game is Over!\nEnter \"exit\" to end the program\n\n");
				String choiceString = commandPanel.getText().trim().toLowerCase();

				switch (choiceString) {
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

	//method to check the game is over by checking if only 1 player left in the player 
	//array or user enter's "quit". This also prints the winner.
	private boolean checkGameOver() {
		if (Players.size() <= 1 || choice == "quit") {
			infoPanel.append("Game Over! Only one player remaining.\n Winner is " + Players.get(Players.size()-1).getName()
					+ " with assets worth " + Players.get(Players.size()-1).getAssetValue());
			return true;
		}
		return false;
	}


	//method for all user inputs on a players turn. gets the input and calls methods to implement choice
	private void userInput() {
		//Set currentPlayer based on currentPlayerNumber
		currentPlayer = Players.get(currentPlayerNumber-1);
		//Get user input
		choice = commandPanel.getText().trim().toLowerCase();
		//Remove text from command panel
		commandPanel.setText(null);
		//Add text to info panel
		infoPanel.append(choice + "\n"); 


		//switch case needs just build or demolish in the string to activate
		//this if extracts the property name and number of buildings and sets choice to be build/demolish
		if (choice.startsWith("build") || choice.startsWith("demolish")) {
			String choiceCopy = choice;
			numBuildings = Integer.parseInt(choiceCopy.replaceAll("\\D+",""));
			choiceCopy = choiceCopy.replace("build ", "");
			choiceCopy = choiceCopy.replace("demolish ", "");
			choiceCopy = choiceCopy.replaceAll("\\d","").trim();

			for (Tile tile : Tiles) {
				if (choiceCopy.equals(tile.getShortName())){
					propertyName = tile.getShortName();
				}
			}

			if (choice.startsWith("build")) {
				choice = "build";
			}
			else {
				choice = "demolish";
			}
		}

		//switch case just needs mortgage to activate
		//this if statement extracts the name of the property to be mortgaged
		if (choice.startsWith("mortgage") || choice.startsWith("redeem")) {
			String choiceCopy = choice;
			boolean found = false;
			choiceCopy = choiceCopy.replace("mortgage", "").trim();
			choiceCopy = choiceCopy.replace("redeem", "").trim();

			for (Tile tile : Tiles) {
				if (choiceCopy.equals(tile.getShortName())){
					found = true;
					propertyName = tile.getShortName();
				}
			}
			if (found == false) {
				propertyName=null;
				infoPanel.append("\nError invalid property name");
			}
			if (choice.startsWith("mortgage")) {
				choice = "mortgage";
			}
			else {
				choice = "redeem";
			}
		}

		//Check user if input is one of our defined commands
		switch(choice) {
		case "help":
			infoPanel.append(helpString);
			break;

			//case below rolls the dice and calls move player method. Checks for any rent owed and assigns that to player
		case "roll":
			//Re-roll the dice to get new values
			dice.roll();

			//Check if player has rolled doubles less than 3 times but is still allowed roll again
			if (rollTurns < 3 && rollTurns >= 0 && rollAgain) {
				//Move currentPlayer the value of the dice
				movePlayer();
				//Info about Tile player landed on:
				infoPanel.append("\n" + currentPlayer.getName() + " landed on " + Tiles.get(currentPlayer.currentTile).getName());
				//If property can be bought
				if(Tiles.get(currentPlayer.currentTile).getPrice() > 0 && Tiles.get(currentPlayer.currentTile).getOwnerNumber() == -1 && Tiles.get(currentPlayer.currentTile).getType() != PropertyImages.TYPE_TAX){
					infoPanel.append("\nThis property may be bought for " + Tiles.get(currentPlayer.currentTile).getPrice() + ".");
				}
				//If Tile landed on is owned
				else if(Tiles.get(currentPlayer.currentTile).getOwnerNumber() != -1){
					//Check for Tile isn't mortgaged
					if(!Tiles.get(currentPlayer.currentTile).checkMortgaged()){
						//Set player debt amount to rent of tile
						currentPlayer.setDebt(Tiles.get(currentPlayer.currentTile).getRent());
						//Set which player is owed money
						currentPlayer.setPlayerOwed(Tiles.get(currentPlayer.currentTile).getOwnerNumber());
						//Tell player money is owed
						infoPanel.append("\n" + currentPlayer.getName() + " owes " + Players.get(Tiles.get(currentPlayer.currentTile).getOwnerNumber()).getName() + " " + Tiles.get(currentPlayer.currentTile).getRent() + ".");
					}
					//Unreachable code as mortgages not implemented in this sprint
					else{
						infoPanel.append("\nProperty is mortgaged");
					}
				}
			}
			//Player has already rolled and didn't get doubles
			else {
				infoPanel.append("Error you cant roll again this turn. Please end turn with 'done'\nor type 'help' for the other options");
			}
			break;

		case "balance": 
			//Returns the players current balance
			infoPanel.append("Player " + currentPlayer.getName() + " has a balance of: " + currentPlayer.getBalance());
			break;

		case "buy":
			//Checks to make sure the player has rolled before allowing them to buy a property
			if(rollTurns==0) {  
				infoPanel.append("Error you must roll before you can buy a property");	
			}
			else {
				//calls buy method to buy tile
				infoPanel.append(buy());
			}
			break;

		case "property":
			//Prints properties owned by currentPlayer
			propertiesOwnedBycurrentPlayerNumber();
			break;

		case "pay rent":
			//Check if player has any rent due
			if(currentPlayer.getDebt() > 0){
				//Check if player has enough money
				//if(currentPlayer.getBalance() >= currentPlayer.getDebt()){
				payRent();
				//}
				//Unable to pay debt. Not enough money.
				//else{
				//	infoPanel.append("Unable to pay debt. Not enough money.");
				//}
			}
			//No rent due
			else{
				infoPanel.append("No rent is owed.");
			}

			break;

		case "done":
			//Check if player must roll again this turn
			if(rollTurns>0 && doubleCount == 0 && !rollAgain) {

				//Check if player still owes money
				if(currentPlayer.getDebt() == 0){

					//Check if player has negative balance and remove them
					if(currentPlayer.getBalance() < 0){
						//returns all properties to bank for free as no auctions in this Sprint
						setPropertyUnowned();
						Players.remove(currentPlayerNumber-1);

						infoPanel.append(currentPlayer.getName() + " has a negative balance and cant continue the game!\nAll properties will be given back to bank.\n");

						//Decrement player number to account for deletion of player
						if(currentPlayerNumber-1 > 0){
							currentPlayerNumber--;
						}else{
							currentPlayerNumber = Players.size();
						}
					}
					//Move on to next player
					done();
					//Check if only one Player left
					if (Players.size() <= 1){
						quitGame();
					}
				}
				//Debt hasn't been paid
				else{
					infoPanel.append("You must pay your rent before ending your turn. Use the \"pay rent\" command");
				}
			}
			else {
				infoPanel.append("\nError: Must roll before turn can end\n");
			}
			break;

		case "quit":
			quitGame();
			break;

			//build houses
		case "build":
			build(numBuildings, propertyName);
			break;

			//demolish houses
		case "demolish":
			demolish(numBuildings, propertyName);
			break;

			//mortgage an owned property
		case "mortgage":
			mortgage(propertyName);
			break;

			//buy back a mortgaged property
		case "redeem":
			redeem(propertyName);
			break;


		default:
			infoPanel.append("Error: Invalid command");
			break;
		}

		//Asks the next player for input
		infoPanel.append("\n" + currentPlayer.getName() + " :");  
	}

	// removes x num of houses on the tile given by short name
	private void demolish(int num, String name) {
		for (Tile tile : Tiles) {
			if (propertyName.equals(tile.getShortName())) {
				//check player owns property
				if (tile.getOwnerNumber() == currentPlayerNumber-1) {
					//check not trying to demolish more building than exists
					if (numBuildings <= tile.getBuildings()) {
						tile.removeBuildings(num);
						int cost = (tile.getHousePrice()/2)*num;
						Players.get(currentPlayerNumber-1).deposit(cost);
						infoPanel.append("Player " +Players.get(currentPlayerNumber-1).getName() + " removed " + num + " houses from " + tile.getName() +" for a gain of " + cost);
					}
					else {
						infoPanel.append("Error cant demolish more buildings than are built on property!");
					}
				}
				else {
					infoPanel.append("Error get demolish houses on a property you dont own!");
				}
			}
		}

	}

	//builds x num of houses on the tile given by short name
	private void build(int num, String name) {
		for (Tile tile : Tiles) {
			if (propertyName.equals(tile.getShortName())) {
				//check player owns property
				if (tile.getOwnerNumber() == currentPlayerNumber-1) {
					//check if property is currently mortgaged
					if(tile.checkMortgaged()==false) {
						//Check if all color group owned
						if(tile.isAllColourOwned()){
							//checks new amount of buildings plus existing wont be more than allowed
							if ((numBuildings+tile.getBuildings()) <= 5) {
								tile.addBuildings(num);
								int cost = tile.getHousePrice()*num;
								Players.get(currentPlayerNumber-1).spend(cost);
								infoPanel.append("Player " +Players.get(currentPlayerNumber-1).getName() + " put " + num + " houses on " + tile.getName() +" at a cost of " + cost);
							}
							else {
								infoPanel.append("Error max number of houses allowed is 5");
							}
						}
						else {
							infoPanel.append("You must own a full color group before you can build houses.");
						}
					}
					else {
						infoPanel.append("Error can't build houses on mortgaged property");
					}
				}
				else {
					infoPanel.append("Error can't build houses on a property you dont own!");
				}
			}
		}
	}

	private void mortgage(String name) {
		for (Tile tile : Tiles) {
			//Searches for the property with the name the player entered
			if (name.equals(tile.getShortName())) {
				//check player owns property
				if (tile.getOwnerNumber() == currentPlayerNumber-1) {
					//Checks if property has already been mortgaged
					if (tile.checkMortgaged()==false) {
						//Checks if property has buildings on it
						if (tile.getBuildings()==0) {
							tile.setMortgaged(true);
							currentPlayer.deposit(tile.getMortgageValue());
							infoPanel.append("\nPlayer " +Players.get(currentPlayerNumber-1).getName() + " mortgaged " +tile.getName()+" for "+tile.getMortgageValue());
						}
						else {
							infoPanel.append("\nError can't mortgage a property with buildings on it");
						}
					}
					else {
						infoPanel.append("\nError property already mortgaged");
					}
				}
				else {
					infoPanel.append("\nError can't mortgage a property you dont own!");
				}
			}
		}
	}

	private void redeem(String name) {
		for (Tile tile : Tiles) {
			//Searches for the property with the name the player entered
			if (name.equals(tile.getShortName())) {
				//check player owns property
				if (tile.getOwnerNumber() == currentPlayerNumber-1) {
					//Checks if property has already been mortgaged
					if (tile.checkMortgaged()==true) {
						tile.setMortgaged(false);
						currentPlayer.spend((tile.getMortgageValue()/10)*11);
						infoPanel.append("\nPlayer " +Players.get(currentPlayerNumber-1).getName() + " redeemed " +tile.getName()+" for "+(tile.getMortgageValue()/10)*11);
					}
					else {
						infoPanel.append("\nError property isn't mortgaged");
					}
				}
				else {
					infoPanel.append("\nError can't redeem a property you dont own!");
				}
			}
		}
	}


	//Sets all property owned by currentPlayer to have no owner (-1 denotes no owner)
	//Used when a player goes bankrupt
	private void setPropertyUnowned() {
		for (Tile tile : Tiles) {
			if (tile.getOwnerNumber() ==  currentPlayerNumber-1) {
				tile.setOwnerNumber(-1);
			}
		}
	}

	//method transfers money from 1 player to the other
	private void payRent() {
		//Take money from player
		currentPlayer.spend(currentPlayer.getDebt());
		//Give money to player owed
		Players.get(currentPlayer.getPlayerOwed()).deposit(currentPlayer.getDebt());
		String s = currentPlayer.getName() + " payed " + currentPlayer.getDebt() + " to " + Players.get(currentPlayer.getPlayerOwed()).getName() + ".";

		//Set player Debt to 0
		currentPlayer.setDebt(0);
		currentPlayer.setPlayerOwed(-1);

		infoPanel.append(s);
	}

	//Loops through Tiles ArrayList and displays all Tiles owned by currentPlayer
	private void propertiesOwnedBycurrentPlayerNumber() {
		String properties = "Property owned by " + currentPlayer.getName() + " :";
		for(Tile o : Tiles){
			if(o.getOwnerNumber() == currentPlayerNumber-1){
				properties += o.getName() + ", ";
			}
		}
		infoPanel.append(properties);
	}

	private String buy() {
		//If Tile is a property
		Tile currTile = Tiles.get(currentPlayer.currentTile);
		if(currTile.getType() == PropertyImages.TYPE_STATION ||
				currTile.getType() == PropertyImages.TYPE_PROPERTY ||
				currTile.getType() == PropertyImages.TYPE_UTILITY){
			//If Tile is not owned
			if(currTile.getOwnerNumber() == -1){
				//If player has enough money
				if(currentPlayer.getBalance() >= currTile.getPrice()){

					//Player spends price of property
					currentPlayer.spend(currTile.getPrice());
					currTile.setOwnerNumber(currentPlayerNumber -1);

					if(currTile.getType() == PropertyImages.TYPE_STATION) {
						currentPlayer.stationsOwned++;
					}


					if (currTile.getColour().equals("brown") || currTile.getColour().equals("navy")) {
						int numProperties = 2;
						setAllColoursOwned(currTile, numProperties);

					}
					else {
						int numProperties = 3;
						setAllColoursOwned(currTile, numProperties);
					}

					return currentPlayer.getName() + " bought " + currTile.getName() + " for " + currTile.getPrice();

				}
				//Not enough Money
				else{
					return "Unable to buy Tile. Player doesn't have enough money.";
				}
			}
			//Tile is already owned by a player
			else{
				return "Unable to buy Tile. Tile is already owned by a player.";
			}
		}
		//Tile isn't a property
		else{
			return "Unable to buy Tile. Not a property";
		}
	}

	//change to holding record of tile numbers and scrolling array of 2/3 size not arraylist of 40 tiles
	private void setAllColoursOwned(Tile currTile, int numProperties) {
		int i = 0;
		for (Tile tile : Tiles) {
			if (tile.getOwnerNumber() == currentPlayer.playerNumber && tile.getColour().equals(currTile.getColour())) {
				i++; 
				if (i == numProperties) {
					for (Tile tile2 : Tiles) {
						if (tile2.getColour().equals(currTile.getColour())) {
							tile2.setAllColourOwned(true);
						}
					}
				}
			}
		}

	}

	private void quitGame() {

		//Check if more than 1 Player left
		if (Players.size() > 1) {
			//calculate AssetValue for each player
			for (Player player : Players) {
				player.calculateAssetValue(Tiles);
			}

			//Sort Players array based on assetValue property of Player objects
			Collections.sort(Players, new Comparator<Player>() {
				@Override public int compare(Player p1, Player p2) {
					return p1.getAssetValue() - p2.getAssetValue(); // Ascending order
				}

			});
		}else{
			//If only 1 Player, Calculate Players assets
			Players.get(Players.size()-1).calculateAssetValue(Tiles);
		}

		//Sort is in ascending order
		Player winner = Players.get(Players.size()-1);

		infoPanel.append("Winner is Player " + winner.getName() + " with a total of " + winner.getAssetValue() + " in assets!");
		infoPanel.append("\nEnter \"exit\" to end the program\n");
		gameOver = true;
	}

	//ends players turn and increments counter to the next player
	private void done() {
		if (currentPlayerNumber >= Players.size()) { //If every player has had a turn, resets to player 1
			currentPlayerNumber = 1;
			currentPlayer = Players.get(currentPlayerNumber-1);
		} else { //Moves on to the next player
			currentPlayerNumber++;
			currentPlayer = Players.get(currentPlayerNumber-1);
		}
		rollTurns = 0;
		rollAgain = true;
		doubleCount = 0;
	}

	//This method moves the players around the board based on player x/y position and value of the dice. 
	private void movePlayer() {

		for(int i = 1; i <= dice.getValue(); i++) {
			//While on the bottom squares, players move to the left
			if(Players.get(currentPlayerNumber-1).currentTile <= 9) { 
				Players.get(currentPlayerNumber-1).xPosition -= TILESIZE;
				Players.get(currentPlayerNumber-1).currentTile++;
			}
			//While along the left side of the board, players move upwards
			else if(Players.get(currentPlayerNumber-1).currentTile > 9 && Players.get(currentPlayerNumber-1).currentTile <= 19) {  
				Players.get(currentPlayerNumber-1).yPosition -= TILESIZE;
				Players.get(currentPlayerNumber-1).currentTile++;
			}
			//While along the top squares, players move to the right
			else if(Players.get(currentPlayerNumber-1).currentTile > 19 && Players.get(currentPlayerNumber-1).currentTile <= 29) {  
				Players.get(currentPlayerNumber-1).xPosition += TILESIZE;
				Players.get(currentPlayerNumber-1).currentTile++;
			}
			//While along the left side of the board, players move downwards
			else if(Players.get(currentPlayerNumber-1).currentTile > 29 && Players.get(currentPlayerNumber-1).currentTile <= 39) { 
				Players.get(currentPlayerNumber-1).yPosition += TILESIZE;
				Players.get(currentPlayerNumber-1).currentTile++;

				//Resets their current tile number to zero when they reach the "go" square
				if(Players.get(currentPlayerNumber-1).currentTile >= Tiles.size()) {  
					Players.get(currentPlayerNumber-1).currentTile = 0;

					//Pass go, collect 200
					infoPanel.append("Player " + currentPlayer.getName() + " passed Go and received 200!\n");
					Players.get(currentPlayerNumber-1).deposit(200);
				}
			}
		}
		rollAgain = false;

		infoPanel.append(Players.get(currentPlayerNumber-1).getName() + " rolled " + dice.getDice1() + " and " + dice.getDice2() + ". Moved " + dice.getValue() + " squares"); //Says how many squares a player has moved

		//Checks to keep track how many times Player rolls doubles
		if (dice.checkDouble() && doubleCount < 4) {
			infoPanel.append("\nDoubles! Roll again!"); //add text to info pane
			doubleCount++;
			rollAgain = true;
			rollTurns++;
		} 
		else {
			rollTurns = 1;
			rollAgain = false;
			doubleCount = 0;
		}


		if (doubleCount == 3) {
			rollAgain = false;
			rollTurns = 1;
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