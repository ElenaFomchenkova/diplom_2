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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static generator.UserRequestGenerator.getRandomUser;
import static java.lang.Thread.sleep;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParametrizedTest {

    private String[] ingredients;
    private int statusCode;

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
    }

    @After
    public void tearDown() throws InterruptedException {
        userClient.delete(token)
                .assertThat()
                .body("success", Matchers.equalTo(true));
        sleep(200);
    }
 public CreateOrderParametrizedTest(String[] ingredients, int statusCode){
        this.ingredients = ingredients;
        this.statusCode = statusCode;
 }

    @Parameterized.Parameters
    public static Object [][] getTestData(){
        return new Object[][] {
                {new String[] {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"}, 200},
                {new String[] {"61c0c5a71d1f82001bdaaa7a", "61c0c5a71d1f82001bdaaa78", "61c0c5a71d1f82001bdaaa6e"}, 200},
                {new String[] {""}, 500},
                {new String[] {"60d3b41abdacab0026a733c6"}, 400},};
    }

    @Test
    @DisplayName("Creating order for user with authorization")
    public void createOrderWithAuthorization() {
        OrderRequest orderRequest = new OrderRequest(ingredients);
        orderClient.createOrderWithAuthorization(orderRequest, token).assertThat()
                .statusCode(statusCode);
    }

    @Test
    @DisplayName("Creating order for user without authorization")
    public void createOrderWithoutAuthorization() {
        OrderRequest orderRequest = new OrderRequest(ingredients);
        orderClient.createOrderWithoutAuthorization(orderRequest).assertThat()
                .statusCode(statusCode);
    }

}
