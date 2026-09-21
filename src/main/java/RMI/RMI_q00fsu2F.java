package RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class RMI_q00fsu2F {
    static void main(String[] args) throws Exception {
        String studentCode = "B23DCCN756";
        String qCode = "q00fsu2F";
        String serverIp = "36.50.135.242";

        Registry registry = LocateRegistry.getRegistry(serverIp);
        DataService dataService = (DataService) registry.lookup("RMIDataService");
        String data = dataService.requestData(studentCode, qCode);

        List<Double> result = new ArrayList<>();
        for(String str : data.split(",")) {
            result.add(Double.parseDouble(str));
        }

        double sum = 0;
        for(Double d : result) {
            sum += d;
        }
        double avg = sum/result.size();

        double variance = 0;
        for(Double d : result) {
            variance += (d - avg) * (d - avg);
        }
        variance /= result.size();
        double stddev = Math.sqrt(variance);

        result.sort(Double::compareTo);
        int index = (int) Math.ceil(result.size() * 0.95) - 1;
        double p95 = result.get(index);

        String output = String.format("average=%.2f;stddev=%.2f;p95=%.2f", avg, stddev, p95);
        dataService.submitData(studentCode, qCode, output);
    }
}
