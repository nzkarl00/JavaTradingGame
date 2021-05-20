import java.util.ArrayList;

public class GameEventNotifier { //subclass that allows other objects to notify GameManager of important changes to the game via a structured interface

	public enum EventType {
		killedByPirates
	}

	private ArrayList<EventType> events; //only one event is used, but using array for extensibility purposes

	public GameEventNotifier() {
		events = new ArrayList<EventType>();
	}

	public void addGameEvent(EventType event) {
		events.add(event);
	}

	public boolean hasEventOccurred(EventType event) {
		return events.contains(event);
	}

}

