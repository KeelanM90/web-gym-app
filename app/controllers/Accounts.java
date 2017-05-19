package controllers;

import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

/**
 * Responsible for the management of the accounts.
 *
 * @author Keelan Murphy
 * @version 18/05/2017
 */

public class Accounts extends Controller {
    
    /**
     * Renders the signup page
     */
    public static void signup() {
        render("signup.html");
    }
    
    /**
     * Renders the login page
     */
    public static void login() {
        render("login.html");
    }
    
    /**
     * Deletes the session
     */
    public static void logout() {
        session.clear();
        redirect("/");
    }
    
    /**
     * Creates a member and saves them. No validation is performed as the app relies on the html
     * elements to ensure proper formatting
     *
     * @param name           name of member
     * @param email          member's email address
     * @param password       password for member
     * @param address        member's address
     * @param gender         member's gender
     * @param height         member's height
     * @param startingWeight initial weight of member
     */
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
    
    /**
     * Authenticates a login for either a member or a trainer. Utilizes the findByEmail method in both classes
     * to find an instance of the object. If an instance is found and the password matches a session is created
     * and the user is redirected to the relevant dashboard
     *
     * @param email    email passed by form
     * @param password password passed by form
     */
    public static void authenticate(String email, String password) {
        Logger.info("Attempting to authenticate with " + email + ":" + password);
        
        Member member = Member.findByEmail(email);
        Trainer trainer = Trainer.findByEmail(email);
        if ((member != null) && (member.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_Memberid", member.id);
            redirect("/dashboard");
        } else if ((trainer != null) && (trainer.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_Trainerid", trainer.id);
            redirect("/trainerdashboard");
        } else {
            Logger.info("Authentication failed");
            redirect("/login");
        }
    }
    
    /**
     * Checks for a session variable containing the members id. If it exists the member is returned,
     * otherwise the return remains null
     *
     * @return the member instance based on the id stored in session
     */
    public static Member getLoggedInMember() {
        Member member = null;
        if (session.contains("logged_in_Memberid")) {
            String memberId = session.get("logged_in_Memberid");
            member = Member.findById(Long.parseLong(memberId));
        } else {
            login();
        }
        return member;
    }
    
    /**
     * Checks for a session variable containing the trainers id. If it exists the trainer is returned,
     * otherwise the return remains null
     *
     * @return the trainer instance based on the id stored in session
     */
    public static Trainer getLoggedInTrainer() {
        Trainer trainer = null;
        if (session.contains("logged_in_Trainerid")) {
            String trainerId = session.get("logged_in_Trainerid");
            trainer = Trainer.findById(Long.parseLong(trainerId));
        } else {
            login();
        }
        return trainer;
    }
    
    /**
     * Updates a users profile by finding the currently logged in member and setting that members
     * information to the data inputted in the form. The final task of the method is to redirect to
     * the update profile view;
     *
     * @param name           the name in the form which the user may edit
     * @param email          the email in the form which the user may edit
     * @param password       the password in the form which the user may edit
     * @param address        the address in the form which the user may edit
     * @param gender         the gender in the form which the user may edit
     * @param height         the height in the form which the user may edit
     * @param startingWeight the initial weight in the form which the user may edit
     */
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
