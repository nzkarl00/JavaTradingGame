package encounters;
import java.util.Random;
import game.Player;

import game.UI;

public class EncounterEvent {

	public EncounterEvent() {
	}

	public void StartEncounter(Player player, UI ui) {
	}

	public float getRandomFloatInRange(float min, float max) {
		Random rand = new Random();
		float randNum = rand.nextFloat(); //returns float from 0 to 1
		//at 0 will return min, at 1 will return max
		return min + randNum * max;
	}

}
