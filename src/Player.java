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
	public void moveToNewIsland(IslandRoute route, UI ui) {
		int days = route.getDaysToTravel(ship.getSpeed());
		float crewCost = ship.getCrewTravelCost(days);
		
		if (money < crewCost) {
			ui.showMessage("ERROR: insufficient funds to travel to island!");
			return;
		}

		ui.showMessage("Sailing to " + route.getEndIsland().getName() + " for " + days + " days");
		money -= crewCost;
		currentIsland = route.getEndIsland();
		ui.showMessage("Arrived at " + currentIsland.getName());

	}

	public Island getCurrentIsland() {
		return currentIsland;
	}

	public Ship getShip() {
		return ship;
	}

}
