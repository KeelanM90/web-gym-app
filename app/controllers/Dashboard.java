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
}