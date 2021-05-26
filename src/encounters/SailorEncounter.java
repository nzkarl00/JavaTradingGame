package encounters;

import game.Player;

/**
 * Sailor encounter, this encounter simply gives the player a monetary reward.
 */

public class SailorEncounter extends EncounterEvent {

	public SailorEncounter() {
		super();
	}
	
	/**
	 * Gives the player money for rescuing sailors.
	 */
	@Override
	public String StartEncounter(Player player, GameEventNotifier notifer) { 
		player.transferMoney(40);
		return ("You find sailors, and rescue them. \nThey give you $40 as a reward.\n");
	}

}
