/**
 * rescue some sailors
 * player recieves money in return
 * */

public class SailorEncounter extends EncounterEvent {

	public SailorEncounter() {
		super();
	}

	public void StartEncounter(Player player, UI ui, GameEventNotifier notifer) { 
		ui.showMessage("You find sailors, and rescue them. They give you some money as a reward.");
		player.transferMoney(20, ui);
	}

}
