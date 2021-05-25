package encounters;

import game.Player;
import game.UI;

/**
 * Class for weather encounter event.
 * if bad weather is met, player's ship takes damage
 * once landed at island, have to pay money to make up for repair costs
 * */

public class WeatherEncounter extends EncounterEvent {
	
	public WeatherEncounter() {
		super();
	}

	@Override
	public String StartEncounter(Player player, UI ui, GameEventNotifier notifier) {
		player.getShip().setDamageState(true);
		return ("You run into some nasty weather. \nYour ship has taken damage and will need to be repaired.\n");
	}
	

}
