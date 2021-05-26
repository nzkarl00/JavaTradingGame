package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.*;
import encounters.*;

class PirateEncounterTest {

	TestableRandom rng;

	@BeforeEach
	void init() {
		rng = new TestableRandom();
	}


	@Test
	void testShipWinProbability() {
		//both ships have 0 chance of success
		float probability = testShipWinProbabilityHelper(0, 0, 0, 0);
		assertEquals(probability, 0.5f);

		//shipA fully overpowers shipB
		probability = testShipWinProbabilityHelper(1, 1, 0, 0);
		assertEquals(probability, 1);

		//shipB fully overpowers shipA
		probability = testShipWinProbabilityHelper(0, 0, 1, 1);
		assertEquals(probability, 0);

		//shipA slightly overpowers shipB
		probability = testShipWinProbabilityHelper(0, 1, 1, 0);
		assertEquals(probability, 0.6f);
		
		//shipB slightly overpowers shipA
		probability = testShipWinProbabilityHelper(1, 0, 0, 1);
		assertEquals(probability, 0.4f);

		//both ships have equal stats
		probability = testShipWinProbabilityHelper(1, 1, 1, 1);
		assertEquals(probability, 0.5f);
	}

	private float testShipWinProbabilityHelper(float shipAHealth, float shipADamage, float shipBHealth, float shipBDamage) {
		PirateEncounter genericPirateEncounter = new PirateEncounter(0, 0, 0); //parameters won't be used here
		Ship shipA = new Ship("shipA", 0, 0, 0, shipAHealth, shipADamage);
		Ship shipB = new Ship("shipB", 0, 0, 0, shipBHealth, shipBDamage);
		float probability = genericPirateEncounter.getShipWinProbability(shipA, shipB);
		return probability;
	}


	@Test
	void testCombatFullChance() {
		//pirate has 100% chance of success
		PirateEncounter encounter = new PirateEncounter(1, 1, 0);
		Ship playerShip = new Ship("player", 0, 0, 0, 0, 0);

		rng.setNextFloat(0);
		boolean hasWon = encounter.startCombat(playerShip, rng);
		assertEquals(hasWon, true);

		rng.setNextFloat(0.99f); //Random.nextFloat() returns 0 (inclusive) to 1 (exclusive) - emulate behaviour here
		hasWon = encounter.startCombat(playerShip, rng);
		assertEquals(hasWon, true);
	}

	@Test
	void testCombatMidChance() {
		//pirate has 40% chance of success
		PirateEncounter encounter = new PirateEncounter(1, 0, 0);
		Ship playerShip = new Ship("player", 0, 0, 0, 0, 1);

		rng.setNextFloat(0);
		boolean hasWon = encounter.startCombat(playerShip, rng);
		assertEquals(hasWon, true);

		rng.setNextFloat(0.99f);
		hasWon = encounter.startCombat(playerShip, rng);
		assertEquals(hasWon, false);
	}

	@Test
	void testCombatNoChance() {
		//pirate has 0% chance of success
		PirateEncounter encounter = new PirateEncounter(0, 0, 0);
		Ship playerShip = new Ship("player", 0, 0, 0, 1, 1);
 
		rng.setNextFloat(0);
		boolean hasWon = encounter.startCombat(playerShip, rng);
		assertEquals(hasWon, false);

		rng.setNextFloat(0.99f);
		hasWon = encounter.startCombat(playerShip, rng);
		assertEquals(hasWon, false);
	}


	@Test
	void testEncounterOutcomes() {
		//player will win
		Ship playerShip = new Ship("player", 0, 0, 0, 1, 1);
		Player player = new Player("", playerShip, null, 0);
		
		GameEventNotifier.EventType event = getEventFromEncounter(player, 0, 0);
		assertEquals(event, GameEventNotifier.EventType.killedPirates);
		assertEquals(player.getMoney(), 100); //ensure money has been won

		//pirates will win, loot is insufficient
		playerShip = new Ship("player", 0, 0, 0, 0, 0);
		player = new Player("", playerShip, null, 0);
		event = getEventFromEncounter(player, 1, 1);
		assertEquals(event, GameEventNotifier.EventType.killedByPirates);

		//pirates will win, loot is sufficient
		Item item = new Item(1, "item", 0);
		playerShip.addItem(item);
		event = getEventFromEncounter(player, 1, 1);
		assertEquals(event, GameEventNotifier.EventType.goodsStolen);
		assertTrue(playerShip.getGoodsValue(null) == 0); //ensure goods have been stolen
	}

	@Test
	void testEncounterStrings() {
		//player will win
		Ship playerShip = new Ship("player", 0, 0, 0, 1, 1);
		Player player = new Player("", playerShip, null, 0);
		
		String output = getStringFromEncounter(player, 0, 0);
		assertEquals(output,
			"Arr!\n" +
			"Your ship has health of 1.0 and damage of 1.0\n" + 
			"The pirate ship has health of 0.0 and damage of 0.0\n" +
			"You defeated the pirates! Well done!\n" +
			"You found $100 in the wreckage\n");


		//pirates will win, loot is insufficient
		playerShip = new Ship("player", 0, 0, 0, 0, 0);
		player = new Player("", playerShip, null, 0);
		output = getStringFromEncounter(player, 1, 1);
		assertEquals(output,
			"Arr!\n" +
			"Your ship has health of 0.0 and damage of 0.0\n" + 
			"The pirate ship has health of 1.0 and damage of 1.0\n" +
			"You lost to the pirates.\n" +
			"Your ship has $0.0 in goods. The pirates want at least $1.0\n" +
			"The pirates are unsatisfied with your goods. Only killing you will bring them joy.\n");

		//pirates will win, loot is sufficient
		Item item = new Item(1, "item", 0);
		playerShip.addItem(item);
		output = getStringFromEncounter(player, 1, 1);
		assertEquals(output,
			"Arr!\n" +
			"Your ship has health of 0.0 and damage of 0.0\n" + 
			"The pirate ship has health of 1.0 and damage of 1.0\n" +
			"You lost to the pirates.\n" +
			"Your ship has $1.0 in goods. The pirates want at least $1.0\n" +
			"The pirates take your stuff and let you go\n");
	}

	GameEventNotifier.EventType getEventFromEncounter(Player player, float pirateHealth, float pirateDamage) {
		PirateEncounter encounter = new PirateEncounter(pirateHealth, pirateDamage, 1);
		GameEventNotifier notifier = new GameEventNotifier();
		String output = encounter.StartEncounter(player, notifier);
		return notifier.getLastEvent();
	}

	String getStringFromEncounter(Player player, float pirateHealth, float pirateDamage) {
		PirateEncounter encounter = new PirateEncounter(pirateHealth, pirateDamage, 1);
		GameEventNotifier notifier = new GameEventNotifier();
		String output = encounter.StartEncounter(player, notifier);
		return output;
	}

}
