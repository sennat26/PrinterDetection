package model;
/**
 * Pojo class for Devices
 * @author satish_k
 * */
public class DeviceData {
	/**
	 * model in devices table
	 */
	private String model;   
	/**
	 * manufacturer in device table
	 */
	private String manufacturer; 
	/**
	 * ipAddress in devices table
	 */
	private String ipAddress;  
	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
          
	
	

}
