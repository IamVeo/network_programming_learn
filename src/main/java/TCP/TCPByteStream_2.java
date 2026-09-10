package TCP;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPByteStream_2 {
    public static void main(String[] args) {
        String serverIp = "36.50.135.242";
        int port = 2206;

        String studentCode = "B23DCCN756";
        String qCode = "qmWVJ74Y";

        try{
            Socket socket = new Socket(serverIp, port);
            socket.setSoTimeout(5000);

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String request = studentCode + ";" + qCode;
            outputStream.write(request.getBytes());
            outputStream.flush();

            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            if(bytesRead != -1){
                String response = new String(buffer, 0, bytesRead);

                String[] nums = response.split(",");
                int[] numbers = new int[nums.length];
                for(int i = 0; i < nums.length; i++){
                    numbers[i] = Integer.parseInt(nums[i]);
                }

                int maxValue = -1;
                int secondLargest = -1;
                int pos = -1;
                int secondLargestIndex = -1;
                for(int i = 0; i < numbers.length; i++){
                    if(numbers[i] > maxValue){
                        secondLargest = maxValue;
                        secondLargestIndex = pos;
                        maxValue = numbers[i];
                        pos = i;
                    } else if (numbers[i] > secondLargest){
                        secondLargest = numbers[i];
                        secondLargestIndex = i;
                    }
                }
                String result = secondLargest + "," + secondLargestIndex;
                outputStream.write(result.getBytes());
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
