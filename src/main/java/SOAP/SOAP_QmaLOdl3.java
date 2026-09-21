package SOAP;

import org.example.ws.data.DataService;
import org.example.ws.data.SoapDataService;

import java.util.List;

public class SOAP_QmaLOdl3 {
    static void main(String[] args) {
        String studentCode = "B23DCCN756";
        String qCode = "QmaLOdl3";

        try {

            DataService ds = new DataService();
            SoapDataService port = ds.getSoapDataServicePort();

            List<Integer> data = port.getData(studentCode, qCode);
            int checksum = 0, primeCnt = 0;
            for(int i = 0; i < data.size(); i++) {
                if(isPrime(data.get(i))){
                    primeCnt++;
                }
                checksum += ((i + 1) * data.get(i)) % 100000;
            }
            String result = "primeCount=" + primeCnt + ";checksum=" + checksum;
            port.submitDataString(studentCode, qCode, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isPrime(int n){
        if(n % 2 == 0) return false;
        for(int i = 3; i <= Math.sqrt(n); i += 2){
            if(n % i == 0)
                return false;
        }
        return n > 1;
    }
}
