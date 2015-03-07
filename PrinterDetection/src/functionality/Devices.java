package functionality;

import java.net.InetAddress;
import java.util.Map;
import org.snmp4j.PDU;
import functionality.DiscoverDevice;
/**
 * class to call the function to find XEROX devices
 *@author senthilnathan_c 
 */

public class Devices {
   /**
    * Address used to send request to all devices in Network
    */
	private static final String BROADCAST_ADDRESS = "255.255.255.255";
		
	/**
	 * method that returns a Map containing InetAddress of Xerox devices found and its PDUs
	 * @return deviceList-Map value containing InetAddress of Xerox devices found and its PDUs
	 */
	public Map<InetAddress, PDU> deviceList() {
		// TODO Auto-generated constructor stub
	
		
		DiscoverDevice discoveryDevice=new DiscoverDevice();
		Map<InetAddress, PDU>deviceList=discoveryDevice.endList(BROADCAST_ADDRESS);
		
	
		return deviceList;
	}

 }
