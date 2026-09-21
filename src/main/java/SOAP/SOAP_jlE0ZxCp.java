package SOAP;

import org.example.ws.object.Customer;
import org.example.ws.object.ObjectService;
import org.example.ws.object.SoapObjectService;

import java.util.ArrayList;
import java.util.List;

public class SOAP_jlE0ZxCp {
    static void main(String[] args) {
        String studentCode = "B23DCCN756";
        String qCode = "jlE0ZxCp";

        try {

            ObjectService objectService = new ObjectService();
            SoapObjectService port = objectService.getSoapObjectServicePort();

            List<Customer> data = port.requestListCustomer(studentCode, qCode);
            List<Customer> result = new ArrayList<>();
            for(Customer customer : data){
                if(customer.getPurchaseCount() >= 6 && customer.getTotalSpent() >= 4000.0){
                    result.add(customer);
                }
            }
            result.sort(((a, b) -> {
                if(a.getTotalSpent() != b.getTotalSpent()){
                    return Float.compare(b.getTotalSpent(), a.getTotalSpent());
                }
                return a.getCustomerId().compareTo(b.getCustomerId());
            }));
            port.submitListCustomer(studentCode, qCode, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
