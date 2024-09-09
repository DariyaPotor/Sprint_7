package CourierTests;
import POJO.CourierData;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateNewCourierTests {
    private final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private final String COURIER_PATH = "/api/v1/courier";
    private int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    public void testCreateCourier() {
        CourierData courier = new CourierData("dvvdv", "12rr", "sasked");

        given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    public void testCreateDuplicateCourier() {
        CourierData courier = new CourierData("dvvdv", "12rr", "saske");

        // Создаем первый раз
        given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER_PATH);

        // Пытаемся создать второй раз с тем же логином
        given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    public void testCreateCourierWithoutLogin() {
        CourierData courier = new CourierData(null, "12rr", "saske");

        given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void testCreateCourierWithoutPassword() {
        CourierData courier = new CourierData("dvvdv", null, "saske");

        given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown() {
        // Логин для получения id курьера
        Response response = given()
                .contentType(ContentType.JSON)
                .body(new CourierData("dvvdv", "12rr", null))
                .when()
                .post(COURIER_PATH + "/login");

        if (response.statusCode() == 200) {
            courierId = response.jsonPath().getInt("id");

            // Удаление курьера
            given()
                    .contentType(ContentType.JSON)
                    .body("{\"id\": \"" + courierId + "\"}")
                    .when()
                    .delete(COURIER_PATH + "/" + courierId)
                    .then()
                    .statusCode(200);
        }
    }
}
