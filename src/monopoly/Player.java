package monopoly;

import java.awt.Color;

public class Player {
	
	Player(int startingTile, int playerNum){
		currentTile = startingTile;
		playerNumber = playerNum;
	}
	
	private Color colour;
	
	
	
	public int playerNumber;
	private int currentTile;
}
