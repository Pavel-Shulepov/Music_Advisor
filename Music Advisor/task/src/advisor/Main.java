package advisor;

import advisor.utils.Server;
import advisor.utils.SpotifyClient;

import java.io.IOException;
import java.util.Scanner;

public class Main {


    private final static String authUrl = "/authorize?client_id=c0f908b3680043d7b7fd6b21f90b9a44&redirect_uri=http://localhost:8080&response_type=code";
    private final static SpotifyClient spotifyClient = new SpotifyClient();


    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length > 0 && args[1] != null ) {
             spotifyClient.setSpotifyAccountUrl(args[1], args[3]);
        }
        var scanner = new Scanner(System.in);
        var action = "";
        while (!action.toLowerCase().equals("exit")) {
            action = scanner.nextLine();
            if (action.startsWith("playlists")) {
                if (checkAuth()) {
                    spotifyClient.playlists(action.replaceFirst("playlists ", ""));
                } else {
                    System.out.println("Please, provide access for application.");
                }
            }
            switch (action.toLowerCase()) {
                case "auth":
                    auth();
                    break;
                case "new":
                    if (checkAuth()) {
                        spotifyClient.newRealises();
                    } else {
                        System.out.println("Please, provide access for application.");
                    }
                    break;
                case "featured":
                    if (checkAuth()) {
                        spotifyClient.featured();
                    } else {
                        System.out.println("Please, provide access for application.");
                    }
                    break;
                case "categories":
                    if (checkAuth()) {
                        spotifyClient.categories();
                    } else {
                        System.out.println("Please, provide access for application.");
                    }
                    break;
                case "exit":
                    break;
                default:
                    break;
            }
        }
    }

    private static void auth() throws IOException, InterruptedException {
        Server server = new Server();
        server.start();
        System.out.println("use this link to request the access code:");
        System.out.println(spotifyClient.getSpotifyAccountUrl() + authUrl);
        System.out.println("waiting for code...");
        while (!server.isCodeReceive()) {
            Thread.sleep(1000);
        }
        System.out.println(server.getCode());
        spotifyClient.spotifyAuth(server.getCode());
    }

    private static boolean checkAuth() {
        return spotifyClient.isAuth();
    }



}
