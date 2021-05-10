/**
 * Pirates can:
 * -fight the player
 * -take damage and die as a result
 * -create too much damage and win as a result
 * -if they win, they will steal goods 
 * */
public class PirateEncounter extends EncounterEvent {

	private String intro = "Arr!";
	private String loseMessage = "You lost to the pirates.";
	private String winMessage = "You defeated the pirates! Well done!";
	private String goodsStolenMessage = "The pirates take your stuff and let you go";
	private String goodsKillMessage = "The pirates are unsatisfied with your goods. Only killing you will bring them joy.";

	private float baseDamage;
	private float satisfactoryGoodsValue;
	private HealthManager healthManager;
	private Ship pirateShip;
	

	public PirateEncounter(float _baseDamageMin, float _baseDamageMax, float _goodsValueMin, float _goodsValueMax) {
		super();

		baseDamage = super.getRandomFloatInRange(_baseDamageMin, _baseDamageMax);
		satisfactoryGoodsValue = super.getRandomFloatInRange(_goodsValueMin, _goodsValueMax);

		pirateShip = new Ship("", 20, 0, 0, 10);

	}

	public void StartEncounter(Player player, UI ui) { //pass in a gui object, just print for now
		ui.showMessage(intro);

		//start fight
		boolean hasWon = startCombat(player.getShip());
		boolean killPlayer = areGoodsSatisfactory(player);
		if (hasWon) {
			//take goods - TODO - implement inventory/items
			ui.showMessage(winMessage);
			if (!killPlayer) {
				ui.showMessage(goodsStolenMessage);
			} else {
				ui.showMessage(goodsKillMessage);
			}
		} else {
			ui.showMessage(loseMessage);
			player.transferMoney(40, ui);
		}

	}

	private boolean startCombat(Ship playerShip) {
		//healthManager of pirate attacks healthManager of player
		//do this until someone dies (reaches critical health)
		//combat done by random numbers based on probability of winning
		//
		float winProbability = getShipWinProbability(pirateShip, playerShip);
		// System.out.println("prob: " + winProbability);
		
		float chance = super.getRandomFloatInRange(0, 1);
		if (chance < winProbability) {
			return true;
		}
		return false;

	}

	private float getShipWinProbability(Ship shipA, Ship shipB) {
		//the higher a ship's speed + damage is, the more likely it is to win
		//weighted 40% speed 60% damage
		float shipASuccess = shipA.getSpeed() * 0.4f + shipA.getDamage() * 0.6f;
		float shipBSuccess = shipB.getSpeed() * 0.4f + shipB.getDamage() * 0.6f;
		float probability = shipASuccess / (shipASuccess + shipBSuccess);
		return probability;
	}

	private boolean areGoodsSatisfactory(Player player) {
		//random for now, calculate based on player goods once implemented
		float chance = super.getRandomFloatInRange(0, 1);
		return chance < 0.5f;
	}
	


}
