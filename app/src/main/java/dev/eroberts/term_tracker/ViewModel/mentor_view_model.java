package dev.eroberts.term_tracker.ViewModel;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import dev.eroberts.term_tracker.Database.TermTrackerRepo;
import dev.eroberts.term_tracker.Entities.entity_mentor;
import java.util.List;

/**
 * The type Mentor view model.
 */
public class mentor_view_model extends AndroidViewModel {
    /**
     * The Course id.
     */
    int courseID;
    private TermTrackerRepo repository_e;
    private LiveData<List<entity_mentor>> associated_mentors_list;
    private LiveData<List<entity_mentor>> all_mentors_list;

    /**
     * Instantiates a new Mentor view model.
     *
     * @param application the application
     * @param courseID    the course id
     */
    public mentor_view_model(Application application, int courseID){
        super(application);
        repository_e =new TermTrackerRepo(application);
        associated_mentors_list = repository_e.get_associated_mentors(courseID);
    }

    /**
     * Instantiates a new Mentor view model.
     *
     * @param application the application
     */
    public mentor_view_model(Application application){
        super(application);
        repository_e =new TermTrackerRepo(application);
        all_mentors_list = repository_e.get_all_mentors();
        associated_mentors_list = repository_e.get_associated_mentors(courseID);
    }

    /**
     * Get associated mentors live data.
     *
     * @param courseID the course id
     * @return the live data
     */
    public LiveData<List<entity_mentor>> getAssociatedMentors(int courseID){
        return repository_e.get_associated_mentors(courseID);
    }

    /**
     * Gets all mentors.
     *
     * @return the all mentors
     */
    public LiveData<List<entity_mentor>> getAllMentors() { return all_mentors_list; };

    /**
     * Insert.
     *
     * @param entitymentor the entitymentor
     */
    public void insert(entity_mentor entitymentor) { repository_e.insert(entitymentor); }

    /**
     * Delete.
     *
     * @param entitymentor the entitymentor
     */
    public void delete(entity_mentor entitymentor) { repository_e.delete(entitymentor);}

    /**
     * Last id int.
     *
     * @return the int
     */
    public int lastID() { return all_mentors_list.getValue().size(); }
}
