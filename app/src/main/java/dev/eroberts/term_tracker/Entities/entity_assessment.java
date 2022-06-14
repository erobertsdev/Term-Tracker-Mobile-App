package dev.eroberts.term_tracker.Entities;

import androidx.room.*;

@Entity(tableName = "assessment_table")

public class entity_assessment {
    @PrimaryKey
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

    public entity_assessment(int assessmentID, String assessmentName, String assessmentDate, String assessmentType, Integer courseID) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.assessmentDate = assessmentDate;
        this.assessmentType = assessmentType;
        this.courseID = courseID;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(String assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }
}
