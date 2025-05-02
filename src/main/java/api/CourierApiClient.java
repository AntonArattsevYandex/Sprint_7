package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierCredentials;
import utils.BaseApiClient;

import static constants.Endpoints.COURIER_PATH;
import static io.restassured.RestAssured.given;

public class CourierApiClient extends BaseApiClient {
    @Step("Создание нового курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then();
    }
}