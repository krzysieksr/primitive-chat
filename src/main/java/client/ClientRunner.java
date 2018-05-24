package client;

public class ClientRunner {

    public static void main(String[] args) {
        Client client = new Client(System.out::println);
        client.connect();
    }
}
