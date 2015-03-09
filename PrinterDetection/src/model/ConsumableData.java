package model;
/**
 * Pojo class for consumables
 * @author senthilnathan_c
 */
public class ConsumableData {
	/**
	 * name of consumable in consumable table
	 */
	private String name;
	/**
	 * type of consumable in consumable table
	 */
	private String type;
	/**
	 * part number of consumable in consumable table
	 */
	private String partNumber;
	/**
	 * units used for consumable in consumable table
	 */
	private String units;
	/**
	 * maxCapacity for consumable in consumable table
	 */
	private int maxCapacity;
	/**
	 * currentLevel for consumable in consumable table
	 */
	private int currentLevel;
	/**
	 * class value of consumable in consumable table
	 */
	private String classValue;
	/**
	 * percentage level of consumable in consumable table
	 */
	private int percentageValue;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(final String type) {
		this.type = type;
	}
	/**
	 * @return the partNumber
	 */
	public String getPartNumber() {
		return partNumber;
	}
	/**
	 * @param partNumber the partNumber to set
	 */
	public void setPartNumber(final String partNumber) {
		this.partNumber = partNumber;
	}
	/**
	 * @return the units
	 */
	public String getUnits() {
		return units;
	}
	/**
	 * @param units the units to set
	 */
	public void setUnits(final String units) {
		this.units = units;
	}
	/**
	 * @return the maxCapacity
	 */
	public int getMaxCapacity() {
		return maxCapacity;
	}
	/**
	 * @param maxCapacity the maxCapacity to set
	 */
	public void setMaxCapacity(final int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	/**
	 * @return the currentLevel
	 */
	public int getCurrentLevel() {
		return currentLevel;
	}
	/**
	 * @param currentLevel the currentLevel to set
	 */
	public void setCurrentLevel(final int currentLevel) {
		this.currentLevel = currentLevel;
	}
	/**
	 * @return the classValue
	 */
	public String getClassValue() {
		return classValue;
	}
	/**
	 * @param classValue the classValue to set
	 */
	public void setClassValue(final String classValue) {
		this.classValue = classValue;
	}
	/**
	 * @return the percentageValue
	 */
	public int getPercentageValue() {
		return percentageValue;
	}
	/**
	 * @param percentageValue the percentageValue to set
	 */
	public void setPercentageValue(final int percentageValue) {
		this.percentageValue = percentageValue;
	}
}
