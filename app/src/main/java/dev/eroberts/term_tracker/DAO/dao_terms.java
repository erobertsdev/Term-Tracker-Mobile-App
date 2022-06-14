package dev.eroberts.term_tracker.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import dev.eroberts.term_tracker.Entities.entity_term;

import java.util.List;

@Dao
public interface dao_terms {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(entity_term term);

    @Delete
    void delete(entity_term term);

    @Query("DELETE FROM term_table")
    void deleteAllTerms();

    @Query("SELECT * FROM term_table ORDER BY termName ASC")
    LiveData<List<entity_term>> getAllTerms();
}
