package controllers;

import play.mvc.Controller;

/**
 * Created by keela on 14/05/2017.
 */
public class Accounts extends Controller {
    public static void signup() {
        render("signup.html");
    }
    
    public static void login() {
        render("login.html");
    }
    
    public static void logout() {
        session.clear();
        redirect ("/");
    }
}
