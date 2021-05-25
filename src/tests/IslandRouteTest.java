package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;

import islands.*;
import encounters.*;

class IslandRouteTest {

	Island islandA;
	Island islandB;
	IslandRoute directRoute;
	IslandRoute midRoute;
	IslandRoute indirectRoute;
	TestableRandom rng;


	@BeforeEach
	public void init() {
		islandA = new Island("island a", 0, 0);
		islandB = new Island("island b", 10, 10);
		directRoute = new IslandRoute(islandA, islandB, 1);
		midRoute = new IslandRoute(islandA, islandB, 0.5f);
		indirectRoute = new IslandRoute(islandA, islandB, 0);
		rng = new TestableRandom();
	}

	@Test
	public void testDaysToTravel() {
		assertEquals(directRoute.getDaysToTravel(1), 15);
		assertEquals(indirectRoute.getDaysToTravel(1), 43);
		assertEquals(directRoute.getDaysToTravel(10), 2);
		assertEquals(indirectRoute.getDaysToTravel(10), 5);
	}

	@Test
	public void testGetNoEncounter() {
		rng.setNextFloat(0);
		EncounterEvent event = indirectRoute.getEncounter(rng);
		assertNull(event);
		event = midRoute.getEncounter(rng);
		assertNull(event);
	}

	@Test
	public void testGetEncounter() {
		rng.setNextFloat(0);
		EncounterEvent event = directRoute.getEncounter(rng);
		assertNotNull(event);
		rng.setNextFloat(1);
		event = midRoute.getEncounter(rng);
		assertNotNull(event);
	}

	@Test
	public void getPirateEncounter() {
		rng.setNextFloat(0);
		EncounterEvent event = directRoute.getEncounter(rng);
		assertTrue(event instanceof PirateEncounter);
	}

	@Test
	public void getWeatherEncounter() {
		rng.setNextFloat(0.3f);
		EncounterEvent event = directRoute.getEncounter(rng);
		assertTrue(event instanceof WeatherEncounter);
	}

	@Test
	public void getSailorEncounter() {
		rng.setNextFloat(0.6f);
		EncounterEvent event = directRoute.getEncounter(rng);
		assertTrue(event instanceof SailorEncounter);
	}

	@Test
	public void testGetString() {
		assertEquals(directRoute.getString(), "island a to island b");
	}

	@Test
	public void testGetDescription() {
		assertEquals(directRoute.getDescription(1), "15 days, 100.0% danger");
	}

	@Test
	public void testShortString() {
		assertEquals(directRoute.shortString(1), "island b, 15 days, 100.0% danger");
	}


	class TestableRandom extends Random {

		float nextFloat;

		public TestableRandom() {
			super();
			nextFloat = 0;
		}

		public void setNextFloat(float _float) {
			nextFloat = _float;
		}

		public float nextFloat() {
			return nextFloat;
		}
	}

}
