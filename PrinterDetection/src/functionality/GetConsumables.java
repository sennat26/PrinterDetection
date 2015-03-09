package functionality;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import model.ConsumableData;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import components.ScanFrame;
import components.ToolFooter;

/**
 * class to send request to device and get its consumables        
 * @author senthilnathan_c 
 */
public class GetConsumables {

	/**
	 * Value used as a splitter in between various SNMP walk output. 
	 */
	private static int COUNT = 0;
	/**
	 * Consumables number in list
	 */
	private static int CONSUMABLE_NO = 0;
	/**
	 * List of consumables
	 */
	private List<ConsumableData> consumableList;
	/**
	 * Array of OID that has to be sent as SNMP request
	 */
	private final String MARKER_SUPPLIES_ENTRY[] = { "1.3.6.1.2.1.43.11.1.1.6",
			"1.3.6.1.2.1.43.11.1.1.5", "1.3.6.1.2.1.43.11.1.1.7",
			"1.3.6.1.2.1.43.11.1.1.8", "1.3.6.1.2.1.43.11.1.1.9",
			"1.3.6.1.2.1.43.11.1.1.4"};
	/**
	 * Array values for respective OID values for prtMarkerSupplyType
	 */
	private final String MARKER_SUPPLY_TYPE[] = { "other", "Unknown", "Toner",
			"wasteToner", "ink", "inkCartridge", "inkRibbon", "wasteInk",
			"opc", "photo conductor developer", " fuserOil", "solidWax",
			" ribbonWax", " wasteWax", "fuser", " coronaWire", " fuserOilWick",
			"cleanerUnit", "fuserCleaningPad", "transferUnit",
			"tonerCartridge", "fuserOiler", " water", "wasteWater",
			"glueWaterAdditive", "wastePaper", "bindingSupply",
			"bandingSupply", "stitchingWire", "shrinkWrap", "paperWrap",
			"staples", "inserts", "covers" };
	/**
	 * Array values for respective OID values for prtMarkerSupplyUnit
	 */
	private final String MARKER_SUPPLY_UNIT[] = { "other", "unknown",
			"tenThousandthsOfInches", "micrometers", "", "", "impressions",
			"sheets", "", "", "hours", "thousandthsOfOunces", "tenthsOfGrams",
			"hundrethsOfFluidOunces", "tenthsOfMilliliters", "feet", "meters",
			"items", "percent" };

	/**
	 * Array values for respective OID values for prtMarkerSupplyClass
	 */
	private final String MARKER_SUPPLY_CLASS[] = { "other", "",
			"supplyThatIsConsumed", "receptacleThatIsFilled" };

	
	/**
	 * validating IP entered by user in text field
	 * @return true or false
	 */
	private boolean validateIp(final String ipAddress) {
		String parts[] = ipAddress.split("\\.");
		if (parts.length != 4) {
			return false;
		}
		String arr[] = parts;
		int length = arr.length;
		for (int i = 0; i < length; i++) {
			String s = arr[i];
			int j = Integer.parseInt(s);
			if (j < 2 || j > 255)
				return false;
		}

		return true;
	}

	/**
	 *  Creating a list of consumables from the response sent by the device
	 *  @return consumableList - Consumable List
	 */
	protected List<ConsumableData> printerConsumables(String strIpAddress) {
		if (validateIp(strIpAddress)) {
			Connection connection = new Connection();
			consumableList = new java.util.ArrayList<ConsumableData>();
			CommunityTarget CommunityTarget = connection.createTarget(strIpAddress);
			for (String markerSupplyEntry : MARKER_SUPPLIES_ENTRY) {

				OID targetOID = new OID(markerSupplyEntry);
				PDU requestPDU = new PDU();
				requestPDU.add(new VariableBinding(targetOID));
				requestPDU.setType(PDU.GETNEXT);

				try {
					TransportMapping transport = connection.createTransport();
					Snmp snmp = connection.createSnmp(transport);
					transport.listen();
					checkException(requestPDU,CommunityTarget, snmp, targetOID);
					snmp.close();

				} catch (IOException e) {
					System.out.println("IOException: " + e);
				}
			}
			COUNT = 0;
           
			return consumableList;
		
		} else {
			ToolFooter.setStatus("stopped");
			String message = "Enter a valid IP address";
			String title = "Trailblazer";
			JOptionPane.showMessageDialog(null, message, title,
					JOptionPane.ERROR_MESSAGE);
			return null;

		}

	}
    /**
     * Method to check for ant exception during SNMP getNext operation
     */
	@SuppressWarnings("deprecation")
	private void checkException(PDU requestPDU, CommunityTarget target,
			Snmp snmp, OID targetOID) {

		boolean finished = false;

		while (!finished) {
			VariableBinding variableBinding = null;

			PDU responsePDU = null;
			try {
				responsePDU = snmp.sendPDU(requestPDU, target);
			} catch (IOException e) {
				ScanFrame.getInstances().Loading(false);
				ToolFooter.setStatus("unable to send request");
			}

			if (responsePDU != null) {
				variableBinding = responsePDU.get(0);
			}

			if (responsePDU == null) {
				ScanFrame.getInstances().Loading(false);
				ToolFooter.setStatus("No response from device..please try again");
				finished = true;
				
			} else if (responsePDU.getErrorStatus() != 0) {
				ScanFrame.getInstances().Loading(false);
				ToolFooter.setStatus("Error in getting response");

				finished = true;
				
			} else if (variableBinding.getOid() == null) {
				ScanFrame.getInstances().Loading(false);
				ToolFooter.setStatus("No such OIDs in device");
				
				finished = true;
				
			} else if (variableBinding.getOid().size() < targetOID.size()) {
				ScanFrame.getInstances().Loading(false);
				ToolFooter.setStatus("Error in OID size");
				
				finished = true;
				
			} else if (targetOID.leftMostCompare(targetOID.size(), variableBinding.getOid()) != 0) {

				COUNT = COUNT + 1;
				CONSUMABLE_NO = 0;

				finished = true;
				
			} else if (Null.isExceptionSyntax(variableBinding.getVariable().getSyntax())) {
				ScanFrame.getInstances().Loading(false);
				ToolFooter.setStatus("Error");
				finished = true;
				
			} else if (variableBinding.getOid().compareTo(targetOID) <= 0) {
				ScanFrame.getInstances().Loading(false);
				ToolFooter.setStatus("Variable received is not "
						+ "lexicographic successor of requested " + "one:");

				finished = true;

			} else {
            	prepareConsumableList(COUNT, consumableList, variableBinding);
				percentage(consumableList);
				requestPDU.setRequestID(new Integer32(0));
				requestPDU.set(0, variableBinding);
			}
		}

	}

