package dev.eroberts.term_tracker.Entities;

import androidx.room.*;

/**
 * The type Entity mentor.
 */
@Entity(tableName = "mentor_table")

public class entity_mentor {
    @PrimaryKey
    private int mentorID;

    private String mentorName;
    private String mentorEmail;
    private String mentorPhone;
    private Integer courseID;

    @Override
    public String toString() {
        return "entity_mentor{" +
                "mentorID=" + mentorID +
                ", mentorName=" + mentorName +
                ", mentorEmail=" + mentorEmail +
                ", mentorPhone=" + mentorPhone +
                ", courseID=" + courseID +
                '}';
    }

    /**
     * Instantiates a new Entity mentor.
     *
     * @param mentorID    the mentor id
     * @param mentorName  the mentor name
     * @param mentorEmail the mentor email
     * @param mentorPhone the mentor phone
     * @param courseID    the course id
     */
    public entity_mentor(int mentorID, String mentorName, String mentorEmail, String mentorPhone, Integer courseID) {
        this.mentorID = mentorID;
        this.mentorName = mentorName;
        this.mentorEmail = mentorEmail;
        this.mentorPhone = mentorPhone;
        this.courseID = courseID;
    }

    /**
     * Gets mentor id.
     *
     * @return the mentor id
     */
    public int getMentorID() {
        return mentorID;
    }

    /**
     * Sets mentor id.
     *
     * @param mentorID the mentor id
     */
    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    /**
     * Gets mentor name.
     *
     * @return the mentor name
     */
    public String getMentorName() {
        return mentorName;
    }

    /**
     * Sets mentor name.
     *
     * @param mentorName the mentor name
     */
    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    /**
     * Gets mentor email.
     *
     * @return the mentor email
     */
    public String getMentorEmail() {
        return mentorEmail;
    }

    /**
     * Sets mentor email.
     *
     * @param mentorEmail the mentor email
     */
    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    /**
     * Gets mentor phone.
     *
     * @return the mentor phone
     */
    public String getMentorPhone() {
        return mentorPhone;
    }

    /**
     * Sets mentor phone.
     *
     * @param mentorPhone the mentor phone
     */
    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    /**
     * Gets course id.
     *
     * @return the course id
     */
    public Integer getCourseID() {
        return courseID;
    }

    /**
     * Sets course id.
     *
     * @param courseID the course id
     */
    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }
}
