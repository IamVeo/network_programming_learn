package TCP;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TCPNIOStream {
    public static void main(String[] args) {
        String serverIp = "36.50.135.242";
        int port = 2211;
        String request = "B23DCCN756;u19wj7ec";

        try(SocketChannel channel = SocketChannel.open()){

            channel.connect(new InetSocketAddress(serverIp, port));
            channel.socket().setSoTimeout(5000);
            writeFrame(channel, request);

            StringBuilder httpBuilder =  new StringBuilder();
            for(int i = 1; i <= 3; ++i){
                String part = readFrame(channel);
                httpBuilder.append(part);
            }
            String httpRequest = httpBuilder.toString();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readFully(SocketChannel channel, ByteBuffer buffer) throws IOException {

        while (buffer.hasRemaining()) {
            int bytesRead = channel.read(buffer);
            if (bytesRead == -1) {
                throw new IOException("End of stream reached");
            }
        }

    }

    private static String readFrame(SocketChannel channel) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(4);
        readFully(channel, buffer);

        buffer.flip();
        int length = buffer.getInt();
        ByteBuffer payloadBuffer = ByteBuffer.allocate(length);
        readFully(channel, payloadBuffer);
        payloadBuffer.flip();

        byte[] payload = new byte[length];
        payloadBuffer.get(payload);
        return new String(payload, StandardCharsets.UTF_8);

    }

    private static void writeFully(SocketChannel channel, ByteBuffer buffer) throws IOException {

        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }

    }

    private static void writeFrame(SocketChannel channel, String message) throws IOException {

        byte[] payload = message.getBytes(StandardCharsets.UTF_8);

        ByteBuffer buffer = ByteBuffer.allocate(4 + payload.length);
        buffer.putInt(payload.length);
        buffer.put(payload);
        buffer.flip();

        writeFully(channel, buffer);
    }

//    private static String parseHttpRequest(String httpRequest){
//        String[] lines = httpRequest.split("\\r\\n");
//
//
//    }
}
