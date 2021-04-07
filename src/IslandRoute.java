/**
 * Gives a description of avaliable routes between two certain islands
 * could be managed either by having one object to contain all possible routes, or having multiple objects to express different types of routes
 * i think second option is easier to work with as it breaks object down into simplest possible form
 * means you can quickly define different properties for different routes without having code for accessing specific routes be overcomplicated
 *
 * this class will be used by Island to define routes to/from itself,
 * and either Player or GameManager to sort out navigation
 * */

public class IslandRoute {

	private Island fromIsland;
	private Island toIsland;
	private String description;
	private float directness;

	public IslandRoute() {

	}

	public void getStartIsland() {

	}

	public void getEndIsland() {

	}

	public void getDaysToTravel(float speed) {

	}

	public void getDistance() {

	}

	public void getEncounterChance() {

	}
}
