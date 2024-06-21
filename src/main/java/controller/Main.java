package controller;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.tyrus.server.Server;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static final int HTTP_SERVER_PORT = 80;
    public static final int WEBSOCKET_SERVER_PORT = 8080;

    public static void main(String[] args) {
        Main m = new Main();
        m.startHttpServer();
        m.startWebSocketServer();
    }

    private void startWebSocketServer() {
        Server server = new Server(null, WEBSOCKET_SERVER_PORT, null, null,
                WebsocketEndpoint.class);
        try {
            server.start();
            System.out.println("WebSocket server is running on port: " + WEBSOCKET_SERVER_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startHttpServer() {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(HTTP_SERVER_PORT), 0);
            server.createContext("/", new HtmlResponseHandler());
            server.start();
            System.out.println("Http server is running on port: " + HTTP_SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}