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
		stock = new HashMap<Item, Integer>();
		storeName = id;
	}
	
	public void getSellableInventory() {
		System.out.println("Sellable Inventory:");
		for(Item i : sellables) {
			System.out.println(i.getName() + " Quantity: " + stock.get(i));
		}
		System.out.println("");
	}
	
	public void getBuyableItems() {
		System.out.println("Buyable Inventory:");
		for(Item i : buyables) {
			System.out.println(i.getName() + " Quantity: " + stock.get(i));
		}
		System.out.println("");
	}
	
	public void stockItem(Item thingy, int quantity, boolean isSellable) {
		stock.put(thingy, quantity);
		if (isSellable) {
			sellables.add(thingy);
		} else {
			buyables.add(thingy);
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
		options.add("Exit store");

		int optionIndex = ui.queryListOfOptions("Would you like to buy, sell, or leave the store?", options);
		
		String message;
		int itemIndex = -1;
		switch(optionIndex) {
			case 0:
				//buy
				message = "Enter a number between 1 and " + buyables.size() + " to select an item to buy";
				itemIndex = ui.queryIntBetweenRange(message, 1, buyables.size());
				itemIndex -= 1;
				ui.showMessage("Buy item " + buyables.get(itemIndex).getName());
				break;
			case 1:
				//sell
				message = "Enter a number between 1 and " + sellables.size() + " to select an item to sell";
				itemIndex = ui.queryIntBetweenRange(message, 1, sellables.size());
				itemIndex -= 1;
				ui.showMessage("Sell item " + sellables.get(itemIndex).getName());
				break;
			case 2:
				ui.showMessage("Exiting store.");
				//exit
				return;
		}

		handleStoreOptions(player, ui);
	}

}
