package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RMI_yhctCxi3 {
    static void main(String[] args) throws Exception {
        String studentCode = "B23DCCN756";
        String qCode = "yhctCxi3";
        String serverIp = "36.50.135.242";

        Registry registry = LocateRegistry.getRegistry(serverIp);
        ByteService byteService = (ByteService) registry.lookup("RMIByteService");
        byte[] data = byteService.requestData(studentCode, qCode);

        Map<Byte, Integer> map = new LinkedHashMap<>();
        for(byte b : data) {
            map.put(b, map.getOrDefault(b, 0) + 1);
        }
        List<Map.Entry<Byte, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.comparingByValue());
        byte[] result = new byte[2];
        result[0] = entries.get(0).getKey();
        result[1] = entries.get(0).getValue().byteValue();
        byteService.submitData(studentCode, qCode, result);
    }
}
