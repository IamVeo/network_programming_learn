package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCPDataStream2 {
    public static void main(String[] args) {
        String serverIp = "36.50.135.242";
        int port = 2207;

        String studentCode = "B23DCCN756";
        String qCode = "Oq4X6yO8";

        try{
            Socket socket = new Socket(serverIp, port);
            socket.setSoTimeout(5000);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String request = studentCode + ";" + qCode;
            dos.writeUTF(request);
            dos.flush();

            String cipherText = dis.readUTF();
            int s = dis.readInt();
            s = s % 26;

            StringBuilder res = new StringBuilder();
            for(char c : cipherText.toCharArray()){
                if(c >= 'A' && c <= 'Z'){
                    char decoded = (char)((c - 'A' - s + 26) % 26 + 'A');
                    res.append(decoded);
                } else if(c >= 'a' && c <= 'z'){
                    char decoded = (char)((c - 'a' - s + 26) % 26 + 'a');
                    res.append(decoded);
                } else{
                    res.append(c);
                }
            }

            String result = res.toString();
            dos.writeUTF(result);
            dos.flush();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
