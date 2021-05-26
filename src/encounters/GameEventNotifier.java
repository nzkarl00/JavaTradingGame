package encounters;

import java.util.ArrayList;

/**
 * Passes info to the game manager of events that occur during the game.
 */
public class GameEventNotifier { //subclass that allows other objects to notify GameManager of important changes to the game via a structured interface

	/**
	 * The three events that can happen during any journey with a risk.
	 */
	public enum EventType {
		killedByPirates, killedPirates, goodsStolen
	}

	private ArrayList<EventType> events; //only one event is used, but using array for extensibility purposes

	/**
	 * Constructor for GameEventNotifier, creates the events ArrayList.
	 */
	public GameEventNotifier() {
		events = new ArrayList<EventType>();
	}

	/**
	 * Adds an event to the events ArrayList.
	 * @param event EventType given event
	 */
	public void addGameEvent(EventType event) {
		events.add(event);
	}

	/**
	 * Checks to see if an event has occurred in a journey.
	 * @param event EventType event to be checked
	 * @return boolean if the event has occurred
	 */
	public boolean hasEventOccurred(EventType event) {
		return events.contains(event);
	}

	/**
	 * Gets the last event that occurred.
	 * @return EventType The most recent item in the events list
	 */
	public EventType getLastEvent() {
		if (events.isEmpty()) {
			return null;
		}
		return events.get(events.size() - 1);
	}

}
