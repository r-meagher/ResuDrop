package com.example.hackwesternapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ApplicantListAdapter extends RecyclerView.Adapter<ApplicantListAdapter.MyViewHolder> {

    private List<EncryptData> resumeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
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
        holder.title.setText(ed.getName());
    }

    @Override
    public int getItemCount() {
        return resumeList.size();
    }
}