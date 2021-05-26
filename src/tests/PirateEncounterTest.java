package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.*;
import encounters.*;

class PirateEncounterTest {

	PirateEncounter encounter;

	@BeforeEach
	void init() {
		encounter = new PirateEncounter(0, 0, 0, 10);
	}

	@Test
	void testStartEncounter() {
		//will do once pirate encounters implemented in gui
	}

	@Test
	void testCombat() {

	}

	@Test
	void testShipWinProbability() {
		Ship shipA = new Ship("shipA", 10, 0, 0, 10);
		Ship shipB = new Ship("shipB", 0, 0, 0, 0);
		//ship ??? should win
	}

}
