package WS;

import org.example.ws.character.CharacterService;
import org.example.ws.character.SoapCharacterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WS_vTPJqEki {
    static void main(String[] args) {
        String studentCode = "B23DCCN756";
        String qCode = "vTPJqEki";

        try{

            CharacterService cs = new CharacterService();
            SoapCharacterService port = cs.getSoapCharacterServicePort();

            List<String> data = port.requestStringArray(studentCode, qCode);
            Map<Integer, List<String>> mp = new TreeMap<>();

            for(String s : data){
                int cnt = 0;
                for(char c : s.toLowerCase().toCharArray()){
                    if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'){
                        cnt++;
                    }
                }
                if(!mp.containsKey(cnt)){
                    mp.put(cnt, new ArrayList<>());
                }
                mp.get(cnt).add(s);
            }

            List<String> result = new ArrayList<>();

            for (List<String> l : mp.values()) {
                l.sort(String::compareTo);
                result.addAll(l);
            }

            port.submitCharacterStringArray(studentCode, qCode, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
