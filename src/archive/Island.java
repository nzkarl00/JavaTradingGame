package archive;

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
	private int xcoord; //X co-ordinate of the island.
	private int ycoord; //Y co-ordinate of the island.
	private ArrayList<IslandRoute> routes = new ArrayList<IslandRoute>(); //ArrayList containing all possible routes from the island.
	private Store store;
	
	
	public Island(String tempname, int tempx, int tempy) { //Constructor for creating new islands.
		name = tempname;
		xcoord = tempx;
		ycoord = tempy;
		store = new Store(name); 
	}
	
	public int getX() {
		return xcoord;
	}
	
	public int getY() {
		return ycoord;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return (name + ", found at (" + xcoord + "," + ycoord + ")");
	}

	public ArrayList<IslandRoute> getRoutes() {
		//routes are either calculated by the island or assigned to the island by GameManager when game is being set up
		return routes;
	}

	public Store getStore() {
		return store;
	}

	public void addRoute(Island endIsland, float directness) {
		IslandRoute route = new IslandRoute(this, endIsland, directness);
		routes.add(route);
	}


}
