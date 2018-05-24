package client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        try {
            Socket client = new Socket("localhost", 8189);
            new Thread(() -> writeToServer(client)).start();
            new Thread(() -> readFromServer(client)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeToServer(Socket client) {
        PrintWriter printWriter = getPrintWriter(client);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                printWriter.println(line);
                if ("quit".equalsIgnoreCase(line)) {
                    printWriter.close();
                    scanner.close();
                    System.exit(0);
                }
            }
        }

    }

    private static PrintWriter getPrintWriter(Socket client) {
        OutputStream outputStream = null;
        try {
            outputStream = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PrintWriter(outputStream, true);
    }


    private static void readFromServer(Socket client) {
        try {
            Scanner scanner = new Scanner(client.getInputStream());
            while (true) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    System.out.println(line);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
