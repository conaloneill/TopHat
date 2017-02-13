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
 * */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;

import propertyImages.PropertyImages;

@SuppressWarnings("serial")
public class GameScreen extends JFrame implements ActionListener, KeyListener{
	public Timer timer;
	public JFrame frame;
	public JTextArea infoPanel, commandPanel;
	public JButton enter;
	public PropertyImages propertyCards = new PropertyImages();
	public int ticks, currentTile, numberOfPlayers, maxNumberOfPlayers = 6, minNumberOfPlayers = 2,currentPlayer=0;
	public static final int  TILESIZE = 64, S_WIDTH = 1300, BOARD_WIDTH = TILESIZE*11;
	public Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();//size of computer screen
	public RenderPanel boardGraphics = new RenderPanel();
	public static GameScreen screen;
	public boolean playerNumberCheck = false;
	public ArrayList<Tile> Tiles = new ArrayList<Tile>();
	public ArrayList<Player> Players = new ArrayList<Player>();
	private boolean firstTime = true;

	GameScreen(){
		init();
		timer = new Timer(20, this);//Params are delay and actionListener

		frame = new JFrame();
		frame.setSize(S_WIDTH,BOARD_WIDTH);
		frame.setResizable(false);
		//Set location of the JFrame to the center of the users screen.
		frame.setLocation(dim.width/2 - frame.getWidth()/2, dim.height/2 - frame.getHeight()/2 - 30);

		addComponentsToPane(frame.getContentPane());

		frame.pack(); //Shrinks size to wrap layout
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);
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
		infoPanel.setText("INFO PANEL\n\n");
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
			Players.add(new Player(0, i+1));
		}

		//Sets the starting position and colour for each individual player
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

		//Only happens on first call of this method to have board drawn before players move
		if (firstTime ) {	
			//Draw board before starting to move players
			boardGraphics.repaint();
			firstTime = false;
		}

		//If button is pushed, add command panel text to the info panel
		if ("ENTER".equals(e.getActionCommand())){
			infoPanel.append(commandPanel.getText()+"\n"); //add text to info panel
			commandPanel.setText(null);
		}

		//Move the player token one square every 15 ticks
		//Keeps track of which Tile each Player is on for use in future Sprints
		if(ticks%15==0){  		
			//While on the bottom squares, players move to the left
			if(Players.get(currentPlayer).currentTile<=9){ 
				Players.get(currentPlayer).xPosition -= TILESIZE;
				Players.get(currentPlayer).currentTile++;
			}
			//While along the left side of the board, players move upwards
			else if(Players.get(currentPlayer).currentTile>9 && Players.get(currentPlayer).currentTile<=19){  
				Players.get(currentPlayer).yPosition -= TILESIZE;
				Players.get(currentPlayer).currentTile++;
			}
			//While along the top squares, players move to the right
			else if(Players.get(currentPlayer).currentTile>19 && Players.get(currentPlayer).currentTile<=29){  
				Players.get(currentPlayer).xPosition += TILESIZE;
				Players.get(currentPlayer).currentTile++;
			}
			//While along the left side of the board, players move downwards
			else if(Players.get(currentPlayer).currentTile>29 && Players.get(currentPlayer).currentTile<=39){  
				Players.get(currentPlayer).yPosition += TILESIZE;
				Players.get(currentPlayer).currentTile++;
				
				//Resets their current tile number to zero when they reach the "go" square
				if(Players.get(currentPlayer).currentTile>=Tiles.size()){  
					Players.get(currentPlayer).currentTile=0;
					currentPlayer++;
					
					//After the last player has moved, cycles back to the first player
					if(currentPlayer>=numberOfPlayers){  
						currentPlayer=0;
					}
				}
			}
			//Redraw board every time a player is moved. (Every 15 ticks)
			boardGraphics.repaint();
		}
	}
	//Meant to enable pressing the space key as a button click
	@Override
	public void keyPressed(KeyEvent key) {
		//If 'SPACE' is pressed, click the button
		if(key.getKeyCode() == KeyEvent.VK_SPACE){
			enter.doClick();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}