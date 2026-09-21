package RMI;

import java.net.URI;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMI_a8GdyUyC {
    static void main(String[] args) throws Exception {
        String studentCode = "B23DCCN756";
        String qCode = "a8GdyUyC";
        String serverIp = "36.50.135.242";

        Registry registry = LocateRegistry.getRegistry(serverIp);
        CharacterService cs = ((CharacterService) registry.lookup("RMICharacterService"));
        String data = cs.requestCharacter(studentCode, qCode);

        String encoded = new URI(null, null, data, null).toASCIIString();
        cs.submitCharacter(studentCode, qCode, encoded);
    }
}
