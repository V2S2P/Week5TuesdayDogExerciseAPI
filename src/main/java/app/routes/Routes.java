package app.routes;

import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {
    PoemRoutes poemRoutes = new PoemRoutes();

    public EndpointGroup getRoutes(){
        return () -> {
            get("/", ctx -> ctx.result("Hello World!"));
            path("/poem", poemRoutes.getRoutes()); //endpoint name, can call it whatever you want (dog, puppy, flower, etc.)
        };
    }
}
