package advisor.utils;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    HttpServer server;
    String code;
    boolean codeReceive = false;

    public Server() throws IOException {
        this.server = HttpServer.create();
        server.bind(new InetSocketAddress(8080), 0);
        server.setExecutor(null);
    }

    public boolean isCodeReceive() {
        return codeReceive;
    }

    public String getCode() {
        return code;
    }

    public void start() {
        server.createContext("/", httpExchange -> {
            try {
                code = httpExchange.getRequestURI().getQuery();
                if (code != null && code.startsWith("code")) {
                    String message = "Got the code. Return back to your program.";
                    code = code.replaceFirst("code=", "");
                    codeReceive = true;
                    httpExchange.sendResponseHeaders(200, message.length());
                    httpExchange.getResponseBody().write(message.getBytes());
                    httpExchange.close();
                    this.stop();
                } else {
                    String message = "Not found authorization code. Try again.";
                    httpExchange.sendResponseHeaders(200, message.length());
                    httpExchange.getResponseBody().write(message.getBytes());
                    httpExchange.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        server.start();
    }

    public void stop() {
        server.stop(0);
        this.codeReceive = true;
    }
}
