package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import encounters.*;

class GameEventNotifierTest {

	GameEventNotifier notifier;

	@BeforeEach
	void init() {
		notifier = new GameEventNotifier();
	}

	@Test
	void testGetLastEventEmpty() {
		assertEquals(null, notifier.getLastEvent());
	}

	@Test
	void testGetLastEvent() {
		notifier.addGameEvent(GameEventNotifier.EventType.goodsStolen);
		notifier.addGameEvent(GameEventNotifier.EventType.killedPirates);
		assertEquals(GameEventNotifier.EventType.killedPirates, notifier.getLastEvent());
	}

}
