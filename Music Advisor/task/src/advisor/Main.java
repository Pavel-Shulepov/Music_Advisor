package advisor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class Main {

    static final String NEW_RELEASES = "---NEW RELEASES---\n" +
            "Mountains [Sia, Diplo, Labrinth]\n" +
            "Runaway [Lil Peep]\n" +
            "The Greatest Show [Panic! At The Disco]\n" +
            "All Out Life [Slipknot]\n";

    static final String FEATURED = "---FEATURED---\n" +
            "Mellow Morning\n" +
            "Wake Up and Smell the Coffee\n" +
            "Monday Motivation\n" +
            "Songs to Sing in the Shower\n";

    static final String CATEGORIES = "---CATEGORIES---\n" +
            "Top Lists\n" +
            "Pop\n" +
            "Mood\n" +
            "Latin\n";

    static final String MOOD_PLAYLISTS = "---MOOD PLAYLISTS---\n" +
            "Walk Like A Badass  \n" +
            "Rage Beats  \n" +
            "Arab Mood Booster  \n" +
            "Sunday Stroll\n";

    static final String GOODBYE = "---GOODBYE!---\n";

    static final String AUTH_URL = "https://accounts.spotify.com/authorize?client_id=c0f908b3680043d7b7fd6b21f90b9a44&redirect_uri=http://127.0.0.1:45678&response_type=code";
    static boolean AUTH = false;

    static String code = null;
    static HttpServer server;

    public static void main(String[] args) throws IOException, InterruptedException {
        var scanner = new Scanner(System.in);
        var action = "";
        while (!action.toLowerCase().equals("exit")) {
            action = scanner.nextLine();
            switch (action.toLowerCase()) {
                case "auth":
                    System.out.println("use this link to request the access code:");
                    System.out.println(AUTH_URL);
                    auth(args[1]);
//                    AUTH = true;
                    break;
                case "new":
                    if (checkAuth()) {
                        System.out.println(NEW_RELEASES);
                    } else {
                        System.out.println("Please, provide access for application.");
                    }
                    break;
                case "featured":
                    if (checkAuth()) {
                        System.out.println(FEATURED);
                    } else {
                        System.out.println("Please, provide access for application.");
                    }
                    break;
                case "categories":
                    if (checkAuth()) {
                        System.out.println(CATEGORIES);
                    } else {
                        System.out.println("Please, provide access for application.");
                    }
                    break;
                case "playlists mood":
                    if (checkAuth()) {
                        System.out.println(MOOD_PLAYLISTS);
                    } else {
                        System.out.println("Please, provide access for application.");
                    }
                    break;
                case "exit":
                    System.out.println(GOODBYE);
                    break;
                default:
                    break;
            }
        }
    }

    private static void auth(String access) throws IOException {
        server = HttpServer.create();
        server.bind(new InetSocketAddress(45678), 0);
        server.createContext("/", new GetHandler());
        server.start();
        System.out.println("waiting for code...");
    }

    private static boolean checkAuth() throws IOException {
        return AUTH;
    }

    static class GetHandler implements HttpHandler {
        public void handle(HttpExchange httpExchange) throws IOException {
            var response = httpExchange.getRequestURI().getQuery();
            if (response == null) {
                String resp = "Not found authorization code. Try again.";
                httpExchange.sendResponseHeaders(200, resp.length());
                httpExchange.getResponseBody().write(resp.getBytes());
                httpExchange.getResponseBody().close();
            } else if (response.startsWith("code=")) {
                code = response.replaceFirst("code=", "");
                String resp = "Got the code. Return back to your program.";
                httpExchange.sendResponseHeaders(200, resp.length());
                httpExchange.getResponseBody().write(resp.getBytes());
                httpExchange.getResponseBody().close();
                stopServer();
            }
            Main.writeResponse(httpExchange, response);
        }
    }

    public static void writeResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public static void stopServer() {
        System.out.println("code received\n");
        server.stop(1);
    }

}
