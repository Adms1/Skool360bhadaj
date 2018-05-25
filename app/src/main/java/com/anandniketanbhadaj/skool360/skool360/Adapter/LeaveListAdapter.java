package com.anandniketanbhadaj.skool360.skool360.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.ExamModel;

public class LeaveListAdapter extends RecyclerView.Adapter<LeaveListAdapter.MyViewHolder> {

    private Context mContext;
    ExamModel arrayList;

    public LeaveListAdapter(Context mContext, ExamModel arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView srno_txt, start_date_txt, reason_txt, end_date_txt, status_txt;

        public MyViewHolder(View view) {
            super(view);
            srno_txt = (TextView) view.findViewById(R.id.srno_txt);
            start_date_txt = (TextView) view.findViewById(R.id.start_date_txt);
            reason_txt = (TextView) view.findViewById(R.id.reason_txt);
            end_date_txt = (TextView) view.findViewById(R.id.end_date_txt);
            status_txt = (TextView) view.findViewById(R.id.status_txt);
        }
    }

    @Override
    public LeaveListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.leave_report_list_item, parent, false);
        return new LeaveListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LeaveListAdapter.MyViewHolder holder, int position) {

        String str = String.valueOf(position + 1);
        holder.srno_txt.setText(str);
        holder.status_txt.setText(arrayList.getFinalArray().get(position).getStatus());
        holder.start_date_txt.setText(arrayList.getFinalArray().get(position).getFromDate());
        holder.end_date_txt.setText(arrayList.getFinalArray().get(position).getToDate());
        holder.reason_txt.setText(arrayList.getFinalArray().get(position).getReason());

    }

    @Override
    public int getItemCount() {
        return arrayList.getFinalArray().size();
    }
}

