package WS;

import org.example.ws.object.EmployeeY;
import org.example.ws.object.ObjectService;
import org.example.ws.object.SoapObjectService;

import java.util.Comparator;
import java.util.List;

public class WS_4tdjYNxe {
    static void main(String[] args) {
        String studentCode = "B23DCCN756";
        String qCode = "4tdjYNxe";
        try{

            ObjectService objectService = new ObjectService();
            SoapObjectService port = objectService.getSoapObjectServicePort();

            List<EmployeeY> data = port.requestListEmployeeY(studentCode, qCode);
            data.sort((e1, e2) -> e1.getStartDate().compare(e2.getStartDate()));

            port.submitListEmployeeY(studentCode, qCode, data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
