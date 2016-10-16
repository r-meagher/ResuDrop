package com.example.hackwesternapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static com.example.hackwesternapp.RecruiterMainActivity.PDF_URL;

public class RecruiterListAdapter extends RecyclerView.Adapter<RecruiterListAdapter.MyViewHolder> {

    private List<ApplicantData> applicantList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView rating;
        public TextView name;
        public TextView email;
        public ImageView favourite;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            rating = (TextView) view.findViewById(R.id.rating);
            name = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.email);
            favourite = (ImageView) view.findViewById(R.id.favourite);
        }

        @Override
        public void onClick(View view) {
            RecruiterMainActivity c = (RecruiterMainActivity) view.getContext();
            c.clickedItem = getAdapterPosition();
            c.viewApplicant(applicantList.get(getAdapterPosition()), 2);
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

        boolean favourite = applicantList.get(position).isFavourite();

        if (favourite)
            holder.favourite.setImageResource(RecruiterMainActivity.fav);
        else
            holder.favourite.setImageResource(RecruiterMainActivity.notfav);
    }

    @Override
    public int getItemCount() {
        return applicantList.size();
    }
}