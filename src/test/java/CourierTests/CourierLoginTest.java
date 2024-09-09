package CourierTests;

import POJO.CourierData;
import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String LOGIN_PATH = "/api/v1/courier/login";
    private int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.filters(new AllureRestAssured());
        createCourier();
    }

    @After
    public void tearDown() {
        deleteCourier();
    }

    private void createCourier() {
        CourierData courierData = new CourierData("dvvdv", "12rr", "sasked");
        given()
                .contentType("application/json")
                .body(courierData)
                .when()
                .post(COURIER_PATH);
    }

    private void deleteCourier() {
        if (courierId != 0) {
            given()
                    .contentType("application/json")
                    .body("{\"id\": \"" + courierId + "\"}")
                    .when()
                    .delete(COURIER_PATH + "/" + courierId)
                    .then()
                    .statusCode(200)
                    .body("ok", equalTo(true));
        }
    }

    @Test
    @Description("Курьер может авторизоваться")
    public void testCourierCanLogin() {
        CourierData courierData = new CourierData("dvvdv", "12rr", null);
        Response response = given()
                .contentType("application/json")
                .body(courierData)
                .when()
                .post(LOGIN_PATH);

        response.then()
                .statusCode(200)
                .body("id", notNullValue());

        courierId = response.then().extract().path("id");
    }

    @Test
    @Description("Авторизация без логина возвращает ошибку")
    public void testLoginWithoutLogin() {
        CourierData notLoginData = new CourierData(null, "12rr", null);
        given()
                .contentType("application/json")
                .body(notLoginData)
                .when()
                .post(LOGIN_PATH)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Авторизация без пароля возвращает ошибку")
    public void testLoginWithoutPassword() {
        CourierData notPasswordData = new CourierData("dvvdv", null, null);
        given()
                .contentType("application/json")
                .body(notPasswordData)
                .when()
                .post(LOGIN_PATH)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Авторизация с неверными данными возвращает ошибку")
    public void testLoginWithIncorrectCredentials() {
        CourierData incorrectData = new CourierData("ccmfjfj", "jigjtjti", null);
        given()
                .contentType("application/json")
                .body(incorrectData)
                .when()
                .post(LOGIN_PATH)
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}