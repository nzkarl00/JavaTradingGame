package game;

/**
 * Subclass for describing item that is used to upgrade a Ship.
 * Has two types, reinforcement or damage. Only the damage type is currently used in the game.
 * 
 * Variables:
 *-upgradeType UpgradeType, currently can only be damage or reinforcement
 * */

public class UpgradeItem extends Item {
	
	public enum UpgradeType {
		reinforcement,
		damage
	}

	private UpgradeType upgradeType;
	
	/**
	 * Constructor for the upgrade.
	 * @param base float base value for upgrade the Ship stat
	 * @param tempname String name of the upgrade
	 * @param desc String description of what the upgrade is
	 * @param tempweight float weight of the upgrade
	 * @param _upgradeType UpgradeType determining which stat will be upgraded by the upgrade
	 */
	public UpgradeItem(float base, String tempname, String desc, float tempweight, UpgradeType _upgradeType) {
		super(base, tempname, tempweight);
		upgradeType = _upgradeType;
	}

	/**
	 * Getter for upgradeType
	 * @return UpgradeType upgradeType
	 */
	public UpgradeType getUpgradeType() {
		return upgradeType;
	}

}
