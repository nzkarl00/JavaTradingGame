package game;

import encounters.EncounterEvent;
import encounters.GameEventNotifier;
import islands.Island;
import islands.IslandRoute;
import java.util.Random;

/**
 * Class containing properties for Player character.
 * Keeps track of player's current state.
 * 
 * Functions of Player:
 * -Handles movement between islands
 * -Keeps track of the player's money and current location
 *
 * Variables:
 * -name String of the player's name input at setup
 * -money float of the current money in the player's possession
 * -ship Ship chosen by the player at setup
 * -currentIsland Islands the player is currently at
 * */

public class Player {

	private String name;
	private float money;
	private Ship ship;
	private Island currentIsland;

	/**
	 * Constructor of the Player class.
	 * @param _name String to be assigned to name
	 * @param _ship Ship to be assigned to ship
	 * @param startIsland Island default starting islands for initial currentIsland
	 * @param startMoney float of the player's starting money
	 */
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
	 * Result is returned as a String which is then output by the GUI.
	 * @param route IslandRoute describing route to new island
	 * @param notifier GameEventNotifier to handle any events that may occur
	 * @return String detailing the success or failure of the attempted journey
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

	/**
	 * Getter for currentIsland.
	 * @return Island currentIsland
	 */
	public Island getCurrentIsland() {
		return currentIsland;
	}

	/**
	 * Getter for ship.
	 * @return Ship ship
	 */
	public Ship getShip() {
		return ship;
	}
	
	/**
	 * Getter for name.
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for money.
	 * @return float money
	 */
	public float getMoney() {
		return money;
	}

	/**
	 * Adds or removes money from the player's total.
	 * If the player has less money than a deduction the transfer fails.
	 * @param amount float that can be positive or negative
	 * @return boolean detialing if the transfer was successful 
	 */
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
