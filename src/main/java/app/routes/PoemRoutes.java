package app.routes;

import app.controllers.PoemController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*; // 👈 this is needed for get(), post(), etc.

public class PoemRoutes {
    PoemController poemController = new PoemController();

    public EndpointGroup getRoutes() {
        return () -> {
            get("/", poemController::getAllPoems);
            get("/{id}", poemController::getPoemById);
            post("/", poemController::createPoem);
            put("/{id}", poemController::updatePoem);
            delete("/{id}", poemController::deletePoem);
        };
    }
}
