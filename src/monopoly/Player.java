package monopoly;
/*
 *---Tophat---
 * Brian O'Leary - 134775468
 * Conal O'Neill - 13315756
 * Daniel Graham - 15319536
 * 
 * This class is used to create Player objects. Each player currently has its
 * own color, number and x,y position. These are used by our 'RenderPanel' class
 * to draw each player. 
 * In future Sprints, 'Player' objects will contain their own balances, owned properties etc.
 * 
 * */
import java.awt.Color;

public class Player {

	Player(int startingTile, int playerNum){
		currentTile = startingTile;
		playerNumber = playerNum;
	}

	public void setColour(Color color) {
		this.colour = color;
	}

	public Color getColour() {
		return this.colour;
	}

	private Color colour;

	public int playerNumber;
	public int currentTile;
	public int xPosition;  //Stores current x position
	public int yPosition;  //Stores current y position
}
