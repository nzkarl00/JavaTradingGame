package islands;
import java.util.*;
import game.Item;
import game.Player;
import game.UpgradeItem;

/**
 * Handles interaction with Store.
 * Store has items that can be sold and items that can be bought.
 * Item's value is 1.1 * value for buying and 0.9 * value for selling.
 * So contains two inventories full of inventory items - one for selling one for buying.
 * Items are in the buyables list if the store has more than 4
 * Items are in the sellables list if the store has less than 16
 * */

public class Store {

	private ArrayList<Item> sellables;
	private ArrayList<Item> buyables;
	private Map<Item, Integer> stock;
	
	/**
	 * Constructor for the Store, creates an empty list of sellables, buyables and an empty hashmap for stock.
	 * @param id String, currently unused, exists here in the event stores are named something different for output
	 */
	public Store(String id) {
		sellables = new ArrayList<Item>();
		buyables = new ArrayList<Item>();
		stock = new HashMap<Item, Integer>();
	}
	
	/**
	 * Gets the price of an item, this is determined by the base value and the amount the store has in stock.
	 * Prices are higher if there is a shortage, lower if there is a surplus.
	 * Prices are higher for buying than selling by default to encourage smart trading.
	 * @param good Item that is being price checked
	 * @param buying boolean, true if the player is buying from the store, false otherwise
	 * @return int price of the item in the store post modifiers
	 */
	public int getPrice(Item good, boolean buying) {
		double priceMod = stock.get(good);
		int adjustedPrice = 0;
		if (buying == true) {
			adjustedPrice = (int) (good.getBaseValue() * (1.55 - (priceMod / 20)));
		} else {
			adjustedPrice = (int) (good.getBaseValue() * (1.45 - (priceMod / 20)));
		}
		return adjustedPrice;
	}
	
	/**
	 * Gets the price of an item given a set quantity, used to calculate theoretical values of goods.
	 * @param item Item that is being checked
	 * @param priceMod int Quantity of the good in stock
	 * @param buying boolean, true if the player is buying from the store, false otherwise
	 * @return
	 */
	public int getModifiablePrice(Item item, double priceMod, boolean buying) {
		if (buying) {
			return (int)(item.getBaseValue() * (1.55 - (priceMod / 20)));
		} else {
			return (int)(item.getBaseValue() * (1.45 - (priceMod / 20)));
		}
	}
	
	/**
	 * Repairs the ship by deducting a specified amount of money from the player's money and setting damaged state to false.
	 * @param player Player
	 * @param repairCost float for how much the repair should cost
	 * @return String containing the transaction info, if it was successful or not
	 */
    public String repairShip(Player player, float repairCost) {
	boolean isBought = player.transferMoney(-repairCost);
		if (isBought) {
			player.getShip().setDamageState(false);
			return ("Ship repaired for $" + repairCost + ".");
		} else {
			return ("Insufficient funds.");
		}
	}

	
    /**
     * Adds a specified quantity of an item to the stock of the store.
     * @param item Item to be added
     * @param quantity int quantity to be added
     */
	public void addItem(Item item, int quantity) {
		if (stock.containsKey(item)) {
			stock.put(item, stock.get(item) + quantity);
		} else {
			stock.put(item, quantity);
		}

		if (item instanceof UpgradeItem) {
			addToItemList(item, buyables);
		} else {
			updateItemBuySellState(item, 5, 15);
		}
	}

	/**
	 * Removes a specified quantity of an item from the stock of the store
	 * @param item Item to be removed
	 * @param quantity int quantity to be removed
	 */
	public void removeItem(Item item, int quantity) {
		stock.put(item, stock.get(item) - quantity);

		if (!(item instanceof UpgradeItem)) {
			updateItemBuySellState(item, 5, 15);
		}
	}

