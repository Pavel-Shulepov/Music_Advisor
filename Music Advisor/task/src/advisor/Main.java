package advisor;

import advisor.controller.SpotifyController;
import advisor.utils.Server;

import java.io.IOException;
import java.util.Scanner;

public class Main {


    private final static String authUrl = "/authorize?client_id=c0f908b3680043d7b7fd6b21f90b9a44&redirect_uri=http://localhost:8080&response_type=code";
    private final static SpotifyController controller = new SpotifyController();
    private static int limit = 5;

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length > 0 && args[1] != null ) {
             controller.setSpotifyAccountUrl(args[1], args[3]);
             limit = Integer.parseInt(args[5]);
        }
        var scanner = new Scanner(System.in);
        var action = "";
        while (!action.toLowerCase().equals("exit")) {
            action = scanner.nextLine();
            if (action.startsWith("playlists")) {
                String act = "";
                int offset = 0;
                int total = controller.getPlaylists(action.replaceFirst("playlists ", ""), limit, offset);
                while (!"exit".equals(act.toLowerCase())) {
                    act = scanner.nextLine();
                    if ("next".equals(act)) {
                        if (offset + limit >= total) {
                            System.out.println("No more pages.");
                        } else {
                            offset = offset + limit;
                            controller.getPlaylists(action.replaceFirst("playlists ", ""), limit, offset);
                        }
                    } else if ("prev".equals(act)) {
                        offset = offset - limit;
                        if (offset == -1) {
                            offset = 0;
                            System.out.println("No more pages.");
                        } else {
                            controller.getPlaylists(action.replaceFirst("playlists ", ""), limit, offset);
                        }
                    }
                }
            }
            switch (action.toLowerCase()) {
                case "auth":
                    auth();
                    break;
                case "new":
                    if (checkAuth()) {
                        String act = "";
                        int offset = 0;
                        int total = controller.getNewRealises(limit, offset);
                        while (!"exit".equals(act.toLowerCase())) {
                            act = scanner.nextLine();
                            if ("next".equals(act)) {
                                if (offset + limit >= total) {
                                    System.out.println("No more pages.");
                                } else {
                                    offset = offset + limit;
                                    controller.getNewRealises(limit, offset);
                                }
                            } else if ("prev".equals(act)) {
                                offset = offset - limit;
                                if (offset == -1) {
                                    offset = 0;
                                    System.out.println("No more pages.");
                                } else {
                                    controller.getNewRealises(limit, offset);
                                }
                            }
                        }
                    } else {
                        System.out.println("Please, provide access for application.");
                    }
                    break;
                case "featured":
                    if (checkAuth()) {
                        String act = "";
                        int offset = 0;
                        int total = controller.getFeatured(limit, offset);
                        while (!"exit".equals(act.toLowerCase())) {
                            act = scanner.nextLine();
                            if ("next".equals(act)) {
                                if (offset + limit >= total) {
                                    System.out.println("No more pages.");
                                } else {
                                    offset = offset + limit;
                                    controller.getFeatured(limit, offset);
                                }
                            } else if ("prev".equals(act)) {
                                offset = offset - limit;
                                if (offset == -1) {
                                    offset = 0;
                                    System.out.println("No more pages.");
                                } else {
                                    controller.getFeatured(limit, offset);
                                }
                            }
                        }
                    } else {
                        System.out.println("Please, provide access for application.");
                    }
                    break;
                case "categories":
                    if (checkAuth()) {
                        String act = "";
                        int offset = 0;
                        int total = controller.getCategories(limit, offset);
                        while (!"exit".equals(act.toLowerCase())) {
                            act = scanner.nextLine();
                            if ("next".equals(act)) {
                                if (offset + limit >= total) {
                                    System.out.println("No more pages.");
                                } else {
                                    offset = offset + limit;
                                    controller.getCategories(limit, offset);
                                }
                            } else if ("prev".equals(act)) {
                                offset = offset - limit;
                                if (offset == -1) {
                                    offset = 0;
                                    System.out.println("No more pages.");
                                } else {
                                    controller.getCategories(limit, offset);
                                }
                            }
                        }
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
        System.out.println(controller.getSpotifyAccountUrl() + authUrl);
        System.out.println("waiting for code...");
        while (!server.isCodeReceive()) {
            Thread.sleep(1000);
        }
        System.out.println(server.getCode());
        controller.doAuth(server.getCode());
    }

    private static boolean checkAuth() {
        return controller.isAuth();
    }



}
