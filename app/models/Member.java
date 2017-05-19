package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.*;

/**
 * Models a member.
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
    
    /**
     * The default constructor for a member
     *
     * @param name           the member's name
     * @param email          the mamber's email
     * @param password       the member's password
     * @param address        the member's address
     * @param gender         the member's gender
     * @param height         the member's height
     * @param startingWeight the member's initial weight
     */
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
    
    /**
     * Setter for members name
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Setter for email
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Setter for password
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Setter for address
     *
     * @param address the new address
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * Setter for gender
     *
     * @param gender the new gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    /**
     * Setter for height
     *
     * @param height the new height
     */
    public void setHeight(double height) {
        this.height = height;
    }
    
    /**
     * Setter for starting weight
     *
     * @param startingWeight the corrected initial weight
     */
    public void setStartingWeight(double startingWeight) {
        this.startingWeight = startingWeight;
    }
    
    /**
     * Returns a member object based on a passed email
     *
     * @param email the email to search for
     * @return the first member instance where the email matches the input
     */
    public static Member findByEmail(String email) {
        return find("email", email).first();
    }
    
    /**
     * Checks if the passed password matches the member's password
     *
     * @param password the password to validate
     * @return a boolean representing whether the password is correct
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
    
    /**
     * This method returns a string of semantic ui classes to indicate if the member has an
     * ideal body weight based on the Devine formula.
     *
     * @return returns a string of semantic classes based on the calculated idealBodyWeight.
     */
    public String isIdealBodyWeight() {
        double weight = 0;
        if (getLatestAssessment() != null) {
            weight = getLatestAssessment().weight;
        } else {
            weight = startingWeight;
        }
        
        double idealBodyWeight = idealBodyWeight();
        
        if (weight >= (idealBodyWeight - 2) && weight <= (idealBodyWeight + 2)) {
            return "green";
        } else if (weight >= (idealBodyWeight - 5) && weight <= (idealBodyWeight + 5)) {
            return "yellow";
        } else if (weight >= (idealBodyWeight - 8) && weight <= (idealBodyWeight + 8)) {
            return "orange";
        } else {
            return "red";
        }
    }
    
    /**
     * This method calculates the BMI for the member.
     *
     * @return the BMI value for the member. The number returned is truncated to two decimal places.
     */
    public double calculateBMI() {
        double weight = 0;
        if (getLatestAssessment() != null) {
            weight = getLatestAssessment().weight;
        } else {
            weight = startingWeight;
        }
        return toTwoDecimalPlaces((weight / height) / height);
    }
    
    /**
     * This method determines the BMI category that the member belongs to.
     *
     * The category is determined by the magnitude of the members BMI according to the following:
     *
     * BMI less than    15   (exclusive)                      is "VERY SEVERELY UNDERWEIGHT"
     * BMI between      15   (inclusive) and 16   (exclusive) is "SEVERELY UNDERWEIGHT"
     * BMI between      16   (inclusive) and 18.5 (exclusive) is "UNDERWEIGHT"
     * BMI between      18.5 (inclusive) and 25   (exclusive) is "NORMAL"
     * BMI between      25   (inclusive) and 30   (exclusive) is "OVERWEIGHT"
     * BMI between      30   (inclusive) and 35   (exclusive) is "MODERATELY OBESE"
     * BMI between      35   (inclusive) and 40   (exclusive) is "SEVERELY OBESE"
     * BMI greater than 40   (inclusive)                      is "VERY SEVERELY OBESE"
     *
     * @return the BMI category that the member belongs to.
     */
    public String getBMICategory() {
        double bmi = calculateBMI();
        if (bmi < 15) {
            return "VERY SEVERELY UNDERWEIGHT";
        } else if (bmi >= 15 && bmi < 16) {
            return "SEVERELY UNDERWEIGHT";
        } else if (bmi >= 16 && bmi < 18.5) {
            return "UNDERWEIGHT";
        } else if (bmi >= 18.5 && bmi < 25) {
            return "NORMAL";
        } else if (bmi >= 25 && bmi < 30) {
            return "OVERWEIGHT";
        } else if (bmi >= 30 && bmi < 35) {
            return "MODERATELY OBESE";
        } else if (bmi >= 35 && bmi < 40) {
            return "SEVERELY OBESE";
        } else if (bmi >= 40) {
            return "VERY SEVERELY OBESE";
        }
        
        return "Error in BMI Calculation";
    }
    
    /**
     * Finds the most recent assessment. Returns null if there is no assessments.
     *
     * @return the latest assessment
     */
    public Assessment getLatestAssessment() {
        if (assessments.size() != 0) {
            return sortedAssessments().get(0);
        }
        return null;
    }
    
    /**
     * Returns the double sent as an argument truncated to two decimal places.
     *
     * @param num the number to be truncated to two decimal places
     * @return the input truncated to two decimal places
     */
    private double toTwoDecimalPlaces(double num) {
        return (int) (num * 100) / 100.0;
    }
    
    /**
     * This method returns the member height converted from metres to inches.
     *
     * @return member height converted from metres to inches using the formula: metres multipled by 39.37. The number returned is truncated to two decimal places.
     */
    public double convertHeightMetresToInches() {
        double heightInInches = toTwoDecimalPlaces(height * 39.37);
        return heightInInches;
    }
    
    /**
     * Compares the epoch time between assessments in order to sort a list of assessments.
     *
     * @return a list of assessments sorted by date
     */
    public List<Assessment> sortedAssessments() {
        List<Assessment> sortedList = new ArrayList<Assessment>(assessments);
        Collections.sort(sortedList, new Comparator<Assessment>() {
            @Override
            public int compare(Assessment assessment1, Assessment assessment2) {
                return assessment2.epoch.compareTo(assessment1.epoch);
            }
        });
        return sortedList;
    }
    
    /**
     * Compares the difference between two assessments (current & previous) and the ideal body weight
     * in order to find which is trending closer towards the ideal body weight. This method takes into
     * whether a member is underweight or overweight and provides positive affirmation by way of a
     * semantic ui label in order to move them towards the ideal body weight.
     *
     * For males, an ideal body weight is:     50 kg + 2.3 kg for each inch over 5 feet.
     * For females, an ideal body weight is:   45.5 kg + 2.3 kg for each inch over 5 feet.
     *
     * Note: if no gender is specified, return the result of the female calculation
     *
     * @param assessment the assessment to find a trend for
     * @return a semantic ui label style representing the trend
     */
    public String findTrend(Assessment assessment) {
        double previousWeight = 0;
        double idealBodyWeight = idealBodyWeight();
        
        List<Assessment> sortedList = new ArrayList<Assessment>(sortedAssessments());
        
        if (sortedList.indexOf(assessment) != sortedList.size() - 1) {
            previousWeight = sortedList.get(sortedList.indexOf(assessment) + 1).weight;
        } else {
            previousWeight = startingWeight;
        }
        
        double deltaCurrent = assessment.weight - idealBodyWeight;
        double deltaPrevious = previousWeight - idealBodyWeight;
        
        if (deltaCurrent < 0) {
            deltaCurrent = -deltaCurrent;
        }
        if (deltaPrevious < 0) {
            deltaPrevious = -deltaPrevious;
        }
        
        if (deltaPrevious < deltaCurrent) {
            return "red tag label";
        } else if (deltaPrevious > deltaCurrent) {
            return "green tag label";
        } else {
            return "blue tag label";
        }
    }
    
    /**
     * Calculates the ideal body weight based on the devine formula
     * @return
     */
    public double idealBodyWeight() {
        double genderWeight = 0;
        
        double heightInInches = convertHeightMetresToInches();
        if (heightInInches < 60) {
            heightInInches = 60;
        }
        
        if (gender.equals("Male")) {
            genderWeight = 50;
        } else {
            genderWeight = 45.5;
        }
        
        double idealBodyWeight = genderWeight + ((heightInInches - 60) * 2.3);
        return idealBodyWeight;
    }
}
