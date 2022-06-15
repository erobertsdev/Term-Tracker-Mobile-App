package dev.eroberts.term_tracker.ViewModel;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.app.Application;
import dev.eroberts.term_tracker.Database.TermTrackerRepo;
import dev.eroberts.term_tracker.Entities.entity_course;
import java.util.List;

public class course_view_model extends AndroidViewModel {
    int term_id;
    private TermTrackerRepo repository_e;
    private LiveData<List<entity_course>> associated_courses_list;
    private LiveData<List<entity_course>> all_courses_list;

    public course_view_model(Application application, int termID){
        super(application);
        repository_e =new TermTrackerRepo(application);
        associated_courses_list = repository_e.get_associated_courses(termID);
    }

    public course_view_model(Application application){
        super(application);
        repository_e =new TermTrackerRepo(application);
        all_courses_list = repository_e.get_all_courses();
        associated_courses_list = repository_e.get_associated_courses(term_id);
    }

    public LiveData<List<entity_course>> getAssociatedCourses(int termID){
        return repository_e.get_associated_courses(termID);
    }

    public LiveData<List<entity_course>> getAllCourses() { return all_courses_list; };
    public void insert(entity_course entitycourse) { repository_e.insert(entitycourse); }
    public void delete(entity_course entitycourse) { repository_e.delete(entitycourse);}
    public int lastID() { return all_courses_list.getValue().size(); }
}
