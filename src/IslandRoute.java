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
	private float directness; //describes how "direct" the route between two islands is
	//a directness of 0 is least direct, meaning it is the slowest but also the safest route
	//wheras a directness of 1 is most direct, meaning it is the fastest but most risky route
	private float indirectDistMultiplier = 2; //the more indirect the route is, the more distance will be multiplied by this amount

	public IslandRoute(Island _fromIsland, Island _toIsland, float _directness) {
		fromIsland = _fromIsland;
		toIsland = _toIsland;
		directness = _directness;
	}

	public Island getStartIsland() {
		return fromIsland;
	}

	public Island getEndIsland() {
		return toIsland;
	}

	/**
	 * returns amount of days it will take for ship to travel route
	 * days is based on distance between islands, directness, and ship speed
	 * */
	public int getDaysToTravel(float speed) {
		//calculate distance between two islands
		float xDiff = fromIsland.getX() - toIsland.getX();
		float yDiff = fromIsland.getY() - toIsland.getY();
		double baseDistance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));

		//the closer directness is to 0, the more distance is increased by
		float distMultiplier = indirectDistMultiplier * (1 - directness); //directness of 0 uses multiplier fully, directness of 1 doesn't use it at all
		double appliedDistance = baseDistance + baseDistance * distMultiplier;

		//the larger speed is, the less time it will take to travel
		//v = dt, so t = d/v
		double travelTime = appliedDistance / speed;
		return (int)Math.ceil(travelTime);
	}

	public void getDistance() {

	}

	public float getEncounterChance() {
		return directness;
	}
	
	/*
	 * Depending on chance of a new encounter, returns an object giving information on encounter (pirates, storm, etc).
	*/
	public EncounterEvent getEncounter() {
		float encounterDeterminant = (float)Math.random();
		float encounterChance = getEncounterChance();
		if (encounterDeterminant > encounterChance) {
			return null;
		}
		
		float encounterChoice = (float)Math.random();
		EncounterEvent event;

		if (encounterChoice < 0.3f) {
			event = new PirateEncounter(0, 3, 0, 5);
		} else if (encounterChoice < 0.6f) {
			event = new WeatherEncounter();
		} else {
			event = new SailorEncounter();
		}

		return event;
	}

	public String getString() {
		//* Gives String representation of this IslandRoute
		return fromIsland.getName() + " to " + toIsland.getName();
	}
}
