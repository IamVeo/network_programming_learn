package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMI_ph1bo0Xu {

    private static String convertDigit(int digit, String one, String five, String ten){
        StringBuilder sb = new StringBuilder();

        if(digit <= 3){
            sb.append(one.repeat(digit));
        } else if (digit == 4) {
            sb.append(one).append(five);
        } else if (digit <= 8){
            sb.append(five).append(one.repeat(digit - 5));
        } else{
            sb.append(one).append(ten);
        }
        return sb.toString();
    }

    private static String toRoman(int n){
        StringBuilder sb = new StringBuilder();

        sb.append("M".repeat(n / 1000));
        n %= 1000;

        sb.append(convertDigit(n / 100, "C", "D", "M"));
        n %= 100;

        sb.append(convertDigit(n / 10, "X", "L", "C"));
        n %= 10;

        sb.append(convertDigit(n, "I", "V", "X"));

        return sb.toString();
    }

    static void main(String[] args) throws Exception {
        String studentCode = "B23DCCN756";
        String qCode = "ph1bo0Xu";
        String serverIp = "36.50.135.242";

        Registry registry = LocateRegistry.getRegistry(serverIp);
        CharacterService characterService = (CharacterService) registry.lookup("RMICharacterService");
        String requestCharacter = characterService.requestCharacter(studentCode, qCode);

        int n = Integer.parseInt(requestCharacter.trim());
        String result = toRoman(n);
        characterService.submitCharacter(studentCode, qCode, result);
    }
}
