package generator;

import dto.LoginUserRequest;
import dto.UserRequest;

public class LoginUserRequestGenerator {
    public static LoginUserRequest from(UserRequest userRequest) {
        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(userRequest.getEmail());
        loginUserRequest.setPassword(userRequest.getPassword());
        return loginUserRequest;
    }

    public static LoginUserRequest withWrongEmailAndPassword() {
        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail("yandex@yandex.ru");
        loginUserRequest.setPassword("1234");
        return loginUserRequest;
    }
}
