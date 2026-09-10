package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Customer implements Serializable {

    private static final long serialVersionUID = 20151107L;

    private String id, code, name, dayOfBirth, userName;

    public Customer(String id, String code, String name, String dayOfBirth, String userName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userName = userName;
    }

    public String getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getDayOfBirth() { return dayOfBirth; }
    public String getUserName() { return userName; }
    public void setId(String id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }
    public void setDayOfBirth(String dayOfBirth) { this.dayOfBirth = dayOfBirth; }
    public void setUserName(String userName) { this.userName = userName; }

}

public class UDPObject2 {
    public static void main(String[] args) {
        String serverIp = "36.50.135.242";
        int port = 2209;
        String request = ";B23DCCN756;2W8eXQfW";

        try(DatagramSocket socket = new DatagramSocket()) {

            socket.setSoTimeout(5000);
            InetAddress address = InetAddress.getByName(serverIp);
            DatagramPacket requestPacket =
                    new DatagramPacket(request.getBytes(), request.length(), address, port);
            socket.send(requestPacket);

            byte[] buffer = new byte[1024];
            DatagramPacket responsePacket =
                    new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);

            String requestId =
                    new String(responsePacket.getData(), responsePacket.getOffset(), 8, StandardCharsets.UTF_8);
            ByteArrayInputStream bais =
                    new ByteArrayInputStream(
                            responsePacket.getData(),
                            responsePacket.getOffset() + 8,
                            responsePacket.getLength() - 8
                    );
            ObjectInputStream ois = new ObjectInputStream(bais);
            Customer customer = (Customer) ois.readObject();

            String name = customer.getName();
            customer.setName(formatName(name));
            customer.setUserName(generateUserName(name));

            String dayOfBirth = customer.getDayOfBirth();
            customer.setDayOfBirth(formatDate(dayOfBirth));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(customer);
            oos.flush();

            byte[] requestIdBytes = requestId.getBytes(StandardCharsets.UTF_8);
            byte[] dataBytes = baos.toByteArray();
            byte[] resultData = new byte[dataBytes.length + requestIdBytes.length];
            System.arraycopy(requestIdBytes, 0, resultData, 0, requestIdBytes.length);
            System.arraycopy(dataBytes, 0, resultData, requestIdBytes.length, dataBytes.length);

            DatagramPacket resultPacket =
                    new DatagramPacket(resultData, resultData.length, address, port);
            socket.send(resultPacket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String formatName(String name){
        String[] words = name.toLowerCase().split(" ");

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length - 1; i++) {
            if(!builder.isEmpty()){
                builder.append(' ');
            }
            builder.append(Character.toUpperCase(words[i].charAt(0))).append(words[i].substring(1));
        }

        return words[words.length - 1].toUpperCase() + ", " + builder;
    }

    public static String formatDate(String date){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inputDate = LocalDate.parse(date, inputFormatter);
        return inputDate.format(outputFormatter);
    }

    public static String generateUserName(String name){
        String[] words = name.toLowerCase().split(" ");

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < words.length - 1; i++){
            sb.append(words[i].charAt(0));
        }
        sb.append(words[words.length - 1]);

        return sb.toString();
    }
}
