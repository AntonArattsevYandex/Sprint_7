package order;

import api.OrderApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class OrderListTest {
    private OrderApiClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderApiClient();
    }

    @Test
    @DisplayName("Получение списка заказов")
    public void testGetOrderList() {
        ValidatableResponse response = orderClient.getOrderList();
        assertEquals(SC_OK, response.extract().statusCode());

        List<Map<String, Object>> orders = response.extract().path("orders");
        assertFalse("Список заказов не должен быть пустым", orders.isEmpty());
    }
}