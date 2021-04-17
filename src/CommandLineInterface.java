import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Implementation of UI interface using the command-line for input and output
 * */

public class CommandLineInterface implements UI {

	Scanner scanner;

	public CommandLineInterface() {
		scanner = new Scanner(System.in);
	}

	/**
	 * Asks player to enter a string, and keeps asking until the provided regex pattern is matched
	 * ideally want all game logic in GameManager, not in here, so valid-username-identification code is done there
	 * @param message Prompt message to show to player
	 * @param regex Regex pattern to match player's input to
	 * @return String player's correct input
	 * */
	public String queryStringByRegex(String message, String regex) { 
		System.out.println(message);

		String username = scanner.nextLine();
		if (!username.matches(regex)) return queryStringByRegex(message, regex);
		return username;
	}

	/**
	 * Prompts user to enter int between min and max
	 * Will keep asking user to enter number until they get it right
	 * @return parsed int between min and max player has chosen
	 * */
	public int queryIntBetweenRange(String message, int min, int max) {
		int ans = min - 1;
		while ((ans < min) || (ans > max)) {
			try {
				System.out.println(message);
				ans = scanner.nextInt();
			} catch(InputMismatchException e) {
				scanner.next();
			}
		}
		return ans;
	}

	/**
	 * Prompts player to select an item from a list of options
	 * Gives index of selected option (makes it easy to select corresponding item from another array)
	 * @param message Message to show player before displaying list
	 * @param options Araylist<String> representing objects player can choose from
	 * @return int giving the array index of the chosen option 
	 * */
	public int queryListOfOptions(String message, ArrayList<String> options) {
		System.out.println(message);
		showListOfOptions(options);
		String selectMessage = "Enter a number between 1 and " + options.size() + " to select.";
		int index = queryIntBetweenRange(selectMessage, 1, options.size());
		index -= 1;
		return index;
	}

	/**
	 * Displays message to player, here printing to standard output
	 * */
	public void showMessage(String message) {
		System.out.println(message);
		System.out.println("");
	}

	/**
	 * Display list of options to player
	 * @param options ArrayList<String> of things to show player
	 * */
	private void showListOfOptions(ArrayList<String> options) {
		for (int i = 0; i < options.size(); i++) {
			String option = options.get(i);
			System.out.println("Option #" + (i + 1) + ": " + option);
		}
	}

}
