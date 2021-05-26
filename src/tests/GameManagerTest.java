package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.*;
import islands.*;

class GameManagerTest {

	GameManager manager;
	Player player;
	Ship ship;
	Island island;
	Store store;
	Item item;

	@BeforeEach
	void init() {
		manager = new GameManager();
		manager.configureAdventure("player name", 10, 1);

		player = manager.getPlayer();
		player.transferMoney(-player.getMoney()); //set player to having 0 funds for easier testability

		ship = player.getShip();
		island = player.getCurrentIsland();
		store = island.getStore();
		item = manager.items.get(0);
	}

	@Test
	void testRepairShipNoMoney() {
		String output = manager.repairShip();
		assertEquals(output, "Insufficient funds.");
	}

	@Test
	void testRepairShipSuccess() {
		float repairCost = 50;
		player.transferMoney(repairCost);
		String output = manager.repairShip();
		assertEquals(output, "Ship repaired for $50.0.");
	}

	@Test
	void testBuyItemNoMoney() {
		String output = manager.buyItem(item);
		assertEquals(output, "fail");
	}

	@Test
	void testBuyItemSuccess() {
		int itemPrice = player.getCurrentIsland().getStore().getPrice(item, true);
		player.transferMoney(itemPrice);
		String output = manager.buyItem(item);
		assertEquals(output, "Purchased " + item.getName() + " for $" + itemPrice);
	}
	
	@Test
	void testSellItemFail() {
		String output = manager.sellItem(item);
		assertEquals(output, "You don't have any " + item.getName());
	}

	@Test
	void testSellItemSuccess() {
		ship.addItem(item);
		int sellPrice = store.getPrice(item, false);
		String output = manager.sellItem(item);
		assertEquals(output, "Sold " + item.getName() + " for $" + sellPrice);
	}

}
