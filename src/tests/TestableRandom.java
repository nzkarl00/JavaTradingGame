package tests;

import java.util.Random;

class TestableRandom extends Random {

	float nextFloat;

	public TestableRandom() {
		super();
		nextFloat = 0;
	}

	public void setNextFloat(float _float) {
		nextFloat = _float;
	}

	public float nextFloat() {
		return nextFloat;
	}
}