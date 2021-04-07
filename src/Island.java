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
	private ArrayList<Store> stores; //use list instead of variable for potential extensibility (if you wanted 0 or 2 stores on an island) - might score us a few more marks?? might be overkill lol
	
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

	public void getRoutes() {
		//routes are either calculated by the island or assigned to the island by GameManager when game is being set up
	}

	public void getStore() {

	}


}
