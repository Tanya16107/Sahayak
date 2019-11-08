package com.mobilecomputing.sahayak.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobilecomputing.sahayak.Activities.MenteeOptionsActivity;
import com.mobilecomputing.sahayak.JavaClasses.Proposal;
import com.mobilecomputing.sahayak.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProposalAdapter extends RecyclerView.Adapter<ProposalAdapter.ProposalHolder> {

    private List<Proposal> mProposals;
    private List<Proposal> store;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    final SimpleDateFormat dateFormat = new SimpleDateFormat("E, ");

    public ProposalAdapter(Context context, List<Proposal> proposals) {
        this.mInflater = LayoutInflater.from(context);
        this.mProposals = proposals;
        store=new ArrayList<Proposal>();
        store.addAll(this.mProposals);
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

        private TextView mProposal_Skill;
        private TextView mProposal_TimeWindow;

        ProposalHolder(View itemview) {
            super(itemview);
            itemView.setOnClickListener(this);

            mProposal_Skill = (TextView) itemView.findViewById(R.id.proposal_skill);
            mProposal_TimeWindow = (TextView) itemview.findViewById(R.id.proposal_time_window);
        }

        public void bind(Proposal proposal) {
            mProposal = proposal;
            mProposal_Skill.setText(mProposal.getSkill());
            String[] splitWindow = mProposal.getTimeWindow().split("-");
            mProposal_TimeWindow.setText(dateFormat.format(mProposal.getStartDate()) + splitWindow[0]+" - "+splitWindow[1]);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
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
