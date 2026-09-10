package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPDataType {
    public static void main(String[] args) {
        String serverIp = "36.50.135.242";
        int port = 2207;

        String studentCode = "B23DCCN756";
        String qCode = "lwx4BTKg";

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(5000);

            InetAddress address = InetAddress.getByName(serverIp);

            String request = ";" + studentCode + ";" + qCode;
            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);

            DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, address, port);
            socket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket =
                    new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String response = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength(),
                    StandardCharsets.UTF_8
            );

            String[] parts = response.split(";");

            String requestId = parts[0];
            int n = Integer.parseInt(parts[1]);
            boolean[] checked = new boolean[n + 1];

            String[] numbers = parts[2].split(",");
            for(String number : numbers) {
                checked[Integer.parseInt(number)] = true;
            }

            StringBuilder sb = new StringBuilder();
            for(int i = 1; i <= n; i++) {
                if(!checked[i]) {
                    if(!sb.isEmpty()) {
                        sb.append(",");
                    }
                    sb.append(i);
                }
            }

            String result = requestId + ";" + sb;
            byte[] resultData = result.getBytes(StandardCharsets.UTF_8);
            DatagramPacket resultPacket =
                new DatagramPacket(resultData, resultData.length, address, port);
            socket.send(resultPacket);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
