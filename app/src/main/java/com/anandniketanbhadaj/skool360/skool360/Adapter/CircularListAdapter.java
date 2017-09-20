package com.anandniketanbhadaj.skool360.skool360.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Models.CircularModel;

import java.util.ArrayList;

public class CircularListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<CircularModel> circularModels = new ArrayList<>();

    // Constructor
    public CircularListAdapter(Context c, ArrayList<CircularModel> circularModels) {
        mContext = c;
        this.circularModels = circularModels;
    }

    @Override
    public int getCount() {
        return circularModels.size();
    }

    @Override
    public Object getItem(int position) {
        return circularModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgAnnIcon = null;
        TextView txtAnnText = null, txtDate = null;

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_circular_row, null);

            imgAnnIcon = (ImageView) convertView.findViewById(R.id.imgAnnIcon);
            txtAnnText = (TextView) convertView.findViewById(R.id.txtAnnText);
            txtDate = (TextView) convertView.findViewById(R.id.txtDate);

        }

        String circularHeading = circularModels.get(position).getCircularHeading();
        SpannableString content = new SpannableString(circularHeading);
        content.setSpan(new UnderlineSpan(), 0, circularHeading.length(), 0);
        txtAnnText.setText(content);

        txtDate.setText(circularModels.get(position).getCreateDate().replace(" 00:00:00", " "));
        convertView.setTag(circularModels.get(position).getCircularFile());

        return convertView;
    }

}