package game;

import encounters.EncounterEvent;
import encounters.GameEventNotifier;
import islands.Island;
import islands.IslandRoute;
import java.util.Random;

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
	private Ship ship;
	private Island currentIsland;

	public Player(String _name, Ship _ship, Island startIsland, float startMoney) {
		name = _name;
		currentIsland = startIsland;
		money = startMoney;
		ship = _ship;
	}

	/**
	 * Moves player to new island.
	 * The cost of paying crew members for the journey will be removed from the player's money total.
	 * If player doesn't have enough money to make the journey, function is aborted.
	 * @param route IslandRoute describing route to new island
	 * @param ui UI object (currently passed in from GameManager) used for outputting info about the journey
	 * */
	public String moveToNewIsland(IslandRoute route, GameEventNotifier notifier) {
		if (ship.getDamageState()) {
			return "Cannot sail while ship is damaged.\n";
		}

		int days = route.getDaysToTravel(ship.getSpeed());
		float crewCost = ship.getCrewTravelCost(days);
		
		if (money < crewCost) {
			return "Insufficient funds to travel to island.\n";
		}
		money -= crewCost;
		currentIsland = route.getEndIsland();

		//check for random encounter
		EncounterEvent en = route.getEncounter(new Random());
		if (en != null)
		GameManager.mainWindow.printEncounter(en.StartEncounter(this, notifier));

		return ("Arrived at " + currentIsland.getName() + ".\n");

		

	}

	public Island getCurrentIsland() {
		return currentIsland;
	}

	public Ship getShip() {
		return ship;
	}
	
	public String getName() {
		return name;
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
		if (money + amount >= 0) { //money minus a negative number adds up - bug fixed
			money += amount;
			return true;
		} else {
			return false;
		}
	}


}
