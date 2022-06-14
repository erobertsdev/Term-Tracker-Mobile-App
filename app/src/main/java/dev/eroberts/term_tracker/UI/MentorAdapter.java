package dev.eroberts.term_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import dev.eroberts.term_tracker.Entities.entity_mentor;
import dev.eroberts.term_tracker.MentorsDetailsActivity;
import dev.eroberts.term_tracker.R;

import java.util.List;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.MentorViewHolder> {

    class MentorViewHolder extends RecyclerView.ViewHolder {
        private final TextView mentorItemView;

        private MentorViewHolder(View itemView) {
            super(itemView);
            mentorItemView = itemView.findViewById(R.id.mentorsTextView);
            itemView.setOnClickListener((v) -> {
                int position = getAdapterPosition();
                final entity_mentor current = mMentors.get(position);
                Intent intent = new Intent(context, MentorsDetailsActivity.class);
                intent.putExtra("mentorID", current.getMentorID());
                intent.putExtra("mentorName", current.getMentorName());
                intent.putExtra("mentorEmail", current.getMentorEmail());
                intent.putExtra("mentorPhone", current.getMentorPhone());
                intent.putExtra("courseID", current.getCourseID());
                context.startActivity(intent);
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<entity_mentor> mMentors;

    public MentorAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public MentorAdapter.MentorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.mentors_list_items, parent, false);
        return new MentorAdapter.MentorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MentorAdapter.MentorViewHolder holder, int position) {

        if(mMentors != null) {
            final entity_mentor current = mMentors.get(position);
            holder.mentorItemView.setText(current.getMentorName());
        } else {
            holder.mentorItemView.setText("No Word");
        }
    }

    public void setWords(List<entity_mentor> words) {
        mMentors = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mMentors != null)
            return mMentors.size();
        else return 0;
    }
}
