package zad1;



import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Client {

    private String host;
    private int port;
    private String id;
    private String clientLog;

    public Client(String host, int port, String id){
        this.host = host;
        this.port = port;
        this.id = id;
    }

    private SocketChannel clientChannel;

    public void connect(){
        try {
            clientChannel = SocketChannel.open();
//            System.out.println("CLIENT: Connected to server: " + clientChannel.socket().getLocalPort());
            clientChannel.configureBlocking(false);
            clientChannel.connect(new InetSocketAddress(host,port));
            while (!clientChannel.finishConnect()){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Client: " + clientChannel.getLocalAddress() + " connected");
        } catch (IOException e) {
            System.out.println("Failed to create client socket..");
            e.printStackTrace();
            System.exit(-1);
        }
    }

//    public String send(String req){
//
//        StringBuffer responseString = new StringBuffer();
//
//        if (clientChannel.isOpen()) {
//            try {
//
//                byte[] message = (req+"\n").getBytes();
//                ByteBuffer buffer = ByteBuffer.wrap(message);
//
//                while (buffer.hasRemaining()) {
//                    clientChannel.write(buffer);
//                }
//
////                System.out.println("CLIENT: Sent a msg to server: " + req);
//
//                ByteBuffer inBuff = ByteBuffer.allocate(1024);
//
//                inBuff.clear();
//                responseString.setLength(0);
//
//
//                while (true) {
//                    int n = clientChannel.read(inBuff);
//                    if (n > 0) {
//                        inBuff.flip();
//                        CharBuffer cbuf = Charset.forName("ISO-8859-2").decode(inBuff);
//                        while (cbuf.hasRemaining()) {
//                            char c = cbuf.get();
//                            responseString.append(c);
//                        }
//                    } else {
//                        break;
//                    }
//                }
//
////                System.out.println("CLIENT: Response from server: " + responseString);
//
//            } catch (IOException e) {
////                e.printStackTrace();
//                System.exit(-1);
//            }
//
//            this.clientLog = responseString.toString();
//            return responseString.toString();
//        }
//        return null;
//    }

    public String getId() {
        return id;
    }

    public String getClientLog() {
        return clientLog;
    }
}
