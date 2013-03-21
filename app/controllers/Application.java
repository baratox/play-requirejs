package controllers;

import play.*; 
import play.api.mvc.Action;
import play.api.mvc.AnyContent;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result page1() {
        return ok(page1.render());
    }

    public static Result page2() {
        return ok(page2.render());
    }

    /**
     * Redirects to the appropriate JavaScript asset depending on the environment.
     * If running in <em>dev mode</em>, raw scripts are used.
     * If running in <em>prod mode</em>, uglyfied and inlined scripts are used.
     *
     * @see controllers.Assets.at
     */
    public static Action<AnyContent> javascripts(String file) {
        String folder = "javascripts/";
        if (Play.isProd()) {
            folder = "javascripts-min/";
        }
        
        Logger.debug(String.format("[%s mode] JavaScript file '%s' served from '%s'.", 
            Play.isProd() ? "prod" : "dev", file, folder + file));

        return controllers.Assets.at("/public", folder + file).apply();
    }
  
}
