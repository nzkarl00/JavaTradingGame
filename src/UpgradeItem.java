/**
 * subclass for describing item that is an upgradeable
 * should say how this upgradeable works i.e. what property it affects and how much it affects it by
 * */

public class UpgradeItem extends Item {
	
	private float healthBoost;
	
	public UpgradeItem(float base, String tempname, String desc, float tempweight) {
		super(base, tempname, desc, tempweight);
	}

}