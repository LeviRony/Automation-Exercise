package com.utilities;

import com.allureReport.AllureLogger;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.Map;

import static com.utilities.Ansi.*;
import static com.utilities.Headers.*;

public class PlaywrightApiClient {

    private static final Logger log = LoggerFactory.getLogger(PlaywrightApiClient.class);

    private final APIRequestContext request;
    private final String baseUrl;

    public PlaywrightApiClient(APIRequestContext request, String baseUrl) {
        this.request = request;
        this.baseUrl = baseUrl != null ? baseUrl : "";
    }

    // =========================================================
    //                      Helpers
    // =========================================================

    private String resolve(String urlOrPath) {
        if (urlOrPath.startsWith(HTTP) || urlOrPath.startsWith(HTTPS)) {
            return urlOrPath;
        }
        if (baseUrl.isEmpty()) {
            return urlOrPath;
        }
        if (baseUrl.endsWith("/") && urlOrPath.startsWith("/")) {
            return baseUrl + urlOrPath.substring(1);
        }
        if (!baseUrl.endsWith("/") && !urlOrPath.startsWith("/")) {
            return baseUrl + "/" + urlOrPath;
        }
        return baseUrl + urlOrPath;
    }

    private RequestOptions withHeaders(Map<String, String> headers) {
        RequestOptions opts = RequestOptions.create();
        if (headers != null) {
            headers.forEach(opts::setHeader);
        }
        return opts;
    }

    private boolean headersContainsContentTypeJson(Map<String, String> headers) {
        if (headers == null) return false;
        return headers.entrySet().stream().anyMatch(e ->
                e.getKey().equalsIgnoreCase(CONTENT_TYPE)
                        && e.getValue().toLowerCase().contains(APPLICATION_JSON));
    }

    private String truncate(String text, int max) {
        if (text == null) return null;
        return text.length() <= max ? text : text.substring(0, max) + "...";
    }

    // =========================================================
    //                          GET
    // =========================================================

    public String get(String urlOrPath, int expectedStatus) {
        return get(urlOrPath, expectedStatus, null);
    }

    @Step("GET {urlOrPath} expects {expectedStatus}")
    public String get(String urlOrPath, int expectedStatus, Map<String, String> headers) {
        String url = resolve(urlOrPath);
        RequestOptions opts = headers != null ? withHeaders(headers) : RequestOptions.create();

        long t0 = System.nanoTime();
        APIResponse res = request.get(url, opts);
        long ms = (System.nanoTime() - t0) / 1_000_000;

        String body = res.text();

        log.info(">>>  [GET] {} -> {} ({} ms)", url, res.status(), ms);
        //log.debug(ORANGE + "Response body (first 2KB): {}", truncate(body, 2048) + RESET);

        Assert.assertEquals(res.status(), expectedStatus,
                "Unexpected HTTP status for GET " + url);

        return body;
    }

    // =========================================================
    //                     POST JSON
    // =========================================================

    public String postJson(String urlOrPath, String jsonBody, int expectedStatus) {
        return postJson(urlOrPath, jsonBody, expectedStatus, null);
    }

    @Step("POST JSON to {urlOrPath} expects {expectedStatus}")
    public String postJson(String urlOrPath, String jsonBody,
                           int expectedStatus, Map<String, String> headers) {

        String url = resolve(urlOrPath);

        RequestOptions opts = headers != null ? withHeaders(headers) : RequestOptions.create();
        opts.setData(jsonBody);

        if (!headersContainsContentTypeJson(headers)) {
            opts.setHeader(CONTENT_TYPE, APPLICATION_JSON);
        }

        long t0 = System.nanoTime();
        APIResponse res = request.post(url, opts);
        long ms = (System.nanoTime() - t0) / 1_000_000;

        String body = res.text();

        log.info("[POST J] {} -> {} ({} ms)", url, res.status(), ms);

        Assert.assertEquals(res.status(), expectedStatus,
                "Unexpected HTTP status for POST JSON " + url);

        return body;
    }

    // =========================================================
    //            POST x-www-form-urlencoded (Form)
    // =========================================================

