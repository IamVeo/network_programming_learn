package SOAP;

import org.example.ws.character.CharacterService;
import org.example.ws.character.SoapCharacterService;

import java.util.List;

public class SOAP_T5fDv1FN {
    static void main(String[] args) {
        String studentCode = "B23DCCN756";
        String qCode = "T5fDv1FN";

        try{

            CharacterService cs = new CharacterService();
            SoapCharacterService port = cs.getSoapCharacterServicePort();
            List<String> data = port.requestStringArray(studentCode, qCode);

            for(int i = 0; i < data.size(); i++){
                String s = data.get(i);
                s = s.replaceAll("email=\\S+","email=[EMAIL]");
                s = s.replaceAll("phone=\\S+","phone=[PHONE]");
                s = s.replaceAll("token=\\S+","token=[TOKEN]");
                data.set(i, s);
            }

            data.sort((a, b) -> Integer.compare(getPriority(a), getPriority(b)));
            port.submitStringArray(studentCode, qCode, data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getPriority(String log){
        if(log.startsWith("ERROR")){
            return 1;
        } else if(log.startsWith("WARN")){
            return 2;
        } else if(log.startsWith("INFO")){
            return 3;
        } else {
            return 4;
        }
    }
}
