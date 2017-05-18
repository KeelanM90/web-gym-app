package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.*;

/**
 * Models a member
 *
 * @author Keelan Murphy
 * @version 2017.04.30
 */
@Entity
public class Trainer extends Model {
    public String name;
    public String email;
    public String password;
    
    public Trainer(
        String name,
        String email,
        String password) {
        
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    public static Trainer findByEmail(String email)
    {
        return find("email", email).first();
    }
    
    public boolean checkPassword(String password)
    {
        return this.password.equals(password);
    }
    
}
