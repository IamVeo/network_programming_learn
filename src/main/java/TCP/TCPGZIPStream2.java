package TCP;

import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TCPGZIPStream2 {
    public static void main(String[] args) {
        String serverIp = "36.50.135.242";
        int serverPort = 2210;
        String request = "B23DCCN756;TwV8hs5J";

        try(Socket socket = new Socket(serverIp, serverPort);){

            socket.setSoTimeout(5000);

            GZIPOutputStream out = new GZIPOutputStream(socket.getOutputStream(), true);
            out.write((request + "\n").getBytes(StandardCharsets.UTF_8));
            out.flush();

            GZIPInputStream in = new GZIPInputStream(socket.getInputStream());
            String response = readLine(in);

            char[] list = response.toCharArray();
            Arrays.sort(list);
            String result = new String(list);

            out.write((result + "\n").getBytes(StandardCharsets.UTF_8));
            out.flush();

            out.finish();
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readLine(GZIPInputStream in) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1) {

            if(b == '\n') { break; }
            if(b != '\r') { baos.write(b); }

        }
        return baos.toString(StandardCharsets.UTF_8);
    }
}
