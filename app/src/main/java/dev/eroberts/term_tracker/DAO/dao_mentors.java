package dev.eroberts.term_tracker.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import dev.eroberts.term_tracker.Entities.entity_mentor;

import java.util.List;

@Dao
public interface dao_mentors {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(entity_mentor mentor);

    @Delete
    void delete(entity_mentor mentor);

    @Query("DELETE FROM mentor_table")
    void deleteAllMentors();

    @Query("SELECT * FROM mentor_table ORDER BY mentorID ASC")
    LiveData<List<entity_mentor>> getAllMentors();

    @Query ("SELECT * FROM mentor_table WHERE courseID= :courseID ORDER BY mentorName ASC")
    LiveData<List<entity_mentor>> getAllAssociatedMentors(int courseID);
}

