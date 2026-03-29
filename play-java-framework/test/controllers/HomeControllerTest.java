package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class HomeControllerTest extends WithApplication {

    @Test
    public void testIndex() {
        // Create a fake GET request
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/api/welcome");

        // Invoke the controller action
        Result result = route(Helpers.fakeApplication(), request);

        // Assertions
        System.out.println(contentAsString(result));
        assertEquals(OK, result.status());
        assertEquals("application/json", result.contentType().get());

        JsonNode json = Json.parse(contentAsString(result));
        assertEquals("Welcome to the Play API", json.get("message").asText());
    }

    @Test
    public void testCreateUser() {
        // Prepare JSON body
        ObjectNode jsonBody = Json.newObject();
        jsonBody.put("name", "John Doe");

        // Create a fake POST request with the body
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/api/users")
                .bodyJson(jsonBody);

        Result result = route(Helpers.fakeApplication(), request);

        assertEquals(CREATED, result.status());
        JsonNode responseJson = Json.parse(contentAsString(result));
        assertEquals("User John Doe created", responseJson.get("status").asText());
    }

}
