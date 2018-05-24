package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;

class Server {
    private final ChatMediator chatMediator = new ChatMediator();
    private Consumer<String> consumer;
    private MessageSender messageSender = new MessageSender();

    Server(Consumer<String> consumer) {
        this.consumer = consumer;
    }

    void startServer() {

        try {
            ServerSocket serverSocket = new ServerSocket(8189);
            while (true) {
                Socket client = serverSocket.accept();

                new Thread(() -> handleClientInput(client)).start();
            }
        } catch (IOException e) {
            consumer.accept(e.getMessage());
        }

    }

    private void handleClientInput(Socket client) {
        messageSender.sendMessageToClient(client, "Hello from server:)");


        try {
            messageSender.sendMessageToClient(client, "Introduce yourself:");
            Scanner scanner = new Scanner(client.getInputStream());
            User user = new User(client, scanner.nextLine(), chatMediator, messageSender);
            while (true) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    consumer.accept(user.getUserName() + ": " + line);
                    user.send(line);
                    if ("quit".equalsIgnoreCase(line)) {
                        scanner.close();
                        chatMediator.detachUser(user);
                        break;
                    }
                }

            }
        } catch (IOException e) {
            consumer.accept(e.getMessage());
        }
    }


}