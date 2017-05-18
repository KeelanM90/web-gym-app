package controllers;

import models.Member;
import models.Trainer;
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
        redirect("/");
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
    
    public static void authenticate(String email, String password) {
        Logger.info("Attempting to authenticate with " + email + ":" + password);
    
        Member member = Member.findByEmail(email);
        Trainer trainer = Trainer.findByEmail(email);
        if ((member != null) && (member.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_Memberid", member.id);
            redirect("/dashboard");
        }
        else if ((trainer != null) && (trainer.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_Trainerid", trainer.id);
            redirect ("/trainerdashboard");
        }
        else {
            Logger.info("Authentication failed");
            redirect("/login");
        }
    }
    
    public static Member getLoggedInMember()
    {
        Member member = null;
        if (session.contains("logged_in_Memberid")) {
            String memberId = session.get("logged_in_Memberid");
            member = Member.findById(Long.parseLong(memberId));
        } else {
            login();
        }
        return member;
    }
    
    public static Trainer getLoggedInTrainer()
    {
        Trainer trainer = null;
        if (session.contains("logged_in_Trainerid")) {
            String trainerId = session.get("logged_in_Trainerid");
            trainer = Trainer.findById(Long.parseLong(trainerId));
        } else {
            login();
        }
        return trainer;
    }
    
    public static void update(String name,
                              String email,
                              String password,
                              String address,
                              String gender,
                              double height,
                              double startingWeight) {
        
        Member member = Accounts.getLoggedInMember();
        member.setName(name);
        member.setEmail(email);
        member.setPassword(password);
        member.setAddress(address);
        member.setGender(gender);
        member.setHeight(height);
        member.setStartingWeight(startingWeight);
        member.save();
        
        Logger.info("Updating: " + member.name);
        redirect("/profile");
    }
}
