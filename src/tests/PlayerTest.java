package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.*;

class PlayerTest {

	Player player;
	
	@BeforeEach
	void init() {
		player = new Player("player", null, null, 0);
	}

	@Test
	void testTransferMoney() {
		player.transferMoney(1);
		assertEquals(player.getMoney(), 1);

		player.transferMoney(-1);
		assertEquals(player.getMoney(), 0);

		boolean success = player.transferMoney(-1);
		assertFalse(success);
	}

}
