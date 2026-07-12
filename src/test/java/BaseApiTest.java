import io.restassured.RestAssured;
import org.junit.Before;
import static data.ApiData.BASE_URL;

public class BaseApiTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }
}
