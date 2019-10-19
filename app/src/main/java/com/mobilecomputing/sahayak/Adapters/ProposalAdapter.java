package com.mobilecomputing.sahayak.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobilecomputing.sahayak.JavaClasses.ProposalLab;
import com.mobilecomputing.sahayak.JavaClasses.Proposal;

import java.util.List;

import com.mobilecomputing.sahayak.R;

public class ProposalAdapter extends RecyclerView.Adapter<ProposalAdapter.ProposalHolder> {

    private List<Proposal> mProposals;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public ProposalAdapter(Context context, List<Proposal> proposals) {
        this.mInflater = LayoutInflater.from(context);
        this.mProposals = proposals;
    }

    @Override
    public ProposalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.proposal_layout, parent, false);
        return new ProposalHolder(view);
    }

    @Override
    public void onBindViewHolder(ProposalHolder holder, int position) {
        Log.d("ProposalAdapter", "Type of mProposalElement " + mProposals.get(position).getSkill());
        Proposal proposal = mProposals.get(position);
        holder.bind(proposal);
    }

    @Override
    public int getItemCount() {
        return mProposals.size();
    }

    public Proposal getItem(int id) {
        return mProposals.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ProposalHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Proposal mProposal;

        private TextView mProposal_Rating;
        private TextView mProposal_Skill;
        private TextView mProposal_TimeWindow;

        ProposalHolder(View itemview) {
            super(itemview);
            itemView.setOnClickListener(this);

            mProposal_Rating = (TextView) itemView.findViewById(R.id.proposal_rating);
            mProposal_Skill = (TextView) itemView.findViewById(R.id.proposal_skill);
            mProposal_TimeWindow = (TextView) itemview.findViewById(R.id.proposal_time_window);
        }

        public void bind(Proposal proposal) {
            mProposal = proposal;
            mProposal_Rating.setText("Instructor Rating: " + mProposal.getRating());
            mProposal_Skill.setText(mProposal.getSkill());
            mProposal_TimeWindow.setText("Time Window: " + mProposal.getTimeWindow());
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
