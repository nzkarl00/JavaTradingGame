package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.*;
import islands.*;
import encounters.*;

class PlayerTest {

	@Test
	void testMoveToNewIslandNoMoney() {
		Ship ship = new Ship("", 1, 0, 1, 0, 0);
		Player player = new Player("player", ship, null, 0);
		GameEventNotifier notifier = new GameEventNotifier();

		String output = player.moveToNewIsland(setupRoute(), notifier);
		assertEquals("Insufficient funds to travel to island.\n", output);
	}

	@Test
	void testMoveToNewIslandSuccess() {
		Ship ship = new Ship("", 1, 0, 1, 0, 0);
		Player player = new Player("player", ship, null, 0);
		GameEventNotifier notifier = new GameEventNotifier();
		IslandRoute route = setupRoute();

		float moneyNeeded = ship.getCrewTravelCost(route.getDaysToTravel(ship.getSpeed()));
		player.transferMoney(moneyNeeded);

		String output = player.moveToNewIsland(setupRoute(), notifier);
		assertEquals("Arrived at island b.\n", output);
	}

	IslandRoute setupRoute() {
		Island islandA = new Island("island a", 0, 0);
		Island islandB = new Island("island b", 1, 0);
		IslandRoute route = new IslandRoute(islandA, islandB, 0);
		return route;
	}

	@Test
	void testTransferMoney() {
		Player player = new Player("player", null, null, 0);
		player.transferMoney(1);
		assertEquals(player.getMoney(), 1);

		player.transferMoney(-1);
		assertEquals(player.getMoney(), 0);

		boolean success = player.transferMoney(-1);
		assertFalse(success);
	}

}
