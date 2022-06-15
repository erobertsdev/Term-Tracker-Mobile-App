package dev.eroberts.term_tracker.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.OnConflictStrategy;
import androidx.room.Dao;

import dev.eroberts.term_tracker.Entities.entity_term;

import java.util.List;

/**
 * The interface Dao terms.
 */
@Dao
public interface dao_terms {
    /**
     * Insert.
     *
     * @param term the term
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(entity_term term);

    /**
     * Delete.
     *
     * @param term the term
     */
    @Delete
    void delete(entity_term term);

    /**
     * Gets all terms.
     *
     * @return the all terms
     */
    @Query("SELECT * FROM term_table ORDER BY termName ASC")
    LiveData<List<entity_term>> get_all_terms();
}
