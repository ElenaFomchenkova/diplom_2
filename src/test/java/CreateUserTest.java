import client.UserClient;
import dto.LoginUserRequest;
import dto.UserRequest;
import generator.LoginUserRequestGenerator;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static generator.UserRequestGenerator.*;
import static java.lang.Thread.sleep;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateUserTest {
    private UserClient userClient;
    private String token;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @After
    public void tearDown() throws InterruptedException {
        if (token != null) {
            userClient.delete(token)
                    .assertThat()
                    .body("success", Matchers.equalTo(true));
        }
        sleep(200);
    }

    @Test
    @DisplayName("Creating user")
    public void userCreatedTest() {
        UserRequest userRequest = getRandomUser();
        userClient.create(userRequest).assertThat().statusCode(SC_OK);

        LoginUserRequest loginUserRequest = LoginUserRequestGenerator.from(userRequest);
        token = userClient.login(loginUserRequest)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken");
        System.out.println(token);
    }

    @Test
    @DisplayName("Same user should not be created")
    public void sameUserShouldNotBeCreatedTest() {
        UserRequest userRequest = getRandomUser();
        userClient.create(userRequest).assertThat().statusCode(SC_OK);

        LoginUserRequest loginUserRequest = LoginUserRequestGenerator.from(userRequest);
        token = userClient.login(loginUserRequest)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken");

        userClient.create(userRequest).assertThat().statusCode(SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Creating user without email impossible")
    public void userShouldNotCreatedWithoutEmail() {
        UserRequest userRequest = getRandomUserWithoutEmail();
        userClient.create(userRequest).assertThat().statusCode(SC_FORBIDDEN)
                .and().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Creating user without password impossible")
    public void userShouldNotCreatedWithoutPassword() {
        UserRequest userRequest = getRandomUserWithoutPassword();
        userClient.create(userRequest).assertThat().statusCode(SC_FORBIDDEN)
                .and().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Creating user without name impossible")
    public void userShouldNotCreatedWithoutUsername() {
        UserRequest userRequest = getRandomUserWithoutName();
        userClient.create(userRequest).assertThat().statusCode(SC_FORBIDDEN)
                .and().body("message", equalTo("Email, password and name are required fields"));
    }
}
