
/**
 * Class for weather encounter event.
 * if bad weather is met, player's ship takes damage
 * once landed at island, have to pay money to make up for repair costs
 * */

public class WeatherEncounter extends EncounterEvent {
	
	public WeatherEncounter() {
		super();
	}

	public void StartEncounter(Player player, UI ui) {
		ui.showMessage("You run into some nasty weather. Your ship has taken damage and will need to be repaired.");
		//TODO: adjust player's health manager to take damage
		player.transferMoney(-20, ui);
	
	}
	

}
