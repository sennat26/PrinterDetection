package snmptests;

 import java.io.IOException;  
 import java.net.UnknownHostException;  
 import java.util.Vector;  
   
 import org.snmp4j.CommandResponder;  
import org.snmp4j.CommandResponderEvent;  
import org.snmp4j.MessageDispatcherImpl;  
import org.snmp4j.Snmp;  
import org.snmp4j.TransportMapping;  
 import org.snmp4j.mp.MPv1;  
 import org.snmp4j.mp.MPv2c;  
 import org.snmp4j.mp.MPv3;  
 import org.snmp4j.security.SecurityModels;  
 import org.snmp4j.security.SecurityProtocols;  
 import org.snmp4j.security.USM;  
 import org.snmp4j.smi.Address;  
 import org.snmp4j.smi.GenericAddress;  
 import org.snmp4j.smi.OctetString;  
 import org.snmp4j.smi.TcpAddress;  
 import org.snmp4j.smi.UdpAddress;  
 import org.snmp4j.smi.VariableBinding;  
 import org.snmp4j.transport.DefaultTcpTransportMapping;  
 import org.snmp4j.transport.DefaultUdpTransportMapping;  
 import org.snmp4j.util.MultiThreadedMessageDispatcher;  
import org.snmp4j.util.ThreadPool;  
   
 
 public class trapreceiver implements CommandResponder {  
   
     private MultiThreadedMessageDispatcher dispatcher;  
     private Snmp snmp = null;  
     private Address listenAddress;  
     private ThreadPool threadPool;  
     private static int SNMP_PORT = 162;
     private static String strIPAddress = "10.105.142.72";
   
     public trapreceiver() {  
         
     }  
   
     private void init() throws UnknownHostException, IOException {  
         threadPool = ThreadPool.create("Trap", 2);  
         dispatcher = new MultiThreadedMessageDispatcher(threadPool,  
                new  MessageDispatcherImpl());  
         listenAddress = GenericAddress.parse(System.getProperty(  
                "snmp4j.listenAddress", "10.105.142.103/162")); 
         //String addrStr = strIPAddress + "/" + SNMP_PORT ;
         //listenAddress = GenericAddress.parse(addrStr);
         TransportMapping transport=new DefaultUdpTransportMapping((UdpAddress) listenAddress);  
                    
                
           
        /* if (listenAddress instanceof UdpAddress) {  
             transport = new DefaultUdpTransportMapping(  
                     (UdpAddress) listenAddress);  
         } else {  
             transport = new DefaultTcpTransportMapping(  
                     (TcpAddress) listenAddress);  
         }  */
         snmp = new Snmp(dispatcher, transport);  
         snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());  
         snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());  
         snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());  
         USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3  
                 .createLocalEngineID()), 0);  
         SecurityModels.getInstance().addSecurityModel(usm);  
         snmp.listen();  
     }  
   
       
     public void run() {  
         try {  
             init();  
             snmp.addCommandResponder(this);  
             System.out.println("Trap");  
         } catch (Exception ex) {  
             ex.printStackTrace();  
         }  
     }  
   
      
     public void processPdu(CommandResponderEvent respEvnt) {  
        
         if (respEvnt != null && respEvnt.getPDU() != null) {  
             Vector<VariableBinding> recVBs = respEvnt.getPDU().getVariableBindings();  
             for (int i = 0; i < recVBs.size(); i++) {  
                 VariableBinding recVB = recVBs.elementAt(i);  
                 System.out.println(recVB.getOid() + " : " + recVB.getVariable());  
             }  
         }  
     }  
   
     public static void main(String[] args) {  
         trapreceiver multithreadedtrapreceiver = new trapreceiver();  
         multithreadedtrapreceiver.run();  
        
     }  
     public void g(){
    	 System.out.println("finish");
    	 System.exit(0);
     }
   
 }  