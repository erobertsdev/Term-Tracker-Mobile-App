package dev.eroberts.term_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import dev.eroberts.term_tracker.Entities.entity_term;
import dev.eroberts.term_tracker.R;
import dev.eroberts.term_tracker.TermsDetailsActivity;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.termsTextView);
            itemView.setOnClickListener((v) -> {
                int position = getAdapterPosition();
                final entity_term current = mTerms.get(position);
                Intent intent = new Intent(context, TermsDetailsActivity.class);
                intent.putExtra("termID", current.getTermID());
                intent.putExtra("termName", current.getTermName());
                intent.putExtra("termStart", current.getTermStart());
                intent.putExtra("termEnd", current.getTermEnd());
                context.startActivity(intent);
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<entity_term> mTerms;

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.terms_list_items, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {

        if(mTerms != null) {
            final entity_term current = mTerms.get(position);
            holder.termItemView.setText(current.getTermName());
        } else {
            holder.termItemView.setText("No Word");
        }
    }

    public void setWords(List<entity_term> words) {
        mTerms = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    }
}
