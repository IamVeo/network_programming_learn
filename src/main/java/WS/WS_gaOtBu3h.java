package WS;

import org.example.ws.data.DataService;
import org.example.ws.data.SoapDataService;

import java.util.List;

public class WS_gaOtBu3h {
    static void main(String[] args) {
        String studentCode = "B23DCCN756";
        String qCode = "gaOtBu3h";

        try{
            DataService dataService = new DataService();
            SoapDataService port = dataService.getSoapDataServicePort();

            List<Integer> data = port.getData(studentCode, qCode);

            int result = 0;
            for(Integer i : data){
                result = result + i;
            }

            port.submitDataInt(studentCode, qCode, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
