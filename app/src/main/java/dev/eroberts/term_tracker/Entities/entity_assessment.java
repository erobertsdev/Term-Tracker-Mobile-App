package dev.eroberts.term_tracker.Entities;

import androidx.room.*;

/**
 * The type Entity assessment.
 */
@Entity(tableName = "assessment_table")

public class entity_assessment {
    @PrimaryKey
    // DO NOT RENAME, breaks everythang
    private int assessmentID;
    private String assessmentName;
    private String assessmentDate;
    private String assessmentType;
    private Integer courseID;

    @Override
    public String toString() {
        return "entity_assessment{" +
                "assessmentID=" + assessmentID +
                ", assessmentName=" + assessmentName +
                ", assessmentDate=" + assessmentDate +
                ", assessmentType=" + assessmentType +
                ", courseID=" + courseID +
                '}';
    }

    /**
     * Instantiates a new Entity assessment.
     *
     * @param assessmentID   the assessment id
     * @param assessmentName the assessment name
     * @param assessmentDate the assessment date
     * @param assessmentType the assessment type
     * @param courseID       the course id
     */
    public entity_assessment(int assessmentID, String assessmentName, String assessmentDate, String assessmentType, Integer courseID) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.assessmentDate = assessmentDate;
        this.assessmentType = assessmentType;
        this.courseID = courseID;
    }

    /**
     * Gets assessment id.
     *
     * @return the assessment id
     */
    public int getAssessmentID() {
        return assessmentID;
    }

    /**
     * Sets assessment id.
     *
     * @param assessmentID the assessment id
     */
    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    /**
     * Gets assessment name.
     *
     * @return the assessment name
     */
    public String getAssessmentName() {
        return assessmentName;
    }

    /**
     * Sets assessment name.
     *
     * @param assessmentName the assessment name
     */
    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    /**
     * Gets assessment date.
     *
     * @return the assessment date
     */
    public String getAssessmentDate() {
        return assessmentDate;
    }

    /**
     * Sets assessment date.
     *
     * @param assessmentDate the assessment date
     */
    public void setAssessmentDate(String assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    /**
     * Gets assessment type.
     *
     * @return the assessment type
     */
    public String getAssessmentType() {
        return assessmentType;
    }

    /**
     * Sets assessment type.
     *
     * @param assessmentType the assessment type
     */
    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
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
