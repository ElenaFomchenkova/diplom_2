package client;

import dto.ChangeUserRequest;
import dto.LoginUserRequest;
import dto.UserRequest;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends RestClient {

    //создаем пользователя
    @Step("Send POST request to /api/auth/register")
    public ValidatableResponse create(UserRequest userRequest) {
        return given()
                .spec(getDefaultRequestSpec())
                .body(userRequest)
                .post("auth/register")
                .then();
    }

    //логинимся под пользователем
    @Step("Send POST request to /api/auth/login")
    public ValidatableResponse login(LoginUserRequest loginUserRequest) {
        return given()
                .spec(getDefaultRequestSpec())
                .body(loginUserRequest)
                .post("auth/login")
                .then();
    }

    //удаляем пользователя
    @Step("Send DELETE request to /api/auth/user with authorization")
    public ValidatableResponse delete(String token) {
        return given()
                .spec(getDefaultRequestSpec())
                .header("Authorization", token)
                .delete("auth/user")
                .then();
    }

    public ValidatableResponse changeUserWithAutorization(ChangeUserRequest changeUserRequest, String token) {
        return given()
                .spec(getDefaultRequestSpec())
                .header("Authorization", token)
                .body(changeUserRequest)
                .patch("auth/user")
                .then();

    }
    public ValidatableResponse changeUserWithoutAutorization(ChangeUserRequest changeUserRequest) {
        return given()
                .spec(getDefaultRequestSpec())
                .body(changeUserRequest)
                .patch("auth/user")
                .then();

    }
}
