package com.example.hackwesternapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecruiterListAdapter extends RecyclerView.Adapter<RecruiterListAdapter.MyViewHolder> {

    private List<ApplicantData> applicantList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView rating;
        public TextView name;
        public TextView email;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            rating = (TextView) view.findViewById(R.id.rating);
            name = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.email);
        }

        @Override
        public void onClick(View view) {
            Context c = view.getContext();
            ApplicantData ad = applicantList.get(getAdapterPosition());
        }
    }

    public RecruiterListAdapter(List<ApplicantData> applicantList) {
        this.applicantList = applicantList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recruiter_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ApplicantData ad = applicantList.get(position);
        holder.rating.setText((String) ((ad.getRating() == 0) ? "" : ad.getRating()));
        holder.name.setText(ad.getName());
        holder.email.setText(ad.getEmail());
    }

    @Override
    public int getItemCount() {
        return applicantList.size();
    }
}