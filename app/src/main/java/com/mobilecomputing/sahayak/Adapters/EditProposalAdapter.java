package com.mobilecomputing.sahayak.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobilecomputing.sahayak.JavaClasses.EditProposalLab;
import com.mobilecomputing.sahayak.JavaClasses.Proposal;
import com.mobilecomputing.sahayak.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;

public class EditProposalAdapter extends RecyclerView.Adapter<EditProposalAdapter.ProposalHolder> {
    private List<Proposal> mProposals;
    private List<Proposal> store;
    private LayoutInflater mInflater;
    private EditProposalAdapter.ItemClickListener mClickListener;
    private Context context;
    private EditProposalLab editProposalLab;

    public EditProposalAdapter(Context context, List<Proposal> proposals,EditProposalLab editProposalLab) {
        this.mInflater = LayoutInflater.from(context);
        this.mProposals = proposals;
        store=new ArrayList<Proposal>();
        store.addAll(this.mProposals);
        this.context =context;
        this.editProposalLab = editProposalLab;
    }

    @Override
    public ProposalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.proposal_layout, parent, false);
        return new ProposalHolder(view);
    }

    @Override
    public void onBindViewHolder(ProposalHolder holder, int position) {
        Log.d("EditProposalAdapter", "Type of mProposalElement " + mProposals.get(position).getSkill());
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

    public void setClickListener(EditProposalAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public Context getContext() {
        return context;
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
    public void removeProposal(int position){
        Proposal proposal=mProposals.remove(position);
        editProposalLab.deleteProposalsIndex(proposal);
    }
    public void restoreItem(Proposal proposal, int index){
        mProposals.add(index,proposal);
        editProposalLab.AddProposal(proposal);

    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mProposals.clear();
        if (charText.length() == 0) {
            mProposals.addAll(store);
        } else {
            for (Proposal wp : store) {
                if (wp.getSkill().toLowerCase(Locale.getDefault()).contains(charText)
                        || wp.getCategory().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mProposals.add(wp);
                    Log.d("Filter","Filtered:"+wp.getSkill());
                }
            }
//            MenteeOptionsActivity.proposals=tmp;
//            mProposals = MenteeOptionsActivity.proposals;
        }
        notifyDataSetChanged();
        Log.d("Filter",store.size()+" "+mProposals.size());
    }
}
