package server;

import java.net.Socket;

public class User {
    private ChatMediator mediator;
    private final MessageSender messageSender;
    private Socket socket;
    private String userName;

    public User(Socket socket, String userName, ChatMediator chatMediator, MessageSender messageSender) {
        this.userName = userName;
        this.socket = socket;
        this.mediator = chatMediator;
        this.messageSender = messageSender;
        mediator.attachUser(this);
    }

    public void send(String message) {
        mediator.sendMessage(message, this);
    }

    public void receive(String message, String senderName) {
        message = senderName + ": " + message;
        messageSender.sendMessageToClient(socket, message);
    }

    public String getUserName() {
        return userName;
    }
}
