package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMI_PTvsQCC7 {
    static void main(String[] args) throws Exception {
        String studentCode = "B23DCCN756";
        String qCode = "PTvsQCC7";
        String serverIp = "36.50.135.242";

        Registry registry = LocateRegistry.getRegistry(serverIp);
        ObjectService os = (ObjectService) registry.lookup("RMIObjectService");
        TicketSla ticketSla = (TicketSla) os.requestObject(studentCode, qCode);

        boolean isBreached = (ticketSla.getPriority().equals("CRITICAL") && ticketSla.getOpenedHoursAgo() > 2)
                || (ticketSla.getPriority().equals("HIGH") && ticketSla.getOpenedHoursAgo() > 8)
                || (ticketSla.getPriority().equals("MEDIUM") && ticketSla.getOpenedHoursAgo() > 24)
                || (ticketSla.getPriority().equals("LOW") && ticketSla.getOpenedHoursAgo() > 72);
        ticketSla.setBreached(isBreached);

        if(!isBreached) {
            ticketSla.setAction("MONITOR");
        } else if(ticketSla.getPriority().equals("CRITICAL") || ticketSla.getOpenedHoursAgo() > 96) {
            ticketSla.setAction("ESCALATE_L2");
        } else{
            ticketSla.setAction("ESCALATE_L1");
        }

        os.submitObject(studentCode, qCode, ticketSla);
    }
}
