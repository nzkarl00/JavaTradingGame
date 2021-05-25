package encounters;

import game.Player;
import game.UI;

public class SailorEncounter extends EncounterEvent {

	public SailorEncounter() {
		super();
	}

	@Override
	public String StartEncounter(Player player, UI ui, GameEventNotifier notifer) { 
		player.transferMoney(40);
		return ("You find sailors, and rescue them. \nThey give you $40 as a reward.\n");
	}

}
