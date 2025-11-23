package com.dataProviders;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import com.utilities.JsonUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.util.HashMap;
import java.util.Map;

import static com.utilities.Endpoints.*;

import static com.utilities.PlaywrightApiClient.log;

public record AccessToken(APIRequestContext request) {

    static String token;

    public APIResponse loginData() {
        Map<String, Object> body = new HashMap<>();
        body.put("email", "test@test.com");
        body.put("password", "1234");
        body.put("rememberMe", true);

        return request.post(
                LOGIN,
                RequestOptions.create().setData(body)
        );
    }


    @Step("Login and extract token")
    public String getToken() {
        APIResponse res = loginData();
        String responseBody = res.text();
        log.info("Login Response: " + responseBody);
        token = JsonUtils.get(responseBody, "token");
        log.info("Extracted Token: " + token);
        Allure.addAttachment("Login Response", responseBody);
        return token;
    }
}