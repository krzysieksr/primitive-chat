package server;

import java.net.Socket;

public class User {
    private ChatMediator mediator;
    private Socket socket;
    private String userName;

    public User(Socket socket, String userName, ChatMediator chatMediator) {
        this.userName = userName;
        this.socket = socket;
        this.mediator = chatMediator;
        mediator.attachUser(this);
    }

    public void send(String message) {
        mediator.sendMessage(message, this);
    }

    public void receive(String message, String senderName) {
        message = senderName + ": " + message;
        Server.sendMessageToClient(socket, message);
    }

    public String getUserName() {
        return userName;
    }
}
