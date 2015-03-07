package functionality;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
/**
 * Class to fetches XEROX devices from network
 * @author senthilnathan_c
 */
public class DiscoverDevice {

    /**
     *  Sysobject OID to send as request to device
     */
	private final String KEY_sysObjectOID = "1.3.6.1.2.1.1.2.0"; 
	/**
     *  Description OID to send as request to device
     */
	private final String KEY_sysDescr = "1.3.6.1.2.1.1.1.0";    
	/**
     *  Name OID to send as request to device
     */
	private final String KEY_sysName = "1.3.6.1.2.1.1.5.0";      

     /**
      * no. of retries
      */
	private final int retries = 2;
     /**
      * array of OIDs that is sent as request
      */
	private final String[] OIDS_TO_QUERY;
	/**
     * Timeout for discovery of a single address (not using broadcast).
     */ 
	private final int SINGLE_TIMEOUT = 2000;
    /**
     * Timeout for auto discovery based on broadcast.
     */
	private final int BROADCAST_TIMEOUT = 5000;
	/**
     * The SNMP GET timeout used (depends on the address type).
     */
	private  int timeout;
	/**
     * Whether the SNMP listener has finished listening for responses.
     */
	@SuppressWarnings("unused")
	private boolean listenerDone;
	
    /**
     * Reference to Connection class
     */
	private Connection connection;
	

   /**
    * array of OID values to be sent
    */
    
	public DiscoverDevice() {
		OIDS_TO_QUERY = new String[] { KEY_sysObjectOID, KEY_sysDescr,
				KEY_sysName };
	}
      
	/**
	 * method that calls the SNMP getrequest to get response from the device
	 * @return initialList- returns all devices
	 */
	public Map<InetAddress, PDU> findDetails(final String address) {
		// TODO Auto-generated method stub

		
		String deviceAddr = null;
		if (address == null) {
			
			deviceAddr = address;
			timeout = BROADCAST_TIMEOUT;
		} else {
			
			deviceAddr = address;
			timeout = SINGLE_TIMEOUT;
		}
		

		Map<InetAddress, PDU> initialList = sendSnmpRequest(deviceAddr);
		return initialList;
	}
      /**
       * sending SNMP GET request to all the devices in the network
       * @return responseList - response from devices
       */
	private Map<InetAddress, PDU> sendSnmpRequest(final String host) {
		connection = new Connection();
		TransportMapping transport = connection.createTransport();
		Snmp snmp = connection.createSnmp(transport);
		USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3
				.createLocalEngineID()), 0);
		SecurityModels.getInstance().addSecurityModel(usm);
		try {
			transport.listen();
		} catch (IOException e) {

		}
		CommunityTarget target = connection.createTarget(host);
		PDU pdu = createRequestPDU();
		/**
		 * List containing InetAddress PDU values of devices that responded
		 * */
		final Map<InetAddress, PDU> responseList = new HashMap<InetAddress, PDU>();
		listenerDone = false;
		ResponseListener listener = new ResponseListener() {
			public void onResponse(ResponseEvent event) {
				PDU response = event.getResponse();
				if (response != null) {
					Address responseAddress = event.getPeerAddress();
					InetAddress inetAddress;
					if (responseAddress instanceof UdpAddress) {
						inetAddress = ((UdpAddress) event.getPeerAddress())
								.getInetAddress();
					} else {
						inetAddress = ((TcpAddress) event.getPeerAddress())
								.getInetAddress();
					}
					responseList.put(inetAddress, response);
					
				}
				
			}
		};
		int waitSlot = 200;
		int waitLimit = timeout * (retries + 1);
		try {
			snmp.send(pdu, target, null, listener);
		} catch (IOException e) {

		}
		try {
			connection.waitForResult(waitSlot, waitLimit);
		} catch (Exception e) {

		} finally {
			snmp.cancel(pdu, listener);
			try {
				snmp.close();
			} catch (IOException e) {
			}
		}
		

		return responseList;
	}
	
	/**
	 * Method to prepare PDU that has to be sent with the request to device
	 * @return PDU
	 */
	private PDU createRequestPDU() {
		PDU pdu = new PDU();
		for (String oid : OIDS_TO_QUERY) {
			pdu.add(new VariableBinding(new OID(oid)));
		}

		pdu.setType(PDU.GET);
		return pdu;
	}
    
    /**
     * Filtering process to filter out only XEROX devices
     * @return results - List of filtered devices
     */
	private Map<InetAddress, PDU> filterSNMPResults(
			final Map<InetAddress, PDU> results) {

		List<InetAddress> devicesToRemove = new ArrayList<InetAddress>();
		for (InetAddress a : results.keySet()) {
			boolean isRemove = true;
			// Process one device
			PDU pdu = results.get(a);
		    String sysObIdvariable = getVariableStringValue(pdu, KEY_sysObjectOID);
			String enterpriseId = "";
			try {
				enterpriseId = sysObIdvariable.split("\\.")[6];

			} catch (Exception e) {

			}

			List<String> enterpriseIdFilter = enterpriseIDs();

			for (Object e : enterpriseIdFilter) {
				if (e.equals(enterpriseId)) {
                    isRemove=false;
                    break;
					//devicesToRemove.add(a);
				}	
			}
				if(isRemove){
					devicesToRemove.add(a);
				}		
		}
			

		for (InetAddress a : devicesToRemove) {
			results.remove(a);
		}

		return results;

	}
	/**
	 * splitting the Value from the variable Binding obtained
	 * @return OID value
	 */

	private String getVariableStringValue(final PDU pdu, final String oidKey) {
		String OIDValue = null;
		OID oid = new OID(oidKey);
		for (Object vb : pdu.getVariableBindings()) {
			OID o1 = ((VariableBinding) vb).getOid();

			if (o1.equals(oid)) {
				OIDValue = ((VariableBinding) vb).getVariable().toString();

				break;
			}
		}
		return OIDValue;
	}
    
	/** Method that calls appropriate methods to produce an endList of Discovered Devices
     * @return filteredList - list of discovered devices.
     */
	protected Map<InetAddress, PDU> endList(final String address) {
		Map<InetAddress, PDU> initialList = findDetails(address);
		Map<InetAddress, PDU> filteredList = filterSNMPResults(initialList);
		return filteredList;

	}
    /**
     * List of enterprise IDs for filtering criteria
     * @return enterpriseIds- list of enterprise IDs 
     */
	private List<String> enterpriseIDs() {

		List<String> enterpriseIds = new ArrayList<String>();
//		enterpriseIds.add("253");
//		enterpriseIds.add("11");
		return enterpriseIds;

	}
	

}
