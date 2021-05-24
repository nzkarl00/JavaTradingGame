package game;

import encounters.EncounterEvent;
import encounters.GameEventNotifier;
import islands.Island;
import islands.IslandRoute;

/**
 * Class containing properties for Player character
 * keeps track of player's current state
 * handles properties relating to player:
 * -how much money player has
 * -movement of player from different islands
 *
 * */

public class Player {

	private String name;
	private float money;
	private float currentPosition;
	private Ship ship;
	private Island currentIsland;
	private HealthManager healthManager;

	public Player(String _name, Ship _ship, Island startIsland, float startMoney) {
		name = _name;
		currentIsland = startIsland;
		money = startMoney;
		ship = _ship;
		healthManager = new HealthManager(50, 50);
	}

	/**
	 * Moves player to new island.
	 * The cost of paying crew members for the journey will be removed from the player's money total.
	 * If player doesn't have enough money to make the journey, function is aborted.
	 * @param route IslandRoute describing route to new island
	 * @param ui UI object (currently passed in from GameManager) used for outputting info about the journey
	 * */
	public void moveToNewIsland(IslandRoute route, UI ui, GameEventNotifier notifier) {
		if (ship.getDamageState()) {
			ui.showMessage("ERROR: ship has been damaged, you must repair your ship before you can travel!");
			return;
		}

		int days = route.getDaysToTravel(ship.getSpeed());
		float crewCost = ship.getCrewTravelCost(days);
		
		if (money < crewCost) {
			ui.showMessage("ERROR: insufficient funds to travel to island!");
			return;
		}

		ui.showMessage("Sailing to " + route.getEndIsland().getName() + " for " + days + " days");
		money -= crewCost;
		currentIsland = route.getEndIsland();

		//check for random encounter
		EncounterEvent en = route.getEncounter();
		if (en != null)
		en.StartEncounter(this, ui, notifier);

		ui.showMessage("Arrived at " + currentIsland.getName());

		

	}

	public Island getCurrentIsland() {
		return currentIsland;
	}

	public Ship getShip() {
		return ship;
	}

	public HealthManager getHealthManager() {
		return healthManager;
	}

	public float getMoney() {
		return money;
	}


	/**
	 * interface to allow any object to give or take money from player
	 * positive values of money indicate money is given to player
	 * negative values mean money is taken away
	 * returns true if transaction was successful and false if not
	 * */
	public boolean transferMoney(float amount) {
		if (amount > 0) { //player receives money
			money += amount;
			return true;
		}

		//player loses money
		if (money - amount >= 0) {
			money += amount;
			return true;
		} else {
			return false;
		}
	}


}
