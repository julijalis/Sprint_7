import io.restassured.response.Response;
import model.CourierLoginModel;
import model.CourierModel;
import org.junit.After;
import org.junit.Test;

import static data.TestData.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static steps.ScooterApiSteps.*;

public class CourierLoginTest extends BaseApiTest{
    private Integer courierId;

    @Test
    public void courierAuthorizationSuccess() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        CourierLoginModel loginData = new CourierLoginModel(LOGIN, PASSWORD);

        createCourier(courier);
        Response loginResponse = courierInSystemLogin(loginData);

        loginResponse.then()
                .statusCode(200)
                .body("id", notNullValue());

        courierId = loginResponse.path("id");
    }

    @Test
    public void shouldNotAuthorizeWithoutLogin() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        CourierLoginModel loginDataCorrect = new CourierLoginModel(LOGIN, PASSWORD);
        CourierLoginModel loginDataTest = new CourierLoginModel(null, PASSWORD);

        createCourier(courier);
        Response loginResponse = courierInSystemLogin(loginDataCorrect);

        courierInSystemLogin(loginDataTest)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        courierId = loginResponse.path("id");
    }

    @Test
    public void shouldNotAuthorizeWithoutPassword() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        CourierLoginModel loginDataCorrect = new CourierLoginModel(LOGIN, PASSWORD);
        CourierLoginModel loginDataTest = new CourierLoginModel(LOGIN, null);

        createCourier(courier);
        Response loginResponse = courierInSystemLogin(loginDataCorrect);

        courierInSystemLogin(loginDataTest)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        courierId = loginResponse.path("id");
    }

    @Test
    public  void shouldNotAuthorizeWithWrongLogin() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        CourierLoginModel loginDataCorrect = new CourierLoginModel(LOGIN, PASSWORD);
        CourierLoginModel loginDataTest = new CourierLoginModel("wrongLogin", PASSWORD);

        createCourier(courier);
        Response loginResponse = courierInSystemLogin(loginDataCorrect);

        courierInSystemLogin(loginDataTest)
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        courierId = loginResponse.path("id");
    }

    @Test
    public  void shouldNotAuthorizeWithWrongPassword() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        CourierLoginModel loginDataCorrect = new CourierLoginModel(LOGIN, PASSWORD);
        CourierLoginModel loginDataTest = new CourierLoginModel(LOGIN, "wrongPassword");

        createCourier(courier);
        Response loginResponse = courierInSystemLogin(loginDataCorrect);

        courierInSystemLogin(loginDataTest)
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        courierId = loginResponse.path("id");
    }

    @Test
    public void shouldNotAuthorizeWithNotExistedCourier() {
        CourierLoginModel courier = new CourierLoginModel(LOGIN, PASSWORD);
        courierInSystemLogin(courier)
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void cleanUp () {
        if (courierId != null) {
            deleteCourier(courierId);
        }
    }
}
