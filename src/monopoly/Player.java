package monopoly;

public class Player {
	
	Player(int startingTile, int playerNum){
		currentTile = startingTile;
		playerNumber = playerNum;
	}
	
	public int playerNumber;
	private int currentTile;
}
