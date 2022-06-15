package dev.eroberts.term_tracker.UI;
import android.content.Intent;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import dev.eroberts.term_tracker.AssessmentsDetailsActivity;
import dev.eroberts.term_tracker.Entities.entity_assessment;
import dev.eroberts.term_tracker.R;

import java.util.List;

/**
 * The type Assessment adapter.
 */
public class assessment_adapter extends RecyclerView.Adapter<assessment_adapter.AssessmentViewHolder> {

    /**
     * The type Assessment view holder.
     */
    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessment_item_view;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessment_item_view = itemView.findViewById(R.id.assessments_text_view);
            itemView.setOnClickListener((v) -> {
                int position = getAdapterPosition();
                final entity_assessment current = assessments_list.get(position);
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

    private final LayoutInflater inflater_e;
    private final Context context;
    private List<entity_assessment> assessments_list;

    /**
     * Instantiates a new Assessment adapter.
     *
     * @param context the context
     */
    public assessment_adapter(Context context) {
        inflater_e = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public assessment_adapter.AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater_e.inflate(R.layout.assessments_list_items, parent, false);
        return new assessment_adapter.AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(assessment_adapter.AssessmentViewHolder holder, int position) {

        if(assessments_list != null) {
            final entity_assessment current = assessments_list.get(position);
            holder.assessment_item_view.setText(current.getAssessmentName());
        } else {
            holder.assessment_item_view.setText("No Word");
        }
    }

    /**
     * Sets words.
     *
     * @param words the words
     */
    public void setWords(List<entity_assessment> words) {
        assessments_list = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (assessments_list != null)
            return assessments_list.size();
        else return 0;
    }
}
