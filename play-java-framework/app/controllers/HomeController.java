package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    // A simple GET endpoint
    public Result index() {
        return ok(Json.newObject().put("message", "Welcome to the Play API"));
    }

    // A POST endpoint to process JSON
    public Result create(Http.Request request) {
        JsonNode json = request.body().asJson();
        if (json == null) {
            return badRequest("Expecting JSON data");
        }

        String name = json.findPath("name").asText();

        // Return a 201 Created response
        return created(Json.newObject().put("status", "User " + name + " created"));
    }

}
