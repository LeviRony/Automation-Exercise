
import com.utilities.BaseApiTest;
import com.utilities.Endpoints;
import com.utilities.JsonUtils;
import io.qameta.allure.*;
import org.testng.annotations.Test;


@Epic("BACKEND")
@Feature("Home Page")
public class ApiTests extends BaseApiTest {

    protected String token;

    @Severity(SeverityLevel.NORMAL)
    @Owner("Rony Levi")
    @Issue("JIRA-123")
    @Test(groups = "Sanity", description = "GET Product List")
    public void testGetProductList() {
        String response = api.get(Endpoints.PRODUCT_LIST, 200);
        log.info(response);
        //String response2 = api.get(Endpoints.VERIFY_LOGIN, 200, Map.of("Authorization", token));
        System.out.println("Product List Response: ");
        JsonUtils.print(response);
    }
}