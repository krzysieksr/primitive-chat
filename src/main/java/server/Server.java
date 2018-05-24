package server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    static ChatMediator chatMediator = new ChatMediator();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8189);
        while (true) {
            Socket client = serverSocket.accept();

            new Thread(() -> handleClientInput(client)).start();
        }
    }


    private static void handleClientInput(Socket client) {
        sendMessageToClient(client, "Hello from server:)");


        try {
            sendMessageToClient(client, "Introduce yourself:");
            Scanner scanner = new Scanner(client.getInputStream());
            User user = new User(client, scanner.nextLine(), chatMediator);
            while (true) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    System.out.println(user.getUserName() + ": " + line);
                    user.send(line);
                    if ("quit".equalsIgnoreCase(line)) {
                        scanner.close();
                        chatMediator.detachUser(user);
                        break;
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessageToClient(Socket client, String message) {
        try {
            OutputStream outputStream = client.getOutputStream();
            PrintWriter write = new PrintWriter(outputStream, true);
            write.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}