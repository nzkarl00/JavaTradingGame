package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.*;
import islands.*;

class StoreTest {

	private Store testStore;

	@BeforeEach
	public void init() {
		testStore = new Store("test-store");
	}

	@Test
	void addItem() {
		Item testItem = new Item(1, "item-1", "desc", 1);
		testStore.addItem(testItem, 1);
		assertEquals(1, testStore.getItemQuantity(testItem));
	}

	@Test
	void removeItem() {
		Item testItem = new Item(1, "item-1", "desc", 1);
		testStore.addItem(testItem, 1);
		testStore.removeItem(testItem, 1);
		assertEquals(0, testStore.getItemQuantity(testItem));
	}

	@Test
	void readdItem() {
		Item testItem = new Item(1, "item-1", "desc", 1);
		testStore.addItem(testItem, 1);
		testStore.removeItem(testItem, 1);
		testStore.addItem(testItem, 1);
		assertEquals(1, testStore.getItemQuantity(testItem));
	}

	/*
	 * test:
	 * quantity should be correct when item is added or bought
	 * quanity should not fall below 0 when stock=1 is bought
	 * stock=0 to stock=1 should work
	 * test basevalue
	 * */


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
		Item testItem = new Item(1, "item-1", "desc", 1);
		testStore.addItem(testItem, quantity);
		ArrayList<Item> buyables = testStore.getBuyables();
		ArrayList<Item> sellables = testStore.getSellables();
		assertEquals(buyables.contains(testItem), shouldBeBuyable);
		assertEquals(sellables.contains(testItem), shouldBeSellable);
	}

	//WILL CURRENTLY FAIL
	//@Test
	void testShipRepairNoCash() {
		Player player = createTestGameScene();
		Island s = player.getCurrentIsland();
		String output = player.getCurrentIsland().getStore().repairShip();
		assertEquals(output, "Insufficient funds.");
	}

	//@Test
	void testShipRepairSuccess() {
		Player player = createTestGameScene();
		player.transferMoney(50);
		String output = player.getCurrentIsland().getStore().repairShip();
		assertEquals(output, "Ship repaired.");
	}

	Player createTestGameScene() {
		Island island = new Island("island name", 0, 0);
		Ship ship = new Ship("ship name", 10, 10, 10, 10);
		Player player = new Player("player name", ship, island, 0);
		return player;
	}

}
