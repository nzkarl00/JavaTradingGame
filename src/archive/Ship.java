package archive;

import java.util.HashMap;
import java.util.Map;
import java.util.*;

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
	private float health;
	private float damage;
	private boolean isDamaged;
	public Map<Item, Integer> playerInventory;

	private float damageUpgradeModifier = 10;

	/**
	 * Create a new Ship object with provided name, speed, capacity, crew size, and max health
	 * */
	public Ship(String _name, float _speed, int _maxCapacity, int _crewSize, float _maxHealth) {
		name = _name;
		speed = _speed;
		maxCapacity = _maxCapacity;
		crewSize = _crewSize;
		maxHealth = _maxHealth;
		health = maxHealth;
		damage = 10;
		isDamaged = false;
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

	public int getCrewCount() {
		return crewSize;
	}

	public float getCrewTravelCost(int numDays) {
		return crewSize * dailyCrewMemberCost * numDays;
	}

	public float getSpeed() {
		return speed;
	}

	public float getDamage() {
		float baseDamage = damage;
		ArrayList<UpgradeItem> upgrades = getUpgrades();
		for (UpgradeItem item : upgrades) {
			if (item.getUpgradeType() == UpgradeItem.UpgradeType.damage) {
				baseDamage += damageUpgradeModifier;
			}
		}
		return baseDamage;
	}

	public float getHealth() {
		return health;
	}
	
	public void addItem(Item item, int quantity) {
		playerInventory.put(item, (playerInventory.get(item) + quantity));
	}
	
	public void removeItem(Item item, int quantity) {
		playerInventory.put(item, (playerInventory.get(item) - quantity));
	}

	public void clearInventory() {
		playerInventory = new HashMap<Item, Integer>();
	}
	
	public void showInventory(UI ui) {
		ui.showMessage("CURRENT PLAYER INVENTORY:");
		for(Item i : GameManager.items) {
			ui.showMessage(i.getName() + ": " + playerInventory.get(i));
		}
		// System.out.println("\n");
	}

	public float getGoodsValue() {
		float totalValue = 0;

		Map<Item, Integer> inventory = getInventory();
		for (Map.Entry<Item, Integer> entry : inventory.entrySet()) {
			Item item = entry.getKey();
			int quantity = entry.getValue();
			totalValue += item.getBaseValue() * quantity;
		}

		return totalValue;
	}

	public Map<Item, Integer> getInventory() {
		return playerInventory;
	}

	public void setDamageState(boolean _isDamaged) {
		isDamaged = _isDamaged;
	}

	public boolean getDamageState() {
		return isDamaged;
	}

	//public float getRepairCost() {
	//	return (maxHealth - health) * 5;
	//}

	//public void repairShip() {
	//	health = maxHealth;
	//}

	//public void takeDamage(float amount) {
	//	//TODO: game over if damage is enough
	//	health -= amount;
		
	//}

	private ArrayList<UpgradeItem> getUpgrades() {
		ArrayList<UpgradeItem> upgrades = new ArrayList<UpgradeItem>();
		for (Map.Entry<Item, Integer> entry : playerInventory.entrySet()) {
			Item item = entry.getKey();
			if (item instanceof UpgradeItem) {
				upgrades.add((UpgradeItem)item);
			}
		}
		return upgrades;
	}
}