    public String postForm(String urlOrPath, Map<String, String> formFields,
                           int expectedStatus) {
        return postForm(urlOrPath, formFields, expectedStatus, null);
    }

    @Step("POST form to {urlOrPath} expects {expectedStatus}")
    public String postForm(String urlOrPath, Map<String, String> formFields,
                           int expectedStatus, Map<String, String> headers) {

        String url = resolve(urlOrPath);

        RequestOptions opts = headers != null ? withHeaders(headers) : RequestOptions.create();
        opts.setForm((FormData) formFields);

        long t0 = System.nanoTime();
        APIResponse res = request.post(url, opts);
        long ms = (System.nanoTime() - t0) / 1_000_000;

        String body = res.text();

        log.info("[POST F] {} -> {} ({} ms)", url, res.status(), ms);

        Assert.assertEquals(res.status(), expectedStatus,
                "Unexpected HTTP status for POST form " + url);

        return body;
    }

    // =========================================================
    //                        PUT JSON
    // =========================================================

    public String putJson(String urlOrPath, String jsonBody, int expectedStatus) {
        return putJson(urlOrPath, jsonBody, expectedStatus, null);
    }

    @Step("PUT JSON to {urlOrPath} expects {expectedStatus}")
    public String putJson(String urlOrPath, String jsonBody,
                          int expectedStatus, Map<String, String> headers) {

        String url = resolve(urlOrPath);

        RequestOptions opts = headers != null ? withHeaders(headers) : RequestOptions.create();
        opts.setData(jsonBody);

        if (!headersContainsContentTypeJson(headers)) {
            opts.setHeader(CONTENT_TYPE, APPLICATION_JSON);
        }

        long t0 = System.nanoTime();
        APIResponse res = request.put(url, opts);
        long ms = (System.nanoTime() - t0) / 1_000_000;

        String body = res.text();

        log.info("[PUT  J] {} -> {} ({} ms)", url, res.status(), ms);
        Assert.assertEquals(res.status(), expectedStatus,
                "Unexpected HTTP status for PUT JSON " + url);

        return body;
    }

    // =========================================================
    //                       PATCH JSON
    // =========================================================

    public String patchJson(String urlOrPath, String jsonBody, int expectedStatus) {
        return patchJson(urlOrPath, jsonBody, expectedStatus, null);
    }

    @Step("PATCH JSON to {urlOrPath} expects {expectedStatus}")
    public String patchJson(String urlOrPath, String jsonBody,
                            int expectedStatus, Map<String, String> headers) {

        String url = resolve(urlOrPath);

        RequestOptions opts = headers != null ? withHeaders(headers) : RequestOptions.create();
        opts.setData(jsonBody);

        if (!headersContainsContentTypeJson(headers)) {
            opts.setHeader(CONTENT_TYPE, APPLICATION_JSON);
        }

        long t0 = System.nanoTime();
        APIResponse res = request.patch(url, opts);
        long ms = (System.nanoTime() - t0) / 1_000_000;

        String body = res.text();

        log.info("[PATCH] {} -> {} ({} ms)", url, res.status(), ms);

        Assert.assertEquals(res.status(), expectedStatus,
                "Unexpected HTTP status for PATCH JSON " + url);

        return body;
    }

    // =========================================================
    //                         DELETE
    // =========================================================

    public String delete(String urlOrPath, int expectedStatus) {
        return delete(urlOrPath, expectedStatus, null);
    }

    @Step("DELETE {urlOrPath} expects {expectedStatus}")
    public String delete(String urlOrPath, int expectedStatus,
                         Map<String, String> headers) {
        String url = resolve(urlOrPath);
        RequestOptions opts = headers != null ? withHeaders(headers) : RequestOptions.create();

        long t0 = System.nanoTime();
        APIResponse res = request.delete(url, opts);
        long ms = (System.nanoTime() - t0) / 1_000_000;

        String body = res.text();

        log.info("[DEL  ] {} -> {} ({} ms)", url, res.status(), ms);
        Assert.assertEquals(res.status(), expectedStatus,
                "Unexpected HTTP status for DELETE " + url);

        return body;
    }
}