	/**
	 * Updates the state of an item, adding it to buyable or sellable with custom thresholds instead of defaults 4 and 16.
	 * @param item Item to be updated
	 * @param minStockForBuyable int minimum value in stock to be in the buyables list
	 * @param maxStockForSellable int maximum value in stock to be in the sellables list
	 */
	void updateItemBuySellState(Item item, int minStockForBuyable, int maxStockForSellable) {
		if (stock.get(item) < minStockForBuyable) {
			//make this item only a sellable 
			addToItemList(item, sellables);
			removeFromItemList(item, buyables);

		} else if (stock.get(item) > maxStockForSellable) {
			//make this item only a buyable
			addToItemList(item, buyables);
			removeFromItemList(item, sellables);

		} else {
			//make this item both a sellable and a buyable
			addToItemList(item, sellables);
			addToItemList(item, buyables);

		}
	}

	/**
	 * Adds an item to a given list.
	 * @param item Item to be added
	 * @param list ArrayList<Item> list for the item to be added to
	 */
	void addToItemList(Item item, ArrayList<Item> list) {
		if (!list.contains(item)) {
			list.add(item);
		}
	}

	/**
	 * Removes an item from a given list.
	 * @param item Item to be removed
	 * @param list ArrayList<Item> list for the item to be removed from
	 */
	void removeFromItemList(Item item, ArrayList<Item> list) {
		if (list.contains(item)) {
			list.remove(item);
		}
	}
	
	/**
	 * Purchases an item from the store by checking if the player has enough money first, returning fail if they do not.
	 * If the player has enough money checks the remaining space on the players ship.
	 * If both conditions are met, the amount of the given item in stock is decreased by 1, and player money is decreased by price.
	 * If successful one of the item is added to the player's ship inventory.
	 * @param item Item to be purchased
	 * @param player Player purchasing the item
	 * @return String detailing what occurred in the transaction, which is eventually printed in the GUI
	 */
	public String purchaseItem(Item item, Player player) {
		int total = getPrice(item, true);
		if (player.getMoney() < total) {
			return "fail";
		}
		if (player.getShip().getRemainingCapacity() < item.getWeight()) {
			return "You don't have enough space for that!";
		}
		total *= -1;
		player.transferMoney(total);
		player.getShip().addItem(item);
		removeItem(item, 1);
		return ("Purchased " + item.getName() + " for $" + total*-1);
	}
	
	/**
	 * Sells an item from player's ship inventory to the store, first checking if they own any of that item.
	 * If the player has an item to sell, amount in player ship inventory is decreased by one and amount in store inventory is increased by one.
	 * Adds money to the player equal to the sale value.
	 * @param item
	 * @param player
	 * @return String detailing what occurred in the transaction, which is eventually printed in the GUI
	 */
	public String sellItem(Item item, Player player) {
		int quantityOwned = player.getShip().playerInventory.get(item);
		if (quantityOwned == 0) {
			return ("You don't have any " + item.getName());
		} else {
			int total = (int) (getPrice(item, false));
			player.transferMoney(total);
			addItem(item, 1);
			player.getShip().removeItem(item);
			return ("Sold " + item.getName() + " for $" + total);
		}
	}
	
	/**
	 * Getter for sellables
	 * @return ArrayList<Item> sellables
	 */
	public ArrayList<Item> getSellables() {
		return sellables;
	}
	
	/**
	 * Getter for buyables
	 * @return ArrayList<Item> buyables
	 */
	public ArrayList<Item> getBuyables() {
		return buyables;
	}
	
	/**
	 * Gets a string of the price of all items in buyables, separated by \n.
	 * @return String of the price list
	 */
	public String buyPriceList() {
		String prices = "";
		for (Item i: buyables) {
			prices = prices + "$" + getPrice(i, true) + "\n";
		}
		return prices;
	}
	
	/**
	 * Gets a string of the price of all items in sellables, separated by \n.
	 * @return String of the price list
	 */
	public String sellPriceList() {
		String prices = "";
		for (Item i: sellables) {
			prices = prices + "$" + getPrice(i, false) + "\n";
		}
		return prices;
	}
	
	/**
	 * Gets the quantity of an item in stock at the store
	 * @param item Item to check quantity
	 * @return int quantity of the item pulled from the hashmap stock
	 */
	public int getItemQuantity(Item item) {
		return stock.get(item);
	}


}
