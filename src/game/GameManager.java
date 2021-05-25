package game;
import java.util.ArrayList;
import java.util.Random;

import encounters.GameEventNotifier;
import gui.MainWindow;
import gui.SetupWindow;
import islands.Island;
import islands.IslandRoute;
import islands.Store;

/**
 * Class for managing high-level game logic
 * split into manager and inputAsker class? to separate logic from output (single responsibility rule) - would make it easier to add in graphical application later
 *
 * manager should:
 * -get configurations from player (high-level)
 * -save this
 * -create new game with provided configs
 * -
 * I'm not sure if it should control interactions between classes directly or if those classes handle things themselves
 * but it can kickstart whatever it needs to here
 *
 * game loop essentially consists of:
 * -moving between islands
 * -encountering anything
 * -selling items at new island
 *
 * either check for game end state directly, or get notified or it from another class
 * whatever it does, tell player game is over
 *
 * */

public class GameManager {

	public static Player player;
	public ArrayList<Island> islands;
	public Island currentIsland;
	public static ArrayList<Item> items;
	public int daysLeft;
	private MainWindow mainWindow;
	private String transactionHistory = "Purchase Log:\n";
	public ArrayList<UpgradeItem> upgradeableItems;
	public Store upgrades;
	public GameEventNotifier notifier;
	UI ui;
	


	enum ActionType {
		viewGameState, viewShip, viewGoods, viewIslands, visitStore, sailToIsland
	}

	public GameManager() {

	}

	/**
	 * Initialises the starting state of the game based on values taken from the setup screen.
	 * This includes creating islands, routes, items, setting starting values and generating inventories.
	 * @param name String containing the player's name
	 * @param duration integer value of the amount of days the game will last
	 * @param shipIndex integer of 
	 * These properties will be saved somewhere for future reference when game is actually started
	 */
	public void configureAdventure(String name, int duration, int shipIndex) {
		notifier = new GameEventNotifier();
		ui = new CommandLineInterface();
		daysLeft = duration;
		Ship ship = selectShip(shipIndex);
		createIslands();
		createRoutes(2);
		createItems();
		generatePlayerInventory(ship);
		generateStoreInventory();
		currentIsland = islands.get(0);
		player = new Player(name, ship, currentIsland, 500);
	}

	/**
	 * Ask player to choose from 4 base ships
	 * @param Scanner is needed for command-line user input to be processed correctly
	 * @return Returns Ship object that player has chosen
	 * */
	Ship selectShip(int shipIndex) {
		Ship sloop = new Ship("Sloop", 10, 20, 3, 10);
		Ship brigantine = new Ship("Brigantine", 20, 30, 5, 10);
		Ship galleon = new Ship("Galleon", 30, 50, 7, 15);
		Ship caravel = new Ship("Caravel", 50, 100, 10, 20);
		Ship[] ships = new Ship[] { sloop, brigantine, galleon, caravel };

		ArrayList<String> shipNames = new ArrayList<String>();
		for (int i=0; i<ships.length; i++) {
			shipNames.add(ships[i].getName());
		}

		Ship chosen = ships[shipIndex - 1];
		ui.showMessage("You have chosen " + chosen.getName() + ".");
		
		return chosen;
	}


	private void createIslands() {
		Island fracturedisle = new Island("Fractured Isle", 9, 7);	// ]
		Island smithscordrefuge = new Island("Smithscord Refuge", 5, 1); // ]
		Island penlycay = new Island("Penly Cay", 2, -10); // ] ------------------------Creating all the island objects for the game
		Island valganisland = new Island("Valgan Island", -4, -2); // ]
		Island stockstallenclave = new Island("Stockstall Enclave", -8, -8); // ]
		islands = new ArrayList<Island>();
		islands.add(fracturedisle);
		islands.add(smithscordrefuge);
		islands.add(penlycay);
		islands.add(valganisland);
		islands.add(stockstallenclave);
	}

	/**
	 * for each island, make a route from itself to every other island on the map
	 * this route will be added to island's internal list of routes
	 * */
	private void createRoutes(int numSimilarRoutes) {

		for (int i = 0; i < islands.size(); i++) {
			Island startIsland = islands.get(i);

			for (int j = 0; j < islands.size(); j++) {
				Island endIsland = islands.get(j);
				if (j == i) continue;

				for (int k = 0; k < numSimilarRoutes; k++) {
					float directness = (1f / numSimilarRoutes) * (k + 1);
					startIsland.addRoute(endIsland, directness);
				}
			}
		}
	}

