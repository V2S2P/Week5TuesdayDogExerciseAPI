package app.routes;

import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {
    DogRoutes dogRoutes = new DogRoutes();

    public EndpointGroup getRoutes(){
        return () -> {
            get("/", ctx -> ctx.result("Hello World!"));
            path("/dog", dogRoutes.getRoutes());
        };
    }
}
