package TCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class TCPCharacterStream {
     public static void main(String[] args) {
         String serverIp = "36.50.135.242";
         int port = 2208;
         String studentCode = "B23DCCN756";
         String qCode = "phxws6is";

         try{
             Socket socket = new Socket(serverIp, port);
             socket.setSoTimeout(5000);

             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

             String request = studentCode + ";" + qCode;
             bw.write(request);
             bw.newLine();
             bw.flush();
             System.out.println("Sent request");

             String response = br.readLine();

             if(response != null && !response.isEmpty()){
                 String[] domains = response.split(",");
                 ArrayList<String> list = new ArrayList<>();

                 for(String domain : domains){
                     domain = domain.trim();
                     if(domain.endsWith(".edu")){
                         list.add(domain);
                     }
                 }

                 String result = String.join(", ", list);
                 System.out.println(result);

                 bw.write(result);
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
