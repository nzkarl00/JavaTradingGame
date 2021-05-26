package islands;

import encounters.EncounterEvent;
import encounters.PirateEncounter;
import encounters.SailorEncounter;
import encounters.WeatherEncounter;
import java.util.Random;

/**
 * Gives a description of available routes between two certain islands
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
	private float directness; //describes how "direct" the route between two islands is
	//a directness of 0 is least direct, meaning it is the slowest but also the safest route
	//whereas a directness of 1 is most direct, meaning it is the fastest but most risky route
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
	 * Returns amount of days it will take for ship to travel route.
	 * Days is based on distance between islands, directness, and ship speed.
	 * @param speed float of the ship speed for calculation
	 * @return int days that the journey will take
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

	/** Getter for directness.
	 * @return float directness
	 */
	public float getDirectness() {
		return directness;
	}
	
	/**
	 * Checks to see if there will be an encounter and which encounter it will be.
	 * @param rng Random to generate randomness of the encounter
	 * @return event EncounterEvent of which encounter happens
	 */
	public EncounterEvent getEncounter(Random rng) {
		// Random rng;
		// if (useSeed) {
		// 	rng = new Random(0);
		// } else {
		// 	rng = new Random();
		// }

		// float encounterDeterminant = (float)Math.random();
		float encounterDeterminant = rng.nextFloat();
		float encounterChance = getDirectness();
		if (encounterDeterminant < (1 - encounterChance)) {
			return null;
		}
		
		float encounterChoice = rng.nextFloat();
		EncounterEvent event;
		if (encounterChoice < 0.3f) {
			event = new PirateEncounter(10, 10, 30);
		} else if (encounterChoice < 0.6f) {
			event = new WeatherEncounter();
		} else {
			event = new SailorEncounter();
		}

		return event;
	}

	/**
	 * Gets a nicely formatted string of the route from one island to another.
	 * @return String formatted for visibility
	 */
	public String getString() {
		//* Gives String representation of this IslandRoute
		return fromIsland.getName() + " to " + toIsland.getName();
	}
	
	/**
	 * Gets a description of the route, returning the duration and chance of an encounter as a string.
	 * @param shipSpeed speed of the ship for duration calculation
	 * @return String containing days to travel and encounter chance formatted for printing
	 */
	public String getDescription(float shipSpeed) {
		return getDaysToTravel(shipSpeed) + " days, " +
			(getDirectness() * 100) + "% danger";
	}
	
	/**
	 * Gets a slightly longer description of the route, including the destination.
	 * @param shipSpeed speed of the ship for duration calculation
	 * @return String containing destination, time and encounter chance
	 */
	public String shortString(float shipSpeed) {
		return (toIsland.getName() + ", " + getDaysToTravel(shipSpeed) + " days, " + getDirectness()*100 + "% danger");
	}

}
