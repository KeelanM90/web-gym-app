package controllers;

import models.Assessment;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

/**
 * Allows a trainer to view a member's assessments and update a comment on a given assessment.
 *
 * @author Keelan Murphy
 * @version 18/05/2017
 */

public class ViewAssessments extends Controller {
    
    /**
     * Renders the assessments view for a member, passing in the member and the trainer
     * should the view be updated to include the trainer name.
     *
     * @param memberid the id of the member to show assessments for
     */
    public static void index(Long memberid) {
        Logger.info("Rendering Assessments");
        
        Trainer trainer = Accounts.getLoggedInTrainer();
        Member member = Member.findById(memberid);
        render("viewassessments.html", trainer, member);
    }
    
    /**
     * Updates a comment on a given assessment. Redirects to the member's assessments view following
     * update of the comment.
     *
     * @param memberid     the members id to redirect to the member's assessments view
     * @param assessmentid the id of the assessment to update
     * @param comment      the comment to be updated.
     */
    public static void updateComment(Long memberid, Long assessmentid, String comment) {
        Logger.info("Rendering Assessments");
        
        Member member = Member.findById(memberid);
        Assessment assessment = Assessment.findById(assessmentid);
        assessment.setComment(comment);
        assessment.save();
        
        redirect("viewassessments.index", memberid);
    }
}
