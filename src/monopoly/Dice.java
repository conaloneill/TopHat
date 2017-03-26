package monopoly;
/*
 * ---Tophat---
 * Brian O'Leary - 13475468
 * Conal O'Neill - 13315756
 * Daniel Graham - 15319536
 * 
 * This Class is used as our Dice Object. We create a dice object in our GameScreen Class and
 * use it to generate random dice rolls. 
 * We use 2 separate dice so that we can check for doubles and also so that the possible values
 * of the dice more realistically represent real dice.
 *
 *  */

import java.util.concurrent.ThreadLocalRandom;

public class Dice {

	public void roll(){
		this.valueDice1 = ThreadLocalRandom.current().nextInt(1, 7);
		this.valueDice2 = ThreadLocalRandom.current().nextInt(1, 7);
	}

	public boolean checkDouble() {
		return valueDice1 == valueDice2;
	}
	public int getValue(){
		return this.valueDice1 + this.valueDice2 ;
	}
	public int getDice1(){
		return this.valueDice1;
	}
	public int getDice2(){
		return this.valueDice2;
	}
	
	//Values held by 2 dice
	private int valueDice1;
	private int valueDice2;
}
