package encounters;

import game.Player;

/**
 * Class for weather encounter event.
 * If bad weather is met, player's ship takes damage.
 * Once landed at island, have to pay money to make up for repair costs.
 * */

public class WeatherEncounter extends EncounterEvent {
	
	public WeatherEncounter() {
		super();
	}

	/**
	 * Damages the player's ship and returns a string to notify the player it has occurred.
	 */
	@Override
	public String StartEncounter(Player player, GameEventNotifier notifier) {
		player.getShip().setDamageState(true);
		return ("You run into some nasty weather. \nYour ship has taken damage and will need to be repaired.\n");
	}
	

}
