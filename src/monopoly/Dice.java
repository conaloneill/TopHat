package monopoly;

import java.util.concurrent.ThreadLocalRandom;

public class Dice {

	public void Roll(){
		this.value = ThreadLocalRandom.current().nextInt(1, 7) + ThreadLocalRandom.current().nextInt(1, 7);
	}
	
	public int getValue(){
		return this.value;
	}
	private int value;
}