	/**
	 * Main game loop where high-level game logic (moving between islands, sellings itmes, etc) is managed
	 * @return 
	 * */
	public void mainLoop() {
		//show options for player to do
		GameEventNotifier notifier = new GameEventNotifier();
		ActionType chosenAction = getNextAction();
		if (chosenAction == ActionType.viewGameState) {
			//show money + days
			ui.showMessage("You have " + daysLeft + " days left, and $" + player.getMoney() + ".");
		}
		if (chosenAction == ActionType.viewShip) {
			//show ship name, crewmembers + wage cost, capacity, ship damage + repair cost
			Ship ship = player.getShip();
			ui.showMessage("You are using ship " + ship.getName() + " .");
			ui.showMessage("This ship has " + ship.getCrewCount() + " members, costing a total of $" + ship.getCrewTravelCost(1) + " per day.");
			ui.showMessage("TODO: show ship damage");
		}
		if (chosenAction == ActionType.viewGoods) {
			//show items, for each show amuont paid + amount it was sold for and where if it was sold
			player.getShip().showInventory();
			
		}
		if (chosenAction == ActionType.viewIslands) {
			//show island name, routes + distance + details, store item sellables, store item buyables
			for (Island island : islands) {
				showIslandInfo(island);
			}
		}
		if (chosenAction == ActionType.visitStore) {
			// Store store = currentIsland.getStore();
			// visitStore(currentIsland);
			currentIsland.getStore().visitStore(player, ui);
		}
		if (chosenAction == ActionType.sailToIsland) {
			//must repair damage to ship before can sail
			IslandRoute chosenRoute = chooseIslandRoute(player.getCurrentIsland());
			sailToIsland(chosenRoute, notifier);
		}

		mainLoop();
	}
	
	public String repairShip() {
		String transaction = upgrades.repairShip();
		if (transaction != "fail") {
			transactionHistory = transactionHistory + (transaction + " at " + player.getCurrentIsland() + "\n");
		}
		return transaction;
	}
	
	public String buyItem(Item item) {
		String transaction = currentIsland.getStore().purchaseItem(item, player);
		if (transaction != "fail") {
			transactionHistory = transactionHistory + (transaction + " from " + player.getCurrentIsland().getName() + "\n");
		}
		return transaction;
	}
	
	public String sellItem(Item item) {
		String transaction = currentIsland.getStore().sellItem(item, player);
		if (transaction.startsWith("Y") == false) {
			transactionHistory += (transaction + " at " + player.getCurrentIsland().getName() + "\n");
		}
		return transaction;
	}
	
	public String getTransactionHistory () {
		return transactionHistory;
	}
	
	public String viewBuyingPrices(int index) {
		String storeString = "";
		for (Item b: islands.get(index).getStore().getBuyables()) {
			storeString += (b.getName() + " $" + islands.get(index).getStore().getPrice(b, true) + "\n");
		}
		return storeString;
	}
	
	public String viewSellingPrices(int index) {
		String storeString = "";
		for (Item s: islands.get(index).getStore().getSellables()) {
			storeString += (s.getName() + " $" + islands.get(index).getStore().getPrice(s, false) + "\n");
		}
		return storeString;
	}

	private ActionType getNextAction() {
		ArrayList<String> options = new ArrayList<String>();
		options.add("View game stats");
		options.add("View ship information");
		options.add("View inventory");
		options.add("View islands");
		options.add("Visit island store");
		options.add("Sail to new island");

		int index = ui.queryListOfOptions("What would you like to do now?", options);

		return ActionType.values()[index];
	}

