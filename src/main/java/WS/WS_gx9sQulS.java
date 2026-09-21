package WS;

import org.example.ws.object.Customer;
import org.example.ws.object.ObjectService;
import org.example.ws.object.SoapObjectService;

import java.util.ArrayList;
import java.util.List;

public class WS_gx9sQulS {
    static void main(String[] args) {
        String studentCode = "B23DCCN756";
        String qCode = "gx9sQulS";

        try {

            ObjectService objectService = new ObjectService();
            SoapObjectService port = objectService.getSoapObjectServicePort();

            List<Customer> data = port.requestListCustomer(studentCode, qCode);
            System.out.println("Data size = " + data.size());

            for (Customer customer : data) {
                System.out.println(
                        customer.getCustomerId()
                                + " | purchaseCount = "
                                + customer.getPurchaseCount()
                                + " | totalSpent = "
                                + customer.getTotalSpent()
                );
            }
            List<Customer> result = new ArrayList<>();
            for (Customer customer : data) {
                if(customer.getTotalSpent() > 500.0 && customer.getPurchaseCount() >= 3){
                    result.add(customer);
                }
            }
            System.out.println("Result size = " + result.size());

            for (Customer customer : result) {
                System.out.println(
                        customer.getCustomerId()
                                + " | "
                                + customer.getPurchaseCount()
                                + " | "
                                + customer.getTotalSpent()
                );
            }
            port.submitListCustomer(studentCode, qCode, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
