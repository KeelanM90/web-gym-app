package controllers;

import models.Member;
import play.Logger;
import play.mvc.Controller;

/**
 * Renders the view responsible for updating a member's profile. Passes
 * the current member to the view.
 *
 * @author Keelan Murphy
 * @version 18/05/2017
 */

public class Profile extends Controller {
    
    public static void index() {
        Logger.info("Rendering profile");
        
        Member member = Accounts.getLoggedInMember();
        render("profile.html", member);
    }
}
