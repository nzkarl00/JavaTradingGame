package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import islands.*;
import game.*;
import java.util.*;

class WorldCreatorTest {

	WorldCreator creator;
	
	@BeforeEach
	void init() {
		creator = new WorldCreator();
	}

	@Test
	void testInitRoutesEmpty() {
		ArrayList<Island> islands = new ArrayList<Island>();
		Island islandA = new Island("a", 0, 0);
		islands.add(islandA);
		creator.initRoutes(islands, 0);
		ArrayList<IslandRoute> routes = islands.get(0).getRoutes();
		assertTrue(routes.isEmpty());
	}

	@Test
	void testInitRoutes() {
		ArrayList<Island> islands = new ArrayList<Island>();

		Island islandA = new Island("a", 0, 0);
		Island islandB = new Island("b", 0, 0);
		Island islandC = new Island("c", 0, 0);

		islands.add(islandA);
		islands.add(islandB);
		islands.add(islandC);

		creator.initRoutes(islands, 2);

		ArrayList<IslandRoute> routesA = islandA.getRoutes();
		//first 2 should lead to islandB, last 2 should lead to islandC
		boolean route1Correct = checkRouteProperties(routesA.get(0), islandA, islandB, 0.5f);
		boolean route2Correct = checkRouteProperties(routesA.get(1), islandA, islandB, 1);
		boolean route3Correct = checkRouteProperties(routesA.get(2), islandA, islandC, 0.5f);
		boolean route4Correct = checkRouteProperties(routesA.get(3), islandA, islandC, 1);
		
		assertTrue(route1Correct && route2Correct && route3Correct && route4Correct);
	}

	boolean checkRouteProperties(IslandRoute route, Island fromIsland, Island toIsland, float directness) {
		boolean areIslandsCorrect = route.getStartIsland() == fromIsland && route.getEndIsland() == toIsland;
		float routeDirectness = route.getDirectness();
		boolean isDirectnessCorrect = Math.abs(routeDirectness - directness) < 0.01f; //do semi-vague comparison to account for precision issues
		return areIslandsCorrect && isDirectnessCorrect;
	}

	@Test
	void testInitStoreInventories() {
		ArrayList<Island> islands = generateIslandList(2);
		ArrayList<Item> items = creator.initItems();
		creator.initStoreInventories(islands, items);
		
		//check each island's store has items of random quantity added
		boolean allStoresStocked = true;
		for (Island island : islands) {

			ArrayList<Item> buyables = island.getStore().getBuyables();
			ArrayList<Item> sellables = island.getStore().getSellables();
			boolean storeContainsItems = buyables.size() > 0 || sellables.size() > 0;

			if (!storeContainsItems) {
				allStoresStocked = false;
			}
		}
		assertTrue(allStoresStocked);
	}

	ArrayList<Island> generateIslandList(int numIslands) {
		ArrayList<Island> islands = new ArrayList<Island>();
		for (int i = 0; i < numIslands; i++) {
			islands.add(new Island("Island " + i, i, 0));
		}
		return islands;
	}

}
