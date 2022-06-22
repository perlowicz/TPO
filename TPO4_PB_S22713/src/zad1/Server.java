///**
// *
// *  @author Perliński Bartłomiej S22713
// *
// */
//
//package zad1;
//
//
//
//import java.io.IOException;
//import java.net.*;
//import java.nio.ByteBuffer;
//import java.nio.CharBuffer;
//import java.nio.channels.*;
//import java.nio.charset.Charset;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class Server extends Thread{
//
//    String host;
//    int port;
//    boolean keepAlive = true;
//    private static List<String> logs = new ArrayList<>();
//
//    public Server(String host, int port){
//        this.host = host;
//        this.port = port;
//    }
//
//    private Selector selector;
//
//    private ServerSocketChannel serverSocket;
//
//    private String clientId = "";
//
//    public void startServer(){
//        start();
//    }
//
//    @Override
//    public void run() {
//        try {
//            serverSocket = ServerSocketChannel.open();
//
//            serverSocket.configureBlocking(false);
//
//            serverSocket.socket().bind(new InetSocketAddress(host,port));
//
//            selector = Selector.open();
//            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
//
////            System.out.println("Serwer wystartował..");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        while (keepAlive){
//
//            try {
//                selector.select();
//
//                Set<SelectionKey> keys = selector.selectedKeys();
//                Iterator<SelectionKey> iterator = keys.iterator();
//
//
//                while (iterator.hasNext()) {
//
//                    SelectionKey myKey = iterator.next();
//                    iterator.remove();
//
//                    if (myKey.isAcceptable()) {
//
//                        SocketChannel client = serverSocket.accept();
//
//                        client.configureBlocking(false);
//
//                        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
//
//                        System.out.println("klient zarejestrowany: " + client.socket().getPort());
//                        continue;
//
//                    }
//                    if (myKey.isReadable()) {
//                        SocketChannel client = (SocketChannel) myKey.channel();
////                        System.out.println("obsługujemy klienta: " + client.socket().getPort());
////                        serviceRequest(client);
////                            client.register(selector, SelectionKey.OP_WRITE);
//                        if (!client.isOpen())
//                            return;
//
//                        requestBuffer.setLength(0);
//                        bbuf.clear();
//
//                        // Czytanie jest nieblokujące
//                        readloop:
//                        while (true) {               // kontynujemy je dopóki
//                            int n = client.read(bbuf);     // nie natrafimy na koniec wiersza
//                            if (n > 0) {
//                                bbuf.flip();
//                                CharBuffer cbuf = charset.decode(bbuf);
//                                while (cbuf.hasRemaining()) {
//                                    char c = cbuf.get();
//                                    if (c == '\r' | c == '\n')
//                                        break readloop;
//                                    requestBuffer.append(c);
//                                }
//                            }
//                        }
//                        System.out.println("dostałem żądanie: " + requestBuffer);
//                        continue;
//                    }
//                    if (myKey.isWritable()){
//                        SocketChannel client = (SocketChannel) myKey.channel();
//
//                        try {
//
//                            String[] requests = requestBuffer.toString().split(" ");
//
//                            if (requestBuffer.toString().equals("bye and log transfer")){
//
//                                logs.add(clientId + " logged out at " + LocalTime.now());
//                                clientLog.add("logged out");
//                                clientLog.add("=== " + clientId + " log end ===\n");
//                                sendResponse(String.join("\n", clientLog), client);
//
//                                System.out.println("wysyłam odpowiedź: " + String.join("\n", clientLog));
//                                clientLog = new ArrayList<>();
//                                client.close();
//                                client.socket().close();
//
//                            } else if (requests[0].equals("login")){
//
//                                clientId = requests[1];
//
////                System.out.println("Aktualny clientID: " + clientId);
//
//
//                                clientLog.add("=== " + clientId + " log start ===");
//                                clientLog.add("logged in");
//
//                                logs.add(requests[1] + " logged in at " + LocalTime.now());
//
//                                sendResponse("logged in", client);
//                                System.out.println("wysyłam odpowiedź: logged in");
//                            } else if (requestBuffer.toString().equals("bye")){
//
//                                clientLog.add("logged out");
//
//                                logs.add(clientId + " logged out");
//
//                                sendResponse("logged out", client);
//
//                                client.close();
//                                client.socket().close();
//                                System.out.println("wysyłam odpowiedź: logged out");
//                            } else {
//                                clientLog.add("Request: " + requests[0] + " " + requests[1]);
//
//                                logs.add(clientId + " request at " + LocalTime.now() + ": \"" + requests[0] + " " + requests[1] + "\"");
//
//                                clientLog.add("Result:\n" + Time.passed(requests[0], requests[1]));
//
//                                sendResponse(Time.passed(requests[0], requests[1]), client);
//                                System.out.println("wysyłam odpowiedź: " + Time.passed(requests[0], requests[1]));
//                            }
//
//                        } catch (Exception e){
//                            System.err.println("SERVER: Failed to service request..");
//                            e.printStackTrace();
//                            try {
//                                client.close();
//                                client.socket().close();
//                            } catch (Exception ex){}
//                        }
//                    }
//                }
//
//            } catch (Exception exc){
//                exc.printStackTrace();
//                continue;
//            }
//
//        }
//    }
//
//    public void stopServer(){
////        System.out.println("Zamykam server");
//        this.keepAlive = false;
//        try {
//            serverSocket.close();
//            serverSocket.socket().close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private StringBuffer requestBuffer = new StringBuffer();
//    private ByteBuffer bbuf = ByteBuffer.allocate(1024);
//    private static Charset charset  = Charset.forName("ISO-8859-2");
//    private List<String> clientLog = new ArrayList<>();
//
////    private void serviceRequest(SocketChannel sc) {}
//
//    private StringBuffer responseBuffer = new StringBuffer();
//
//    private void sendResponse(String response, SocketChannel client) throws IOException{
//        responseBuffer.setLength(0);
//        responseBuffer.append(response);
////        responseBuffer.append('\n');
//
//        ByteBuffer buffer = charset.encode(CharBuffer.wrap(responseBuffer));
//
//        client.write(buffer);
////        System.out.println("odsyłam odpowiedź: " + responseBuffer);
//    }
//
//    String getServerLog(){
//        return String.join("\n", logs);
//    }
//}

