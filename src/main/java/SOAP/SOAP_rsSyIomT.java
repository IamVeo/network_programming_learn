package SOAP;

import org.example.ws.character.CharacterService;
import org.example.ws.character.SoapCharacterService;

public class SOAP_rsSyIomT {
    static void main(String[] args) {
        String studentCode = "B23DCCN756";
        String qCode = "rsSyIomT";

        try {

            CharacterService cs = new CharacterService();
            SoapCharacterService port = cs.getSoapCharacterServicePort();

            String data = port.requestString(studentCode, qCode);
            StringBuilder result = new StringBuilder(data).reverse();
            port.submitString(studentCode, qCode, result.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
