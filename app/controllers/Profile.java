package controllers;

import models.Member;
import play.Logger;
import play.mvc.Controller;

/**
 * Created by keela on 18/05/2017.
 */
public class Profile extends Controller{
    
    public static void index()
    {
        Logger.info("Rendering profile");
        
        Member member = Accounts.getLoggedInMember();
        render("profile.html", member);
    }
}
