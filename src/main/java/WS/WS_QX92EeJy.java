package WS;


import org.example.ws.character.CharacterService;
import org.example.ws.character.SoapCharacterService;

import java.util.Collections;
import java.util.List;

public class WS_QX92EeJy {
    static void main(String[] args) {
        String studentCode = "B23DCCN756";
        String qCode = "QX92EeJy";

        try {

            CharacterService cs = new CharacterService();
            SoapCharacterService port = cs.getSoapCharacterServicePort();

            List<Integer> data = port.requestCharacter(studentCode, qCode);

            int k = data.getFirst() % data.size();
            Collections.rotate(data, k);

            port.submitCharacterCharArray(studentCode, qCode, data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
