package com.pageObjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Description;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class SignupLoginPageObjects {

    private final Page page;

    public SignupLoginPageObjects(Page page) {
        this.page = page;
    }

    // ---------- Locators ----------

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

    // ---------- Actions / Assertions ----------
    @Step
    public SignupLoginPageObjects assertLoginSectionVisible() {
        assertThat(signupLoginSection()).isVisible();
        return this;
    }

    @Step("Entering user email and verify the string is shown at the email field & check that field isn't empty after fill")
    public SignupLoginPageObjects fillEmail(String email) {
        emailAddressInput().fill(email);
        assertThat(emailAddressInput()).hasValue(email);
        return this;
    }

    @Step("Entering user password and verify the string is shown at the password field & check that field isn't empty after fill")
    public SignupLoginPageObjects fillPassword(String password) {
        passwordInput().fill(password);
        assertThat(passwordInput()).not().hasValue(""); //check that field is not empty
        return this;
    }

    @Step("Clicking the 'Login' button")
    public SignupLoginPageObjects clickLogin() {
        loginButton().click();
        return this;
    }

    @Step("Login to website with email address and password, during the ")
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