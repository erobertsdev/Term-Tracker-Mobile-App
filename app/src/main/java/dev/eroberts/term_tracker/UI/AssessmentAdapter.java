package dev.eroberts.term_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import dev.eroberts.term_tracker.AssessmentsDetailsActivity;
import dev.eroberts.term_tracker.Entities.entity_assessment;
import dev.eroberts.term_tracker.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessmentsTextView);
            itemView.setOnClickListener((v) -> {
                int position = getAdapterPosition();
                final entity_assessment current = mAssessments.get(position);
                Intent intent = new Intent(context, AssessmentsDetailsActivity.class);
                intent.putExtra("assessmentID", current.getAssessmentID());
                intent.putExtra("assessmentName", current.getAssessmentName());
                intent.putExtra("assessmentDate", current.getAssessmentDate());
                intent.putExtra("assessmentType", current.getAssessmentType());
                intent.putExtra("courseID", current.getCourseID());
                context.startActivity(intent);
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<entity_assessment> mAssessments;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessments_list_items, parent, false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentAdapter.AssessmentViewHolder holder, int position) {

        if(mAssessments != null) {
            final entity_assessment current = mAssessments.get(position);
            holder.assessmentItemView.setText(current.getAssessmentName());
        } else {
            holder.assessmentItemView.setText("No Word");
        }
    }

    public void setWords(List<entity_assessment> words) {
        mAssessments = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null)
            return mAssessments.size();
        else return 0;
    }
}
