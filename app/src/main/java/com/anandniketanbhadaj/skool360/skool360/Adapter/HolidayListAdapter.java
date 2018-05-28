package com.anandniketanbhadaj.skool360.skool360.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

    List<String> montharrayList;
    List<ExamDatum> monthwisedata;
    ExamModel holidayDataResponse;
    LayoutInflater linf;
    private Context mContext;
//    public HolidayListAdapter(Context mContext, List<String> montharrayList, List<ExamDatum> monthwisedata) {
//        this.mContext = mContext;
//        this.montharrayList = montharrayList;
//        this.monthwisedata = monthwisedata;
//
//    }

    public HolidayListAdapter(Context mContext, ExamModel holidayDataResponse, List<ExamDatum> monthwisedata) {
        this.mContext = mContext;
        this.holidayDataResponse = holidayDataResponse;
    }

    @Override
    public HolidayListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holiday_listitem, parent, false);
//        return new HolidayListAdapter.MyViewHolder(itemView);

        HolidayListAdapter.MyViewHolder holder = new HolidayListAdapter.MyViewHolder(itemView);

        Log.d("holder", "" + holder.getAdapterPosition());
        if (holder.getAdapterPosition() != -1) {
            List<ExamDatum> examData = holidayDataResponse.getFinalArray().get(holder.getAdapterPosition()).getData();


            if (examData.size() > 0) {
                for (int j = 0; j < examData.size(); j++) {

                    LayoutInflater inflater = LayoutInflater.from(mContext);

                    View view = inflater.inflate(R.layout.sub_holiday_item, null);

                    LinearLayout LinearData = (LinearLayout) view.findViewById(R.id.linear_click);
                    LinearData.setVisibility(View.VISIBLE);

//                LinearLayout LinearNoData=(LinearLayout)view.findViewById(R.id.norecord_Linear);
//                LinearNoData.setVisibility(View.GONE);

                    TextView norecordtxt = (TextView) view.findViewById(R.id.norecordtxt);
                    norecordtxt.setVisibility(View.GONE);

                    TextView name = (TextView) view.findViewById(R.id.holiday_name_txt);
                    name.setText(examData.get(j).getHoliday());

                    TextView date = (TextView) view.findViewById(R.id.holiday_date_txt);
                    date.setText(examData.get(j).getStartDate());

                    holder.main_holiday.addView(view);
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
        }
        return holder;

    }

    @Override
    public void onBindViewHolder(final HolidayListAdapter.MyViewHolder holder, int position) {

        List<ExamDatum> examData = holidayDataResponse.getFinalArray().get(position).getData();

        holder.month_name.setText(holidayDataResponse.getFinalArray().get(position).getMonthName());
        holder.month_image.setBackgroundResource(Integer.parseInt(holidayDataResponse.getFinalArray().get(position).getMonthImage()));
//        if (holidayDataResponse.getFinalArray().get(position).getData().size() > 0) {
////            holder.norecord_Linear.setVisibility(View.GONE);
////            holder.linear_click.setVisibility(View.VISIBLE);
//
//            String nameTxt = "", dateTxt = "";
//            for (int j = 0; j < examData.size(); j++) {
////                nameTxt +=examData.get(j).getHoliday();
////                dateTxt +=examData.get(j).getStartDate();
////                LayoutInflater inflater = LayoutInflater.from(mContext);
////                View v = inflater.inflate(R.layout.sub_holiday_item, null);
////                holder.main_holiday.addView(v);
////                LinearLayout linear = (LinearLayout) holder.main_holiday.findViewById(R.id.norecord_Linear);
////                LinearLayout linear1 = (LinearLayout) holder.main_holiday.findViewById(R.id.linear_click);
////                linear1.setVisibility(View.VISIBLE);
////                linear.setVisibility(View.GONE);
////                TextView name = (TextView) holder.main_holiday.findViewById(R.id.holiday_name_txt);
////                TextView date=(TextView)holder.main_holiday.findViewById(R.id.holiday_date_txt);
////                ImageView image=(ImageView)holder.main_holiday.findViewById(R.id.time_image) ;
////                date.setText(examData.get(j).getStartDate());
////                name.setText(examData.get(j).getHoliday());
//                LayoutInflater inflater = LayoutInflater.from(mContext);
//
//                View view = inflater.inflate(R.layout.sub_holiday_item, null);
//
//                LinearLayout LinearData=(LinearLayout)view.findViewById(R.id.linear_click);
//                LinearData.setVisibility(View.VISIBLE);
//
////                LinearLayout LinearNoData=(LinearLayout)view.findViewById(R.id.norecord_Linear);
////                LinearNoData.setVisibility(View.GONE);
//
//                TextView norecordtxt=(TextView)view.findViewById(R.id.norecordtxt);
//                norecordtxt.setVisibility(View.GONE);
//
//                TextView name = (TextView)view.findViewById(R.id.holiday_name_txt);
//                name.setText(examData.get(j).getHoliday());
//
//                TextView date = (TextView)view.findViewById(R.id.holiday_date_txt);
//                date.setText(examData.get(j).getStartDate());
//
//                holder.main_holiday.addView(view);
//            }
//        } else {
//            LayoutInflater inflater = LayoutInflater.from(mContext);
//
//            View view = inflater.inflate(R.layout.sub_holiday_item, null);
//
//            LinearLayout LinearData=(LinearLayout)view.findViewById(R.id.linear_click);
//            LinearData.setVisibility(View.GONE);
//
//
//            TextView norecordtxt=(TextView)view.findViewById(R.id.norecordtxt);
//            norecordtxt.setVisibility(View.VISIBLE);
//
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
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout month_image, norecord_Linear, linear_click, main_holiday;

        TextView month_name, holiday_name_txt, holiday_date_txt;

        public MyViewHolder(View view) {
            super(view);
//            holiday_date_txt = (TextView) view.findViewById(R.id.holiday_date_txt);
//            holiday_name_txt = (TextView) view.findViewById(R.id.holiday_name_txt);
            month_name = (TextView) view.findViewById(R.id.month_name);
            month_image = (LinearLayout) view.findViewById(R.id.month_image);
//            linear_click=(LinearLayout)view.findViewById(R.id.linear_click);
//            norecord_Linear = (LinearLayout) view.findViewById(R.id.norecord_Linear);
            main_holiday = (LinearLayout) view.findViewById(R.id.main_holiday);
        }
    }
}

