package game;

/**
 * Represents information of the items present in the game.
 * Items can be sold and bought.
 * Variables:
 *-baseValue float containing the pre-modifier price of an item
 *-name String name of the item
 *-weight float amount of space the item takes up in ship storage
 * */

public class Item {
	
	private float baseValue;
	private String name;
	private float weight;
	
	/**
	 * Constructor for the Item class.
	 * @param base float pre-modifier price of an item
	 * @param tempname String name that will be assigned to the item
	 * @param tempweight float weight that will be assigned to the item
	 */
	public Item(float base, String _name, float _weight) {
		baseValue = base;
		name = _name;
		weight = _weight;
	}
	
	/**
	 * Getter for the pre-modifier value of the item.
	 * @return baseValue float of the base value of the item
	 */
	public float getBaseValue() {
		return baseValue;
	}
	
	/**
	 * Setter for the pre-modifier value of the item. Not currently used in the game.
	 * Could potentially be used to create market events later.
	 * @param newValue float of the new base value
	 */
	public void setBaseValue(float newValue) {
		baseValue = newValue;
	}
    
	/**
	 * Getter for the name of the item.
	 * @return String name of the item
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for the weight of the item.
	 * @return float weight of the item.
	 */
	public float getWeight() {
		return weight;
	}
	
	@Override
	/**
	 * New toString method for items to return text better suited for the GUI.
	 * @return String containing name and weight formatted for the GUI
	 */
	public String toString() {
		return (name + "    " + weight + "kg/unit"); 		
	}

}
