package monopoly;

import java.awt.Color;

public class Player {
	
	Player(int startingTile, int playerNum){
		currentTile = startingTile;
		playerNumber = playerNum;
	}
	
	private Color colour;
	
	public int playerNumber;
	public int currentTile;
	public int xPosition;  //Stores current x position of player
	public int yPosition;  //Stores current y position of player
}
