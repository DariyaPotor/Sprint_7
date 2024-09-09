package OrderTests;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ListOfOrderTests {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    private static final String PATH = "/api/v1/orders";

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Проверка получения списка заказов без указания курьера")
    @Description("Этот тест проверяет, что API возвращает список заказов, если не указан идентификатор курьера.")
    public void testGetOrdersWithoutCourierId() {
        given()
                .when()
                .get(PATH)
                .then()
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
        int nonExistentCourierId = 9999;

        given()
                .queryParam("courierId", nonExistentCourierId)
                .when()
                .get(PATH)
                .then()
                .statusCode(404)
                .body("message", equalTo("Курьер с идентификатором " + nonExistentCourierId + " не найден"));
    }
}