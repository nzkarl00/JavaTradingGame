package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import game.*;
import encounters.*;

class WeatherEncounterTest {

	@Test
	void testEncounter() {
		Ship ship = new Ship("", 0, 0, 0, 0);
		Player player = new Player("", ship, null, 0);
		WeatherEncounter encounter = new WeatherEncounter();
		encounter.StartEncounter(player, null, null);
		assertTrue(ship.getDamageState());
	}

}
