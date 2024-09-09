package OrderTests;

import POJO.CreateOrderData;
import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTests {

    @Parameterized.Parameters(name = "Тестирование создания заказа с цветами: {0}")
    public static Object[][] colorOptions() {
        return new Object[][] {
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        };
    }

    private final String[] colors;

    public CreateOrderTests(String[] colors) {
        this.colors = colors;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    @Description("Проверка создания заказа с параметрами цвета")
    public void testCreateOrderWithColors() {
        CreateOrderData orderData = new CreateOrderData(
                "Naruto", "Uchiha", "Konoha, 142 apt.", "4",
                "+7 800 355 35 35", 5, "2020-06-06",
                "Saske, come back to Konoha", colors
        );

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(orderData)
                .when()
                .post("/api/v1/orders");

        response.then().statusCode(201)
                .body("track", notNullValue());
    }
}