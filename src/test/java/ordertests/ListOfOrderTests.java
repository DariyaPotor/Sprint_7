package ordertests;

import apisteps.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class ListOfOrderTests {

    private static OrderApi orderApi;

    @BeforeClass
    public static void setup() {
        orderApi = new OrderApi();
    }

    @Test
    @DisplayName("Проверка получения списка заказов без указания курьера")
    @Description("Этот тест проверяет, что API возвращает список заказов, если не указан идентификатор курьера.")
    public void testGetOrdersWithoutCourierId() {
        Response response = orderApi.getOrders();

        response.then()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders.size()", greaterThan(0))
                .body("pageInfo.page", equalTo(0))
                .body("pageInfo.limit", lessThanOrEqualTo(30));
    }

    @Test
    @DisplayName("Запрос с несуществующим идентификатором курьера")
    @Description("Этот тест проверяет, что API возвращает ошибку 404, если указан несуществующий идентификатор курьера.")
    public void testGetOrdersWithNonExistentCourierId() {
        int nonExistentCourierId = 9999999;

        Response response = orderApi.getOrdersWithCourierId(nonExistentCourierId);

        response.then()
                .statusCode(404)
                .body("message", equalTo("Курьер с идентификатором " + nonExistentCourierId + " не найден"));
    }
}