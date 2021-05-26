package game;
import java.util.ArrayList;
import encounters.GameEventNotifier;
import gui.MainWindow;
import gui.SetupWindow;
import islands.Island;
import islands.IslandRoute;
import islands.Store;
import islands.WorldCreator;

/**
 * Class for managing high-level game logic
 * 
 * Functions of the manager:
 * -Calls WorldCreator to initialise the game.
 * -Connects to various stores and calls buy and sell operations.
 * -Collects information from all islands of the game.
 * -Calls Player to move between islands.
 * -Checks to see if any end conditions of the game have been met.
 *
 * Game loop consists of:
 * -Moving between islands.
 * -Encountering anything.
 * -Selling or buying items at new island.
 *
 * Variables:
 * player: Player object that handles operations which change player info or statistics.
 * islands: ArrayList of all islands present.
 * items: ArrayList of all items present.
 * daysLeft: int counter of the time remaining.
 * mainWindow: The main GUI window.
 * transactionHistory: String which has every transaction that occurs in the game appended to it.
 * upgradeableItems: ArrayList of all upgrades the player can purchase.
 * upgrades: Global store accessible from any island containing only upgradeItems and the repair function.
 * notifier: GameEventNotifier to handle any events that may occur traveling between islands.
 * ui: UI interface for any command line operations in testing.
 * */

public class GameManager {
	
	private Player player;
	public ArrayList<Island> islands;
	public ArrayList<Item> items;
	public int daysLeft;
	public static MainWindow mainWindow;
	private String transactionHistory = "Purchase Log:\n";
	public ArrayList<UpgradeItem> upgradeableItems;
	public Store upgrades;
	public GameEventNotifier notifier = new GameEventNotifier();
	enum ActionType {
		viewGameState, viewShip, viewGoods, viewIslands, visitStore, sailToIsland
	}

