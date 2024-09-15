package couriertests;

import POJO.CourierData;
import apisteps.CourierApi;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateNewCourierTests {
    private final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private int courierId;
    private CourierApi courierApi;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.filters(new AllureRestAssured());
        courierApi = new CourierApi();
    }

    @Test
    public void testCreateCourier() {
        CourierData courier = new CourierData("dvvdv", "12rr", "sasked");
        courierApi.createCourier(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    public void testCreateDuplicateCourier() {
        CourierData courier = new CourierData("dvvdv", "12rr", "saske");

        // Создаем курьера первый раз
        courierApi.createCourier(courier);

        // Пытаемся создать курьера с тем же логином
        courierApi.createCourier(courier)
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    public void testCreateCourierWithoutLogin() {
        CourierData courier = new CourierData(null, "12rr", "saske");
        courierApi.createCourier(courier)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void testCreateCourierWithoutPassword() {
        CourierData courier = new CourierData("dvvdv", null, "saske");
        courierApi.createCourier(courier)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown() {
        // Логинимся, чтобы получить ID курьера
        Response response = courierApi.loginCourier(new CourierData("dvvdv", "12rr", null));

        if (response.statusCode() == 200) {
            courierId = response.jsonPath().getInt("id");

            // Удаляем курьера
            courierApi.deleteCourier(courierId)
                    .then()
                    .statusCode(200);
        }
    }
}