package game.game.items;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public class Item {

	public Item(String itemName, int itemAmount, int weight) {
		setItemAmount(itemAmount);
		setItemName(itemName);
		setWeight(weight);
	}
	
	public Item(String itemName, int itemAmount, String slot, int weight) {
		setItemAmount(itemAmount);
		setItemName(itemName);
		setItemSlot(slot);
		setWeight(weight);
	}
	
	private String itemName, itemSlot;
	private int itemAmount, weight;
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getItemAmount() {
		return itemAmount;
	}
	public void setItemAmount(int itemAmount) {
		this.itemAmount = itemAmount;
	}
	public String getItemSlot() {
		return itemSlot;
	}

	public void setItemSlot(String itemSlot) {
		this.itemSlot = itemSlot;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
