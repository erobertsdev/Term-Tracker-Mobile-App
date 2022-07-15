package dev.eroberts.term_tracker.ViewModel;
import androidx.lifecycle.LiveData;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import dev.eroberts.term_tracker.Database.TermTrackerRepo;
import dev.eroberts.term_tracker.Entities.entity_assessment;
import java.util.List;

/**
 * The type Assessment view model.
 */
public class assessment_view_model extends AndroidViewModel {
    /**
     * The Course id.
     */
    int course_id;
    private final TermTrackerRepo repository_e;
    private final LiveData<List<entity_assessment>> assessments_list;
    private LiveData<List<entity_assessment>> all_assessments_list;

    /**
     * Instantiates a new Assessment view model.
     *
     * @param application the application
     * @param courseID    the course id
     */
    public assessment_view_model(Application application, int courseID){
        super(application);
        repository_e =new TermTrackerRepo(application);
        assessments_list = repository_e.get_associated_assessments(courseID);
    }

    /**
     * Instantiates a new Assessment view model.
     *
     * @param application the application
     */
    public assessment_view_model(Application application){
        super(application);
        repository_e =new TermTrackerRepo(application);
        all_assessments_list = repository_e.get_all_assessments();
        assessments_list = repository_e.get_associated_assessments(course_id);
    }

    /**
     * Get course assessments live data.
     *
     * @param courseID the course id
     * @return the live data
     */
    public LiveData<List<entity_assessment>> get_course_assessments(int courseID){
        return repository_e.get_associated_assessments(courseID);
    }

    /**
     * Gets all assessments.
     *
     * @return the all assessments
     */
    public LiveData<List<entity_assessment>> getAllAssessments() { return all_assessments_list; };

    /**
     * Insert.
     *
     * @param entityassessment the entityassessment
     */
    public void insert(entity_assessment entityassessment) { repository_e.insert(entityassessment); }

    /**
     * Delete.
     *
     * @param entityassessment the entityassessment
     */
    public void delete(entity_assessment entityassessment) { repository_e.delete(entityassessment); }

    /**
     * Last id int.
     *
     * @return the int
     */
    public int lastID() { return all_assessments_list.getValue().size(); }
}
