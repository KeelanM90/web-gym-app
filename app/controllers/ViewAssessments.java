package controllers;

import models.Assessment;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

/**
 * Created by keela on 18/05/2017.
 */
public class ViewAssessments extends Controller {
    
    public static void index(Long memberid) {
        Logger.info("Rendering Assessments");
    
        Trainer trainer = Accounts.getLoggedInTrainer();
        Member member = Member.findById(memberid);
        List<Assessment> assessments = member.assessments;
        render("viewassessments.html", trainer, member, assessments);
    }
}
