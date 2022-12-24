import client.UserClient;
import dto.ChangeUserRequest;
import dto.LoginUserRequest;
import dto.UserRequest;
import generator.ChangeUserRequestGenerator;
import generator.LoginUserRequestGenerator;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static generator.UserRequestGenerator.getRandomUser;
import static java.lang.Thread.sleep;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ChangeUserTest {
    private UserClient userClient;
    private String token;
    private UserRequest userRequest;

    @Before
    public void setUp() {
        userClient = new UserClient();
        userRequest = getRandomUser();
        userClient.create(userRequest).assertThat().statusCode(SC_OK);
        LoginUserRequest loginUserRequest = LoginUserRequestGenerator.from(userRequest);
        token = userClient.login(loginUserRequest)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken");
    }

    @After
    public void tearDown() throws InterruptedException {
        userClient.delete(token)
                .assertThat()
                .body("success", Matchers.equalTo(true));
        sleep(200);
    }

    @Test
    @DisplayName("Changing Email of user")
    public void changeUserEmailTest() {
        ChangeUserRequest changeUserRequest = ChangeUserRequestGenerator.changeEmail(userRequest);
        userClient.changeUserWithAuthorization(changeUserRequest, token)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Changing password of user")
    public void changeUserPasswordTest() {
        ChangeUserRequest changeUserRequest = ChangeUserRequestGenerator.changePassword(userRequest);
        userClient.changeUserWithAuthorization(changeUserRequest, token)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Changing name of user")
    public void changeUserNameTest() {
        ChangeUserRequest changeUserRequest = ChangeUserRequestGenerator.changeName(userRequest);
        userClient.changeUserWithAuthorization(changeUserRequest, token)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Changing email of user without authorization impossible")
    public void changeUserEmailTestWithoutAutorazation() {
        ChangeUserRequest changeUserRequest = ChangeUserRequestGenerator.changeEmail(userRequest);
        userClient.changeUserWithoutAuthorization(changeUserRequest)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Changing password of user without authorization impossible")
    public void changeUserPasswordWithoutAutorazationTest() {
        ChangeUserRequest changeUserRequest = ChangeUserRequestGenerator.changePassword(userRequest);
        userClient.changeUserWithoutAuthorization(changeUserRequest)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Changing name of user without authorization impossible")
    public void changeUserNameWithoutAutorazationTest() {
        ChangeUserRequest changeUserRequest = ChangeUserRequestGenerator.changeName(userRequest);
        userClient.changeUserWithoutAuthorization(changeUserRequest)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false));
    }
}
