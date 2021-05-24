package game;
import java.util.ArrayList;

/**
 * Interface for handling generic player queries
 * Other classes that require input/output to player should use functions defined in this interface, rather than handling things themselves
 * This should make it easier to implement the GUI later on as game logic isn't intertwined with the user interface
 * */

public interface UI {

	public String queryStringByRegex(String message, String regex);

	public int queryIntBetweenRange(String message, int min, int max);

	public int queryListOfOptions(String message, ArrayList<String> options);

	public void showMessage(String message);

	public void showList(String description, ArrayList<String> items);


}