	/**
	 * Method to prepare consumable List for a device
	 */
	private void prepareConsumableList(final int count,
			final List<ConsumableData> consumableList,
			final VariableBinding variableBinding) {
		if (GetConsumables.COUNT==0) {
			ConsumableData consumableData = new ConsumableData();
			String str[] = variableBinding.toString().split("=");
			if (str[1].contains(",")) {
				String name_number[] = str[1].split(",");
				if(name_number.length>=2){
				consumableData.setName(name_number[0]);
				consumableData.setPartNumber(name_number[(name_number.length-1)]);
				}else{
					consumableData.setName(name_number[0]);
					consumableData.setPartNumber("null");
				}
				//consumableList.add(consumableData);
			} else {
                 consumableData.setName(str[1]);
                 consumableData.setPartNumber("null");
			}
			consumableList.add(consumableData);
		}
		if (GetConsumables.COUNT == 1) {
			ConsumableData consumableData = consumableList
					.get(GetConsumables.CONSUMABLE_NO);
			String str[] = variableBinding.toString().split("=");
			consumableData.setType(MARKER_SUPPLY_TYPE[Integer.parseInt(str[1]
					.trim()) - 1]);
			GetConsumables.CONSUMABLE_NO = GetConsumables.CONSUMABLE_NO + 1;
		}
		if (GetConsumables.COUNT == 2) {
			ConsumableData consumableData = consumableList
					.get(GetConsumables.CONSUMABLE_NO);
			String str[] = variableBinding.toString().split("=");
			consumableData.setUnits(MARKER_SUPPLY_UNIT[Integer.parseInt(str[1]
					.trim()) - 1]);
			GetConsumables.CONSUMABLE_NO = GetConsumables.CONSUMABLE_NO + 1;

		}
		if (GetConsumables.COUNT == 3) {
			ConsumableData consumableData = consumableList
					.get(GetConsumables.CONSUMABLE_NO);
			String str[] = variableBinding.toString().split("=");
			consumableData.setMaxCapacity(Integer.parseInt(str[1].trim()));
			GetConsumables.CONSUMABLE_NO = GetConsumables.CONSUMABLE_NO + 1;
		}
		if (GetConsumables.COUNT == 4) {
			ConsumableData consumableData = consumableList
					.get(GetConsumables.CONSUMABLE_NO);
			String str[] = variableBinding.toString().split("=");
			consumableData.setCurrentLevel(Integer.parseInt(str[1].trim()));
			GetConsumables.CONSUMABLE_NO = GetConsumables.CONSUMABLE_NO + 1;
		}

		if (GetConsumables.COUNT == 5) {

			ConsumableData consumableData = consumableList
					.get(GetConsumables.CONSUMABLE_NO);
			String str[] = variableBinding.toString().split("=");
			consumableData.setClassValue(MARKER_SUPPLY_CLASS[Integer
					.parseInt(str[1].trim()) - 1]);
			GetConsumables.CONSUMABLE_NO = GetConsumables.CONSUMABLE_NO + 1;
		}
		
	}

	/**
	 * calculating percentage from maximum capacity value and current level value
	 */
	private void percentage(List<ConsumableData> consumableList) {
		for (int i = 0; i < consumableList.size(); i++) {
			ConsumableData consumableData = consumableList.get(i);
			if (consumableData.getCurrentLevel() > 0
					&& consumableData.getMaxCapacity() > 0) {
				consumableData
						.setPercentageValue(((consumableData.getCurrentLevel() * 100) / consumableData
								.getMaxCapacity()));
			} else {
				consumableData.setPercentageValue(0);
			}

		}

	}

}
