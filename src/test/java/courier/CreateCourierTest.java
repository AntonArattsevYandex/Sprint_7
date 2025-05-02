package courier;

import api.CourierApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.CourierDataGenerator;

import static constants.ErrorMessages.*;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CreateCourierTest {
    private CourierApiClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierApiClient();
    }

    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Проверка создания курьера с валидными данными")
    public void testSuccessfulCourierCreation() {
        Courier courier = CourierDataGenerator.generateRandomCourier();
        ValidatableResponse response = courierClient.createCourier(courier);
        assertEquals(SC_CREATED, response.extract().statusCode());
        assertTrue(response.extract().path("ok"));

        courierId = courierClient.loginCourier(CourierCredentials.from(courier))
                .extract().path("id");
    }

    @Test
    @DisplayName("Ошибка при создании дубликата курьера")
    public void testDuplicateCourierCreation() {
        Courier courier = CourierDataGenerator.generateRandomCourier();
        courierClient.createCourier(courier);
        ValidatableResponse response = courierClient.createCourier(courier);
        assertEquals(SC_CONFLICT, response.extract().statusCode());
        assertEquals(DUPLICATE_LOGIN, response.extract().path("message"));
    }

    @Test
    @DisplayName("Ошибка при создании курьера без логина")
    public void testCreateCourierWithoutLogin() {
        Courier courier = new Courier(null, "validPass", "name");
        ValidatableResponse response = courierClient.createCourier(courier);
        assertEquals(SC_BAD_REQUEST, response.extract().statusCode());
        assertEquals(INSUFFICIENT_CREATION_DATA, response.extract().path("message"));
    }

    @Test
    @DisplayName("Ошибка при создании курьера без пароля")
    public void testCreateCourierWithoutPassword() {
        Courier courier = new Courier("validLogin", null, "name");
        ValidatableResponse response = courierClient.createCourier(courier);
        assertEquals(SC_BAD_REQUEST, response.extract().statusCode());
        assertEquals(INSUFFICIENT_CREATION_DATA, response.extract().path("message"));
    }

    @After
    public void tearDown() {
        if (courierId > 0) {
            courierClient.deleteCourier(courierId);
        }
    }
}