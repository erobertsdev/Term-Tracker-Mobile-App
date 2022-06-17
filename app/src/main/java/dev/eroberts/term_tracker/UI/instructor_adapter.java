package dev.eroberts.term_tracker.UI;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import dev.eroberts.term_tracker.Entities.entity_instructor;
import dev.eroberts.term_tracker.Instructors_Detail_Activity;
import dev.eroberts.term_tracker.R;
import java.util.List;

/**
 * The type Instructor adapter.
 */
public class instructor_adapter extends RecyclerView.Adapter<instructor_adapter.instructor_view_holder> {
    /**
     * The type Instructor view holder.
     */
    class instructor_view_holder extends RecyclerView.ViewHolder {
        private final TextView instructor_item_view;
        private instructor_view_holder(View itemView) {
            super(itemView);
            instructor_item_view = itemView.findViewById(R.id.instructors_text_view_list);
            itemView.setOnClickListener((v) -> {
                int position = getAdapterPosition();
                final entity_instructor current = instructors_list.get(position);
                Intent intent = new Intent(context, Instructors_Detail_Activity.class);
                intent.putExtra("mentorID", current.getMentorID());
                intent.putExtra("mentorName", current.getMentorName());
                intent.putExtra("mentorEmail", current.getMentorEmail());
                intent.putExtra("mentorPhone", current.getMentorPhone());
                intent.putExtra("courseID", current.getCourseID());
                context.startActivity(intent);
            });
        }
    }

    private List<entity_instructor> instructors_list;
    private final LayoutInflater inflater_e;
    private final Context context;


    /**
     * Instantiates a new Inscructor adapter.
     *
     * @param context the context
     */
    public instructor_adapter(Context context) {
        inflater_e = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public instructor_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater_e.inflate(R.layout.instructors_list_items, parent, false);
        return new instructor_view_holder(itemView);
    }

    @Override
    public void onBindViewHolder(instructor_view_holder holder, int position) {
        if(instructors_list != null) {
            final entity_instructor current = instructors_list.get(position);
            holder.instructor_item_view.setText(current.getMentorName());
        } else {
            holder.instructor_item_view.setText("No Word");
        }
    }

    /**
     * Sets words.
     *
     * @param words the words
     */
    public void setWords(List<entity_instructor> words) {
        instructors_list = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (instructors_list != null)
            return instructors_list.size();
        else return 0;
    }
}
