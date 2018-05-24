package server;

import java.util.ArrayList;
import java.util.List;

class ChatMediator {
    private List<User> userList = new ArrayList<>();


    void sendMessage(String message, User user) {
        userList.stream().filter(u -> !u.equals(user)).forEach(u -> u.receive(message, user.getUserName()));
    }


    void attachUser(User user) {
        userList.add(user);
    }

    void detachUser(User user) {
        userList.remove(user);
    }
}
