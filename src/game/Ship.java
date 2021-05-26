package game;

import java.util.HashMap;
import java.util.Map;

import islands.Store;

import java.util.*;

/**
 * All inventory and Ship capacity/combat functionality.
 * 
 * A Ship can:
 * -Contain crew members that must be paid
 * -Carry cargo, up to a maximum limit
 * -Be upgraded
 * -Attack other ships
 *
 * Variables:
 * name String identifier
 * speed float detailing how fast the ship goes
 * maxCapacity int of the maximum carrying capacity of the ship's inventory
 * crewSize int number of crew on the ship
 * dailyCrewMemberCost float amount each crew member must be paid for a day of travel
 * maxHealth float maximum damage a ship can take before being sunk
 * health float current remaining health of the ship
 * damange float amount of health missing from the ship
 * isDamaged boolean if the ship has taken damage or not
 * playerInventory Map<Item, Integer> of each item and the quantity carried by the player
 * */

public class Ship {

	private String name;
	private float speed;
	private int maxCapacity;
	private int crewSize;
	private float dailyCrewMemberCost = 3;
	private float maxHealth;
	private float health;
	private float damage;
	private boolean isDamaged;
	public Map<Item, Integer> playerInventory;

	private float damageUpgradeModifier = 10;

	/**
	 * Constructor for Ship, sets all stats for it.
	 * @param _name String identifier for the ship's title
	 * @param _speed float speed of the selected class of ship
	 * @param _maxCapacity float inventory capacity of the selected class of ship
	 * @param _crewSize int number of crew members present on the selected class of ship
	 * @param _maxHealth float maximum health capacity of the selected class of ship
	 * @param _damage damage value of the ship
	 */
	public Ship(String _name, float _speed, int _maxCapacity, int _crewSize, float _maxHealth, float _damage) {
		name = _name;
		speed = _speed;
		maxCapacity = _maxCapacity;
		crewSize = _crewSize;
		maxHealth = _maxHealth;
		health = maxHealth;
		damage = _damage;
		isDamaged = false;
		playerInventory = new HashMap<Item, Integer>();
	}

	/**
	 * New toString method to better view all details of the Ship.
	 * @return String An easy-to-read overview of this Ship's properties
	 * */
	@Override
	public String toString() {
		String output = "Class: " + name + "\n"
			+ "Speed: " + speed + "\n"
			+ "Capacity: " + maxCapacity + "\n"
			+ "Crew members: " + crewSize + "\n"
			+ "Max health: " + maxHealth + "\n"
			+ "\nCurrent inventory: " + showInventory();
		return output;
	}

	/**
	 * Getter for name.
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for crewSize.
	 * @return int crewSize
	 */
	public int getCrewCount() {
		return crewSize;
	}

	/**
	 * Getter for the cost of a journey. Multiplies crewSize by dailyCrewMemberCost by number of days in the journey.
	 * @param numDays int number of days in the journey
	 * @return float total cost of the journey
	 */
	public float getCrewTravelCost(int numDays) {
		return crewSize * dailyCrewMemberCost * numDays;
	}

	/** 
	 * Getter for the speed of the Ship.
	 * @return float speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * Gets the current damage of the Ship including any upgrades that have been purchased during the game.
	 * @return float post-modifier damage of the Ship.
	 */
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

	/**
	 * Getter for the current health value of the Ship.
	 * @return float health
	 */
	public float getHealth() {
		return health;
	}
	
	/**
	 * Adds an item to the Ship's inventory, or creates a new entry for it in the inventory if it is not already present.
	 * @param item Item object passed from the store
	 */
	public void addItem(Item item) {
		if (playerInventory.containsKey(item)) {
			playerInventory.put(item, (playerInventory.get(item) + 1));
		} else {
			playerInventory.put(item, 1);
		}
	}
	
	/**
	 * Removes an item from the Ship's inventory.
	 * @param item Item object passed from the store
	 */
	public void removeItem(Item item) {
		if (playerInventory.containsKey(item)) {
			playerInventory.put(item, (playerInventory.get(item) - 1));
		} else {
			//error
		}
	}
	
	/**
	 * Resets the Ship's inventory to a blank state, currently only occurs after a pirate raid.
	 */
	public void clearInventory() {
		playerInventory = new HashMap<Item, Integer>();
	}
	
	/**
	 * Returns a string of all items present in the Ship's inventory, and their quantities.
	 * @return String containing all items and quantities
	 */
	public String showInventory() {
		//function rewritten to be easier to test reliably
		ArrayList<String> items = new ArrayList<String>();

		for (Map.Entry<Item, Integer> entry : playerInventory.entrySet()) {
			Item item = entry.getKey();
			int quantity = entry.getValue();
			
			items.add(item.getName() + " x" + quantity);
		}

		items.sort(String::compareToIgnoreCase);
		return "\n" + String.join("\n", items);
	}

	/**
	 * Gets the value of all goods present in the Ship's inventory if they were to be sold at the store of the given island.
	 * @param store Store selected to get prices from
	 * @return int of the total value of goods
	 */
	public int getGoodsValue(Store store) { //value of goods sold at store or base value if no store provided
		int totalValue = 0;

		Map<Item, Integer> inventory = getInventory();
		for (Map.Entry<Item, Integer> entry : inventory.entrySet()) {
			Item item = entry.getKey();
			if (item instanceof UpgradeItem) {
				continue;
			}
			int playerQuantity = entry.getValue();
			int price = 0;
			if (store != null) {
				int storeQuantity = store.getItemQuantity(item);  
				int priceMod = storeQuantity;
				while (priceMod > 0) {
					price += store.getModifiablePrice(item, priceMod, true);
					priceMod -= 1;
				}
			} else {
				price = (int)item.getBaseValue();
				price *= playerQuantity;
			}
			totalValue += price;
		}

		return totalValue;
	}


	/**
	 * Getter for the Ship's inventory.
	 * @return Map<Item, Integer> playerInventory
	 */
	public Map<Item, Integer> getInventory() {
		return playerInventory;
	}

	/**
	 * Setter for the damaged status.
	 * @param _isDamaged true if the ship is damaged, false otherwise
	 */
	public void setDamageState(boolean _isDamaged) {
		isDamaged = _isDamaged;
	}

	/** Getter for the damage status.
	 * @return boolean isDamaged, true if damaged, false otherwise
	 */
	public boolean getDamageState() {
		return isDamaged;
	}

	/**
	 * Gets all of the upgrades the player has purchased.
	 * @return ArrayList<UpgradeItem> containing all purchased upgrades
	 */
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

	/**
	 * Getter for the maximum capacity of the Ship.
	 * @return int maxCapacity
	 */
	public int getMaxCapacity() {
		return maxCapacity;
	}

	/**
	 * Getter for the remaining capacity of the Ship.
	 * @return int remainingCapacity
	 */
	public int getRemainingCapacity() {
		int weight = 0;

		Map<Item, Integer> inventory = getInventory();
		for (Map.Entry<Item, Integer> entry : inventory.entrySet()) {
			Item item = entry.getKey();
			int quantity = entry.getValue();
			weight += item.getWeight() * quantity;
		}

		return maxCapacity - weight;
	}

}
