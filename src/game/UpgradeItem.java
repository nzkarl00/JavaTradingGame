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
	
	public UpgradeItem(float base, String tempname, String desc, float tempweight, UpgradeType _upgradeType) {
		super(base, tempname, tempweight);
		upgradeType = _upgradeType;
	}

	public UpgradeType getUpgradeType() {
		return upgradeType;
	}

}
