import java.util.*;

/**
 * Handles interaction with Store
 * Store has items that can be sold and items that can be bought
 * item's value is 0.9 * value for buying and 1.1 * value for selling
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

	ArrayList<Item> sellables;
	ArrayList<Item> buyables;

	public void getSellableInventory() {

	}

	public void getBuyableItems() {

	}

	public void buyFromStore() {

	}

	public void sellToStore() {

	}

}
