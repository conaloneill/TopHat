package monopoly;

public class Tile {
	public Tile(int numberl, int type, int rent,int posx, int posy){
		number = numberl;
		Type = type;
		Rent = rent;
		hasPlayer = false;
		x=posx;
		y=posy;
	}
	
	public boolean hasPlayer;
	private int Type;
	private int Rent;
	public int number;
	public int x;
	public int y;
}
