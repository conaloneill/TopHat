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
			numberOfPlayers, maxNumberOfPlayers = 6, minNumberOfPlayers = 2;
	public static final int  TILESIZE = 60, S_WIDTH = 1100, BOARD_WIDTH = TILESIZE*11, BOARD_HEIGHT = TILESIZE*11;
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
		
		boardGraphics.setPreferredSize(new Dimension(BOARD_WIDTH+1, BOARD_HEIGHT+1));
		pane.add(boardGraphics, BorderLayout.LINE_START);
		
		Container INFOAREA = new Container();//Container to hold text boxes and button (everything on the right)
		INFOAREA.setPreferredSize(new Dimension(S_WIDTH - (BOARD_WIDTH+TILESIZE/2+1), BOARD_HEIGHT+1));
		INFOAREA.setLayout(new BorderLayout());
		
		infoPanel = new JTextArea(37,5);//Parameters are rows and columns
		infoPanel.setText("INFO PANEL");
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
	}
	public static void main(String[] args) {
		screen = new GameScreen();
	}

	@Override
	public void actionPerformed(ActionEvent e) {  //MAIN LOOP, gets called when timer ticks
		ticks++;
		
		
		//Keep mouse position in the command panel
		String mousePos = mouseX + ", " + mouseY;
		//commandPanel.setText(mousePos);
		
		//If button is pushed, add command panel text to the info panel
		if ("ENTER".equals(e.getActionCommand())){
			String s = commandPanel.getText();
			String temp = infoPanel.getText();
			infoPanel.setText(temp + "\n" + s);
		}
		
		//Idea for a popup to appear on the screen containing tile information for whatever tile mouse is on
		for(Tile o : Tiles){ //figure out what tile the mouse is on
			if(mouseX > o.x - TILESIZE/2 && mouseX < o.x + TILESIZE/2 && 
			   mouseY > o.y - TILESIZE/2 && mouseY < o.y + TILESIZE/2){
				mouseIsOnATile = true;
				currentTile =  o.number;
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
		}
		//Moves a token around the board
		if(ticks%10==0){//Every 10 ticks...
			
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
			
			boardGraphics.repaint();//redraw board
		}
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
