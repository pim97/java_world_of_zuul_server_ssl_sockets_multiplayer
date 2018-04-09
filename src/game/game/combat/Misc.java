package game.game.combat;

/**
 * Items die nergens anders bij horen
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public enum Misc {

	HALLWAYKEYONE(1),
	BEAMER(1),
	PRISONKEY(5),
	BLUEKEY(1),
	YELLOWKEY(1),
	PURPLEKEY(1),
	GRAYKEY(1),
	RAINBOWKEY(1),
	JOHNSNOWCAPE(3),
	ANTIFIRESHIELD(6),
	GEORGEHEAD(9),
	TOOHEAVY(99);
	
	private Misc(int weight) {
		setWeight(weight);
	}
	
	private int weight;
	
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public static Misc getMisc(String weaponName) {
		for (Misc misc : Misc.values()) {
			if (misc.name().equalsIgnoreCase(weaponName)) {
				return misc;
			}
		}
		return null;
	}

	
}
