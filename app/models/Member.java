package models;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Models a member
 *
 * @author Keelan Murphy
 * @version 2017.04.30
 */
public class Member {
    public String name;
    public String email;
    public String password;
    String address;
    String gender;
    double height;
    double startingWeight;
    
    @OneToMany(cascade = CascadeType.ALL)
    public List<Assessment> assessments = new ArrayList<Assessment>();
    
    public Member(
        String name,
        String email,
        String password,
        String address,
        String gender,
        double height,
        double startingWeight) {
        
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.height = height;
        this.startingWeight = startingWeight;
    }
    
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
