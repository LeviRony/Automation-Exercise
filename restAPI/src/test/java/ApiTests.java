import com.dataProviders.AccessToken;
import com.utilities.BaseApiTest;
import com.utilities.Endpoints;
import com.utilities.JsonUtils;
import io.qameta.allure.*;
import org.testng.annotations.Test;


@Epic("BACKEND")
@Feature("Home Page")
public class ApiTests extends BaseApiTest {


    @Severity(SeverityLevel.NORMAL)
    @Owner("Rony Levi")
    @Issue("JIRA-123")
    @Test(groups = "Sanity", description = "GET Product List")
    public void testGetProductList() {
        String response = api().get(Endpoints.PRODUCT_LIST, 200);
        log.info(response);
        System.out.println("Product List Response: ");
        JsonUtils.print(response);
    }

    @Severity(SeverityLevel.NORMAL)
    @Owner("Rony Levi")
    @Issue("JIRA-123")
    @Test(groups = "Sanity", description = "POST User login")
    public void loginViaAPI() {
        String response = api().postForm(Endpoints.VERIFY_LOGIN, AccessToken.loginBody(), 200);
        log.info(response);
    }
}