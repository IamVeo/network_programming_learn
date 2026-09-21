package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RMI_2pVAVwb1 {
    static void main(String[] args) throws Exception {
        String studentCode = "B23DCCN756";
        String qCode = "2pVAVwb1";
        String serverIp = "36.50.135.242";

        Registry registry = LocateRegistry.getRegistry(serverIp);
        ByteService service = (ByteService) registry.lookup("RMIByteService");
        byte[] data = service.requestData(studentCode, qCode);

        Map<Byte, Integer> frequency = new LinkedHashMap<Byte, Integer>();
        for(byte b : data) {
            frequency.put(b, frequency.getOrDefault(b, 0) + 1);
        }
        List<Map.Entry<Byte, Integer>> entries = new ArrayList<>(frequency.entrySet());
        entries.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));
        byte[] result = new byte[2];
        result[0] = entries.get(0).getKey();
        result[1] = entries.get(0).getValue().byteValue();

        service.submitData(studentCode, qCode, result);
    }
}
