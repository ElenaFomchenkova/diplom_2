package generator;

import dto.ChangeUserRequest;
import dto.LoginUserRequest;
import dto.UserRequest;

public class ChangeUserRequestGenerator {
    public static ChangeUserRequest changeEmail(UserRequest userRequest) {
        ChangeUserRequest changeUserRequest = new ChangeUserRequest();
        changeUserRequest.setEmail( "change");
        changeUserRequest.setPassword(userRequest.getPassword());
        changeUserRequest.setName(userRequest.getName());
        return changeUserRequest;
    }

    public static ChangeUserRequest changePassword(UserRequest userRequest) {
        ChangeUserRequest changeUserRequest = new ChangeUserRequest();
        changeUserRequest.setEmail(userRequest.getEmail());
        changeUserRequest.setPassword("change");
        changeUserRequest.setName(userRequest.getName());
        return changeUserRequest;
    }

    public static ChangeUserRequest changeName(UserRequest userRequest) {
        ChangeUserRequest changeUserRequest = new ChangeUserRequest();
        changeUserRequest.setEmail(userRequest.getEmail());
        changeUserRequest.setPassword(userRequest.getPassword());
        changeUserRequest.setName("change");
        return changeUserRequest;
    }
}
