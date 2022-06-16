package dev.eroberts.term_tracker.UI;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.content.Context;
import android.widget.TextView;
import dev.eroberts.term_tracker.Courses_Details_Activity;
import dev.eroberts.term_tracker.Entities.entity_course;
import dev.eroberts.term_tracker.R;
import java.util.List;

/**
 * The type Course adapter.
 */
public class course_adapter extends RecyclerView.Adapter<course_adapter.course_view_holder> {
    /**
     * The type Course view holder.
     */
    class course_view_holder extends RecyclerView.ViewHolder {
        private final TextView course_item_view;
        private course_view_holder(View itemView) {
            super(itemView);
            course_item_view = itemView.findViewById(R.id.courses_text_view_list);
            itemView.setOnClickListener((v) -> {
                int position = getAdapterPosition();
                final entity_course current = courses_list.get(position);
                Intent intent = new Intent(context, Courses_Details_Activity.class);
                intent.putExtra("courseID", current.getCourseID());
                intent.putExtra("courseName", current.getCourseName());
                intent.putExtra("courseStart", current.getCourseStart());
                intent.putExtra("courseEnd", current.getCourseEnd());
                intent.putExtra("courseStatus", current.getCourseStatus());
                intent.putExtra("courseNotes", current.getCourseNotes());
                intent.putExtra("termID", current.getTermID());
                context.startActivity(intent);
            });
        }
    }

    private List<entity_course> courses_list;
    private final LayoutInflater inflater_e;
    private final Context context;


    /**
     * Instantiates a new Course adapter.
     *
     * @param context the context
     */
    public course_adapter(Context context) {
        inflater_e = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public course_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater_e.inflate(R.layout.courses_list_items, parent, false);
        return new course_view_holder(itemView);
    }

    @Override
    public void onBindViewHolder(course_view_holder holder, int position) {
        if(courses_list != null) {
            final entity_course current = courses_list.get(position);
            holder.course_item_view.setText(current.getCourseName());
        } else {
            holder.course_item_view.setText("No Word");
        }
    }

    /**
     * Sets words.
     *
     * @param words the words
     */
    public void setWords(List<entity_course> words) {
        courses_list = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (courses_list != null)
            return courses_list.size();
        else return 0;
    }
}
