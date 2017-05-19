package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Models an assessment.
 */

@Entity
public class Assessment extends Model {
    public Long epoch;
    public double weight;
    public double chest;
    public double thigh;
    public double upperArm;
    public double waist;
    public double hips;
    public String comment;
    
    /**
     * The default constructor for an assessment. The information is passed from the html along
     * with setting the unix-timestamp using the systems current time
     *
     * @param weight   the latest weight
     * @param chest    the latest chest measurement
     * @param thigh    the latest thigh measurement
     * @param upperArm the latest upper arm measurement
     * @param waist    the latest waist measurement
     * @param hips     the latest hip measurement
     * @param comment  the comment for the assessment
     */
    public Assessment(
        double weight,
        double chest,
        double thigh,
        double upperArm,
        double waist,
        double hips,
        String comment) {
        
        
        this.epoch = System.currentTimeMillis();
        
        this.weight = weight;
        this.chest = chest;
        this.thigh = thigh;
        this.upperArm = upperArm;
        this.waist = waist;
        this.hips = hips;
        this.comment = comment;
    }
    
    /**
     * Returns the date in human readable format.
     *
     * @return The date in the following format: d-MMM-yyy HH:mm:ss
     */
    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("d-MMM-yyyy HH:mm:ss");
        return dateFormat.format(epoch);
    }
    
    /**
     * Setter for an assessment's comment
     *
     * @param comment the new comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}

