package controllers;

import java.util.List;

import models.Member;
import models.Assessment;
import play.Logger;
import play.mvc.Controller;

/**
 * Provides the member's dashboard and associated functionality.
 *
 * @author Keelan Murphy
 * @version 18/05/2017
 */

public class Dashboard extends Controller {
    
    /**
     * Renders the dashboard, providing the currently logged in member object to the render.
     */
    public static void index() {
        Logger.info("Rendering Dashboard");
        
        Member member = Accounts.getLoggedInMember();
        render("dashboard.html", member);
    }
    
    /**
     * Adds an assessment by creating a new assessment and adding it to the current member's
     * assessments.
     *
     * @param weight   the latest weight retrieved from the form
     * @param chest    the latest chest measurement retrieved from the form
     * @param thigh    the latest thigh measurement retrieved from the form
     * @param upperArm the latest upper arm measurement retrieved from the form
     * @param waist    the latest waist measurement retrieved from the form
     * @param hips     the latest hip measurement retrieved from the form
     */
    public static void addAssessment(double weight,
                                     double chest,
                                     double thigh,
                                     double upperArm,
                                     double waist,
                                     double hips) {
        Member member = Accounts.getLoggedInMember();
        Assessment newAssessment = new Assessment(weight, chest, thigh, upperArm, waist, hips, "");
        member.assessments.add(newAssessment);
        member.save();
        Logger.info("Adding Assessment");
        redirect("/dashboard");
    }
    
    /**
     * Deletes an assessment by first removing it from the members assessments and then deleting
     * the assessment itself. The method then reloads the dashboard.
     *
     * @param memberid     The id of the member the assessment belongs to
     * @param assessmentid the assessments id
     */
    public static void deleteAssessment(Long memberid, Long assessmentid) {
        Member member = Member.findById(memberid);
        Assessment assessment = Assessment.findById(assessmentid);
        Logger.info("Removing assessment: " + assessment.getDate());
        member.assessments.remove(assessment);
        member.save();
        assessment.delete();
        redirect("/dashboard");
    }
}