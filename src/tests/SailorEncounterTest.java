package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import game.*;
import encounters.*;

class SailorEncounterTest {

	@Test
	void testEncounter() {
		Player player = new Player("", null, null, 0);
		SailorEncounter encounter = new SailorEncounter();
		encounter.StartEncounter(player, null, null);
		int moneyFromEncounter = 40;
		assertEquals(player.getMoney(), moneyFromEncounter);
	}

}
