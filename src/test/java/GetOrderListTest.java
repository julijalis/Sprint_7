import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static steps.ScooterApiSteps.getOrderList;

public class GetOrderListTest extends BaseApiTest {

    @Test
    @DisplayName("Should get order list")
    @Description("Testing that order list is returned after request")
    public void shouldGetOrderList() {
        getOrderList()
                .then()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue())
                .body("orders.size()", greaterThan(0));
    }
}
