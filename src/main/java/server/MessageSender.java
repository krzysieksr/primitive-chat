package server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

class MessageSender {
    void sendMessageToClient(Socket client, String message) {
        try {
            OutputStream outputStream = client.getOutputStream();
            PrintWriter write = new PrintWriter(outputStream, true);
            write.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
