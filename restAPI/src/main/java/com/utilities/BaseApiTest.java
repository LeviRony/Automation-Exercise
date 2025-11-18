package com.utilities;

import com.CustomLogger;
import com.configurations.BaseUri;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.*;

import static com.utilities.Ansi.*;

public class BaseApiTest {
    protected static final CustomLogger log = CustomLogger.getLogger(CustomLogger.class);

    protected Playwright playwright;
    protected APIRequestContext request;
    protected PlaywrightApiClient api;

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
}