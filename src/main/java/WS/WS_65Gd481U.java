package WS;

import org.example.ws.data.DataService;
import org.example.ws.data.SoapDataService;

import java.util.ArrayList;
import java.util.List;

public class WS_65Gd481U {
    static void main(String[] args) {
        String studentCode = "B23DCCN756";
        String qCode = "65Gd481U";

        try{

            DataService dataService = new DataService();
            SoapDataService port = dataService.getSoapDataServicePort();

            List<Integer> data = port.getData(studentCode, qCode);

            String result = createLargestNumber(data);

            port.submitDataString(studentCode, qCode, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String createLargestNumber(List<Integer> data){

        List<String> numbers = new ArrayList<>();

        for(Integer i : data){
            numbers.add(i.toString());
        }

        numbers.sort((a, b) -> (b + a).compareTo(a + b));

        if(!numbers.isEmpty() && numbers.getFirst().equals("0")){
            return "0";
        }

        StringBuilder result = new StringBuilder();
        for(String number : numbers){
            result.append(number);
        }
        return result.toString();
    }
}
