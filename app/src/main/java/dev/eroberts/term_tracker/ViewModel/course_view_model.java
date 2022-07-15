package dev.eroberts.term_tracker.ViewModel;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.app.Application;
import dev.eroberts.term_tracker.Database.TermTrackerRepo;
import dev.eroberts.term_tracker.Entities.entity_course;
import java.util.List;

/**
 * The type Course view model.
 */
public class course_view_model extends AndroidViewModel {
    /**
     * The Term id.
     */
    int term_id;
    private TermTrackerRepo repository_e;
    private LiveData<List<entity_course>> associated_courses_list;
    private LiveData<List<entity_course>> all_courses_list;

    /**
     * Instantiates a new Course view model.
     *
     * @param application the application
     * @param termID      the term id
     */
    public course_view_model(Application application, int termID){
        super(application);
        repository_e =new TermTrackerRepo(application);
        associated_courses_list = repository_e.get_associated_courses(termID);
    }

    /**
     * Instantiates a new Course view model.
     *
     * @param application the application
     */
    public course_view_model(Application application){
        super(application);
        repository_e =new TermTrackerRepo(application);
        all_courses_list = repository_e.get_all_courses();
        associated_courses_list = repository_e.get_associated_courses(term_id);
    }

    /**
     * Get associated courses live data.
     *
     * @param termID the term id
     * @return the live data
     */
    public LiveData<List<entity_course>> getAssociatedCourses(int termID){
        return repository_e.get_associated_courses(termID);
    }

    /**
     * Gets all courses.
     *
     * @return the all courses
     */
    public LiveData<List<entity_course>> getAllCourses() { return all_courses_list; };

    /**
     * Insert.
     *
     * @param entitycourse the entitycourse
     */
    public void insert(entity_course entitycourse) { repository_e.insert(entitycourse); }

    /**
     * Delete.
     *
     * @param entitycourse the entitycourse
     */
    public void delete(entity_course entitycourse) { repository_e.delete(entitycourse);}

    /**
     * Last id int.
     *
     * @return the int
     */
    public int lastID() { return all_courses_list.getValue().size(); }
}
