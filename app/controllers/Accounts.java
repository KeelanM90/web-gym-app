package controllers;

import models.Member;
import play.Logger;
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
    
    public static void register(String name,
                                String email,
                                String password,
                                String address,
                                String gender,
                                double height,
                                double startingWeight) {
        Logger.info("Registering new user " + email);
        Member member = new Member(name, email, password, address, gender, height, startingWeight);
        member.save();
        redirect("/");
    }
}
