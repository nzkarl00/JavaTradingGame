package islands;
import java.util.*;

import game.GameManager;
import game.Item;
import game.Player;
import game.UI;
import game.UpgradeItem;

/**
 * Handles interaction with Store
 * Store has items that can be sold and items that can be bought
 * item's value is 1.1 * value for buying and 0.9 * value for selling
 * so contains two inventories full of inventory items - one for selling one for buying
 * Inventory vs InventoryItem?
 * 
 * do stores have a set amount of cash they can buy items for? ie if store runs out of cash you have to sell it more in order to buy from them
 *
 * will need an interface for actually interacting with the store - this interface should be supplied by the command line or gui
 * so probably code for interface should be passed over to class that manages that
 * 
 * */

public class Store {

	private String storeName;
	private ArrayList<Item> sellables;
	private ArrayList<Item> buyables;
	private Map<Item, Integer> stock;
	
	public Store(String id) {
		sellables = new ArrayList<Item>();
		buyables = new ArrayList<Item>();
		stock = new HashMap<Item, Integer>();
		storeName = id;
	}
	
	public void printSellableInventory() {
		System.out.println("Sellable Inventory:");
		for(Item i : sellables) {
			System.out.println(i.getName() + " Quantity: " + stock.get(i));
		}
		System.out.println("");
	}
	
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
	
	public int getModifiablePrice(Item item, double priceMod, boolean buying) {
		if (buying) {
			return (int)(item.getBaseValue() * (1.55 - (priceMod / 20)));
		} else {
			return (int)(item.getBaseValue() * (1.45 - (priceMod / 20)));
		}
	}

	
	public void getBuyableItems() {
		System.out.println("Buyable Inventory:");
		for(Item i : buyables) {
			System.out.println(i.getName() + " Quantity: " + stock.get(i));
		}
		System.out.println("");
	}
	
    public String repairShip(Player player) {
	float repairCost = 50;
	boolean isBought = player.transferMoney(-repairCost);
		if (isBought) {
			player.getShip().setDamageState(false);
			return ("Ship repaired for $50.");
		} else {
			return ("Insufficient funds.");
		}
		}

	

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

	
	public void removeItem(Item item, int quantity) {
		stock.put(item, stock.get(item) - quantity); //should probably handle what happens if quantity is already 0 (likely error)

		if (!(item instanceof UpgradeItem)) {
			updateItemBuySellState(item, 5, 15);
		}
	}

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

	void addToItemList(Item item, ArrayList<Item> list) {
		if (!list.contains(item)) {
			list.add(item);
		}
	}

	void removeFromItemList(Item item, ArrayList<Item> list) {
		if (list.contains(item)) {
			list.remove(item);
		}
	}

	
	public void printStock() {
		System.out.println(storeName + " Store:");
		for(Item i : GameManager.items) {
			System.out.println(i.getName() + ": " + stock.get(i));
		}
	}

	public void visitStore(Player player, UI ui) {
		// ui.showMessage("You are at " + island.getName() + "'s store. What would you like to do?");
		printSellableInventory();
		getBuyableItems();
		player.getShip().showInventory();

		handleStoreOptions(player, ui);
	}

	private void handleStoreOptions(Player player, UI ui) {
		ArrayList<String> options = new ArrayList<String>();
		options.add("Buy from store");
		options.add("Sell to store");
		options.add("Exit store");

		int optionIndex = ui.queryListOfOptions("Would you like to buy, sell, or leave the store?", options);
		
		String message;
		int itemIndex = -1;
		switch(optionIndex) {
			case 0:
				//buy
				message = "Enter a number between 1 and " + buyables.size() + " to select an item to buy";
				itemIndex = ui.queryIntBetweenRange(message, 1, buyables.size());
				Item selectedItem = buyables.get(itemIndex - 1);
				purchaseItem(selectedItem, player);
				break;
			case 1:
				//sell
				message = "Enter a number between 1 and " + sellables.size() + " to select an item to sell";
				itemIndex = ui.queryIntBetweenRange(message, 1, sellables.size());
				Item forSale = sellables.get(itemIndex - 1);
				sellItem(forSale, player);
				break;
			case 2:
				ui.showMessage("Exiting store.");
				//exit
				return;
		}

		handleStoreOptions(player, ui);
	}
	
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
	
	public ArrayList<Item> getSellables() {
		return sellables;
	}
	
	public ArrayList<Item> getBuyables() {
		return buyables;
	}
	
	public String buyPriceList() {
		String prices = "";
		for (Item i: buyables) {
			prices = prices + "$" + getPrice(i, true) + "\n";
		}
		return prices;
	}
	
	public String sellPriceList() {
		String prices = "";
		for (Item i: sellables) {
			prices = prices + "$" + getPrice(i, false) + "\n";
		}
		return prices;
	}
	
	public int getItemQuantity(Item item) {
		return stock.get(item);
	}


}
