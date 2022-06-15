package dev.eroberts.term_tracker.DAO;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.lifecycle.LiveData;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Dao;

import dev.eroberts.term_tracker.Entities.entity_mentor;

import java.util.List;

@Dao
public interface dao_mentors {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(entity_mentor mentor);

    @Delete
    void delete(entity_mentor mentor);

    @Query("SELECT * FROM mentor_table ORDER BY mentorID ASC")
    LiveData<List<entity_mentor>> get_all_mentors();

    @Query ("SELECT * FROM mentor_table WHERE courseID= :courseID ORDER BY mentorName ASC")
    LiveData<List<entity_mentor>> get_course_mentors(int courseID);
}

