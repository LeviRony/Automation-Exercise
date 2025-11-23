package com.utilities;

import com.CustomLogger;
import com.configurations.BaseUri;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

import static com.utilities.Ansi.*;
import static com.utilities.Endpoints.*;
import static com.utilities.Endpoints.VERIFY_LOGIN;
import static com.utilities.Headers.*;

public class BaseApiTest {

    protected static final CustomLogger log =
            CustomLogger.getLogger(CustomLogger.class);

    // One token per parallel thread
    protected static final ThreadLocal<String> token = new ThreadLocal<>();

    protected Playwright playwright;
    protected APIRequestContext request;
    protected PlaywrightApiClient api;

    protected String getToken() {
        return token.get();
    }

    protected void setToken(String value) {
        token.set(value);
    }

    @BeforeClass(alwaysRun = true)
    public void setup() {
        System.out.println(BLUE + "<<<  API TEST SETUP  >>>" + RESET);

        playwright = Playwright.create();

        request = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(BaseUri.urlAutomationExercise())
        );

        api = new PlaywrightApiClient(request, BaseUri.urlAutomationExercise());

        System.out.println(BLUE + "Base URL: " + BaseUri.urlAutomationExercise() + RESET);
        System.out.println(BLUE + "API client initialized" + RESET);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        System.out.println(BLUE + "<<<  Closing API Test Environment  >>>" + RESET);

        if (playwright != null) {
            playwright.close();
            System.out.println(BLUE + "Playwright closed" + RESET);
        }
    }

    /**
     * Runs before each test method.
     * Ensures a valid token for THIS thread.
     */
    @BeforeMethod(alwaysRun = true)
    public void ensureValidToken() {

        String current = getToken();

        // If no token -> login
        if (current == null || current.isBlank()) {
            log.info("No token for thread " + Thread.currentThread().getId() + " -> login");
            loginAndStoreToken();
            return;
        }

        try {
            log.info("Checking token validity for thread " + Thread.currentThread().getId());

            String verifyRes = api.get(
                    VERIFY_LOGIN,
                    200,
                    Map.of(AUTHORIZATION, "Bearer " + current)
            );

            boolean valid =
                    Boolean.parseBoolean(JsonUtils.get(verifyRes, "valid"));

            if (!valid) {
                log.warn("Token invalid/expired for thread "
                        + Thread.currentThread().getId() + " -> re-login");
                loginAndStoreToken();
            } else {
                log.info("Token is valid for thread " + Thread.currentThread().getId());
                api.setBearerToken(current);
            }

        } catch (Exception e) {
            log.warn("Token verify failed for thread "
                    + Thread.currentThread().getId()
                    + " -> re-login. Reason: " + e.getMessage());
            loginAndStoreToken();
        }
    }


    @Step("Login and store access token")
    protected void loginAndStoreToken() {

        Map<String, Object> body = new HashMap<>();
        body.put("email", "test@test.com");
        body.put("password", "1234");
        body.put("rememberMe", true);

        String loginRes = api.post(LOGIN, 200, body);

        String newToken = JsonUtils.get(loginRes, "token");

        setToken(newToken);
        api.setBearerToken(newToken); // applies to THIS thread only

        log.info("New token generated for thread "
                + Thread.currentThread().getId() + ": " + newToken);

        Allure.addAttachment("Login Response", loginRes);
    }
}