package encounters;

import java.util.*;

import game.HealthManager;
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

	private float baseDamage;
	private HealthManager healthManager;
	private Ship pirateShip;
	private float satisfactoryGoodsValue = 30;
	

	public PirateEncounter(float _baseDamageMin, float _baseDamageMax, float _goodsValueMin, float _goodsValueMax) {
		super();

		baseDamage = super.getRandomFloatInRange(_baseDamageMin, _baseDamageMax);
		// satisfactoryGoodsValue = super.getRandomFloatInRange(_goodsValueMin, _goodsValueMax);

		pirateShip = new Ship("", 20, 0, 0, 10);

	}

	public void StartEncounter(Player player, UI ui, GameEventNotifier notifier) { //pass in a gui object, just print for now
		Ship playerShip = player.getShip();
		ui.showMessage(intro);
		ui.showMessage("Your ship has health of " + playerShip.getHealth() + " and damage of " + playerShip.getDamage());
		ui.showMessage("The pirate ship has health of " + pirateShip.getHealth() + " and damage of " + pirateShip.getDamage());

		//start fight
		boolean havePiratesWon = startCombat(player.getShip());
		if (havePiratesWon) {
			//take goods - TODO - implement inventory/items
			ui.showMessage(loseMessage);

			float goodsValue = getGoodsValue(playerShip);
			ui.showMessage("Your ship has $" + goodsValue + " in goods. The pirates want at least $" + satisfactoryGoodsValue);

			if (goodsValue >= satisfactoryGoodsValue) {
				playerShip.clearInventory();
				ui.showMessage(goodsStolenMessage);
			} else {
				ui.showMessage(goodsKillMessage);
				notifier.addGameEvent(GameEventNotifier.EventType.killedByPirates);
			}
		} else {
			ui.showMessage(winMessage);
			player.transferMoney(40);
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
