package islands;
//handles initialisation of game objects - islands, stores, items

import java.util.ArrayList;
import java.util.Random;

import game.Item;
import game.Ship;
import game.UpgradeItem;
/**
 * Class to handle all the starting operations of the game, generating items, islands, stores, upgrades, routes, and inventories.
 *
 */
public class WorldCreator {

	/**
	 * Calls to create all starting islands and their routes.
	 * @return ArrayList<Island> containing all created islands
	 */
	public ArrayList<Island> createIslandsWithRoutes() {
		//so islands with routes etc attached
		ArrayList<Island> islands = initIslands();
		initRoutes(islands, 2);
		return islands;
	}

	/**
	 * Creates all islands and adds them to an arraylist to be returned.
	 * @return ArrayList<Island> containing all created islands
	 */
	private ArrayList<Island> initIslands() {
		ArrayList<Island> islands = new ArrayList<Island>();

		Island fracturedisle = new Island("Fractured Isle", 9, 7);	// ]
		Island smithscordrefuge = new Island("Smithscord Refuge", 5, 1); // ]
		Island penlycay = new Island("Penly Cay", 2, -10); // ] ------------------------Creating all the island objects for the game
		Island valganisland = new Island("Valgan Island", -4, -2); // ]
		Island stockstallenclave = new Island("Stockstall Enclave", -8, -8); // ]

		islands.add(fracturedisle);
		islands.add(smithscordrefuge);
		islands.add(penlycay);
		islands.add(valganisland);
		islands.add(stockstallenclave);

		return islands;
	}

	/**
	 * For each island, makes a route from itself to every other island on the map.
	 * This route will be added to island's internal list of routes.
	 */
	public void initRoutes(ArrayList<Island> islands, int numSimilarRoutes) {
		for (int i = 0; i < islands.size(); i++) {
			Island startIsland = islands.get(i);

			for (int j = 0; j < islands.size(); j++) {
				Island endIsland = islands.get(j);
				if (j == i) continue;

				for (int k = 0; k < numSimilarRoutes; k++) {
					float directness = (1f / numSimilarRoutes) * (k + 1);
					startIsland.addRoute(endIsland, directness);
				}
			}
		}
	}

	/**
	 * Creates all items for the game and adds them to an arraylist to be returned.
	 * @return ArrayList<Item> containing all created items for the game
	 */
	public ArrayList<Item> initItems() {
		ArrayList<Item> items = new ArrayList<Item>();

		Item Silk = new Item(50,"Silk",2);
		Item Linen = new Item(10,"Linen",3);
		Item Wine = new Item(20,"Wine",5);
		Item Cinnamon =  new Item(30,"Cinnamon",1);
		Item Saffron = new Item(100,"Saffron",1);
		
		items.add(Silk);
		items.add(Wine);
		items.add(Linen);
		items.add(Saffron);
		items.add(Cinnamon);	

		return items;
	}

	/**
	 * Creates all upgrade items for the game and adds them to an arraylist to be returned.
	 * @return ArrayList<UpgradeItem> containing all created upgrades
	 */
	public ArrayList<UpgradeItem> initUpgradeItems() {
		ArrayList<UpgradeItem> upgradeableItems = new ArrayList<UpgradeItem>();

		UpgradeItem cannon1 = new UpgradeItem(70, "Cannon 1", "Upgrades damage", 0, UpgradeItem.UpgradeType.damage);
		UpgradeItem cannon2 = new UpgradeItem(100, "Cannon 2", "Upgrades damage", 0, UpgradeItem.UpgradeType.damage);
		UpgradeItem cannon3 = new UpgradeItem(150, "Cannon 3", "Upgrades damage", 0, UpgradeItem.UpgradeType.damage);

		upgradeableItems.add(cannon1);
		upgradeableItems.add(cannon2);
		upgradeableItems.add(cannon3);

		return upgradeableItems;
	}

	/**
	 * Generates the starting inventories of all stores randomly, with between 0 and 20 of each item in stock.
	 * @param islands ArrayList<Island> of every island in the game
	 * @param items ArrayList<Item> of every item in the game
	 */
	public void initStoreInventories(ArrayList<Island> islands, ArrayList<Item> items) {
		Random rng = new Random(); 
		for(Island i : islands) {
			for(Item j : items) {
				Store store = i.getStore();
				store.addItem(j, rng.nextInt(20));
			}
		}
	}

	/**
	 * Creates the upgrade store and adds all upgrade items to its stock.
	 * @param upgradeItems ArrayList<UpgradeItem> of all upgrade items
	 * @return Store containing all stocked upgrades
	 */
	public Store initUpgradeStore(ArrayList<UpgradeItem> upgradeItems) {
		Store upgradeStore = new Store("upgrades");

		UpgradeItem damageUpgrade1 = upgradeItems.get(0);
		UpgradeItem damageUpgrade2 = upgradeItems.get(1);
		UpgradeItem damageUpgrade3 = upgradeItems.get(2);

		upgradeStore.addItem(damageUpgrade1, 1);
		upgradeStore.addItem(damageUpgrade2, 1);
		upgradeStore.addItem(damageUpgrade3, 1);

		return upgradeStore;
	}
	
	/**
	 * Creates the player inventory in their ship, initialising with 0 of all items.
	 * @param playership Ship with inventory to be populated
	 * @param items ArrayList<Item> with all items of the game
	 * @param upgradeableItems ArrayList<UpgradeItem> with all upgrades in the game
	 */
	public void initPlayerInventory(Ship playership, ArrayList<Item> items, ArrayList<UpgradeItem> upgradeableItems) {
		for(Item i : items) {
			playership.playerInventory.put(i, 0);
		}
		for(Item i : upgradeableItems) {
			playership.playerInventory.put(i, 0);
		}
	}

}
