# Playwright API Automation – Utilities Package
![Java](https://img.shields.io/badge/Java-17+-blue?logo=oracle&logoColor=white)
![Playwright](https://img.shields.io/badge/Playwright-Java-2EAD33?logo=playwright&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-Framework-orange)
![Allure Report](https://img.shields.io/badge/Allure-Report-ED6C30?logo=allure&logoColor=white)

This module provides a reusable, lightweight API automation framework built on top of Playwright Java, TestNG, and
Allure.
It centralizes all API request logic in PlaywrightApiClient, allowing you to perform clean, consistent, and logged HTTP
requests in your tests.

---

## 1. Overview

The utilities in com.utilities include:

PlaywrightApiClient

A wrapper around APIRequestContext providing:
• GET, POST, PUT, PATCH, DELETE methods
• JSON requests
• URL-encoded form requests
• Automatic header injection
• Truncated logging for large responses
• Allure-friendly request/response logging
• Automatic status validation
• Optional custom headers overloads

BaseApiTest

A class that initializes:
• Playwright engine
• APIRequestContext with base URL
• PlaywrightApiClient instance

It ensures a consistent environment for all API tests.

Headers

Common header constants.

Endpoints

Centralized endpoint path definitions.

---

## 2. Class Structure

```
com.utilities
 ├── PlaywrightApiClient.java
 ├── BaseApiTest.java
 ├── Headers.java
 ├── Endpoints.java
 └── JsonUtils.java (optional, for pretty print)
```

---

## 3. PlaywrightApiClient – Features

**GET**

```java
String res = api.get("api/productsList", 200);
```

**POST JSON**

```java
String res = api.postJson("/api/login", "{ \"user\": \"rony\" }", 200);
```

**POST Form**

```java
String res = api.postForm("/api/submit", Map.of("key", "value"), 200);
```

**PUT / PATCH**

```java
api.putJson("api/update",json, 200);
api.

patchJson("api/update",json, 200);
```

DELETE

```java
api.delete("api/delete/1",200);
```

### All methods:

	• Build the full URL
	• Inject headers if provided
	• Set JSON or FORM data correctly
	• Log request + response through AllureLogger
	• Assert the exact HTTP status
	• Return the raw response body as String

---

## 4. BaseApiTest – Test Foundation

BaseApiTest initializes Playwright:

```java
playwright =Playwright.

create();

request =playwright.

request().

newContext(
    new APIRequest.NewContextOptions()
        .

setBaseURL(BaseUri.urlAutomationExercise())
        );

api =new

PlaywrightApiClient(request, BaseUri.urlAutomationExercise());
```

### Automatically printed:

	• Base URL
	• API client initialized
	• All resources closed after suite execution

 
    All API tests should extend this class.

---

## 5. Endpoints

Centralized endpoints:

```java
public class Endpoints {
    public static final String PRODUCT_LIST = "api/productsList";
    public static final String VERIFY_LOGIN = "api/verifyLogin";
}
```

---

## 6. Headers

Reusable header constants:

```java
public class Headers {
    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
}
```

---

## 7. Example Test

```java

@Epic("BACKEND")
@Feature("Home Page")
public class apitests extends BaseApiTest {

    protected String token;

    @Severity(SeverityLevel.NORMAL)
    @Owner("Rony Levi")
    @Test(description = "GET Product List")
    public void testGetProductList() {

        String response = api.get(Endpoints.PRODUCT_LIST, 200);
        log.info(response);

        String response2 = api.get(
                Endpoints.VERIFY_LOGIN,
                200,
                Map.of("Authorization", token)
        );

        JsonUtils.print(response);
    }
}
```

---

## 8. How to Add New API Tests

	1. Create a new test class extending BaseApiTest
	2. Use api.get(), api.postJson(), etc.
	3. Use Endpoints to reference paths
	4. Add Allure annotations as needed
	5. Use JsonUtils.print() to preview responses

---