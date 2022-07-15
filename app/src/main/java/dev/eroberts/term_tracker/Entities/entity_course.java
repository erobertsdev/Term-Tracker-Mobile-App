package dev.eroberts.term_tracker.Entities;

import androidx.room.*;

/**
 * The type Entity course.
 */
@Entity(tableName = "course_table")

public class entity_course {
    @PrimaryKey
    private int courseID;
    private String courseName;
    private String courseStart;
    private String courseEnd;
    private String courseStatus;
    private String courseNotes;
    private int termID;

    @Override
    public String toString() {
        return "entity_course{" +
                "courseID=" + courseID +
                ", courseName=" + courseName +
                ", courseStart=" + courseStart +
                ", courseEnd=" + courseEnd +
                ", courseStatus=" + courseStatus +
                ", courseNotes=" + courseNotes +
                ", termID=" + termID +
                '}';
    }

    /**
     * Instantiates a new Entity course.
     *
     * @param courseID     the course id
     * @param courseName   the course name
     * @param courseStart  the course start
     * @param courseEnd    the course end
     * @param courseStatus the course status
     * @param courseNotes  the course notes
     * @param termID       the term id
     */
    public entity_course(int courseID, String courseName, String courseStart, String courseEnd, String courseStatus, String courseNotes, int termID) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.courseNotes = courseNotes;
        this.termID = termID;
    }

    /**
     * Gets course id.
     *
     * @return the course id
     */
    public int getCourseID() {
        return courseID;
    }

    /**
     * Sets course id.
     *
     * @param courseID the course id
     */
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    /**
     * Gets course name.
     *
     * @return the course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets course name.
     *
     * @param courseName the course name
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Gets course start.
     *
     * @return the course start
     */
    public String getCourseStart() {
        return courseStart;
    }

    /**
     * Sets course start.
     *
     * @param courseStart the course start
     */
    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    /**
     * Gets course end.
     *
     * @return the course end
     */
    public String getCourseEnd() {
        return courseEnd;
    }

    /**
     * Sets course end.
     *
     * @param courseEnd the course end
     */
    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    /**
     * Gets course status.
     *
     * @return the course status
     */
    public String getCourseStatus() {
        return courseStatus;
    }

    /**
     * Sets course status.
     *
     * @param courseStatus the course status
     */
    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    /**
     * Gets course notes.
     *
     * @return the course notes
     */
    public String getCourseNotes() {
        return courseNotes;
    }

    /**
     * Sets course notes.
     *
     * @param courseNotes the course notes
     */
    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    /**
     * Gets term id.
     *
     * @return the term id
     */
    public int getTermID() {
        return termID;
    }

    /**
     * Sets term id.
     *
     * @param termID the term id
     */
    public void setTermID(int termID) {
        this.termID = termID;
    }
}
