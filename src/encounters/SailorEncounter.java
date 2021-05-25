package encounters;

import game.Player;
import game.UI;

public class SailorEncounter extends EncounterEvent {

	public SailorEncounter() {
		super();
	}

	@Override
	public void StartEncounter(Player player, UI ui, GameEventNotifier notifer) { 
		ui.showMessage("You find sailors, and rescue them. They give you some money as a reward.");
		player.transferMoney(20);
	}

}
