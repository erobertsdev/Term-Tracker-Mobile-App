package dev.eroberts.term_tracker.UI;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import dev.eroberts.term_tracker.Entities.entity_mentor;
import dev.eroberts.term_tracker.Mentors_Details_Activity;
import dev.eroberts.term_tracker.R;
import java.util.List;

/**
 * The type Mentor adapter.
 */
public class mentor_adapter extends RecyclerView.Adapter<mentor_adapter.mentor_view_holder> {
    /**
     * The type Mentor view holder.
     */
    class mentor_view_holder extends RecyclerView.ViewHolder {
        private final TextView mentor_item_view;
        private mentor_view_holder(View itemView) {
            super(itemView);
            mentor_item_view = itemView.findViewById(R.id.mentors_text_view_list);
            itemView.setOnClickListener((v) -> {
                int position = getAdapterPosition();
                final entity_mentor current = mentors_list.get(position);
                Intent intent = new Intent(context, Mentors_Details_Activity.class);
                intent.putExtra("mentorID", current.getMentorID());
                intent.putExtra("mentorName", current.getMentorName());
                intent.putExtra("mentorEmail", current.getMentorEmail());
                intent.putExtra("mentorPhone", current.getMentorPhone());
                intent.putExtra("courseID", current.getCourseID());
                context.startActivity(intent);
            });
        }
    }

    private List<entity_mentor> mentors_list;
    private final LayoutInflater inflater_e;
    private final Context context;


    /**
     * Instantiates a new Mentor adapter.
     *
     * @param context the context
     */
    public mentor_adapter(Context context) {
        inflater_e = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public mentor_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater_e.inflate(R.layout.mentors_list_items, parent, false);
        return new mentor_view_holder(itemView);
    }

    @Override
    public void onBindViewHolder(mentor_view_holder holder, int position) {
        if(mentors_list != null) {
            final entity_mentor current = mentors_list.get(position);
            holder.mentor_item_view.setText(current.getMentorName());
        } else {
            holder.mentor_item_view.setText("No Word");
        }
    }

    /**
     * Sets words.
     *
     * @param words the words
     */
    public void setWords(List<entity_mentor> words) {
        mentors_list = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mentors_list != null)
            return mentors_list.size();
        else return 0;
    }
}
