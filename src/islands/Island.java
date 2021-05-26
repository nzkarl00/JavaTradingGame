package islands;
import java.util.ArrayList;

/**
 * Island class
 * An Island:
 * -Has a name
 * -Has a location on the map
 * -Has routes to other islands 
 * -Has a store the player can buy/sell items from (may require a subclass)
 * */

public class Island {
	private String name; //Name of the island.
	private int xCoord; //X co-ordinate of the island.
	private int yCoord; //Y co-ordinate of the island.
	private ArrayList<IslandRoute> routes = new ArrayList<IslandRoute>(); //ArrayList containing all possible routes from the island.
	private Store store;
	
	/**
	 * Constructor for the Island
	 * @param tempname String name to be assigned to the island
	 * @param tempx int x coordinates to be assigned to the island
	 * @param tempy int y coordinates to be assigned to the island
	 */
	public Island(String _name, int _x, int _y) { //Constructor for creating new islands.
		name = _name;
		xCoord = _x;
		yCoord = _y;
		store = new Store(name); 
	}
	
	/**
	 * Getter for xcoord.
	 * @return int xcoord
	 */
	public int getX() {
		return xCoord;
	}
	
	/**
	 * Getter for ycoord.
	 * @return int ycoord
	 */
	public int getY() {
		return yCoord;
	}
	
	/**
	 * Getter for name.
	 * @return String name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Updated toString method for the island to display more relevant information.
	 * @return String containing more relevant information
	 */
	public String toString() {
		return (name + ", found at (" + xCoord + "," + yCoord + ")");
	}

	/**
	 * Getter for routes.
	 * @return ArrayList<IslandRoute> routes
	 */
	public ArrayList<IslandRoute> getRoutes() {
		//routes are either calculated by the island or assigned to the island by GameManager when game is being set up
		return routes;
	}
	
	/**
	 * Getter for route descriptions, to display more detailing information about all routes.
	 * @param shipSpeed float speed used to calculate days to take the route
	 * @return String detailed descriptions of all routes
	 */
	public ArrayList<String> getRoutesDescriptions(float shipSpeed) {
		ArrayList<IslandRoute> routes = getRoutes();
		
		ArrayList<String> routeDescriptions = new ArrayList<String>();
		for (IslandRoute route : routes) {
			String description = route.getDescription(shipSpeed);
			routeDescriptions.add(description);
		}

		return routeDescriptions;
	}

	/**
	 * Getter for all routes to a specific island.
	 * @param shipSpeed float speed used to calculate days to take the route
	 * @param toIsland Island destination
	 * @return String descriptions of all routes to a specific island
	 */
	public String getRoutesToIslandDescriptions(float shipSpeed, Island toIsland) {
		ArrayList<IslandRoute> routes = getRoutes();
		
		String routeDescriptions = "";
		for (IslandRoute route : routes) {
			if (route.getEndIsland().name == toIsland.name) {
				String description = route.getDescription(shipSpeed);
				routeDescriptions += (description + "\n");
			}
		}
		if (routeDescriptions == "") {
			routeDescriptions = ("Current location");
		}
		return routeDescriptions;
	}

	/**
	 * Getter for store
	 * @return Store store
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * Adds a new route to the island
	 * @param endIsland Island destination of the route
	 * @param directness float how direct the route is to the island, direct routes are more dangerous
	 */
	public void addRoute(Island endIsland, float directness) {
		IslandRoute route = new IslandRoute(this, endIsland, directness);
		for (IslandRoute existingRoute : routes) {
			if (existingRoute.getEndIsland() == endIsland && existingRoute.getDirectness() == directness) {
				return;
			}
		}
		routes.add(route);
	}


}
