import java.util.*;

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
		// upgradeItems = new ArrayList<Item>();
		stock = new HashMap<Item, Integer>();
		storeName = id;
	}
	
	public void getSellableInventory() {
		System.out.println("Sellable Inventory:");
		showItemList(sellables);
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
	
	public void getBuyableItems() {
		System.out.println("Buyable Inventory:");
		showItemList(buyables);
		// System.out.println("Upgrades:");
		// showItemList(upgradeItems);
	}

	// public void getUpgradeItems() {
	// 	showItemList(upgradeItems);
	// }

	private void showItemList(ArrayList<Item> items) {
		for(Item i : items) {
			System.out.println(i.getName() + " Quantity: " + stock.get(i));
		}
		System.out.println("");
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
		getSellableInventory();
		getBuyableItems();
		player.getShip().showInventory(ui);

		handleStoreOptions(player, ui);
	}

	private void handleStoreOptions(Player player, UI ui) {
		ArrayList<String> options = new ArrayList<String>();
		options.add("Buy from store");
		options.add("Sell to store");
		options.add("Repair ship");
		options.add("Exit store");

		int optionIndex = ui.queryListOfOptions("Would you like to buy, sell, repair your ship, or leave the store?", options);
		
		String message;
		int itemIndex = -1;
		switch(optionIndex) {
			case 0:
				//buy
				message = "Enter a number between 1 and " + buyables.size() + " to select an item to buy";
				itemIndex = ui.queryIntBetweenRange(message, 1, buyables.size());
				Item selectedItem = buyables.get(itemIndex - 1);
				purchaseItem(selectedItem, player, ui);
				break;
			case 1:
				//sell
				message = "Enter a number between 1 and " + sellables.size() + " to select an item to sell";
				itemIndex = ui.queryIntBetweenRange(message, 1, sellables.size());
				Item forSale = sellables.get(itemIndex - 1);
				sellItem(forSale, player, ui);
				break;
			case 2:
				float repairCost = 50;
				message = "It will cost $" + repairCost + " to repair the ship. Answer 0 for no or 1 for yes.";
				int reply = ui.queryIntBetweenRange(message, 0, 1);
				if (reply == 1) {
					boolean isBought = player.transferMoney(-repairCost, ui);
					if (isBought) {
						player.getShip().setDamageState(false);
						ui.showMessage("Ship repaired.");
					} else {
						ui.showMessage("Insufficient funds.");
					}
				}
				break;
			case 3:
				ui.showMessage("Exiting store.");
				//exit
				return;
		}

		handleStoreOptions(player, ui);
	}
	
	public void purchaseItem(Item item, Player player, UI ui) {
		String message = ("Enter quantity, " + stock.get(item) + " in stock for $" + getPrice(item, true) + " each. You have $" + player.getMoney() + ".");
		int purchaseQuantity = ui.queryIntBetweenRange(message, 1, stock.get(item));
		int total = (int) (getPrice(item, true) * purchaseQuantity);
		if (player.getMoney() < total) {
			System.out.println("Insufficient funds.");
		} else {
			String purchaseMessage = "Successfully purchased " + purchaseQuantity + " " + item.getName() + " for $" + total + ".";
			total *= -1;
			player.transferMoney(total, ui);
			player.getShip().addItem(item, purchaseQuantity);
			System.out.println(purchaseMessage + " You have $" + player.getMoney() + " remaining.");
			removeItem(item, purchaseQuantity);
		}
	}
	
	public void sellItem(Item item, Player player, UI ui) {
		String message = ("Enter quantity, purchasing price of " + item.getName() + " is " + getPrice(item, false) + " per unit.");
		int quantityOwned = player.getShip().playerInventory.get(item);
		if (quantityOwned == 0) {
			System.out.println("You don't have any " + item.getName());
		} else {
			int saleQuantity = ui.queryIntBetweenRange(message, 0, quantityOwned);
			int total = (int) (getPrice(item, false) * saleQuantity);
			player.transferMoney(total, ui);
			player.getShip().removeItem(item, saleQuantity);
			System.out.println("Successfully sold " + saleQuantity + " " + item.getName() + " for $" + total + ".");
			System.out.println("You now have $" + player.getMoney() + ".");
			addItem(item, saleQuantity);
		}
	}
}
