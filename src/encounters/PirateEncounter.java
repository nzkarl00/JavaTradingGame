package encounters;

import java.util.*;
import game.Item;
import game.Ship;
import game.UI;
import game.Player;
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
	private Ship pirateShip;
	private float satisfactoryGoodsValue = 30;
	

	public PirateEncounter(float _baseDamageMin, float _baseDamageMax, float _goodsValueMin, float _goodsValueMax) {
		super();
		pirateShip = new Ship("", 20, 0, 0, 10);

	}

	@Override
	public String StartEncounter(Player player, UI ui, GameEventNotifier notifier) { //pass in a GUI object, just print for now
		Ship playerShip = player.getShip();
		String text = "";
		text += (intro + "\n");
		text += ("Your ship has health of " + playerShip.getHealth() + " and damage of " + playerShip.getDamage() + "\n");
		text += ("The pirate ship has health of " + pirateShip.getHealth() + " and damage of " + pirateShip.getDamage() + "\n");

		//start fight
		boolean havePiratesWon = startCombat(player.getShip());
		if (havePiratesWon) {
			text += (loseMessage + "\n");

			float goodsValue = getGoodsValue(playerShip);
			text += ("Your ship has $" + goodsValue + " in goods. The pirates want at least $" + satisfactoryGoodsValue + "\n");

			if (goodsValue >= satisfactoryGoodsValue) {
				playerShip.clearInventory();
				text += (goodsStolenMessage + "\n");
				return text;
			} else {
				text += (goodsKillMessage + "\n");
				notifier.addGameEvent(GameEventNotifier.EventType.killedByPirates);
				return text;
			}
		} else {
			text += (winMessage + "\nYou found $100 in the wreckage\n");
			player.transferMoney(100);
			return text;
		}
	}

	private boolean startCombat(Ship playerShip) { //return true if pirates win, false if player wins
		//healthManager of pirate attacks healthManager of player
		//do this until someone dies (reaches critical health)
		//combat done by random numbers based on probability of winning
		//
		float pirateWinProbability = getShipWinProbability(pirateShip, playerShip);
		// System.out.println("prob: " + winProbability);
		
		float chance = super.getRandomFloatInRange(0, 1);
		return chance < pirateWinProbability;
	}

	private float getShipWinProbability(Ship shipA, Ship shipB) { //probability of shipA winning against shipB
		//the higher a ship's damage + health is, the more likely it is to win
		//weighted 40% health 60% damage
		float shipASuccess = shipA.getHealth() * 0.4f + shipA.getDamage() * 0.6f;
		float shipBSuccess = shipB.getHealth() * 0.4f + shipB.getDamage() * 0.6f;
		float probability = shipASuccess / (shipASuccess + shipBSuccess);
		return probability;
	}

	private float getGoodsValue(Ship ship) {
		float totalValue = 0;

		Map<Item, Integer> inventory = ship.getInventory();
		for (Map.Entry<Item, Integer> entry : inventory.entrySet()) {
			Item item = entry.getKey();
			int quantity = entry.getValue();
			totalValue += item.getBaseValue() * quantity;
		}

		return totalValue;
	}
	


}
