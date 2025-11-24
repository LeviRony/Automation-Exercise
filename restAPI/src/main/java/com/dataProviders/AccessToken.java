package com.dataProviders;

import com.CustomLogger;
import com.microsoft.playwright.APIRequestContext;

import java.util.HashMap;
import java.util.Map;

public class AccessToken {

    private static final CustomLogger log = CustomLogger.getLogger(AccessToken.class);
    private final APIRequestContext request;

    public AccessToken(APIRequestContext request) {
        this.request = request;
    }

    // Form-data body (not JSON)
    public static Map<String, String> loginBody() {
        Map<String, String> body = new HashMap<>();
        body.put("email", "rony@gmail.com");
        body.put("password", "1234");
        return body;
    }

}