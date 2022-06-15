package dev.eroberts.term_tracker.ViewModel;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.app.Application;
import dev.eroberts.term_tracker.Database.TermTrackerRepo;
import dev.eroberts.term_tracker.Entities.entity_term;
import java.util.List;

/**
 * The type Term view model.
 */
public class term_view_model extends AndroidViewModel {
    private TermTrackerRepo repository_e;
    private LiveData<List<entity_term>> all_terms_list;

    /**
     * Instantiates a new Term view model.
     *
     * @param application the application
     */
    public term_view_model(Application application) {
        super(application);
        repository_e = new TermTrackerRepo(application);
        all_terms_list = repository_e.get_all_terms();
    }

    /**
     * Gets all terms.
     *
     * @return the all terms
     */
    public LiveData<List<entity_term>> getAllTerms() { return all_terms_list; }

    /**
     * Insert.
     *
     * @param entityterm the entityterm
     */
    public void insert(entity_term entityterm) { repository_e.insert(entityterm); }

    /**
     * Delete.
     *
     * @param entityterm the entityterm
     */
    public void delete(entity_term entityterm) { repository_e.delete(entityterm); }

    /**
     * Last id int.
     *
     * @return the int
     */
    public int lastID() { return all_terms_list.getValue().size(); }
}
