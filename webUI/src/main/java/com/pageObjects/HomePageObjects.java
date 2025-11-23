package com.pageObjects;

import com.allureReport.AllureLogger;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.accessibility.AccessibilityUtils;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public record HomePageObjects (Page page){

    private static final Logger log = LoggerFactory.getLogger(HomePageObjects.class);

    // =========================================================
    ///                   Locators
    // =========================================================

    private Locator marketingParagraph() {
        return page.getByRole(AriaRole.PARAGRAPH)
                .filter(new Locator.FilterOptions()
                        .setHasText("All QA engineers can use this"));
    }

    private Locator testCasesButton() {
        return page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Test Cases")
        );
    }

    private Locator searchBox() {
        return page.getByRole(
                AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search an example..."));
    }

    private Locator menuSignupLogin() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" Signup / Login"));
    }

    private Locator menuLogout() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" Logout"));
    }

    // =========================================================
    ///              Actions / Assertions
    // =========================================================

    @Step("Verify that the 'Logout' menu item is visible on the page")
    public HomePageObjects logoutVisible() {
        assertThat(menuLogout()).isVisible();
        return this;
    }

    @Step("Click on the 'Logout' menu item")
    public HomePageObjects logoutClick() {
        menuLogout().click();
        return this;
    }

    @Step("Run accessibility scan on the Home page (Axe-core)")
    public HomePageObjects runAccessibilityScan() {
        AccessibilityUtils.runAxeScan(page);
        return this;
    }

    @Step("Verify that the marketing paragraph is visible and log its content")
    public HomePageObjects assertMarketingParagraphVisible() {
        Locator paragraph = marketingParagraph();
        assertThat(paragraph).isVisible();
        AllureLogger.step(paragraph.textContent());
        log.info(paragraph.textContent());
        return this;
    }

    @Step("Navigate to the 'Test Cases' page")
    public HomePageObjects goToTestCases() {
        testCasesButton().click();
        return this;
    }

    @Step("Open the Signup / Login page from the main menu")
    public HomePageObjects signupOrLogin() {
        menuSignupLogin().click();
        return this;
    }
}