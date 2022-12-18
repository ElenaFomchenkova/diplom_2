package client;

import dto.OrderRequest;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    //создаем заказ авторизованным пользователем
    @Step("Send POST request to /api/orders with authorization")
    public ValidatableResponse createOrderWithAuthorization(OrderRequest orderRequest, String token) {
        return given()
                .spec(getDefaultRequestSpec())
                .header("Authorization", token)
                .body(orderRequest)
                .post("orders")
                .then()
                .log()
                .all();
    }
//создаем заказ неавторизованным пользователем
    @Step("Send POST request to /api/orders without authorization")
    public ValidatableResponse createOrderWithoutAuthorization(OrderRequest orderRequest) {
        return given()
                .spec(getDefaultRequestSpec())
                .body(orderRequest)
                .post("orders")
                .then()
                .log()
                .all();
    }
// получаем список заказов авторизованного пользователя
    @Step("Send GET request to /api/orders with authorization")
    public ValidatableResponse getListOfOrdersWithAuthorization(String token) {
        return given()
                .spec(getDefaultRequestSpec())
                .header("Authorization", token)
                .get("orders")
                .then()
                .log()
                .all();
    }
// получаем список заказов неавторизованного пользователя
    @Step("Send GET request to /api/orders without authorization")
    public ValidatableResponse getListOfOrdersWithoutAuthorization() {
        return given()
                .spec(getDefaultRequestSpec())
                .get("orders")
                .then()
                .log()
                .all();
    }
}
