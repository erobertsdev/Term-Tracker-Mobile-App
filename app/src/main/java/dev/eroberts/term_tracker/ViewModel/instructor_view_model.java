package dev.eroberts.term_tracker.ViewModel;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import dev.eroberts.term_tracker.Database.TermTrackerRepo;
import dev.eroberts.term_tracker.Entities.entity_instructor;

import java.util.List;

/**
 * The type Instructor view model.
 */
public class instructor_view_model extends AndroidViewModel {
    /**
     * The Course id.
     */
    int courseID;
    private TermTrackerRepo repository_e;
    private LiveData<List<entity_instructor>> associated_instructors_list;
    private LiveData<List<entity_instructor>> all_instructors_list;

    /**
     * Instantiates a new Instructor view model.
     *
     * @param application the application
     * @param courseID    the course id
     */
    public instructor_view_model(Application application, int courseID){
        super(application);
        repository_e =new TermTrackerRepo(application);
        associated_instructors_list = repository_e.get_associated_instructors(courseID);
    }

    /**
     * Instantiates a new Instructor view model.
     *
     * @param application the application
     */
    public instructor_view_model(Application application){
        super(application);
        repository_e =new TermTrackerRepo(application);
        all_instructors_list = repository_e.get_all_instructors();
        associated_instructors_list = repository_e.get_associated_instructors(courseID);
    }

    /**
     * Get associated instructors live data.
     *
     * @param courseID the course id
     * @return the live data
     */
    public LiveData<List<entity_instructor>> getAssociatedInstructors(int courseID){
        return repository_e.get_associated_instructors(courseID);
    }

    /**
     * Gets all instructors.
     *
     * @return the all instructor
    s
     */
    public LiveData<List<entity_instructor>> getAllInstructors() { return all_instructors_list; };

    /**
     * Insert.
     *
     * @param entityinstructor the entityinstructor
     */
    public void insert(entity_instructor entityinstructor) { repository_e.insert(entityinstructor); }

    /**
     * Delete.
     *
     * @param entityinstructor the entityinstructor
     */
    public void delete(entity_instructor entityinstructor) { repository_e.delete(entityinstructor);}

    /**
     * Last id int.
     *
     * @return the int
     */
    public int lastID() { return all_instructors_list.getValue().size(); }
}
