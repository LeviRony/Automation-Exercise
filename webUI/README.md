# WebUI Automation Framework – Playwright + TestNG + Allure + Accessibility

![Java](https://img.shields.io/badge/Java-17+-blue?logo=oracle&logoColor=white)
![Playwright](https://img.shields.io/badge/Playwright-Java-2EAD33?logo=playwright&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-Framework-orange)
![Allure Report](https://img.shields.io/badge/Allure-Report-ED6C30?logo=allure&logoColor=white)

This module (`webUI`) is a complete Web UI automation framework designed for testing  
https://automationexercise.com and its environment variants.  
It is built using:

- Java 17
- Playwright for Java
- TestNG
- Allure Reporting
- axe-core for Accessibility Testing

---

## Project Structure

webUI/
pom.xml

src/main/java/com/configurations/
BaseUri.java

src/main/java/com/utilities/
BaseWebUITest.java

src/main/java/com/pageObjects/
HomePageObjects.java
TestCasesObjects.java

src/main/java/com/accessibility/
AccessibilityUtils.java

src/test/java/
HomePage.java


---

## BaseUri – Environments and Flags

`BaseUri` manages:

1. Environment selection
2. Accessibility flag

```java
private static final String ENV_TYPE =
        System.getProperty("tests.general.envType", "PROD");
// Options: DEV | QA | STG | PROD

public static final String RUN_ACCESSIBILITY_TEST =
        System.getProperty("tests.general.accessibility", "false");
// "true" or "false"
```

## Environment Examples

Run against STG:

```bash
-Dtests.general.envType=STG
```

Enable accessibility testing:

```bash
-Dtests.general.accessibility=true
```

---

## Page Objects

The Page Object Model (POM) is used for element locators and actions.

### Example: HomePageObjects

```java
public class HomePageObjects {
    private final Page page;

    public HomePageObjects(Page page) {
        this.page = page;
    }

    private Locator marketingParagraph() {
        return page.getByRole(AriaRole.PARAGRAPH)
                .filter(new Locator.FilterOptions().setHasText("All QA engineers can use this"));
    }

    public HomePageObjects runAccessibilityScan() {
        AccessibilityUtils.runAxeScan(page);
        return this;
    }

    public HomePageObjects assertMarketingParagraphVisible() {
        Locator p = marketingParagraph();
        assertThat(p).isVisible();
        return this;
    }

    public HomePageObjects goToTestCases() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Test Cases")).click();
        return this;
    }
}
```

---

## How to Run Tests

### From Maven

Run all webUI tests:

```bash
mvn test -pl webUI
```

Run with accessibility enabled:

```bash
mvn test -pl webUI -Dtests.general.accessibility=true
```

Run using a specific environment:

```bash
mvn test -pl webUI -Dtests.general.envType=STG
```

Run both:

```bash
mvn test -pl webUI -Dtests.general.envType=STG -Dtests.general.accessibility=true
```

---

## How to Create a New Test

1. Extend BaseWebUITest:

```java
public class LoginTest extends BaseWebUITest {
}
```

2. Create Page Object(s).
3. Write your test:

```java

@Test
public void testLogin() {
    LoginPageObjects login = new LoginPageObjects(page);

    login.fillCredentials("email", "password")
            .submit()
            .assertLoggedIn();
}
```