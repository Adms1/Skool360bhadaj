package com.anandniketanbhadaj.skool360.skool360.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Models.ImprestDataModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ImprestListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ImprestDataModel> imprestModels = new ArrayList<>();

    // Constructor
    public ImprestListAdapter(Context c, ArrayList<ImprestDataModel> imprestModels) {
        mContext = c;
        this.imprestModels = imprestModels;
    }

    @Override
    public int getCount() {
        return imprestModels.size();
    }

    @Override
    public Object getItem(int position) {
        return imprestModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txtMessage = null, txtDate = null, txtOpening = null, txtDeduct = null, txtBalance = null, txtDay = null;
        LinearLayout  llDateRow = null;

        if(convertView == null){
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_imprest_row, null);

            llDateRow = (LinearLayout) convertView.findViewById(R.id.llDateRow);
            txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
            txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            txtOpening = (TextView) convertView.findViewById(R.id.txtOpening);
            txtDeduct = (TextView) convertView.findViewById(R.id.txtDeduct);
            txtBalance = (TextView) convertView.findViewById(R.id.txtBalance);
            txtDay = (TextView) convertView.findViewById(R.id.txtDay);
        }

        if(position == 0){
            llDateRow.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
        }

        txtMessage.setText(imprestModels.get(position).getMessage());
        txtDate.setText(imprestModels.get(position).getDate().replace("00:00:00", ""));

        try {
            SimpleDateFormat inFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = inFormat.parse(imprestModels.get(position).getDate().replace("00:00:00", "").toString());
            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            String day = outFormat.format(date);
            txtDay.setText(day);
        }catch (Exception e){
            e.printStackTrace();
        }

        txtOpening.setText(imprestModels.get(position).getOpeningBalance());
        txtDeduct.setText(imprestModels.get(position).getDeductAmount());
        txtBalance.setText(imprestModels.get(position).getBalance());
        return convertView;
    }

}