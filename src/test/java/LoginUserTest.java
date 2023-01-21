import client.UserClient;
import dto.LoginUserRequest;
import dto.UserRequest;
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
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginUserTest {

    private UserClient userClient;
    private String token;
    private UserRequest userRequest;

    @Before
    public void setUp() {
        userClient = new UserClient();
        userRequest = getRandomUser();
        userClient.create(userRequest).assertThat().statusCode(SC_OK);
    }

    @After
    public void tearDown() throws InterruptedException {
            userClient.delete(token)
                    .assertThat()
                    .body("success", Matchers.equalTo(true));
        sleep(200);
    }

    @Test
    @DisplayName("Login user")
    public void userLoginTest() {
        LoginUserRequest loginUserRequest = LoginUserRequestGenerator.from(userRequest);
        token = userClient.login(loginUserRequest)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Login user without mail and password")
    public void userLoginWithWrongEmailAndPassword(){
        LoginUserRequest loginUserRequest = LoginUserRequestGenerator.withWrongEmailAndPassword();
        userClient.login(loginUserRequest)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED);

        LoginUserRequest loginUserRequest1 = LoginUserRequestGenerator.from(userRequest);
        token = userClient.login(loginUserRequest1)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken");
    }
}
