/**
 * subclass for describing item that is an upgradeable
 * should say how this upgradeable works i.e. what property it affects and how much it affects it by
 * */

public class UpgradeItem extends Item {
	
	public enum UpgradeType {
		reinforcement,
		damage
	}

	private UpgradeType upgradeType;
	
	public UpgradeItem(float base, String tempname, String desc, float tempweight, UpgradeType _upgradeType) {
		super(base, tempname, desc, tempweight);
		upgradeType = _upgradeType;
	}

}
