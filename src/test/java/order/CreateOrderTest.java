package order;

import api.OrderApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.OrderDataGenerator;

import java.util.Arrays;
import java.util.Collection;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private OrderApiClient orderClient;
    private final String[] colors;

    public CreateOrderTest(String colors) {
        this.colors = colors.isEmpty() ? new String[0] : colors.split(",");
    }

    @Parameterized.Parameters(name = "Цвета: {0}")
    public static Collection<String> colorCombinations() {
        return Arrays.asList("BLACK", "GREY", "BLACK,GREY", "");
    }

    @Before
    public void setUp() {
        orderClient = new OrderApiClient();
    }

    @Test
    @DisplayName("Создание заказа с разными цветами")
    public void testOrderCreationWithColors() {
        Order order = OrderDataGenerator.generateRandomOrder(colors);
        ValidatableResponse response = orderClient.createOrder(order);
        assertEquals(SC_CREATED, response.extract().statusCode());
        assertNotNull(response.extract().path("track"));
    }
}