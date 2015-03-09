package functionality;

import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.MessageDispatcher;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
/**
 * This class is used as a generic class to set community target,transport mapping and create SNMP 
 *  @author senthilnathan_c
 */
 public class Connection{
	/**
	 * port number used for connection
	 */
	private final int SNMP_PORT = 161;
	/**
	 *boolean value to check what type of protocol is used
	 */
	private boolean isUdp = true; 
	/**
	 * to set number of retires
	 */
	private final int RETRIES = 2;
	/**
	 * to set timeout for the operation
	 */
	private final int TIMEOUT=6000;
	/**
     * Whether the SNMP listener has finished listening for responses.
     */
	private boolean listenerDone;
    
	/**
     * method to create community target for the SNMP connection
     * @return target - the community target
     */
	protected CommunityTarget createTarget(final String host) {
		String protocol = isUdp ? "udp" : "tcp";
		String addrStr = protocol + ":" + host + "/" + SNMP_PORT;
		Address targetAddress = GenericAddress.parse(addrStr);
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString("public"));
		target.setAddress(targetAddress);
		target.setRetries(RETRIES);
		target.setTimeout(TIMEOUT);
		target.setVersion(SnmpConstants.version1);
		return target;
	}
	/**
	 * method to set the transport mapping for SNMP request
	 * @return transport- object of the transport mapping that is set.
	 */
	protected TransportMapping createTransport() {
		TransportMapping transport = null;
		try {
			if (isUdp) {
				transport = new DefaultUdpTransportMapping();
			} else {
				transport = new DefaultTcpTransportMapping();
			}
		} catch (IOException e) {

		}
		return transport;
	}
	  /**
	   * used to create SNMP connection with the transport mapping set and message dispatcher
	   * @return snmp 
	   */
	protected Snmp createSnmp(final TransportMapping transport) {
		MessageDispatcher md = new MessageDispatcherImpl();
		md.addMessageProcessingModel(new MPv2c());
		md.addMessageProcessingModel(new MPv1());
		md.addMessageProcessingModel(new MPv3());
		return new Snmp(md, transport);

	}
	/**
     * Waits for the result of SNMP GET request.
     * @param waitSlot wait re-check time slot.
     * @param waitLimit maximum wait period
     */
	protected void waitForResult(final int waitSlot, final int waitLimit) {
		int expiredTime = 0;
		try {
			while (expiredTime < waitLimit && !listenerDone) {
				Thread.sleep(waitSlot);
				expiredTime += waitSlot;
			}
		} catch (Exception e) {

		}
	}
	
	
}
