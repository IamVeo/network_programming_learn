package SOAP;

import org.example.ws.object.ObjectService;
import org.example.ws.object.ProductY;
import org.example.ws.object.SoapObjectService;

public class SOAP_sUsrRaQL {
    static void main(String[] args) {
        String studentCode = "B23DCCN756";
        String qCode = "sUsrRaQL";

        try {

            ObjectService objectService = new ObjectService();
            SoapObjectService port = objectService.getSoapObjectServicePort();

            ProductY data = port.requestProductY(studentCode, qCode);
            float finalPrice = data.getPrice() * (1 + data.getTaxRate() / 100) * (1 - data.getDiscount() / 100);
            data.setFinalPrice(finalPrice);
            port.submitProductY(studentCode, qCode, data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
