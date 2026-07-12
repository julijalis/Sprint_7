import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierLoginModel;
import org.junit.Test;

import static data.TestData.LOGIN;
import static data.TestData.PASSWORD;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static org.hamcrest.Matchers.equalTo;
import static steps.ScooterApiSteps.courierInSystemLogin;

public class CourierLoginNotExistingCourierTest extends BaseApiTest {

    @Test
    @DisplayName("Shouldn't authorize with not existed courier")
    @Description("Testing that authorization is not happening if courier hasn't been created")
    public void shouldNotAuthorizeWithNotExistedCourier() {
        CourierLoginModel courier = new CourierLoginModel(LOGIN, PASSWORD);
        courierInSystemLogin(courier)
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
