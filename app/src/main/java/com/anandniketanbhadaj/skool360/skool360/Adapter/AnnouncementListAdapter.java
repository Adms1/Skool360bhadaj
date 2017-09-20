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
import com.anandniketanbhadaj.skool360.skool360.Models.AnnouncementModel;

import java.util.ArrayList;

public class AnnouncementListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<AnnouncementModel> announcementModels = new ArrayList<>();

    // Constructor
    public AnnouncementListAdapter(Context c, ArrayList<AnnouncementModel> announcementModels) {
        mContext = c;
        this.announcementModels = announcementModels;
    }

    @Override
    public int getCount() {
        return announcementModels.size();
    }

    @Override
    public Object getItem(int position) {
        return announcementModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgAnnIcon = null;
        TextView txtAnnText = null, txtDate = null;

        if(convertView == null){
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_announcement_row, null);

            imgAnnIcon = (ImageView) convertView.findViewById(R.id.imgAnnIcon);
            txtAnnText = (TextView) convertView.findViewById(R.id.txtAnnText);
            txtDate = (TextView) convertView.findViewById(R.id.txtDate);

        }

        if(announcementModels.get(position).getAnnoucementDescription().equalsIgnoreCase("")){
            imgAnnIcon.setImageResource(R.drawable.ann_download_img);
            txtAnnText.setText("Download PDF");
        }else {
            imgAnnIcon.setImageResource(R.drawable.ann_text_img);
            txtAnnText.setText(announcementModels.get(position).getAnnoucementDescription());
        }
        txtDate.setText(announcementModels.get(position).getCreateDate().replace(" 00:00:00", " "));
        convertView.setTag(announcementModels.get(position).getAnnoucementPDF());

        return convertView;
    }

}