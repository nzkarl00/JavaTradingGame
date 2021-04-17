import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

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

	public GameManager() {

	}

	/**
	 * Main function run when a new game is being created
	 * Ask player for username, game duration, and ship type
	 * These properties will be saved somewhere for future reference when game is actually started
	 * */
	public void configureAdventure() {
		Scanner scanner = new Scanner(System.in);

		String username = "1";
		while ((username.matches("^[A-Za-z ]*$") == false) | ((username.length() < 3) | (username.length() > 15))) {
			System.out.println("Enter trader name:");
			username = scanner.nextLine();
		}

		int duration = askForBoundedInt("Enter game duration between 20 and 50:", 20, 50, scanner);

		Ship ship = selectShip(scanner);
		createIslands();
		createRoutes(3);
		currentIsland = islands.get(0);
		player = new Player(username, ship, currentIsland, 200);

		mainLoop(scanner);
	}

	/**
	 * Ask player to choose from 4 base ships
	 * @param Scanner is needed for command-line user input to be processed correctly
	 * @return Returns Ship object that player has chosen
	 * */
	Ship selectShip(Scanner scanner) {
		Ship sloop = new Ship("Sloop", 10, 5, 3, 20);
		Ship brigantine = new Ship("Brigantine", 50, 20, 5, 10);
		Ship galleon = new Ship("Galleon", 30, 20, 10, 15);
		Ship caravel = new Ship("Caravel", 20, 10, 5, 20);
		Ship[] ships = new Ship[] { sloop, brigantine, galleon, caravel };
		
		System.out.println("Select your ship:");
		for (int i=0; i<ships.length; i++) {
			System.out.println("Ship #" + (i + 1));
			Ship ship = ships[i];
			System.out.println(ship.toString());
			System.out.println("");
		}

		String shipAskMessage = "Enter a number between 1 and " + ships.length + " to choose your ship: ";
		int shipNum = askForBoundedInt(shipAskMessage, 1, ships.length, scanner);
		shipNum -= 1;

		Ship chosen = ships[shipNum];
		System.out.println("You have chosen " + chosen.getName() + ".");
		
		return chosen;
	}

	/**
	 * Prompts user to enter int between lowerBound and upperBound
	 * Will keep asking user to enter number until they get it right
	 * @return parsed int between lowerBound and upperBound player has chosen
	 * */
	int askForBoundedInt(String message, int lowerBound, int upperBound, Scanner scanner) {
		int ans = lowerBound - 1;
		while ((ans < lowerBound) || (ans > upperBound)) {
			try {
				System.out.println(message);
				ans = scanner.nextInt();
			} catch(InputMismatchException e) {
				scanner.next();
			}
		}
		return ans;
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
	 * */
	private void mainLoop(Scanner scanner) {
		IslandRoute chosenRoute = chooseIslandRoute(player.getCurrentIsland(), scanner);
		sailToIsland(chosenRoute);
		mainLoop(scanner);
	}

	/**
	* prints out list of routes that can be taken to other islands from specified island
	* is currently called when choosing routes to travel from an island
	* could also be called when you want to view routes from another island 
	* @param island Island that is the starting point for routes that will be printed out
	* */
	private void giveIslandRoutesInformation(Island island) {
		System.out.println("From island " + island.getName() + " you can go to: ");
		ArrayList<IslandRoute> routes = island.getRoutes();
		for (int i = 0; i < routes.size(); i++) {
			IslandRoute route = routes.get(i);
			int routeTravelTime = route.getDaysToTravel(player.getShip().getSpeed());
			System.out.println("Route " + (i + 1) + ": " + route.getString() + ", will take " + routeTravelTime + " days");
		}
	}

	/**
	* Asks player for route to choose from list of routes
	* if route is selected, start move towards that island
	* @param fromIsland Island that is the starting point for any possible routes
	* @param scanner Scanner object to handle command-line input
	* @return IslandRoute describing selected route from current island to new one
	* */
	private IslandRoute chooseIslandRoute(Island fromIsland, Scanner scanner) {
		giveIslandRoutesInformation(fromIsland);
		
		String askMessage = "press <n> for number route to go to";

		int routeIndex = askForBoundedInt(askMessage, 1, fromIsland.getRoutes().size(), scanner);
		routeIndex -= 1;
		return fromIsland.getRoutes().get(routeIndex);
	}

	private void sailToIsland(IslandRoute route) {
		//details managed by Player class
		System.out.println("sail using route " + route.getString());
		player.moveToNewIsland(route);
	}

	private void createItems() {
		Item Silk = new Item(50,"Silk","A high quality textile.",2);
		Item Linen = new Item(10,"Linen","A generic textile.",3);
		Item Wine = new Item(20,"Wine","An alcoholic drink.",5);
		Item Cinnamon =  new Item(30,"Cinnamon","A generic spice.",1);
		Item Saffron = new item(100,"Saffron","An exotic spice.",1);
	}

}
