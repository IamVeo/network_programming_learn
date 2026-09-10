package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

class Student implements Serializable {

    private static final long serialVersionUID = 20171107L;

    private String id, code, name, email;

    public Student(String id, String code, String name, String email) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.email = email;
    }

    public String getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public void setId(String id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}

public class UDPObject {
    public static void main(String[] args) {
        String serverIp = "36.50.135.242";
        int port = 2209;
        String request = ";B23DCCN756;gyj1kQCD";

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
            Student student = (Student) ois.readObject();

            String name = student.getName();
            StringBuilder builder = new StringBuilder();
            for(String word : name.split(" ")){
                word = Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
                if(!builder.isEmpty()){
                    builder.append(' ');
                }
                builder.append(word);
            }
            student.setName(builder.toString());

            String[] words = name.toLowerCase().split(" ");
            StringBuilder sb = new StringBuilder();
            sb.append(words[words.length - 1]);
            for(int i = 0; i < words.length - 1; i++){
                sb.append(words[i].charAt(0));
            }
            sb.append("@ptit.edu.vn");
            student.setEmail(sb.toString());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(student);
            oos.flush();

            byte[] studentBytes = baos.toByteArray();
            byte[] requestIdBytes = requestId.getBytes(StandardCharsets.UTF_8);
            byte[] resultData = new byte[studentBytes.length + requestIdBytes.length];
            System.arraycopy(requestIdBytes, 0, resultData, 0, requestIdBytes.length);
            System.arraycopy(studentBytes, 0, resultData, requestIdBytes.length, studentBytes.length);

            DatagramPacket resultPacket =
                    new DatagramPacket(resultData, resultData.length, address, port);
            socket.send(resultPacket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
