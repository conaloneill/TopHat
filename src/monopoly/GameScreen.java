package monopoly;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameScreen extends JFrame implements ActionListener, MouseMotionListener{
	public Timer timer;
	public JFrame frame;
	public JTextArea infoPanel, commandPanel;
	public JButton enter;
	public int ticks, tileIndex =0, mouseX, mouseY, currentTile,
			numberOfPlayers, maxNumberOfPlayers = 6, minNumberOfPlayers = 2,count=1;
	public static final int  TILESIZE = 64, S_WIDTH = 1300, BOARD_WIDTH = TILESIZE*11, BOARD_HEIGHT = TILESIZE*11;
	public Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();//size of computer screen
	public RenderPanel boardGraphics = new RenderPanel();
	public static GameScreen screen;
	public boolean mouseIsOnATile = false, playerNumberCheck = false;
	public ArrayList<Tile> Tiles = new ArrayList<Tile>();
	public ArrayList<Player> Players = new ArrayList<Player>();

	GameScreen(){
		init();
		timer = new Timer(20, this);//Params are delay and actionListener
		
		frame = new JFrame();
		frame.setSize(S_WIDTH,BOARD_HEIGHT);
		frame.setResizable(false);
		frame.setLocation(dim.width/2 - frame.getWidth()/2, dim.height/2 - frame.getHeight()/2 - 30);
		
		addComponentsToPane(frame.getContentPane());
		
		frame.pack(); //shrinks size to wrap layout
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseMotionListener(this); //Listener for Mouse position
		frame.setVisible(true);
		
		ticks = 0;
		timer.start();
	}
	private void addComponentsToPane(Container pane) {//Adds Components to screen
		pane.setLayout(new BorderLayout());
		
		boardGraphics.setPreferredSize(new Dimension(BOARD_WIDTH+TILESIZE/2+1, BOARD_HEIGHT+1));
		pane.add(boardGraphics, BorderLayout.LINE_START);
		
		Container INFOAREA = new Container();//Container to hold text boxes and button (everything on the right)
		INFOAREA.setPreferredSize(new Dimension(S_WIDTH - (BOARD_WIDTH+TILESIZE/2+1), BOARD_HEIGHT+1));
		INFOAREA.setLayout(new BorderLayout());
		
		infoPanel = new JTextArea(37,5);//Parameters are rows and columns
		infoPanel.setText("INFO PANEL\nPlayer 1:");
		infoPanel.setEditable(false);
		
		//lines now wrap to next line so only vertical scrolling needed
		infoPanel.setLineWrap(true);
		infoPanel.setWrapStyleWord(true);
		
		infoPanel.setBackground(boardGraphics.insideGreen);

		//adding a JScrollPane to the info panel to allow it to vertically scroll through all the commands
		JScrollPane infoScrollPane = new JScrollPane(infoPanel);
		infoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		INFOAREA.add(infoScrollPane, BorderLayout.NORTH);
		
	    //commandPanel set at JTextArea so line can wrap around and can be made scrollable vertically
		commandPanel = new JTextArea("COMMAND PANEL", 5,3);
		commandPanel.setLineWrap(true);
		commandPanel.setWrapStyleWord(true);

		//adding a JScrollPane to the command panel to allow it to vertically scroll through the new command
		JScrollPane commandScrollPane = new JScrollPane(commandPanel);
		commandScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		
		
		INFOAREA.add(commandScrollPane, BorderLayout.CENTER);


		enter = new JButton("Enter");
		enter.setPreferredSize(new Dimension((S_WIDTH - (BOARD_WIDTH+TILESIZE/2+1))/2,50));
		enter.addActionListener(this);
		enter.setActionCommand("ENTER");//Name of action
		INFOAREA.add(enter, BorderLayout.LINE_END);
		
		pane.add(INFOAREA, BorderLayout.LINE_END);
	}
	private void init() {  //Called once on create. Used to setup game
	
		//Get number of Players
		while(!playerNumberCheck){
			String n = JOptionPane.showInputDialog("Enter Number of Players (2-6)");
			numberOfPlayers = Integer.parseInt(n);
			
			if(numberOfPlayers >= minNumberOfPlayers && numberOfPlayers <= maxNumberOfPlayers){
				playerNumberCheck = true;
			}
		}
		//Create Players in Player ArrayList
		for(int i = 0;i<numberOfPlayers;i++){
			Players.add(new Player(0, i));
		}
		
		//Sets the starting position for each individual player
		Players.get(0).xPosition=650;
		Players.get(0).yPosition=645;
		
		Players.get(1).xPosition=675;
		Players.get(1).yPosition=645;
		
		if(numberOfPlayers>=3){
			Players.get(2).xPosition=650;
			Players.get(2).yPosition=665;
		}
		
		if(numberOfPlayers>=4){
		Players.get(3).xPosition=675;
		Players.get(3).yPosition=665;
		}
		
		if(numberOfPlayers>=5){
		Players.get(4).xPosition=650;
		Players.get(4).yPosition=685;
		}
		
		if(numberOfPlayers>=6){
		Players.get(5).xPosition=675;
		Players.get(5).yPosition=685;
		}
				
		
		//Loops to create Tiles in correct order
		int x = BOARD_WIDTH - TILESIZE/2;
		int y = BOARD_HEIGHT - TILESIZE/2;
		for(int row = 0;row<11;row++){//BOTTOM ROW
			Tiles.add(new Tile(row, 0,100,x,y));
			x-= TILESIZE;
		}
		x+=TILESIZE;
		y-= TILESIZE;
		for(int col = 0;col<9;col++){//LEFT COL
			Tiles.add(new Tile(col + 11, 0,100,x,y));
			y-= TILESIZE;
		}
		for(int row = 0;row<11;row++){//TOP ROW
			Tiles.add(new Tile(row + 20, 0,100,x,y));
			x+= TILESIZE;
		}
		x-=TILESIZE;
		y+= TILESIZE;
		for(int col = 0;col<9;col++){//RIGHT COL
			Tiles.add(new Tile(col + 31, 0,100,x,y));
			y+= TILESIZE;
		}
		
		//Loop to setup Tile images should be here
		for(Tile o : Tiles){
			
		}
		
	}

	
	public static void main(String[] args) {
		screen = new GameScreen();
	}

	@Override
	public void actionPerformed(ActionEvent e) {  //MAIN LOOP, gets called when timer ticks
		ticks++;
		boardGraphics.repaint();//redraw board every frame
		
		//		cant put mouse position in command panel if using command panel to input textual commands
		//		//Keep mouse position in the command panel
		//		String mousePos = mouseX + ", " + mouseY;
		//		commandPanel.setText(mousePos);
		
		//If button is pushed, add command panel text to the info panel
		
		if ("ENTER".equals(e.getActionCommand())){
			String s = commandPanel.getText();
			String temp = infoPanel.getText();
			infoPanel.setText(temp + "\n" + s);
			if(s.equals("roll")){   //Moves the players when the command "roll" is entered. Starts with Player 1 and then cycles through the other players in order
				int dice = ThreadLocalRandom.current().nextInt(2, 13);  //Generates a random number between 2 and 12,then moves the player that many squares
				int a=1;  //"a" keeps track of how many squares left to move
				while(a<=dice){  //This loop moves the players around the board
				if(Players.get(count-1).currentTile<=9){ //While on the bottom squares, players move to the left
				Players.get(count-1).xPosition=Players.get(count-1).xPosition-64;
				Players.get(count-1).currentTile++;
				}
				else if(Players.get(count-1).currentTile>9 && Players.get(count-1).currentTile<=19){  //While along the left side of the board, players move upwards
					Players.get(count-1).yPosition=Players.get(count-1).yPosition-64;
					Players.get(count-1).currentTile++;
				}
				else if(Players.get(count-1).currentTile>19 && Players.get(count-1).currentTile<=29){  //While along the top squares, players move to the right
					Players.get(count-1).xPosition=Players.get(count-1).xPosition+64;
					Players.get(count-1).currentTile++;
				}
				else if(Players.get(count-1).currentTile>29 && Players.get(count-1).currentTile<=39){  //While along the left side of the board, players move downwards
					Players.get(count-1).yPosition=Players.get(count-1).yPosition+64;
					Players.get(count-1).currentTile++;
					if(Players.get(count-1).currentTile>=Tiles.size()){  //Resets their current tile number to zero when they reach go
						Players.get(count-1).currentTile=0;
					}
				}
				a++;
			}
			infoPanel.append("\nPlayer "+count+  " moved "+dice+" squares\n");  //Says how many squares a player has moved
			
			if(count>=numberOfPlayers){  //If every player has had a turn, resets to player 1
				count=1;
			}
			else{  //Moves on to the next player
			  count++;
			}
			
		}
			
			infoPanel.append("\nPlayer "+count+" :");  //Asks the next player for input
		}
		
		//Idea for a popup to appear on the screen containing tile information for whatever tile mouse is on
		for(Tile o : Tiles){ //figure out what tile the mouse is on
			if(mouseX > o.x - TILESIZE/2 && mouseX < o.x + TILESIZE/2 && 
			   mouseY > o.y - TILESIZE/2 && mouseY < o.y + TILESIZE/2){
				mouseIsOnATile = true;
				currentTile =  o.getTileNum();
			}
		}
		//If mouse is in the center of the board ( not a tile )
		if(mouseX > BOARD_WIDTH - TILESIZE*10 && 
				mouseX <  BOARD_WIDTH - TILESIZE &&
				mouseY > BOARD_HEIGHT - TILESIZE*10 &&//BOARD_HEIGHT - TILESIZE*10 + TILESIZE/2 + TILESIZE/2 &&
				mouseY < BOARD_HEIGHT - TILESIZE ||
				//or off the board
				mouseX > BOARD_WIDTH - 10){
			mouseIsOnATile = false;
			currentTile = 100;
		}
		//Moves a token around the board
		/*if(ticks%10==0){//Every 10 ticks...
			if(tileIndex>=Tiles.size()-1){
				Tiles.get(tileIndex).hasPlayer = false;
				tileIndex = 0;
			}
			if(tileIndex ==0){
				Tiles.get(tileIndex).hasPlayer = true;
				tileIndex++;
			}
			else{
				Tiles.get(0).hasPlayer = false;
				Tiles.get(tileIndex).hasPlayer = false;
				tileIndex++;
				Tiles.get(tileIndex).hasPlayer = true;
			}	
		}*/
	}
	@Override
	public void mouseDragged(MouseEvent m) {
	}
	@Override
	public void mouseMoved(MouseEvent m) {//When mouse is moved
		mouseX = m.getX() - 2;//Get mouse Location
		mouseY = m.getY() - 25;
	}
	
}