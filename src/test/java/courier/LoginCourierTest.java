package courier;

import api.CourierApiClient;
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

public class LoginCourierTest {
    private CourierApiClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierApiClient();
        courier = CourierDataGenerator.generateRandomCourier();
        courierClient.createCourier(courier);
        courierId = courierClient.loginCourier(CourierCredentials.from(courier))
                .extract().path("id");
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    public void testSuccessfulLogin() {
        ValidatableResponse response = courierClient.loginCourier(
                CourierCredentials.from(courier)
        );
        assertEquals(SC_OK, response.extract().statusCode());
        assertNotNull(response.extract().path("id"));
    }

    @Test
    @DisplayName("Ошибка при авторизации с неверным логином")
    public void testLoginWithInvalidLogin() {
        ValidatableResponse response = courierClient.loginCourier(
                new CourierCredentials("invalid_login", courier.getPassword())
        );
        assertEquals(SC_NOT_FOUND, response.extract().statusCode());
        assertEquals(ACCOUNT_NOT_FOUND, response.extract().path("message"));
    }

    @Test
    @DisplayName("Ошибка при авторизации с неверным паролем")
    public void testLoginWithInvalidPassword() {
        ValidatableResponse response = courierClient.loginCourier(
                new CourierCredentials(courier.getLogin(), "invalid_pass")
        );
        assertEquals(SC_NOT_FOUND, response.extract().statusCode());
        assertEquals(ACCOUNT_NOT_FOUND, response.extract().path("message"));
    }

    @Test
    @DisplayName("Ошибка при авторизации без логина")
    public void testLoginWithoutLogin() {
        ValidatableResponse response = courierClient.loginCourier(
                new CourierCredentials("", courier.getPassword())
        );
        assertEquals(SC_BAD_REQUEST, response.extract().statusCode());
        assertEquals(INSUFFICIENT_LOGIN_DATA, response.extract().path("message"));
    }

    @Test
    @DisplayName("Ошибка при авторизации без пароля")
    public void testLoginWithoutPassword() {
        ValidatableResponse response = courierClient.loginCourier(
                new CourierCredentials(courier.getLogin(), "")
        );
        assertEquals(SC_BAD_REQUEST, response.extract().statusCode());
        assertEquals(INSUFFICIENT_LOGIN_DATA, response.extract().path("message"));
    }

    @After
    public void tearDown() {
        if (courierId > 0) {
            courierClient.deleteCourier(courierId);
        }
    }
}