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
	private ArrayList<IslandRoutes> routes = new ArrayList<IslandRoutes>(); //ArrayList containing all possible routes from the island.
	
	public Island(String tempname, int tempx, int tempy) { //Constructor for creating new islands.
		name = tempname;
		xcoord = tempx;
		ycoord = tempy;
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
