package monopoly;

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
