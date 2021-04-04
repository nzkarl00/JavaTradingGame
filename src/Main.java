import java.util.InputMismatchException;
import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		Scanner temp = new Scanner(System.in);
		String username = "1";
		while ((username.matches("^[A-Za-z ]*$") == false) | ((username.length() < 3) | (username.length() > 15))) {
					System.out.println("Enter trader name:");
					username = temp.nextLine();
		}
		int duration = 0;
		while ((duration < 20) || (duration > 50)) {
			try {
				System.out.println("Enter game duration between 20 and 50:");
				duration = temp.nextInt();
			} catch(InputMismatchException e) {
				temp.next();
			}
		}
		int shipselect = 0;
		while ((shipselect < 1) || (shipselect > 4)) {
			try {
				System.out.println("Select starting ship:\n1 for sloop\n2 for brigantine\n3 for galleon\n4 for caravel");
				shipselect = temp.nextInt();
			} catch(InputMismatchException e) {
				temp.next();
			}
		}
		Player user = new Player(username, duration);
		}
}
