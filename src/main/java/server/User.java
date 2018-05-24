package server;

import java.net.Socket;

class User {
    private ChatMediator mediator;
    private final MessageSender messageSender;
    private Socket socket;
    private String userName;

    User(Socket socket, String userName, ChatMediator chatMediator, MessageSender messageSender) {
        this.userName = userName;
        this.socket = socket;
        this.mediator = chatMediator;
        this.messageSender = messageSender;
        mediator.attachUser(this);
    }

    void send(String message) {
        mediator.sendMessage(message, this);
    }

    void receive(String message, String senderName) {
        message = senderName + ": " + message;
        messageSender.sendMessageToClient(socket, message);
    }

    String getUserName() {
        return userName;
    }
}
