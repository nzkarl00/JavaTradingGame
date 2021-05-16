import java.util.ArrayList;
import java.util.Random;

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

	private Player player;
	private ArrayList<Island> islands;
	private Island currentIsland;
	public static ArrayList<Item> items;
	private ArrayList<Store> stores;

	UI ui;

	public GameManager() {

	}

	/**
	 * Main function run when a new game is being created
	 * Ask player for username, game duration, and ship type
	 * These properties will be saved somewhere for future reference when game is actually started
	 * */
	public void configureAdventure() {

		ui = new CommandLineInterface(); //use only command line in this version of project, should be as easy as possible to change to gui once that is all implemented

		String regex = "^[A-Za-z ]{3,15}$";
		String username = ui.queryStringByRegex("Enter trader name", regex);

		int duration = ui.queryIntBetweenRange("Enter game duration between 20 and 50:", 20, 50);

		Ship ship = selectShip();
		createIslands();
		createRoutes(3);
		createItems();
		generateplayerInventory(ship);
		generateStoreInventory();
		currentIsland = islands.get(0);
		player = new Player(username, ship, currentIsland, 200);

		mainLoop();
	}

	/**
	 * Ask player to choose from 4 base ships
	 * @param Scanner is needed for command-line user input to be processed correctly
	 * @return Returns Ship object that player has chosen
	 * */
	Ship selectShip() {
		Ship sloop = new Ship("Sloop", 10, 5, 3, 20);
		Ship brigantine = new Ship("Brigantine", 50, 20, 5, 10);
		Ship galleon = new Ship("Galleon", 30, 20, 10, 15);
		Ship caravel = new Ship("Caravel", 20, 10, 5, 20);
		Ship[] ships = new Ship[] { sloop, brigantine, galleon, caravel };

		ArrayList<String> shipNames = new ArrayList<String>();
		for (int i=0; i<ships.length; i++) {
			shipNames.add(ships[i].getName());
		}

		int shipIndex = ui.queryListOfOptions("Select your ship:", shipNames);
		Ship chosen = ships[shipIndex];
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
		
		Store fiStore = new Store("Fractured Isle Inventory");	// ]
		Store srStore = new Store("Smithscord Refuge Retail");	// ]
		Store pcStore = new Store("Penly Cay Market");	// ] --------Creating all the stores for each island, initials used for names.
		Store viStore = new Store("Valgan Island Store");	// ]
		Store seStore = new Store("Stockstall Enclave Bazaar");	// ]
		
		stores = new ArrayList<Store>();
		stores.add(fiStore);
		stores.add(srStore);
		stores.add(pcStore);
		stores.add(viStore);
		stores.add(seStore);

		
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
	 * */
	private void mainLoop() {
		IslandRoute chosenRoute = chooseIslandRoute(player.getCurrentIsland());
		sailToIsland(chosenRoute);
		mainLoop();
	}

	/**
	* returns list of routes that can be taken to other islands from specified island
	* is currently called when choosing routes to travel from an island
	* could also be called when you want to view routes from another island 
	* @param island Island that is the starting point for routes that will be printed out
	* @return ArrayList<String> containing descriptions for each route from island
	* */
	private ArrayList<String> getIslandRoutesInformation(Island island) {
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

	private void sailToIsland(IslandRoute route) {
		//details managed by Player class
		ui.showMessage("sail using route " + route.getString());
		player.moveToNewIsland(route, ui);
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
	}

	private void generateStoreInventory() {
		Random rng = new Random(); 
		for(Store i : stores) {
			for(Item j : items) {
				i.stockItem(j, rng.nextInt(20));
			}
		}
	}
	
	private void generateplayerInventory(Ship playership) {
		for(Item i : items) {
			playership.playerInventory.put(i, 0);
		}
	}

}
