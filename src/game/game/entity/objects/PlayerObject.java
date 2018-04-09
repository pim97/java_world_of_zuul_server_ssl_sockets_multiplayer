package game.game.entity.objects;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public class PlayerObject {

	public PlayerObject(String objectName, int amount, int weight) {
		setObjectName(objectName);
		setObjectAmount(amount);
		setWeight(weight);
	}
	
	public PlayerObject(String objectName, int amount) {
		setObjectName(objectName);
		setObjectAmount(amount);
		setWeight(0);
	}
	
	private String objectName;
	private int objectAmount, weight;

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public int getObjectAmount() {
		return objectAmount;
	}

	public void setObjectAmount(int objectAmount) {
		this.objectAmount = objectAmount;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
