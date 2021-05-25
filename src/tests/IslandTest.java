package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import islands.*;
import java.util.*;

class IslandTest {

	Island island;
	Island endIslandA;
	Island endIslandB;

	@BeforeEach
	void init() {
		island = new Island("island", 0, 0);
		endIslandA = new Island("end A", 10, 0);
		endIslandB = new Island("end B", 0, 5);
		island.addRoute(endIslandA, 1);
		island.addRoute(endIslandA, 0);
		island.addRoute(endIslandB, 1);
		island.addRoute(endIslandB, 0);
	}

	@Test
	void testToString() {
		assertEquals(island.toString(), "island, found at (0,0)");
	}

	@Test
	void testGetRoutesDescription() {
		ArrayList<String> routes = island.getRoutesDescriptions(1);
		assertEquals(routes.get(0), "10 days, 100.0% danger");
		assertEquals(routes.get(1), "30 days, 0.0% danger");
		assertEquals(routes.get(2), "5 days, 100.0% danger");
		assertEquals(routes.get(3), "15 days, 0.0% danger");
	}

	@Test
	void testGetRoutesToIslandDescription() {
		String routes = island.getRoutesToIslandDescriptions(1, endIslandA);
		assertEquals(routes, "10 days, 100.0% danger\n30 days, 0.0% danger\n");
		routes = island.getRoutesToIslandDescriptions(1, endIslandB);
		assertEquals(routes, "5 days, 100.0% danger\n15 days, 0.0% danger\n");
	}
	
	@Test
	void testAddRoute() {
		island = new Island("island", 0, 0);
		Island endIslandC = new Island("island C", 10, 10);

		island.addRoute(endIslandC, 1);
		IslandRoute route = island.getRoutes().get(0);
		assertEquals(route.getStartIsland(), island);
		assertEquals(route.getEndIsland(), endIslandC);
		assertEquals(route.getDirectness(), 1);

		island.addRoute(endIslandC, 1);
		assertEquals(island.getRoutes().size(), 1); //should not have two identical routes
	}

}