/**
 *
 *  @author Perliński Bartłomiej S22713
 *
 */

package zad1;



import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.time.LocalTime;
import java.util.*;

public class Server extends Thread{

    String host;
    int port;
    boolean keepAlive = true;
    private static List<String> logs = new ArrayList<>();

    public Server(String host, int port){
        this.host = host;
        this.port = port;
    }

    private Selector selector;

    private ServerSocketChannel serverSocket;

    private String clientId = "";

    public void startServer(){
        start();
    }

    @Override
    public void run() {
        try {
            serverSocket = ServerSocketChannel.open();

            serverSocket.configureBlocking(false);

            serverSocket.bind(new InetSocketAddress(host,port));

            selector = Selector.open();
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Serwer wystartował..");

        } catch (Exception e) {
            e.printStackTrace();
        }

        while (keepAlive){

            try {
                selector.select();

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();


                while (iterator.hasNext()) {

                    SelectionKey myKey = iterator.next();
                    iterator.remove();

                    if (myKey.isAcceptable()) {

                        SocketChannel client = serverSocket.accept();

                        client.configureBlocking(false);

                        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                        System.out.println("klient zarejestrowany: " + client.socket().getPort());
                        continue;

                    }
//                    if (myKey.isReadable()) {
//                        SocketChannel client = (SocketChannel) myKey.channel();
////                        System.out.println("obsługujemy klienta: " + client.socket().getPort());
//                        serviceRequest(client);
////                            client.register(selector, SelectionKey.OP_WRITE);
//                        continue;
//                    }
                }

            } catch (Exception exc){
                exc.printStackTrace();
                continue;
            }

        }
    }

    public void stopServer(){
//        System.out.println("Zamykam server");
        this.keepAlive = false;
        try {
            serverSocket.close();
            serverSocket.socket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StringBuffer requestBuffer = new StringBuffer();
    private ByteBuffer bbuf = ByteBuffer.allocate(1024);
    private static Charset charset  = Charset.forName("ISO-8859-2");
    private List<String> clientLog = new ArrayList<>();

    private void serviceRequest(SocketChannel sc) {

        while (keepAlive) {

            if (!sc.isOpen())
                return;

            requestBuffer.setLength(0);
            bbuf.clear();


            try {
                readLoop:
                // Czytanie jest nieblokujące
                while (true) {               // kontynujemy je dopóki
                    int n = sc.read(bbuf);     // nie natrafimy na koniec wiersza
                    if (n > 0) {
                        bbuf.flip();
                        CharBuffer cbuf = charset.decode(bbuf);
                        requestBuffer.append(cbuf.get());
                        while (cbuf.hasRemaining()) {
                            char c = cbuf.get();
                            if (c == '\r' || c == '\n') break readLoop;
                            requestBuffer.append(c);
                        }
                    }
//                    else break;
                }

//            System.out.println("dostałem żądanie: " + requestBuffer);

                String[] requests = requestBuffer.toString().split(" ");

                if (requestBuffer.toString().equals("bye and log transfer")) {

                    logs.add(clientId + " logged out at " + LocalTime.now());
                    clientLog.add("logged out");
                    clientLog.add("=== " + clientId + " log end ===\n");
                    sendResponse(String.join("\n", clientLog), sc);

                    clientLog = new ArrayList<>();
                    sc.close();
                    sc.socket().close();
                } else if (requests[0].equals("login")) {

                    clientId = requests[1];

//                System.out.println("Aktualny clientID: " + clientId);


                    clientLog.add("=== " + clientId + " log start ===");
                    clientLog.add("logged in");

                    logs.add(requests[1] + " logged in at " + LocalTime.now());

                    sendResponse("logged in", sc);
                } else if (requestBuffer.toString().equals("bye")) {

                    clientLog.add("logged out");

                    logs.add(clientId + " logged out");

                    sendResponse("logged out", sc);

                    sc.close();
                    sc.socket().close();
                } else {
                    clientLog.add("Request: " + requests[0] + " " + requests[1]);

                    logs.add(clientId + " request at " + LocalTime.now() + ": \"" + requests[0] + " " + requests[1] + "\"");

                    clientLog.add("Result:\n" + Time.passed(requests[0], requests[1]));

                    sendResponse(Time.passed(requests[0], requests[1]), sc);
                }

            } catch (Exception e) {
                System.err.println("SERVER: Failed to service request..");
                e.printStackTrace();
                try {
                    sc.close();
                    sc.socket().close();
                } catch (Exception ex) {
                }
            }
        }
    }

    private StringBuffer responseBuffer = new StringBuffer();

    private void sendResponse(String response, SocketChannel client) throws IOException{
        responseBuffer.setLength(0);
        responseBuffer.append(response);
//        responseBuffer.append('\n');


        ByteBuffer buffer = charset.encode(CharBuffer.wrap(responseBuffer));

        client.write(buffer);
//        System.out.println("odsyłam odpowiedź: " + responseBuffer);
    }

    String getServerLog(){
        return String.join("\n", logs);
    }
}