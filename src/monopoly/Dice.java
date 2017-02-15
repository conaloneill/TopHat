package monopoly;

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
	
	
	private int valueDice1;
	private int valueDice2;
}