	private void showIslandInfo(Island island) {
		//show island name, routes + distance + details, store item sellables, store item buyables
		ui.showMessage(island.getName());
		ArrayList<String> routesInfoList = getIslandRoutesInformation(island);
		ui.showList("Routes:", routesInfoList);
		// ArrayList<String> storeSellables = island.getStore().
		island.getStore().printSellableInventory();
		island.getStore().getBuyableItems();
	}

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
	* Asks player for route to choose from list of routes
	* when route is selected, start move towards that island
	* @param fromIsland Island that is the starting point for any possible routes
	* @return IslandRoute describing selected route from current island to new one
	* */
	private IslandRoute chooseIslandRoute(Island fromIsland) {
		String message = "From island " + fromIsland.getName() + " you can go to: ";
		ArrayList<String> routeDescriptions = getIslandRoutesInformation(fromIsland);
		int routeIndex = ui.queryListOfOptions(message, routeDescriptions);
		return fromIsland.getRoutes().get(routeIndex);
	}

	public void sailToIsland(IslandRoute route, GameEventNotifier notifier) {
		//details managed by Player class
		ui.showMessage("sail using route " + route.getString());
		daysLeft -= route.getDaysToTravel(player.getShip().getSpeed());
		player.moveToNewIsland(route, ui, notifier);
	}

	private void createItems() {
		Item Silk = new Item(50,"Silk","A high quality textile.",2);
		Item Linen = new Item(10,"Linen","A generic textile.",3);
		Item Wine = new Item(20,"Wine","An alcoholic drink.",5);
		Item Cinnamon =  new Item(30,"Cinnamon","A generic spice.",1);
		Item Saffron = new Item(100,"Saffron","An exotic spice.",1);
		
		items = new ArrayList<Item>();
		items.add(Silk);
		items.add(Wine);
		items.add(Linen);
		items.add(Saffron);
		items.add(Cinnamon);	
		
		UpgradeItem cannon1 = new UpgradeItem(70, "Cannon 1", "Upgrades damage", 0, UpgradeItem.UpgradeType.damage);
		UpgradeItem cannon2 = new UpgradeItem(100, "Cannon 2", "Upgrades damage", 0, UpgradeItem.UpgradeType.damage);
		UpgradeItem cannon3 = new UpgradeItem(150, "Cannon 3", "Upgrades damage", 0, UpgradeItem.UpgradeType.damage);
		upgradeableItems = new ArrayList<UpgradeItem>();
		upgradeableItems.add(cannon1);
		upgradeableItems.add(cannon2);
		upgradeableItems.add(cannon3);

	}

	private void generateStoreInventory() {
		Random rng = new Random(); 
		for(Island i : islands) {
			for(Item j : items) {
				Store store = i.getStore();
				store.addItem(j, rng.nextInt(20));
			}
		}
		upgrades = new Store("upgrades");
		UpgradeItem damageUpgrade1 = upgradeableItems.get(0);
		UpgradeItem damageUpgrade2 = upgradeableItems.get(1);
		UpgradeItem damageUpgrade3 = upgradeableItems.get(2);
		upgrades.addItem(damageUpgrade1, 1);
		upgrades.addItem(damageUpgrade2, 1);
		upgrades.addItem(damageUpgrade3, 1);

	}
	
	private void generatePlayerInventory(Ship playership) {
		for(Item i : items) {
			playership.playerInventory.put(i, 0);
		}
		for(Item i : upgradeableItems) {
			playership.playerInventory.put(i, 0);
		}
	}
	
	private boolean hasRunOutOfDays() {
		//get min travel day
		//compare to existing day
		int minDays = getMinDaysToTravel(player.getCurrentIsland());
		return daysLeft - minDays < 0;
	}

	private boolean hasRunOutOfMoney() {
		//get min travel day, convert to money
		//compare to existing money
		int minDays = getMinDaysToTravel(player.getCurrentIsland());
		float moneyNeededToTravel = player.getShip().getCrewTravelCost(minDays);
		float hypotheticalMoneyFromStore = player.getShip().getGoodsValue(currentIsland.getStore());
		return moneyNeededToTravel > player.getMoney() + hypotheticalMoneyFromStore;
	}
	
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

	
	public void launchMainWindow() {
		mainWindow = new MainWindow(this);
	}
	
	public void closeMainWindow(MainWindow mainWindow) {
		mainWindow.closeWindow();
	}
	
	public void launchSetupWindow() {
		new SetupWindow(this);
	}
	
	public void closeSetupWindow(SetupWindow setupWindow) {
		setupWindow.closeWindow();
		launchMainWindow();
	}
}
