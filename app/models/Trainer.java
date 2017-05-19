package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.*;

/**
 * Models a trainer.
 *
 * @author Keelan Murphy
 * @version 2017.04.30
 */
@Entity
public class Trainer extends Model {
    public String name;
    public String email;
    public String password;
    
    /**
     * The default constructor for a trainer
     *
     * @param name     the trainers name
     * @param email    the trainers email
     * @param password the trainers password
     */
    public Trainer(
        String name,
        String email,
        String password) {
        
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    /**
     * Returns a trainer object based on a passed email
     *
     * @param email the email to search for
     * @return the first trainer instance where the email matches the input
     */
    public static Trainer findByEmail(String email) {
        return find("email", email).first();
    }
    
    /**
     * Checks if the passed password matches the trainer's password
     *
     * @param password the password to validate
     * @return a boolean representing whether the password is correct
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
