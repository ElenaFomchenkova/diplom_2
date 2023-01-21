import client.OrderClient;
import client.UserClient;
import dto.LoginUserRequest;
import dto.OrderRequest;
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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetListOfOrdersTest {
    UserClient userClient;
    String token;
    UserRequest userRequest;

    OrderClient orderClient;

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
        orderClient = new OrderClient();
        OrderRequest orderRequest = new OrderRequest(new String[] {"61c0c5a71d1f82001bdaaa7a", "61c0c5a71d1f82001bdaaa78", "61c0c5a71d1f82001bdaaa6e"});
        orderClient.createOrderWithAuthorization(orderRequest, token).assertThat()
                .statusCode(SC_OK);
    }

    @After
    public void tearDown() throws InterruptedException {
        userClient.delete(token)
                .assertThat()
                .body("success", Matchers.equalTo(true));
        sleep(200);
    }

    @Test
    @DisplayName("Getting list of orders for user with authorization")
    public void getListOfOrdersWithAuthorization() {
        orderClient.getListOfOrdersWithAuthorization(token).assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Impossible to get list of orders for user without authorization")
    public void getListOfOrdersWithoutAuthorization() {
        orderClient.getListOfOrdersWithoutAuthorization().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false));
    }

}
