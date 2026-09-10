package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPString2 {
    public static void main(String[] args) {
        String serverIp = "36.50.135.242";
        int port = 2208;

        String request = ";B23DCCN756;T8YluI4f";

        try (DatagramSocket socket = new DatagramSocket()) {

            socket.setSoTimeout(5000);

            InetAddress address = InetAddress.getByName(serverIp);
            DatagramPacket requestPacket =
                    new DatagramPacket(request.getBytes(), request.length(), address, port);
            socket.send(requestPacket);

            byte[] buffer = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);
            String responseString = new
                    String(responsePacket.getData(), 0, responsePacket.getLength(), StandardCharsets.UTF_8);

            String[] parts = responseString.split(";");
            String requestId = parts[0];
            String[] data = parts[1].split(" ");
            StringBuilder sb = new StringBuilder();
            for(String word : data){
                word = Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
                if(!sb.isEmpty()){
                    sb.append(' ');
                }
                sb.append(word);
            }
            String normalizedData = sb.toString();

            String result = requestId + ";" + normalizedData;
            DatagramPacket resultPacket = new
                    DatagramPacket(result.getBytes(), result.length(), address, port);
            socket.send(resultPacket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
