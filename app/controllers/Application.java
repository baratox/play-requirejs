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
  
}
