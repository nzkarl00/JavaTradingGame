package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.*;

class GameManagerTest {

	GameManager manager;
	Player player;

	@BeforeEach
	void init() {
		manager = new GameManager();
		manager.configureAdventure("player name", 10, 1);
		player = manager.getPlayer();
	}

	@Test
	void testRepairShip() {
		//test when player has no money
		player.transferMoney(-player.getMoney()); //set player to having 0 funds
		String output = manager.repairShip();
		System.out.println(output);
	}
	

}
