package dev.eroberts.term_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import dev.eroberts.term_tracker.CoursesDetailsActivity;
import dev.eroberts.term_tracker.Entities.entity_course;
import dev.eroberts.term_tracker.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.coursesTextView);
            itemView.setOnClickListener((v) -> {
                int position = getAdapterPosition();
                final entity_course current = mCourses.get(position);
                Intent intent = new Intent(context, CoursesDetailsActivity.class);
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

    private final LayoutInflater mInflater;
    private final Context context;
    private List<entity_course> mCourses;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.courses_list_items, parent, false);
        return new CourseAdapter.CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseAdapter.CourseViewHolder holder, int position) {

        if(mCourses != null) {
            final entity_course current = mCourses.get(position);
            holder.courseItemView.setText(current.getCourseName());
        } else {
            holder.courseItemView.setText("No Word");
        }
    }

    public void setWords(List<entity_course> words) {
        mCourses = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }
}
