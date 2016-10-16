package com.example.hackwesternapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ApplicantListAdapter extends RecyclerView.Adapter<ApplicantListAdapter.MyViewHolder> {

    private List<EncryptData> resumeList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.title);
            name = (TextView) view.findViewById(R.id.name);
        }

        @Override
        public void onClick(View view) {
            Context c = view.getContext();
            EncryptData ed = resumeList.get(getAdapterPosition());
            Intent intent = new Intent(c, ShowQrCodeActivity.class);
            intent.putExtra(RecruiteeMainActivity.EXTRA_QR_STRING, ed.getId());
            intent.putExtra(RecruiteeMainActivity.EXTRA_NAME, ed.getApplicantName());
            c.startActivity(intent);
        }
    }

    public ApplicantListAdapter(List<EncryptData> resumeList) {
        this.resumeList = resumeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.applicant_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EncryptData ed = resumeList.get(position);
        holder.title.setText(ed.getCompanyName());
        holder.name.setText(ed.getApplicantName());
    }

    @Override
    public int getItemCount() {
        return resumeList.size();
    }
}