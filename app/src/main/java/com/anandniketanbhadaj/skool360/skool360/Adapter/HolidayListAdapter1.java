package com.anandniketanbhadaj.skool360.skool360.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.ExamDatum;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.ExamModel;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HolidayListAdapter1 extends RecyclerView.Adapter<HolidayListAdapter1.MyViewHolder> {

    ExamModel holidayDataResponse;

    List<HolidayListAdapter.MyViewHolder> myViewHoldersList;
    private Context mContext;
    private int recyclerViewHeight;

    public HolidayListAdapter1(Context mContext, ExamModel holidayDataResponse, List<ExamDatum> monthwisedata, int recyclerViewHeight) {
        this.mContext = mContext;
        this.holidayDataResponse = holidayDataResponse;
        myViewHoldersList = new ArrayList<>();
        this.recyclerViewHeight = recyclerViewHeight;
    }


    @Override
    public HolidayListAdapter1.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holiday_list_demo, parent, false);
        HolidayListAdapter1.MyViewHolder holder = new HolidayListAdapter1.MyViewHolder(itemView);
        LinearLayout linearLayout = (LinearLayout) itemView.findViewById(R.id.holiday_linear);


        if (viewType > 0) {
            for (int i = 0; i < viewType; i++) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.sub_holiday_item1, null, false);
                linearLayout.addView(layout);
            }
        } else {
            for (int i = 0; i < 1; i++) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.sub_holiday_item1, null, false);

                LinearLayout LinearData = (LinearLayout) layout.findViewById(R.id.holiday_linear);
                LinearData.setVisibility(View.GONE);

                LinearLayout LinearData1 = (LinearLayout) layout.findViewById(R.id.event_linear);
                LinearData1.setVisibility(View.GONE);
               View view=(View)layout.findViewById(R.id.view);
                view.setVisibility(View.GONE);

                TextView norecordtxt = (TextView) layout.findViewById(R.id.norecordtxt);
                norecordtxt.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER;

                norecordtxt.setLayoutParams(params);
                linearLayout.addView(layout);
            }
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final HolidayListAdapter1.MyViewHolder holder, final int position) {
        List<ExamDatum> examData = holidayDataResponse.getFinalArray().get(position).getData();
        String monthStr = holidayDataResponse.getFinalArray().get(position).getMonthName();
        holder.month_name.setText(holidayDataResponse.getFinalArray().get(position).getMonthName());// + " " +
        // holidayDataResponse.getFinalArray().get(position).getYear());

        holder.itemView.setTag(holder);
        Log.d("setext", "" + holder.holiday_linear.getChildCount());
        if (holder.holiday_linear.getChildCount() == examData.size()) {
            if (examData.size() > 0) {
                for (int i = 0; i < examData.size(); i++) {
                    View view = holder.holiday_linear.getChildAt(i);
                    TextView holidayname = (TextView) view.findViewById(R.id.holiday_name_txt);
                    TextView eventname = (TextView) view.findViewById(R.id.event_name_txt);
                    TextView holidaydate = (TextView) view.findViewById(R.id.holiday_date_txt);
                    TextView eventdate = (TextView) view.findViewById(R.id.event_date_txt);
                    TextView date = (TextView) view.findViewById(R.id.date_txt);
                    TextView edate = (TextView) view.findViewById(R.id.edate_txt);

                    holidayname.setText(examData.get(i).getHoliday());
                    eventname.setText(examData.get(i).getEvent());
                    if (!examData.get(i).getHolidayDate().equalsIgnoreCase("-")) {
                        String[] dateStr = examData.get(i).getHolidayDate().split("\\-");
                        String[] datesplit = dateStr[0].split("\\/");
                        holidaydate.setText(datesplit[0]);

                    } else {
                        holidaydate.setText("");
                    }

                    if (!examData.get(i).getEventDate().equalsIgnoreCase("-")) {
                        String[] eventdateStr = examData.get(i).getEventDate().split("//-");
                        String[] datesplit = eventdateStr[0].split("\\/");
                        eventdate.setText(datesplit[0]);
                    } else {
                        eventdate.setText("");
                    }

                    if (!examData.get(i).getHolidayDate().equalsIgnoreCase("-")) {
                        String[] holidayDatesplit = examData.get(i).getHolidayDate().split("\\-");

                        if (!holidayDatesplit[0].equalsIgnoreCase(holidayDatesplit[1])) {
                            TextView date_txt = (TextView) view.findViewById(R.id.date_txt);
                            date_txt.setVisibility(View.VISIBLE);
                            String inputPattern = "dd/MM/yyyy";
                            String outputPattern1 = "dd MMM";
                            String outputPattern2 = "dd MMM";

                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern1);
                            SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern2);

                            Date startdateTime = null, enddateTime = null;
                            String str = null, StartTimeStr = null, EndTimeStr = null;

                            try {
                                startdateTime = inputFormat.parse(holidayDatesplit[0]);
                                enddateTime = inputFormat.parse(holidayDatesplit[1]);
                                StartTimeStr = outputFormat.format(startdateTime);
                                EndTimeStr = outputFormat1.format(enddateTime);

                                Log.i("mini", "Converted Date Today:" + StartTimeStr + "=" + EndTimeStr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            date_txt.setText(StartTimeStr + " - " + EndTimeStr);

                        } else {
                            TextView date_txt = (TextView) view.findViewById(R.id.date_txt);
                            date_txt.setVisibility(View.GONE);
                        }
                    }
                    if (!examData.get(i).getEventDate().equalsIgnoreCase("-")) {
                        String[] eventDatesplit = examData.get(i).getEventDate().split("\\-");
                        if (!eventDatesplit[0].equalsIgnoreCase(eventDatesplit[1])) {
                            TextView edate_txt = (TextView) view.findViewById(R.id.edate_txt);
                            edate_txt.setVisibility(View.VISIBLE);
                            String inputPattern = "dd/MM/yyyy";
                            String outputPattern1 = "dd MMM";
                            String outputPattern2 = "dd MMM";

                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern1);
                            SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern2);

                            Date startdateTime = null, enddateTime = null;
                            String str = null, StartTimeStr = null, EndTimeStr = null;

                            try {
                                startdateTime = inputFormat.parse(eventDatesplit[0]);
                                enddateTime = inputFormat.parse(eventDatesplit[1]);
                                StartTimeStr = outputFormat.format(startdateTime);
                                EndTimeStr = outputFormat1.format(enddateTime);

                                Log.i("mini", "Converted Date Today:" + StartTimeStr + "=" + EndTimeStr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            edate_txt.setText(StartTimeStr + " - " + EndTimeStr);

                        } else {
                            TextView edate_txt = (TextView) view.findViewById(R.id.edate_txt);
                            edate_txt.setVisibility(View.GONE);
                        }
                    }

                }
            }
        }

        holder.itemView.setTag(holder);

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
        public TextView holiday_date_txt, holiday_name_txt, date_txt, event_date_txt, event_name_txt, edate_txt, month_name, norecordtxt,
                month_name1, month_name2, month_name3, month_name4, month_name5, month_name6, month_name7, month_name8, month_name9;
        public LinearLayout linear_click, main_holiday, holiday_event_linear, event_linear, holiday_linear, planner_linear;

        public MyViewHolder(View view) {
            super(view);
            linear_click = (LinearLayout) view.findViewById(R.id.linear_click);
            month_name = (TextView) view.findViewById(R.id.month_name);
            main_holiday = (LinearLayout) view.findViewById(R.id.main_holiday);
            holiday_event_linear = (LinearLayout) view.findViewById(R.id.holiday_event_linear);
            event_linear = (LinearLayout) view.findViewById(R.id.event_linear);
            planner_linear = (LinearLayout) view.findViewById(R.id.planner_linear);
            holiday_linear = (LinearLayout) view.findViewById(R.id.holiday_linear);
            norecordtxt = (TextView) view.findViewById(R.id.norecordtxt);

//            month_name1 = (TextView) view.findViewById(R.id.month_name1);
//            month_name2 = (TextView) view.findViewById(R.id.month_name2);
//            month_name3 = (TextView) view.findViewById(R.id.month_name3);
//            month_name4 = (TextView) view.findViewById(R.id.month_name4);
//            month_name5 = (TextView) view.findViewById(R.id.month_name5);
//            month_name6 = (TextView) view.findViewById(R.id.month_name6);
//            month_name7 = (TextView) view.findViewById(R.id.month_name7);
//            month_name8 = (TextView) view.findViewById(R.id.month_name8);
//            month_name9 = (TextView) view.findViewById(R.id.month_name9);

        }
    }
}


