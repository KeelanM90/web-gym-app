package controllers;

import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

/**
 * Provides the trainer's dashboard passing the current member and a list of all members.
 *
 * @author Keelan Murphy
 * @version 18/05/2017
 */

public class TrainerDashboard extends Controller {
    
    /**
     * Renders the trainer's dashboard passing in the current trainer and a list of all
     * members. While it is not necessary to pass the trainer instance, it is useful if
     * in the future the view were updated to show the trainer's name.
     */
    public static void index() {
        Logger.info("Rendering Trainer Dashboard");
        
        Trainer trainer = Accounts.getLoggedInTrainer();
        List<Member> members = Member.findAll();
        render("trainerdashboard.html", trainer, members);
    }
    
    /**
     * Deletes a member found from the passed member id. Redirects to the trainers dashboard
     * after deletion.
     *
     * @param memberid the ID of the member to be deleted
     */
    public static void deleteMember(Long memberid) {
        Member member = Member.findById(memberid);
        member.delete();
        
        redirect("trainerdashboard.index");
    }
}