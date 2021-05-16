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

	private ArrayList<Item> sellables;
	private ArrayList<Item> buyables;
	private Map<Item, Integer> stock;
	private String name;
	
	public Store(String id) {
		sellables = new ArrayList<Item>();
		buyables = new ArrayList<Item>();
		stock = new HashMap<Item, Integer>();
		name = id;
	}
	
	public void getSellableInventory() {
		for(Item i : sellables) {
			System.out.println("Sellable Inventory:");
			System.out.println(i.getName() + "\n");
		}
	}
	
	public void getBuyableItems() {
		for(Item i : buyables) {
			System.out.println("Buyable Inventory:");
			System.out.println(i.getName() + "\n");
		}
	}
	
	public void stockItem(Item thingy, int quantity) {
		stock.put(thingy, quantity);
	}
	
	public void printStock() {
		System.out.println(name.toUpperCase());
		for(Item i : GameManager.items) {
			System.out.println(i.getName() + ": " + stock.get(i));
		}
	}

}
