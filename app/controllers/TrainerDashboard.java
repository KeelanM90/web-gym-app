package controllers;

import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

public class TrainerDashboard extends Controller {
    public static void index() {
        Logger.info("Rendering Trainer Dashboard");
        
        Trainer trainer = Accounts.getLoggedInTrainer();
        List<Member> members = Member.findAll();
        render("trainerdashboard.html", trainer, members);
    }
    
    public static void deleteMember (Long memberid) {
        Member member = Member.findById(memberid);
        member.delete();
        
        redirect("trainerdashboard.index");
    }
}