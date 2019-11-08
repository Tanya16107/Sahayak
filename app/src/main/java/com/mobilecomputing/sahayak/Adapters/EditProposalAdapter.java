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

import java.text.SimpleDateFormat;
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
    final SimpleDateFormat dateFormat = new SimpleDateFormat("E, ");

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
    public void removeProposal(Proposal proposal,int position){
        editProposalLab.deleteProposalsIndex(proposal);
        mProposals.remove(position);
        notifyItemRemoved(position);

    }
    public void restoreItem(Proposal proposal, int position){
        editProposalLab.AddProposal(proposal);
        mProposals.add(position,proposal);
        notifyItemInserted(position);

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
