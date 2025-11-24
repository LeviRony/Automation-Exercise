package com.utilities;

import com.CustomLogger;
import com.configurations.BaseUri;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.*;

import static com.utilities.Ansi.*;

public class BaseApiTest {

    protected static final CustomLogger log =
            CustomLogger.getLogger(CustomLogger.class);

    private static final ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();

    private static final ThreadLocal<PlaywrightApiClient> tlApi = new ThreadLocal<>();

    protected PlaywrightApiClient api() {
        return tlApi.get();
    }

    @BeforeClass(alwaysRun = true)
    public void globalSetup() {
        System.out.println(BLUE + "<<<  SETUP API TEST  >>>" + RESET);
        System.out.println(BLUE + "Base URL: " + BaseUri.urlAutomationExercise() + RESET);
    }

    @BeforeMethod(alwaysRun = true)
    public void perThreadSetup() {
        Playwright pw = Playwright.create();
        tlPlaywright.set(pw);

        APIRequestContext ctx = pw.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(BaseUri.urlAutomationExercise())
        );

        // Create API client per thread
        tlApi.set(new PlaywrightApiClient(ctx, BaseUri.urlAutomationExercise()));
    }

    @AfterMethod(alwaysRun = true)
    public void perThreadTearDown() {
        PlaywrightApiClient client = tlApi.get();
        if (client != null) {
            client.disposeContext();
        }
        tlApi.remove();

        Playwright pw = tlPlaywright.get();
        if (pw != null) {
            pw.close();
        }
        tlPlaywright.remove();
    }

    @AfterClass(alwaysRun = true)
    public void globalTearDown() {
        System.out.println(BLUE + "<<<  Closing API Test Environment  >>>" + RESET);
    }
}