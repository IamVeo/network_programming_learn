package TCP;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class TCPByteStream {
    public static void main(String[] args){
        String serverIp = "36.50.135.242";
        int port = 2206;

        String studentCode = "B23DCCN756";
        String qCode = "aPllz2A1";

        try{
            Socket socket = new Socket(serverIp, port);
            socket.setSoTimeout(5000);

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            String request = studentCode + ";" + qCode;
            out.write(request.getBytes());
            out.flush();

            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            if (bytesRead != -1) {
                String response = new String(buffer, 0, bytesRead);

                String[] nums = response.split(",");
                int[] list = new int[nums.length];
                for(int i = 0; i < nums.length; i++){
                    list[i] = Integer.parseInt(nums[i]);
                }

                Arrays.sort(list);

                int minDist = Integer.MAX_VALUE;
                int val1 = -1;
                int val2 = -1;

                for(int i = 0; i < list.length - 1; i++){
                    int dist = list[i + 1] - list[i];

                    if(dist <= minDist){
                        minDist = dist;
                        val1 = list[i];
                        val2 = list[i + 1];
                    }
                }

                String result = minDist + "," + val1 + "," + val2;
                out.write(result.getBytes());
                out.flush();
            }

            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
