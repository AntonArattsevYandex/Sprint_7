package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Order;
import utils.BaseApiClient;

import static constants.Endpoints.ORDERS_PATH;
import static io.restassured.RestAssured.given;

public class OrderApiClient extends BaseApiClient {
    @Step("Создание нового заказа")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDERS_PATH)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_PATH)
                .then();
    }
}