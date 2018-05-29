package com.anandniketanbhadaj.skool360.skool360.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Interfacess.onViewClick;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.ExamDatum;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.ExamFinalArray;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.ExamModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

public class HolidayListAdapter extends RecyclerView.Adapter<HolidayListAdapter.MyViewHolder> {

    ExamModel holidayDataResponse;
    LayoutInflater linf;
    private Context mContext;


    public HolidayListAdapter(Context mContext, ExamModel holidayDataResponse, List<ExamDatum> monthwisedata) {
        this.mContext = mContext;
        this.holidayDataResponse = holidayDataResponse;
    }

    @Override
    public HolidayListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holiday_listitem, parent, false);
        HolidayListAdapter.MyViewHolder holder = new HolidayListAdapter.MyViewHolder(itemView);

        if (viewType > 0) {
            for (int i = 0; i < viewType; i++) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                View view = inflater.inflate(R.layout.sub_holiday_item, null);
//                List<ExamDatum> examData = holidayDataResponse.getFinalArray().get(holder.getAdapterPosition()).getData();
//                for (int j = 0; j < examData.size(); j++) {
////
//                    LayoutInflater inflater = LayoutInflater.from(mContext);
//
//                    View view = inflater.inflate(R.layout.sub_holiday_item, null);
//
                    LinearLayout LinearData = (LinearLayout) view.findViewById(R.id.linear_click);
                    LinearData.setVisibility(View.VISIBLE);

                    TextView norecordtxt = (TextView) view.findViewById(R.id.norecordtxt);
                    norecordtxt.setVisibility(View.GONE);
//
                     TextView name = (TextView) view.findViewById(R.id.holiday_name_txt);
//                    name.setText(holidayDataResponse.getFinalArray().get(0).getData().get(0).getHoliday());
//
                     TextView date = (TextView) view.findViewById(R.id.holiday_date_txt);
//                    date.setText(examData.get(j).getStartDate());
//
                holder.main_holiday.addView(view);
//                }
            }
        } else {
            LayoutInflater inflater = LayoutInflater.from(mContext);

            View view = inflater.inflate(R.layout.sub_holiday_item, null);

            LinearLayout LinearData = (LinearLayout) view.findViewById(R.id.linear_click);
            LinearData.setVisibility(View.GONE);


            TextView norecordtxt = (TextView) view.findViewById(R.id.norecordtxt);
            norecordtxt.setVisibility(View.VISIBLE);

            holder.main_holiday.addView(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final HolidayListAdapter.MyViewHolder holder, int position) {

        List<ExamDatum> examData = holidayDataResponse.getFinalArray().get(position).getData();

        holder.month_name.setText(holidayDataResponse.getFinalArray().get(position).getMonthName());
        holder.month_image.setBackgroundResource(Integer.parseInt(holidayDataResponse.getFinalArray().get(position).getMonthImage()));


        Log.d("setext",""+holder.main_holiday.getChildCount());
//        if(holder.main_holiday.getChildCount() == examData.size()){
        if(examData.size()>0){
            for(int i=0;i<examData.size();i++){
                View view = holder.main_holiday.getChildAt(i);
              TextView name=(TextView)view.findViewById(R.id.holiday_name_txt);
              name.setText(examData.get(i).getHoliday());
            }
        }

//        if (holidayDataResponse.getFinalArray().get(position).getData().size() > 0) {

//            for (int j = 0; j < examData.size(); j++) {
//
//                LayoutInflater inflater = LayoutInflater.from(mContext);
//
//                View view = inflater.inflate(R.layout.sub_holiday_item, null);
//
//                LinearLayout LinearData = (LinearLayout) view.findViewById(R.id.linear_click);
//                LinearData.setVisibility(View.VISIBLE);
//
//                TextView norecordtxt = (TextView) view.findViewById(R.id.norecordtxt);
//                norecordtxt.setVisibility(View.GONE);
//
//                TextView name = (TextView) view.findViewById(R.id.holiday_name_txt);
//                name.setText(examData.get(j).getHoliday());
//
//                TextView date = (TextView) view.findViewById(R.id.holiday_date_txt);
//                date.setText(examData.get(j).getStartDate());
//
//                holder.main_holiday.addView(view);
//
//            }
//        }
// else {
//            LayoutInflater inflater = LayoutInflater.from(mContext);
//
//            View view = inflater.inflate(R.layout.sub_holiday_item, null);
//
//            LinearLayout LinearData = (LinearLayout) view.findViewById(R.id.linear_click);
//            LinearData.setVisibility(View.GONE);
//
//            TextView norecordtxt = (TextView) view.findViewById(R.id.norecordtxt);
//            norecordtxt.setVisibility(View.VISIBLE);
//
//            holder.main_holiday.addView(view);
//        }

    }

    @Override
    public int getItemCount() {
        return holidayDataResponse.getFinalArray().size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return holidayDataResponse.getFinalArray().get(position).getData().size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout month_image, norecord_Linear, linear_click, main_holiday;

        TextView month_name, holiday_name_txt, holiday_date_txt;

        public MyViewHolder(View view) {
            super(view);
            month_name = (TextView) view.findViewById(R.id.month_name);
            month_image = (LinearLayout) view.findViewById(R.id.month_image);
            main_holiday = (LinearLayout) view.findViewById(R.id.main_holiday);
        }
    }
}

