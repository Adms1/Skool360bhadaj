package com.anandniketanbhadaj.skool360.skool360.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HolidayEventListAdapter extends RecyclerView.Adapter<HolidayEventListAdapter.MyViewHolder> {

    ExamModel holidayDataResponse;

    List<HolidayListAdapter.MyViewHolder> myViewHoldersList;
    private Context mContext;

    public HolidayEventListAdapter(Context mContext, ExamModel holidayDataResponse, List<ExamDatum> monthwisedata, int recyclerViewHeight) {
        this.mContext = mContext;
        this.holidayDataResponse = holidayDataResponse;
        myViewHoldersList = new ArrayList<>();
    }

    @Override
    public HolidayEventListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holiday_list_demo, parent, false);
        HolidayEventListAdapter.MyViewHolder holder = new HolidayEventListAdapter.MyViewHolder(itemView);

        List<ExamDatum> holidayEventSchedule = holidayDataResponse.getFinalArray().get(viewType).getData();

        LinearLayout holidayLinear = (LinearLayout) itemView.findViewById(R.id.holiday_linear);
        LinearLayout eventLinear = (LinearLayout) itemView.findViewById(R.id.event_linear);
        if (holidayEventSchedule.size() == 0) {
            // No Event found
            LayoutInflater inflater = LayoutInflater.from(mContext);
            LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_event, null, false);
            TextView textView = (TextView) layout.findViewById(R.id.event_name_txt);
            textView.setText("No event found");
            eventLinear.addView(layout);

            // No Holiday found
            LayoutInflater holidayInflater = LayoutInflater.from(mContext);
            LinearLayout holidayLayout = (LinearLayout) holidayInflater.inflate(R.layout.item_holiday, null, false);
            TextView holiTextView = (TextView) holidayLayout.findViewById(R.id.holiday_name_txt);
            holiTextView.setText("No holiday found");
            holidayLinear.addView(holidayLayout);
        } else {
            for (int i = 0; i < holidayEventSchedule.size(); i++) {
                if (!holidayEventSchedule.get(i).getEvent().equals("")) {
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_event, null, false);
                    TextView textView = (TextView) layout.findViewById(R.id.event_name_txt);
                    TextView edate_txt=(TextView)layout.findViewById(R.id.edate_txt) ;
                    TextView event_date_txt=(TextView)layout.findViewById(R.id.event_date_txt);

                    if (!holidayEventSchedule.get(i).getEventDate().equalsIgnoreCase("-")) {
                        String[] eventdateStr = holidayEventSchedule.get(i).getEventDate().split("//-");
                        String[] datesplit = eventdateStr[0].split("\\/");
                        event_date_txt.setText(datesplit[0]);
                    } else {
                        event_date_txt.setText("");
                    }
                    if (!holidayEventSchedule.get(i).getEventDate().equalsIgnoreCase("-")) {
                        String[] eventDatesplit = holidayEventSchedule.get(i).getEventDate().split("\\-");
                        if (!eventDatesplit[0].equalsIgnoreCase(eventDatesplit[1])) {
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
                            edate_txt.setVisibility(View.GONE);
                        }
                    }


                    textView.setText(holidayEventSchedule.get(i).getEvent());
                    eventLinear.addView(layout);
                }
                if (!holidayEventSchedule.get(i).getHoliday().equals("")){
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_holiday, null, false);
                    TextView textView = (TextView) layout.findViewById(R.id.holiday_name_txt);
                    TextView holiday_date_txt=(TextView)layout.findViewById(R.id.holiday_date_txt) ;
                    TextView date_txt=(TextView)layout.findViewById(R.id.date_txt) ;

                    textView.setText(holidayEventSchedule.get(i).getHoliday());
                    if (!holidayEventSchedule.get(i).getHolidayDate().equalsIgnoreCase("-")) {
                        String[] dateStr = holidayEventSchedule.get(i).getHolidayDate().split("\\-");
                        String[] datesplit = dateStr[0].split("\\/");
                        holiday_date_txt.setText(datesplit[0]);

                    } else {
                        holiday_date_txt.setText("");
                    }
                    if (!holidayEventSchedule.get(i).getHolidayDate().equalsIgnoreCase("-")) {
                        String[] holidayDatesplit = holidayEventSchedule.get(i).getHolidayDate().split("\\-");

                        if (!holidayDatesplit[0].equalsIgnoreCase(holidayDatesplit[1])) {
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
                            date_txt.setVisibility(View.GONE);
                        }
                    }
                    holidayLinear.addView(layout);
                }
            }

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final HolidayEventListAdapter.MyViewHolder holder, final int position) {
        holder.month_name.setText(holidayDataResponse.getFinalArray().get(position).getMonthName());
    }

    @Override
    public int getItemCount() {
        return holidayDataResponse.getFinalArray().size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView month_name;
        public LinearLayout holiday_linear, event_linear;

        public MyViewHolder(View view) {
            super(view);

            month_name = (TextView) view.findViewById(R.id.month_name);
            event_linear = (LinearLayout) view.findViewById(R.id.event_linear);
            holiday_linear = (LinearLayout) view.findViewById(R.id.holiday_linear);

        }
    }
}


