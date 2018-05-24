package server;

public class ServerRunner {
    public static void main(String[] args) {
        Server server = new Server(System.out::println);
        server.startServer();
    }

}