	public GameManager() {

	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * Initialises the starting state of the game based on values taken from the setup screen.
	 * This includes creating islands, routes, items, setting starting values and generating inventories.
	 * @param name String containing the player's name
	 * @param duration integer value of the amount of days the game will last
	 * @param shipIndex integer that is passed to selectShip to choose the index of selected ship in setup
	 * These properties will be saved somewhere for future reference when game is actually started
	 */
	public void configureAdventure(String name, int duration, int shipIndex) {
		daysLeft = duration;
		Ship ship = selectShip(shipIndex);

		WorldCreator worldCreator = new WorldCreator();
		islands = worldCreator.createIslandsWithRoutes();
		items = worldCreator.initItems();
		upgradeableItems = worldCreator.initUpgradeItems();
		worldCreator.initPlayerInventory(ship, items, upgradeableItems);
		worldCreator.initStoreInventories(islands, items);
		upgrades = worldCreator.initUpgradeStore(upgradeableItems);

		player = new Player(name, ship, islands.get(0), 500);
	}

	/**
	 * Selects a ship from the fixed generation based on user input at setup.
	 * @param shipIndex provides the index of the selected ship
	 * @return Returns Ship object that player has chosen
	 * */
	Ship selectShip(int shipIndex) {
		Ship sloop = new Ship("Sloop", 10, 20, 2, 10, 30);
		Ship brigantine = new Ship("Brigantine", 15, 20, 5, 15, 10);
		Ship galleon = new Ship("Galleon", 20, 30, 7, 15, 15);
		Ship caravel = new Ship("Caravel", 20, 50, 10, 20, 7);
		Ship[] ships = new Ship[] { sloop, brigantine, galleon, caravel };

		ArrayList<String> shipNames = new ArrayList<String>();
		for (int i=0; i<ships.length; i++) {
			shipNames.add(ships[i].getName());
		}
		Ship chosen = ships[shipIndex - 1];
		return chosen;
	}

	/**
	 * Repairs the ship in the universal upgrade store, and appends the transaction string to the log.
	 * @return String transaction which is then printed in the GUI
	 */
	public String repairShip() {
		String transaction = upgrades.repairShip(player, 50);
		if (transaction != "fail") {
			transactionHistory = transactionHistory + (transaction + " at " + player.getCurrentIsland().getName() + "\n");
		}
		return transaction;
	}
	
	/**
	 * Purchases an item from the store of the current island and appends the transaction string to the log.
	 * @param item Item that will be purchased from the store
	 * @return String transaction which is then printed in the GUI
	 */
	public String buyItem(Item item) {
		String transaction = player.getCurrentIsland().getStore().purchaseItem(item, player);
		if (transaction != "fail") {
			transactionHistory = transactionHistory + (transaction + " from " + player.getCurrentIsland().getName() + "\n");
		}
		return transaction;
	}
	
	/**
	 * Sells an item to the store of the current island and appends the transaction string to the log.
	 * @param item Item that will be sold to the store
	 * @return String transaction which is then printed in the GUI
	 */
	public String sellItem(Item item) {
		String transaction = player.getCurrentIsland().getStore().sellItem(item, player);
		if (transaction.startsWith("Y") == false) {
			transactionHistory += (transaction + " at " + player.getCurrentIsland().getName() + "\n");
		}
		return transaction;
	}
	
	/**
	 * Getter for the history of all transactions that have occurred in the game.
	 * @return String transactionHistory which contains all transactions separated by \n
	 */
	public String getTransactionHistory () {
		return transactionHistory;
	}
	
	/**
	 * Gets a string containing the prices of every item for sale on the given island.
	 * @param index integer of the index of the island in the islands list
	 * @return storeString String of all sale prices separated by \n
	 */
	public String viewBuyingPrices(int index) {
		String storeString = "";
		for (Item b: islands.get(index).getStore().getBuyables()) {
			storeString += (b.getName() + " $" + islands.get(index).getStore().getPrice(b, true) + "\n");
		}
		return storeString;
	}
	
	/**
	 * Gets a string containing the prices of every item the island is purchasing.
	 * @param index integer of the index of the island in the islands list
	 * @return storeString String of all purchasing prices separated by \n
	 */
	public String viewSellingPrices(int index) {
		String storeString = "";
		for (Item s: islands.get(index).getStore().getSellables()) {
			storeString += (s.getName() + " $" + islands.get(index).getStore().getPrice(s, false) + "\n");
		}
		return storeString;
	}

	//UNUSED FUNCTION 
	/**
	* returns list of routes that can be taken to other islands from specified island
	* is currently called when choosing routes to travel from an island
	* could also be called when you want to view routes from another island 
	* @param island Island that is the starting point for routes that will be printed out
	* @return ArrayList<String> containing descriptions for each route from island
	* */
	public ArrayList<String> getIslandRoutesInformation(Island island) {
		ArrayList<IslandRoute> routes = island.getRoutes();
		
		ArrayList<String> routeDescriptions = new ArrayList<String>();
		for (IslandRoute route : routes) {
			int routeTravelTime = route.getDaysToTravel(player.getShip().getSpeed());
			String description = route.getString() + ", will take " + routeTravelTime + " days";
			routeDescriptions.add(description);
		}

		return routeDescriptions;
	}

	/**
	 * Moves the player to a new island and decrements daysLeft by the length of the journey.
	 * @param route IslandRoute selected by the player in the GUI
	 * @param notifier GameEventNotifier to handle any encounters that may occur in the journey
	 */
	public void sailToIsland(IslandRoute route, GameEventNotifier notifier) {
		//details managed by Player class
		String islandMove = player.moveToNewIsland(route, notifier);
		if (islandMove.startsWith("Arrived")) {
			daysLeft -= route.getDaysToTravel(player.getShip().getSpeed());
		}
		mainWindow.printEncounter(islandMove);
	}

	/**
	 * Checks if the player does not have enough days remaining in the game to sail to a new island.
	 * @return boolean of true if the player is out of days, false if not out of days
	 */
	private boolean hasRunOutOfDays() {
		//get min travel day
		//compare to existing day
		int minDays = getMinDaysToTravel(player.getCurrentIsland());
		return daysLeft - minDays < 0;
	}
	
	/**
	 * Checks if the player does not have enough money to pay crew wages for any journey.
	 * This includes the value of all items in the player's inventory if they were to be sold at the store of the current island
	 * @return boolean of true if the player does not have enough money to continue playing, false if they do have enough money
	 */
	private boolean hasRunOutOfMoney() {
		//get min travel day, convert to money
		//compare to existing money
		int minDays = getMinDaysToTravel(player.getCurrentIsland());
		float moneyNeededToTravel = player.getShip().getCrewTravelCost(minDays);
		float hypotheticalMoneyFromStore = player.getShip().getGoodsValue(player.getCurrentIsland().getStore());
		return moneyNeededToTravel > player.getMoney() + hypotheticalMoneyFromStore;
	}

	/**
	 * Checks if the game should end based on the conditions {out of money, out of days, sunk by pirates}.
	 * @return String detailing continue if the game should not end, the way the game ended if it should end
	 */
	public String checkGameEnd() {
		boolean outOfMoney = hasRunOutOfMoney();
		boolean outOfDays = hasRunOutOfDays();
		boolean killedByPirates = notifier.hasEventOccurred(GameEventNotifier.EventType.killedByPirates);
		if (outOfMoney) {
			return "You ran out of money to pay your crew";
		} else if (outOfDays) {
			return "You ran out of days";
		} else if (killedByPirates) {
			return "You didn't have enough goods on board and were sunk by pirates";
		} else {
			return "continue";
		}
	}
	
	/**
	 * Finds the minimum possible days for any journey to a new island.
	 * @param island Island that the player is currently on
	 * @return integer of the minimum number of days required to travel
	 */
	private int getMinDaysToTravel(Island island) {
		ArrayList<IslandRoute> routes = island.getRoutes();
		int minDays = -1;
		
		for (IslandRoute route : routes) {
			int routeTravelTime = route.getDaysToTravel(player.getShip().getSpeed());
			if (minDays == -1 || routeTravelTime < minDays) {
				minDays = routeTravelTime;
			}
	}
	
		return minDays;
	}

	/**
	 * Launches the main window of the GUI.
	 */
	public void launchMainWindow() {
		mainWindow = new MainWindow(this);
	}
	
	/**
	 * closes the main window of the GUI.
	 * @param mainWindow MainWindow object carried by the game manager
	 */
	public void closeMainWindow(MainWindow mainWindow) {
		mainWindow.closeWindow();
	}
	
	/**
	 * Launches the setup window of the GUI.
	 */
	public void launchSetupWindow() {
		new SetupWindow(this);
	}
	
	/**
	 * Closes the setup window of the GUI and calls to launch the main window immediately.
	 * @param setupWindow SetupWindow object carried by the game manager
	 */
	public void closeSetupWindow(SetupWindow setupWindow) {
		setupWindow.closeWindow();
		launchMainWindow();
	}
}
