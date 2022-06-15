package dev.eroberts.term_tracker.UI;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import dev.eroberts.term_tracker.Entities.entity_term;
import dev.eroberts.term_tracker.R;
import dev.eroberts.term_tracker.TermsDetailsActivity;
import java.util.List;

public class term_adapter extends RecyclerView.Adapter<term_adapter.term_view_holder> {
    class term_view_holder extends RecyclerView.ViewHolder {
        private final TextView termItemView;
        private term_view_holder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.termsTextView);
            itemView.setOnClickListener((v) -> {
                int position = getAdapterPosition();
                final entity_term current = terms_list.get(position);
                Intent intent = new Intent(context, TermsDetailsActivity.class);
                intent.putExtra("termID", current.getTermID());
                intent.putExtra("termName", current.getTermName());
                intent.putExtra("termStart", current.getTermStart());
                intent.putExtra("termEnd", current.getTermEnd());
                context.startActivity(intent);
            });
        }
    }

    private List<entity_term> terms_list;
    private final LayoutInflater inflater_e;
    private final Context context;


    public term_adapter(Context context) {
        inflater_e = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public term_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater_e.inflate(R.layout.terms_list_items, parent, false);
        return new term_view_holder(itemView);
    }

    @Override
    public void onBindViewHolder(term_view_holder holder, int position) {
        if(terms_list != null) {
            final entity_term current = terms_list.get(position);
            holder.termItemView.setText(current.getTermName());
        } else {
            holder.termItemView.setText("No Word");
        }
    }

    public void setWords(List<entity_term> words) {
        terms_list = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (terms_list != null)
            return terms_list.size();
        else return 0;
    }
}
