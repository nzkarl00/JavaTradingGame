package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.*;
import islands.Store;
import java.util.*;

class ShipTest {

	Ship ship;

	@BeforeEach
	void init() {
		ship = new Ship("ship", 10, 10, 10, 10);
	}

	@Test
	void testToString() {
		Ship ship = new Ship("ship", 10, 10, 10, 10);
		String output = ship.toString();
		assertEquals(output, "Class: ship\nSpeed: 10.0\nCapacity: 10\nCrew members: 10\nMax health: 10.0\n\nCurrent inventory: \n");
	}

	@Test
	void testGetCrewTravelCost() {
		float costForOneDay = ship.getCrewTravelCost(1);
		assertEquals(costForOneDay, 10 * 3 * 1);

		float costForMultipleDays = ship.getCrewTravelCost(2);
		assertEquals(costForMultipleDays, 10 * 3 * 2);
	}

	@Test
	void testGetDamage() {
		//test ship with no upgrades
		float damage = 10;
		float damageUpgradeModifier = 10;
		assertEquals(ship.getDamage(), damage);

		//test one upgrade
		UpgradeItem damageUpgrade = new UpgradeItem(1, "damageUpgrade", "", 1, UpgradeItem.UpgradeType.damage);
		ship.addItem(damageUpgrade);
		assertEquals(ship.getDamage(), damage + damageUpgradeModifier);

		//a specific upgrade should only be bought once - should never have two of the same upgrade active on ship
		//but check a second upgrade doesn't apply if two are somehow bought
		ship.addItem(damageUpgrade);
		assertEquals(ship.getDamage(), damage + damageUpgradeModifier);

		//test multiple upgrade items
		UpgradeItem damageUpgrade2 = new UpgradeItem(1, "damageUpgrade", "", 1, UpgradeItem.UpgradeType.damage);
		ship.addItem(damageUpgrade2);
		assertEquals(ship.getDamage(), damage + damageUpgradeModifier * 2);
	}

	@Test
	void testAddItem() {
		Map<Item, Integer> inventory = ship.getInventory(); //saved as reference so don't need to keep referring to it

		Item itemA = new Item(1, "itemA", "", 1);
		ship.addItem(itemA);
		assertTrue(inventory.containsKey(itemA));
		assertTrue(inventory.get(itemA) == 1); //ensure quantity of itemA is 1

		ship.addItem(itemA);
		assertTrue(inventory.get(itemA) == 2);

		Item itemB = new Item(1, "itemB", "", 1);
		ship.addItem(itemB);
		assertTrue(inventory.containsKey(itemB));
		assertTrue(inventory.get(itemB) == 1);
	}

	@Test
	void testRemoveItem() {
		Map<Item, Integer> inventory = ship.getInventory(); //saved as reference so don't need to keep referring to it

		Item itemA = new Item(1, "itemA", "", 1);
		ship.addItem(itemA);
		ship.removeItem(itemA);
		assertTrue(inventory.get(itemA) == 0); 

		//TODO: add if know how to throw exception
// 		ship.removeItem(itemA);

// 		Item itemB = new Item(1, "itemB", "", 1);
// 		ship.removeItem(itemB);
	}

	@Test
	void testShowInventory() {
		String output = ship.showInventory(); //test on blank ship
		assertEquals(output, "\n");

		Item itemA = new Item(1, "itemA", "", 1);
		ship.addItem(itemA);
		output = ship.showInventory();
		assertEquals(output, "\nitemA x1");

		ship.addItem(itemA);
		output = ship.showInventory();
		assertEquals(output, "\nitemA x2");

		Item itemB = new Item(1, "itemB", "", 1);
		ship.addItem(itemB);
		output = ship.showInventory();
		assertEquals(output, "\nitemA x2\nitemB x1");
	}

	@Test
	void testGetGoodsValue() {
		Store store = new Store("a");
		Ship ship = new Ship("", 0, 10, 0, 0);
		Item itemA = new Item(1, "itemA", "", 1);
		Item itemB = new Item(2, "itemB", "", 1);
		ship.addItem(itemA);
		ship.addItem(itemB);
		store.addItem(itemA, 1);
		store.addItem(itemB, 1);

		int val = ship.getGoodsValue(store);
		assertEquals(val, 4);
	}

}
