import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CourierLoginModel;
import model.CourierModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static data.TestData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static steps.ScooterApiSteps.*;

public class CourierLoginTest extends BaseApiTest{
    private Integer courierId;
    private CourierModel courier;
    private CourierLoginModel validLoginData;

    @Before
    public void setUpCourier() {
        courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        validLoginData = new CourierLoginModel(LOGIN, PASSWORD);
        createCourier(courier);
        courierId = loginAndGetCourierId(validLoginData);
    }

    @Test
    @DisplayName("Courier authorization success")
    @Description("Testing that courier has successfully logged in")
    public void courierAuthorizationSuccess() {
        Response loginResponse = courierInSystemLogin(validLoginData);
        loginResponse.then()
                .statusCode(HTTP_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Shouldn't authorize without login")
    @Description("Testing that courier can't log in without login")
    public void shouldNotAuthorizeWithoutLogin() {
        CourierLoginModel loginDataTest = new CourierLoginModel(null, PASSWORD);

        courierInSystemLogin(loginDataTest)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Shouldn't authorize without password")
    @Description("Testing that courier can't log in without password")
    public void shouldNotAuthorizeWithoutPassword() {
        CourierLoginModel loginDataTest = new CourierLoginModel(LOGIN, null);

        courierInSystemLogin(loginDataTest)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Shouldn't authorize with wrong login")
    @Description("Testing that courier can't log in using incorrect login")
    public  void shouldNotAuthorizeWithWrongLogin() {
        CourierLoginModel loginDataTest = new CourierLoginModel("wrongLogin", PASSWORD);

        courierInSystemLogin(loginDataTest)
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Shouldn't authorize with wrong password")
    @Description("Testing that courier can't log in using incorrect password")
    public  void shouldNotAuthorizeWithWrongPassword() {
        CourierLoginModel loginDataTest = new CourierLoginModel(LOGIN, "wrongPassword");

        courierInSystemLogin(loginDataTest)
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void cleanUp () {
        if (courierId != null) {
            deleteCourier(courierId);
        }
    }
}
