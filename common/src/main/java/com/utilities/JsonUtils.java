package com.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.qameta.allure.Step;

public class JsonUtils {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * Pretty print any JSON string.
     *
     * @param json Raw JSON string
     * @return Pretty formatted JSON
     */
    public static String pretty(String json) {
        try {
            Object parsed = GSON.fromJson(json, Object.class);
            return GSON.toJson(parsed);
        } catch (Exception e) {
            // return original if not valid JSON
            return json;
        }
    }


    @Step("Print pretty JSON directly to console.")
    public static void print(String json) {
        System.out.println(pretty(json));
    }

   @Step
    public static String get(String json, String key) {
        try {
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
            return obj.get(key).getAsString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract key '" + key + "' from JSON:\n" + json, e);
        }
        }
}