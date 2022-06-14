package com.example.c196_degree_tracker.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_degree_tracker.Entities.TermEntity;
import com.example.c196_degree_tracker.TermsActivity;
import com.example.c196_degree_tracker.R;
import com.example.c196_degree_tracker.TermsDetailsActivity;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.termsTextView);
            itemView.setOnClickListener((v) -> {
                int position = getAdapterPosition();
                final TermEntity current = mTerms.get(position);
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
    private List<TermEntity> mTerms;

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
            final TermEntity current = mTerms.get(position);
            holder.termItemView.setText(current.getTermName());
        } else {
            holder.termItemView.setText("No Word");
        }
    }

    public void setWords(List<TermEntity> words) {
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
