package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UDPString {
    public static void main(String[] args) {
        String serverIp = "36.50.135.242";
        int port = 2208;

        String studentCode = "B23DCCN756";
        String qCode = "rGLN84II";

        try(DatagramSocket socket = new DatagramSocket()) {

            socket.setSoTimeout(5000);

            InetAddress address = InetAddress.getByName(serverIp);

            String request = ";" + studentCode + ";" + qCode;
            DatagramPacket requestPacket = new DatagramPacket(request.getBytes(), request.getBytes().length, address, port);
            socket.send(requestPacket);

            byte[] buffer = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);

            String response =
                    new String(responsePacket.getData(), 0, responsePacket.getLength(), StandardCharsets.UTF_8);

            String[] parts = response.split(";");
            String requestId = parts[0];
            char maxChar = getMaxChar(parts);

            StringBuilder sb = new StringBuilder();
            sb.append(maxChar).append(":");
            for(int i = 0; i < parts[1].length(); i++) {
                if(maxChar == parts[1].charAt(i)) {
                    sb.append(i + 1).append(",");
                }
            }

            String result = requestId + ";" + sb;
            DatagramPacket resultPacket =
                    new DatagramPacket(result.getBytes(), result.getBytes().length, address, port);
            socket.send(resultPacket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static char getMaxChar(String[] parts) {
        Map<Character, Integer> charCountMap = new HashMap<>();
        for(char c : parts[1].toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }

        char maxChar = parts[1].charAt(0);
        int maxCount = 0;

        for(char c : parts[1].toCharArray()) {
            int count = charCountMap.get(c);
            if(count > maxCount) {
                maxCount = count;
                maxChar = c;
            }
        }
        return maxChar;
    }
}
