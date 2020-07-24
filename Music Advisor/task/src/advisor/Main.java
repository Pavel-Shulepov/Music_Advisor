package advisor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
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

    static String spotifyUrl = "https://accounts.spotify.com";
    static String authUrl = "/authorize?client_id=c0f908b3680043d7b7fd6b21f90b9a44&redirect_uri=http://localhost:8080&response_type=code";

    static boolean AUTH = false;

    static String code = "";
    static String token = "";


    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length > 0 && args[1] != null ) {
            spotifyUrl = args[1];
        }
        var scanner = new Scanner(System.in);
        var action = "";
        while (!action.toLowerCase().equals("exit")) {
            action = scanner.nextLine();
            switch (action.toLowerCase()) {
                case "auth":
                    auth();
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

    private static void auth() throws IOException, InterruptedException {
        Server server = new Server();
        server.start();
        System.out.println("use this link to request the access code:");
        System.out.println(spotifyUrl + authUrl);
        System.out.println("waiting for code...");
        while (!server.isCode) {
            Thread.sleep(1000);
        }
        System.out.println(server.code);
        code = server.code;
        token();
    }

    private static boolean checkAuth() throws IOException {
        return AUTH;
    }

    private static void token() throws IOException, InterruptedException {

        String uri = spotifyUrl + "/api/token";

        System.out.println("code received\n" +
                "making http request for access_token...");

        HttpClient httpClient = HttpClient.newBuilder().build();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization"," Basic " + Base64.getEncoder().encodeToString(("c0f908b3680043d7b7fd6b21f90b9a44:f90c31ce1569413082142ea9c0a3fde9").getBytes()))
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code&code=" + code + "&redirect_uri=http://localhost:8080"))
                .build();

        HttpResponse<String> response =  httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        token = response.body();
        response.body();
        System.out.println("response:");
        System.out.println(token);
        AUTH = true;
    }

}
