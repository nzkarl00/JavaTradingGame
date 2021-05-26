package archive;

/**
 * represents information about a particular item
 * items can be sold, bought, or used as an upgrade
 * if item is sold, needs info about island of purchase + price paid
 * if sold as upgrade, needs info about how the upgrade benefits player/player's ship
 *
 * */

public class Item {

	private float baseValue;
	private float currentValue;
	private String name;
	private String description;
	private float weight;
	private Island islandStore;
	
	public Item(float base, String tempname, String desc, float tempweight) {
		baseValue = base;
		name = tempname;
		description = desc;
		weight = tempweight;
	}
	
	public float getBaseValue() {
		return baseValue;
	}
	
	public void setBaseValue(float newValue) {
		baseValue = newValue;
	}

	public String getName() {
		return name;
	}
	
	public float getWeight() {
		return weight;
	}
	
	public String toString() {
		return (name + ":\n" + description + "\nIt weighs " + weight); 		
	}

}
