package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class RMI_4hOTJlbS {

    public static void main(String[] args) throws Exception {

        String studentCode = "B23DCCN756";
        String qCode = "4hOTJlbS";
        String serverIp = "36.50.135.242";

        Registry registry =
                LocateRegistry.getRegistry(serverIp);

        DataService dataService =
                (DataService) registry.lookup("RMIDataService");

        // requestData trả về int[]
//        int[] data =
//                dataService.requestData(
//                        studentCode,
//                        qCode
//                );
//
//        List<Integer> result =
//                new ArrayList<>();
//
//        for (int i = 0; i < data.length; i++) {
//
//            boolean isLocalMax = true;
//
//            if (i > 0
//                    && data[i - 1] >= data[i]) {
//                isLocalMax = false;
//            }
//
//            if (i < data.length - 1
//                    && data[i + 1] >= data[i]) {
//                isLocalMax = false;
//            }
//
//            if (isLocalMax) {
//                // đề yêu cầu index bắt đầu từ 1
//                result.add(i + 1);
//            }
//        }
//
//        System.out.println("Result: " + result);
//
//        dataService.submitData(
//                studentCode,
//                qCode,
//                result
//        );
    }
}