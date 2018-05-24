package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;

class Client {

    private final Consumer<String> consumer;
    private Socket client;
    private PrintWriter printWriter;

    Client(Consumer<String> consumer) {
        this.consumer = consumer;
        try {
            this.client = new Socket("localhost", 8189);
            this.printWriter = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            consumer.accept(e.getMessage());
        }
    }

    void connect() {
        new Thread(() -> writeToServer()).start();
        new Thread(() -> readFromServer()).start();
    }


    private void writeToServer() {
        Scanner scanner = new Scanner(System.in);

        while (!client.isClosed()) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                printWriter.println(line);
                if ("quit".equalsIgnoreCase(line)) {
                    try {
                        client.close();
                    } catch (IOException e) {
                        consumer.accept(e.getMessage());
                    }
                    printWriter.close();
                    scanner.close();
                }
            }
        }

    }


    private void readFromServer() {
        try {
            Scanner scanner = new Scanner(client.getInputStream());

            while (!client.isClosed()) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    consumer.accept(line);
                }

            }
        } catch (IOException e) {
            consumer.accept(e.getMessage());
        }
    }
}
