package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.*;
import islands.*;

class StoreTest {

	private Store store;
	private Player player;
	private Island island;
	private Ship ship;
	private Item item;

	@BeforeEach
	public void init() {
		player = createTestGameScene();
		island = player.getCurrentIsland();
		store = island.getStore();
		ship = player.getShip();
		item = new Item(1, "item", 1);
	}

	@Test
	void addItem() {
		store.addItem(item, 1);
		assertEquals(1, store.getItemQuantity(item));
	}

	@Test
	void removeItem() {
		store.addItem(item, 1);
		store.removeItem(item, 1);
		assertEquals(0, store.getItemQuantity(item));
	}

	@Test
	void readdItem() {
		store.addItem(item, 1);
		store.removeItem(item, 1);
		store.addItem(item, 1);
		assertEquals(1, store.getItemQuantity(item));
	}

	@Test
	void testBuySellThresholds() {
		/*
		 * item with quantity 1-4 should only be a sellable 
		 * item with quantity 5-15 should be sellable + buyable
		 * item with quantity 16+ should only be buyable
		 */
		testBuySellState(4, false, true);
		testBuySellState(5, true, true);
		testBuySellState(15, true, true);
		testBuySellState(16, true, false);
	}

	void testBuySellState(int quantity, boolean shouldBeBuyable, boolean shouldBeSellable) {
		store = new Store("test");
		store.addItem(item, quantity);
		ArrayList<Item> buyables = store.getBuyables();
		ArrayList<Item> sellables = store.getSellables();
		assertEquals(buyables.contains(item), shouldBeBuyable);
		assertEquals(sellables.contains(item), shouldBeSellable);
	}

	@Test
	void testShipRepairNoCash() {
		String output = store.repairShip(player, 1);
		assertEquals(output, "Insufficient funds.");
	}

	@Test
	void testShipRepairSuccess() {
		Player player = createTestGameScene();
		player.transferMoney(50);
		String output = player.getCurrentIsland().getStore().repairShip(player, 50);
		assertEquals(output, "Ship repaired for $50.0.");
	}

	Player createTestGameScene() {
		Island island = new Island("island name", 0, 0);
		Ship ship = new Ship("ship name", 10, 10, 10, 10, 10);
		Player player = new Player("player name", ship, island, 0);
		return player;
	}

	@Test
	void testPurchaseItemSuccess() {
		store.addItem(item, 1);
		player.getShip().registerInventoryItem(item);
		player.transferMoney(1);
		String output = store.purchaseItem(item, player);
		assertEquals(output, "Purchased item for $1");
	}

	@Test
	void testPurchaseItemNoCash() {
		store.addItem(item, 1);
		String output = store.purchaseItem(item, player);
		assertEquals(output, "fail");
	}

	@Test
	void testPurchaseItemNoCapacity() {
		Item item = new Item(1, "item", player.getShip().getRemainingCapacity() + 1);
		store.addItem(item, 1);
		player.transferMoney(1);
		String output = store.purchaseItem(item, player);
		assertEquals(output, "You don't have enough space for that!");
	}

	@Test
	void testSellItemFail() {
		store.addItem(item, 1);
		player.getShip().registerInventoryItem(item);
		String output = store.sellItem(item, player);
		assertEquals(output, "You don't have any item");
	}

	@Test
	void testSellItemSuccess() {
		store.addItem(item, 1);
		player.getShip().addItem(item);
		String output = store.sellItem(item, player);
		assertEquals(output, "Sold item for $1");
	}

	@Test
	void testAdjustedPriceLowQuantity() {
		Item item = new Item(10, "item", 1);
		store.addItem(item, 1);
		boolean isCorrectBuyingPrice = store.getPrice(item, true) == 15;
		boolean isCorrectSellingPrice = store.getPrice(item, false) == 14;
		assertTrue(isCorrectBuyingPrice && isCorrectSellingPrice);
	}

	@Test
	void testAdjustedPriceHighQuantity() {
		Item item = new Item(10, "item", 1);
		store.addItem(item, 30);
		boolean isCorrectBuyingPrice = store.getPrice(item, true) == 0;
		boolean isCorrectSellingPrice = store.getPrice(item, false) == 0;
		assertTrue(isCorrectBuyingPrice && isCorrectSellingPrice);
	}

}
