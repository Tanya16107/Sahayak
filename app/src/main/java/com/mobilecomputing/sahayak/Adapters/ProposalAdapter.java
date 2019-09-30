package com.mobilecomputing.sahayak.Adapters;

import android.content.Context;
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

    public ProposalAdapter(Context context, List<Proposal> proposals)
    {
        this.mInflater=LayoutInflater.from(context);
        this.mProposals = proposals;
    }

    @Override
    public ProposalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.proposal_layout,parent,false);
        return new ProposalHolder(view);
    }

    @Override
    public void onBindViewHolder(ProposalHolder holder, int position) {
        Proposal proposal = mProposals.get(position);
        holder.bind(proposal);
    }

    @Override
    public int getItemCount() {
        return mProposals.size();
    }

    public class ProposalHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Proposal mProposal;

        private TextView mProposal_ID;
        private TextView mProposal_Skill;

        ProposalHolder(View itemview) {
            super(itemview);
            itemView.setOnClickListener(this);

            mProposal_ID = (TextView) itemView.findViewById(R.id.proposal_id);
            mProposal_Skill = (TextView) itemView.findViewById(R.id.proposal_skill);
        }

        public void bind(Proposal proposal) {
            mProposal = proposal;
            mProposal_ID.setText(""+mProposal.getID());
            mProposal_Skill.setText(mProposal.getSkill());
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public Proposal getItem(int id)
    {
        return mProposals.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
