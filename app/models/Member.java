package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Models a member
 *
 * @author Keelan Murphy
 * @version 2017.04.30
 */
@Entity
public class Member extends Model {
    public String name;
    public String email;
    public String password;
    public String address;
    public String gender;
    public double height;
    public double startingWeight;
    
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
    public static Member findByEmail(String email)
    {
        return find("email", email).first();
    }
    
    public boolean checkPassword(String password)
    {
        return this.password.equals(password);
    }
    
    /**
     * This method returns a boolean to indicate if the member has an ideal body weight based on the Devine formula.<br>
     *  For males, an ideal body weight is:     50 kg + 2.3 kg for each inch over 5 feet.<br>
     *  For females, an ideal body weight is:   45.5 kg + 2.3 kg for each inch over 5 feet.<br>
     *  <br>
     *  Note: if no gender is specified, return the result of the female calculation
     *
     *  @return Returns true if the result of the devine formula is within 2 kgs (inclusive) of the starting weight; false if it is outside this range.
     */
    public String isIdealBodyWeight()
    {
        double weight = 0;
        if (getLatestAssessment() != null) {
            weight = getLatestAssessment().weight;
        }
        else {
            weight = startingWeight;
        }
        
        double genderWeight = 0;
        
        double heightInInches = convertHeightMetresToInches();
        if (heightInInches < 60)
        {
            heightInInches = 60;
        }
        
        if (gender.equals("Male"))
        {
            genderWeight = 50;
        }
        else
        {
            genderWeight = 45.5;
        }
        
        double idealBodyWeight = genderWeight + ((heightInInches - 60) * 2.3);
        if (weight >= (idealBodyWeight - 2) && weight <= (idealBodyWeight + 2)) {
            return "green";
        }
        else if (weight >= (idealBodyWeight - 5) && weight <= (idealBodyWeight + 5)) {
            return "yellow";
        }
        else if (weight >= (idealBodyWeight - 8) && weight <= (idealBodyWeight + 8)) {
            return "orange";
        }
        else {
            return "red";
        }
    }
    
    /**
     * This method calculates the BMI for the member.
     * @return the BMI value for the member. The number returned is truncated to two decimal places.
     */
    public double calculateBMI()
    {
        double weight = 0;
        if (getLatestAssessment() != null) {
            weight = getLatestAssessment().weight;
        }
        else {
            weight = startingWeight;
        }
        return toTwoDecimalPlaces((weight / height) / height);
    }
    
    /**
     * This method determines the BMI category that the member belongs to.<br>
     * <br>
     * The category is determined by the magnitude of the members BMI according to the following:<br>
     * <br>
     *  BMI less than    15   (exclusive)                      is "VERY SEVERELY UNDERWEIGHT"<br>
     *  BMI between      15   (inclusive) and 16   (exclusive) is "SEVERELY UNDERWEIGHT"<br>
     *  BMI between      16   (inclusive) and 18.5 (exclusive) is "UNDERWEIGHT"<br>
     *  BMI between      18.5 (inclusive) and 25   (exclusive) is "NORMAL"<br>
     *  BMI between      25   (inclusive) and 30   (exclusive) is "OVERWEIGHT"<br>
     *  BMI between      30   (inclusive) and 35   (exclusive) is "MODERATELY OBESE"<br>
     *  BMI between      35   (inclusive) and 40   (exclusive) is "SEVERELY OBESE"<br>
     *  BMI greater than 40   (inclusive)                      is "VERY SEVERELY OBESE"<br>
     *
     *  @return the BMI category that the member belongs to.
     */
    public String getBMICategory()
    {
        double bmi = calculateBMI();
        if (bmi < 15)
        {
            return "VERY SEVERELY UNDERWEIGHT";
        }
        else if (bmi >= 15 && bmi < 16)
        {
            return "SEVERELY UNDERWEIGHT";
        }
        else if (bmi >= 16 && bmi < 18.5)
        {
            return "UNDERWEIGHT";
        }
        else if (bmi >= 18.5 && bmi < 25)
        {
            return "NORMAL";
        }
        else if (bmi >= 25 && bmi < 30)
        {
            return "OVERWEIGHT";
        }
        else if (bmi >= 30 && bmi < 35)
        {
            return "MODERATELY OBESE";
        }
        else if (bmi >= 35 && bmi < 40)
        {
            return "SEVERELY OBESE";
        }
        else if (bmi >= 40)
        {
            return "VERY SEVERELY OBESE";
        }
        
        return "Error in BMI Calculation";
    }
    
    public Assessment getLatestAssessment() {
       if (assessments.size() != 0) {
           return assessments.get(assessments.size() - 1);
       }
       return null;
    }
    
    /**
     * Returns the double sent as an argument truncated to two decimal places.
     * @param num the number to be truncated to two decimal places
     * @return the input truncated to two decimal places
     */
    private double toTwoDecimalPlaces(double num)
    {
        return (int) (num * 100 ) / 100.0;
    }
    
    /**
     * This method returns the member height converted from metres to inches.
     * @return member height converted from metres to inches using the formula: metres multipled by 39.37. The number returned is truncated to two decimal places.
     */
    public double convertHeightMetresToInches()
    {
        double heightInInches = toTwoDecimalPlaces(height * 39.37);
        return heightInInches;
    }
}
