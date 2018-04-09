package game.food;

/**
 * Eten wat mensen kunnen eten zodat hun hp weer hoger wordt
 * @author pim
 *
 */
public enum Food {

	APPLE(10, 1),
	MAGICCOOKIE(100, 5),
	PEAR(50, 3),
	RAINBOWAPPLE(100, 1),
	COOKIE(100, 1);
	
	public int getRegenerate() {
		return regenerate;
	}

	public void setRegenerate(int regenerate) {
		this.regenerate = regenerate;
	}

	private int regenerate, weight;
	
	private Food(int regenerate, int weight) {
		setRegenerate(regenerate);
		setWeight(weight);
	}
	
	public static Food getFood(String itemName) {
		for (Food food : values()) {
			if (food.name().equalsIgnoreCase(itemName)) {
				return food;
			}
		}
		return null;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	
}
