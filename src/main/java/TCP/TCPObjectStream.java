package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

class Laptop implements Serializable {
    private static final long serialVersionUID = 20150711L;

    private int id;
    private String code, name;
    private int quantity;

    public Laptop(int id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}

public class TCPObjectStream {
    public static void main(String[] args) {
        String serverIp = "36.50.135.242";
        int serverPort = 2209;

        String studentCode = "B23DCCN756";
        String qCode = "LDa90hWW";

        try (
            Socket socket = new Socket(serverIp, serverPort);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
            ){

            socket.setSoTimeout(5000);
            out.writeObject(studentCode + ";" + qCode);
            out.flush();

            Laptop laptop = (Laptop) in.readObject();

            String name = laptop.getName();
            String[] words = name.split("\\s+");
            String temp = words[0];
            words[0] = words[words.length - 1];
            words[words.length - 1] = temp;
            String newName = String.join(" ", words);

            int quantity = laptop.getQuantity();
            StringBuilder builder = new StringBuilder(String.valueOf(quantity)).reverse();
            int newQuantity = Integer.parseInt(builder.toString());

            Laptop answer = new Laptop(laptop.getId(), laptop.getCode(), newName, newQuantity);
            out.writeObject(answer);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
