package dev.eroberts.term_tracker.Entities;

import androidx.room.*;

/**
 * The type Entity term.
 */
@Entity(tableName = "term_table")

public class entity_term {

    @PrimaryKey
    private int termID;

    private String termName;
    private String termStart;
    private String termEnd;

    /**
     * Instantiates a new Entity term.
     *
     * @param termID    the term id
     * @param termName  the term name
     * @param termStart the term start
     * @param termEnd   the term end
     */
    public entity_term(int termID, String termName, String termStart, String termEnd) {
        this.termID = termID;
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }

    @Override
    public String toString() {
        return "TermEntity{" +
                "termID=" + termID +
                ", termName=" + termName +
                ", termStart=" + termStart +
                ", termEnd=" + termEnd +
                '}';
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

    /**
     * Gets term name.
     *
     * @return the term name
     */
    public String getTermName() {
        return termName;
    }

    /**
     * Sets term name.
     *
     * @param termName the term name
     */
    public void setTermName(String termName) {
        this.termName = termName;
    }

    /**
     * Gets term start.
     *
     * @return the term start
     */
    public String getTermStart() {
        return termStart;
    }

    /**
     * Sets term start.
     *
     * @param termStart the term start
     */
    public void setTermStart(String termStart) {
        this.termStart = termStart;
    }

    /**
     * Gets term end.
     *
     * @return the term end
     */
    public String getTermEnd() {
        return termEnd;
    }

    /**
     * Sets term end.
     *
     * @param termEnd the term end
     */
    public void setTermEnd(String termEnd) {
        this.termEnd = termEnd;
    }
}
