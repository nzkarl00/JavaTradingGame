package islands;
//handles init of game obejcts - islands, stores, items

import java.util.ArrayList;
import java.util.Random;

import game.Item;
import game.Ship;
import game.UpgradeItem;
import game.UpgradeItem.UpgradeType;

public class WorldCreator {

	public ArrayList<Island> createIslandsWithRoutes() {
		//so islands with routes etc attached
		ArrayList<Island> islands = initIslands();
		initRoutes(islands, 2);
		return islands;
	}

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
	 * for each island, make a route from itself to every other island on the map
	 * this route will be added to island's internal list of routes
	 * */
	private void initRoutes(ArrayList<Island> islands, int numSimilarRoutes) {

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

	public ArrayList<Item> initItems() {
		ArrayList<Item> items = new ArrayList<Item>();

		Item Silk = new Item(50,"Silk","A high quality textile.",2);
		Item Linen = new Item(10,"Linen","A generic textile.",3);
		Item Wine = new Item(20,"Wine","An alcoholic drink.",5);
		Item Cinnamon =  new Item(30,"Cinnamon","A generic spice.",1);
		Item Saffron = new Item(100,"Saffron","An exotic spice.",1);
		
		items.add(Silk);
		items.add(Wine);
		items.add(Linen);
		items.add(Saffron);
		items.add(Cinnamon);	

		return items;
	}

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

	public void initStoreInventories(ArrayList<Island> islands, ArrayList<Item> items) {
		Random rng = new Random(); 
		for(Island i : islands) {
			for(Item j : items) {
				Store store = i.getStore();
				store.addItem(j, rng.nextInt(20));
			}
		}
	}

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
	
	public void initPlayerInventory(Ship playership, ArrayList<Item> items, ArrayList<UpgradeItem> upgradeableItems) {
		for(Item i : items) {
			playership.playerInventory.put(i, 0);
		}
		for(Item i : upgradeableItems) {
			playership.playerInventory.put(i, 0);
		}
	}

}
