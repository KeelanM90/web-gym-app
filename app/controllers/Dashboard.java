package controllers;

import java.util.List;

import models.Member;
import models.Assessment;
import play.Logger;
import play.mvc.Controller;

public class Dashboard extends Controller
{
    public static void index()
    {
        Logger.info("Rendering Dashboard");
        
        Member member = Accounts.getLoggedInMember();
        List<Assessment> assessments = member.assessments;
        render("dashboard.html", member, assessments);
    }
    
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
    
    public static void deleteAssessment (Long memberid, Long assessmentid)
    {
        Member member = Member.findById(memberid);
        Assessment assessment = Assessment.findById(assessmentid);
        Logger.info ("Removing assessment");
        member.assessments.remove(assessment);
        member.save();
        assessment.delete();
        redirect("/dashboard");
    }
}