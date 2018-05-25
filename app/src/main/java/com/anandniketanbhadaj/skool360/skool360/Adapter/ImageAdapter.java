package com.anandniketanbhadaj.skool360.skool360.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public Integer[] mThumbIds = {
            R.drawable.attendance, R.drawable.home_work, R.drawable.class_work,
            R.drawable.timetable, R.drawable.unit_test, R.drawable.results,
            R.drawable.report_card,R.drawable.fees_1, R.drawable.imprest,
            R.drawable.canteen, R.drawable.ptm,R.drawable.principalmessage,
            R.drawable.circular,R.drawable.circular
    };

//    public String[] mThumbNames = {"Attendance",  "Homework", "Classwork", "Timetable", "Unit Test", "Results", "Report Card",
//            "Fees", "Imprest", "Canteen", "PTM", "Message","Circular"};

    public String[] mThumbNames = {"Attendance",  "Homework", "Classwork", "Timetable", "Exam Syllabus", "Results", "Report Card",
            "Fees", "Imprest","Holiday","Leave Application","Circular","Gallery","Suggestion"};

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgGridOptions = null;
        TextView txtGridOptionsName = null;

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.gridview_cell, null);

        imgGridOptions = (ImageView) convertView.findViewById(R.id.imgGridOptions);
        txtGridOptionsName = (TextView) convertView.findViewById(R.id.txtGridOptionsName);

        imgGridOptions.setImageResource(mThumbIds[position]);
        txtGridOptionsName.setText(mThumbNames[position]);
        return convertView;
    }

}