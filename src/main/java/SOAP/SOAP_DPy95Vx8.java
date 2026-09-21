package SOAP;

import org.example.ws.data.DataService;
import org.example.ws.data.SoapDataService;

import java.util.ArrayList;
import java.util.List;

public class SOAP_DPy95Vx8 {
    static void main(String[] args) {
        String studentCode = "B23DCCN756";
        String qCode = "DPy95Vx8";

        try{

            DataService dataService = new DataService();
            SoapDataService port = dataService.getSoapDataServicePort();

            List<Integer> data = port.getData(studentCode, qCode);
            int even = 0, odd = 0;
            for (Integer i : data) {
                if (i % 2 == 0) {
                    even++;
                } else {
                    odd++;
                }
            }

            List<String> result = new ArrayList<>();
            result.add("EVEN=" + even);
            result.add("ODD=" + odd);
            port.submitDataStringArray(studentCode, qCode, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
