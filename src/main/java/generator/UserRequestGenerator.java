package generator;

import dto.LoginUserRequest;
import dto.UserRequest;
import org.apache.commons.lang3.RandomStringUtils;

public class UserRequestGenerator {
public static UserRequest getRandomUser() {
UserRequest userRequest = new UserRequest();
userRequest.setEmail(String.format(RandomStringUtils.randomAlphabetic(5)+"@yandex.ru"));
userRequest.setPassword(RandomStringUtils.randomAlphabetic(5));
userRequest.setName(RandomStringUtils.randomAlphabetic(5));
return userRequest;
}

    public static UserRequest getRandomUserWithoutEmail() {
        UserRequest userRequest = new UserRequest();
        userRequest.setPassword(RandomStringUtils.randomAlphabetic(5));
        userRequest.setName(RandomStringUtils.randomAlphabetic(5));
        return userRequest;
    }

    public static UserRequest getRandomUserWithoutPassword() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(String.format(RandomStringUtils.randomAlphabetic(5)+"@yandex.ru"));
        userRequest.setName(RandomStringUtils.randomAlphabetic(5));
        return userRequest;
    }
    public static UserRequest getRandomUserWithoutName() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(String.format(RandomStringUtils.randomAlphabetic(5)+"@yandex.ru"));
        userRequest.setPassword(RandomStringUtils.randomAlphabetic(5));
        return userRequest;
    }

}
