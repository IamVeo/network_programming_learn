package TCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedHashMap;

public class TCPCharacterStream2 {
    public static void main(String[] args) {
        String serverIp = "36.50.135.242";
        int serverPort = 2208;

        String studentCode = "B23DCCN756";
        String qCode = "l1hTDQyN";

        try {
            Socket socket = new Socket(serverIp, serverPort);
            socket.setSoTimeout(5000);

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String request = studentCode + ";" + qCode;
            bw.write(request);
            bw.newLine();
            bw.flush();

            String response = br.readLine();
            if(response != null){
                LinkedHashMap<Character, Integer> countMap = new LinkedHashMap<>();
                for(Character c : response.toCharArray()){
                    if(Character.isLetterOrDigit(c)){
                        countMap.put(c, countMap.getOrDefault(c, 0) + 1);
                    }
                }

                StringBuilder result = new StringBuilder();
                for(Character c : countMap.keySet()){
                    if(countMap.get(c) > 1) {
                        result.append(c).append(":").append(countMap.get(c)).append(",");
                    }
                }

                bw.write(result.toString());
                bw.newLine();
                bw.flush();
            }

            bw.close();
            br.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
