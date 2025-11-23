package com.dataProviders;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

import java.util.HashMap;
import java.util.Map;

import static com.utilities.Endpoints.*;
import static com.utilities.Headers.*;

public class AccessToken {

    protected APIRequestContext request;

    public APIResponse getProducts(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, "Bearer " + token);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        RequestOptions opts = RequestOptions.create();
        headers.forEach(opts::setHeader);
        return request.get(PRODUCT, opts);
    }
}