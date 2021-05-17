import java.util.HashMap;
import java.util.Map;

/**
 * Base class for all Ship functionality.
 * A ship can:
 * -Contain crew members that must be paid
 * -Carry cargo, up to a maximum limit
 * -Be upgradable
 * -Travel between islands
 * -Attack other ships
 * -Take damage from other objects that can give damage
 * -Regain health via repairs
 *
 *  The ship class is looking fairly complex, stuff like health may have overlapping functionality from other classes,
 *  might be best to break into subclasses as much as possible.
 * */

public class Ship {

	private String name;
	private float speed;
	private int maxCapacity;
	private int crewSize;
	private float dailyCrewMemberCost = 10;
	private float maxHealth;
	private float damage;
	public Map<Item, Integer> playerInventory;

	/**
	 * Create a new Ship object with provided name, speed, capacity, crew size, and max health
	 * */
	public Ship(String _name, float _speed, int _maxCapacity, int _crewSize, float _maxHealth) {
		name = _name;
		speed = _speed;
		maxCapacity = _maxCapacity;
		crewSize = _crewSize;
		maxHealth = _maxHealth;
		damage = 10;
		playerInventory = new HashMap<Item, Integer>();
	}

	/**
	 * @return String An easy-to-read overview of this Ship's properties
	 * */
	public String toString() {
		String output = "Name: " + name + "\n"
			+ "Speed: " + speed + "\n"
			+ "Capacity: " + maxCapacity + "\n"
			+ "Crew members: " + crewSize + "\n"
			+ "Max health: " + maxHealth + "\n";
		return output;
	}

	public String getName() {
		return name;
	}

	public float getCrewCount() {
		return crewSize;
	}

	public float getCrewTravelCost(int numDays) {
		return crewSize * dailyCrewMemberCost * numDays;
	}

	public float getSpeed() {
		return speed;
	}

	public float getDamage() {
		return damage;
	}
	
	public void showInventory(UI ui) {
		ui.showMessage("CURRENT PLAYER INVENTORY:");
		for(Item i : GameManager.items) {
			ui.showMessage(i.getName() + ": " + playerInventory.get(i));
		}
		// System.out.println("\n");
	}

}
