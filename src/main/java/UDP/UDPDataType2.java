package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPDataType2 {
    public static void main(String[] args) {
        String serverIp = "36.50.135.242";
        int port = 2207;

        String studentCode = "B23DCCN756";
        String qCode = "wfaCAvRV";

        try (DatagramSocket socket = new DatagramSocket()) {

            socket.setSoTimeout(5000);

            InetAddress address = InetAddress.getByName(serverIp);

            String request = ";" + studentCode + ";" + qCode;
            DatagramPacket sendPacket = new DatagramPacket(
                    request.getBytes(),
                    request.length(),
                    address,
                    port
            );
            socket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            String receiveString =
                    new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);

            String[] parts = receiveString.split(";");
            String requestId = parts[0];
            String[] list = parts[1].split(",");
            int max = Integer.parseInt(list[0]);
            int min = Integer.parseInt(list[0]);
            for(String s : list){
                int number = Integer.parseInt(s);
                if(number < min) min = number;
                if(number > max) max = number;
            }

            String result = requestId + ";" + max + "," + min;
            DatagramPacket resultPacket = new DatagramPacket(
                    result.getBytes(),
                    result.length(),
                    address,
                    port
            );
            socket.send(resultPacket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
