package com.example.c196_degree_tracker.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_degree_tracker.Entities.MentorEntity;
import com.example.c196_degree_tracker.MentorsActivity;
import com.example.c196_degree_tracker.MentorsDetailsActivity;
import com.example.c196_degree_tracker.R;

import java.util.List;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.MentorViewHolder> {

    class MentorViewHolder extends RecyclerView.ViewHolder {
        private final TextView mentorItemView;

        private MentorViewHolder(View itemView) {
            super(itemView);
            mentorItemView = itemView.findViewById(R.id.mentorsTextView);
            itemView.setOnClickListener((v) -> {
                int position = getAdapterPosition();
                final MentorEntity current = mMentors.get(position);
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
    private List<MentorEntity> mMentors;

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
            final MentorEntity current = mMentors.get(position);
            holder.mentorItemView.setText(current.getMentorName());
        } else {
            holder.mentorItemView.setText("No Word");
        }
    }

    public void setWords(List<MentorEntity> words) {
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
