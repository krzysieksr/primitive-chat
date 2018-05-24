package server;

import java.util.ArrayList;
import java.util.List;

public class ChatMediator {
    private List<User> userList = new ArrayList<>();


    public void sendMessage(String message, User user) {
        userList.stream().filter(u -> !u.equals(user)).forEach(u -> u.receive(message, user.getUserName()));
    }


    public void attachUser(User user) {
        userList.add(user);
    }

    public void detachUser(User user) {
        userList.remove(user);
    }
}
