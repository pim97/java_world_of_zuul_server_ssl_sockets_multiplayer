package game.game.combat;

import game.configs.Global;

/**
 * Items dat wapens zijn
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public enum Weapons {
	PICKAXE(40, 1, Global.WEAPONINDEX),
	BRONSWEAPON(60, 1, Global.WEAPONINDEX),
	DAGGER(100, 5, Global.WEAPONINDEX),
	SKELETONSCIMITAR(120, 5, Global.WEAPONINDEX),
	SKELETONDAGGER(150, 6, Global.WEAPONINDEX),
	ULTIMATEWEAPON(250, 15, Global.WEAPONINDEX),
	TARGARYANSWORD(300, 25, Global.WEAPONINDEX),
	FIRESWORD(500, 30, Global.WEAPONINDEX),
	TESTSWORD(10000, 1, Global.WEAPONINDEX);
	
	private int damage, weight, slot;
	
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	private Weapons(int damage, int weight, int slot) {
		setDamage(damage);
		setWeight(weight);
		setSlot(slot);
	}
	
	public static int getWeaponDamage(String weaponName) {
		for (Weapons weapon : Weapons.values()) {
			if (weapon.name().equalsIgnoreCase(weaponName)) {
				return weapon.getDamage();
			}
		}
		return 0;
	}
	
	public static Weapons getWeapon(String weaponName) {
		for (Weapons weapon : Weapons.values()) {
			if (weapon.name().equalsIgnoreCase(weaponName)) {
				return weapon;
			}
		}
		return null;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}
}
