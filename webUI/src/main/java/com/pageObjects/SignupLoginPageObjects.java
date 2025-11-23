package com.pageObjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public record SignupLoginPageObjects (Page page){

    // =========================================================
    ///                   Locators
    // =========================================================
    private Locator signupLoginSection() {
        return page.getByRole(
                AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Login to your account")
        );
    }

    private Locator emailAddressInput() {
        return page.locator("form")
                .filter(new Locator.FilterOptions().setHasText("Login"))
                .getByPlaceholder("Email Address");
    }

    private Locator passwordInput() {
        return page.getByRole(
                AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Password")
        );
    }

    private Locator loginButton() {
        return page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Login")
        );
    }

    // =========================================================
    ///              Actions / Assertions
    // =========================================================

    @Step("Verify that the login section title is visible on the page")
    public void assertLoginSectionVisible() {
        assertThat(signupLoginSection()).isVisible();
    }

    @Step("Fill the email field with '{email}' and verify the value is correctly displayed")
    public void fillEmail(String email) {
        emailAddressInput().fill(email);
        assertThat(emailAddressInput()).hasValue(email);
    }

    @Step("Fill the password field and verify it is not left empty")
    public void fillPassword(String password) {
        passwordInput().fill(password);
        assertThat(passwordInput()).not().hasValue(""); // check that field is not empty
    }

    @Step("Click the Login button to submit the form")
    public void clickLogin() {
        loginButton().click();
    }

    @Step("Perform full login flow using email '{email}' and password")
    public SignupLoginPageObjects login(HomePageObjects home, String email, String password) {
        home.signupOrLogin();
        assertLoginSectionVisible();
        fillEmail(email);
        fillPassword(password);
        clickLogin();
        home.logoutVisible();
        return this;
    }
}