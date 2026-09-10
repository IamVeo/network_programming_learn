package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCPDataStream {
    public static void main(String[] args){
        String serverIp = "36.50.135.242";
        int port = 2207;

        String studentCode = "B23DCCN756";
        String qCode = "n9Fw7YEY";

        try {
            Socket socket = new Socket(serverIp, port);
            socket.setSoTimeout(5000);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String request = studentCode + ";" + qCode;
            dos.writeUTF(request);
            dos.flush();

            int a = dis.readInt();
            int b = dis.readInt();
            int sum = a + b;
            int product = a * b;
            dos.writeInt(sum);
            dos.flush();
            dos.writeInt(product);
            dos.flush();

            dis.close();
            dos.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
