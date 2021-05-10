/**
 * Manages health for any object that can gain/lose health (ie player's ship, enemy pirates, etc)
 * probably give some kind of alert to controlling class when health reaches 0
 * */

public class HealthManager {

	private float currentHealth;
	private float maxHealth;

	public HealthManager(float startHealth, float _maxHealth) {
		currentHealth = startHealth;
		maxHealth = _maxHealth;
	}

	public void giveHealth(float amount) {
		currentHealth += amount;
	}

	public void takeDamage(float amount) {
		currentHealth -= amount;
	}

	public float getCurrentHealth() {
		return currentHealth;
	}


}
