import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class for managing high-level game logic
 * */

public class GameManager { //moving all code to a new class so can call sub-functions without having to make everything static

	public GameManager() {

	}

	public void configureAdventure() {
		/**
		 * Main function run when a new game is being created
		 * Ask player for username, game duration, and ship type
		 * These properties will be saved somewhere for future reference when game is actually started
		 * */
		Scanner scanner = new Scanner(System.in);

		String username = "1";
		while ((username.matches("^[A-Za-z ]*$") == false) | ((username.length() < 3) | (username.length() > 15))) {
			System.out.println("Enter trader name:");
			username = scanner.nextLine();
		}

		int duration = askForBoundedInt("Enter game duration between 20 and 50:", 20, 50, scanner);

		Ship ship = selectShip(scanner);

		// Player user = new Player(username, duration); //doesn't work for me bc no Player class in the project - ?

	}

	Ship selectShip(Scanner scanner) {
		/**
		 * Ask player to choose from 4 base ships
		 * @param Scanner is needed for command-line user input to be processed correctly
		 * @return Returns Ship object that player has chosen
		 * */
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

	int askForBoundedInt(String message, int lowerBound, int upperBound, Scanner scanner) {
		/**
		 * Prompts user to enter int between lowerBound and upperBound
		 * Will keep asking user to enter number until they get it right
		 * @return parsed int between lowerBound and upperBound player has chosen
		 * */
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

}
