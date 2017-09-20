package com.anandniketanbhadaj.skool360.skool360.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Models.CanteenModel;

import java.util.ArrayList;

public class CanteenListAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<CanteenModel> canteenModels = new ArrayList<>();

    // Constructor
    public CanteenListAdapter(Context c, ArrayList<CanteenModel> canteenModels) {
        mContext = c;
        this.canteenModels = canteenModels;
    }

    @Override
    public int getCount() {
        return canteenModels.get(0).getCanteenData().size();
    }

    @Override
    public Object getItem(int position) {
        return canteenModels.get(0).getCanteenData();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txtDate = null, txtDay = null, txtBreakfast = null, txtLunch = null, txtMilk = null;
        ImageView imgBulletCanteen = null;
        LinearLayout llChildRowFlvMilk = null;

        if(convertView == null){
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_row_canteen, null);
        }

        llChildRowFlvMilk = (LinearLayout) convertView.findViewById(R.id.llChildRowFlvMilk);
        txtMilk = (TextView) convertView.findViewById(R.id.txtMilk);
        txtBreakfast = (TextView) convertView.findViewById(R.id.txtBreakfast);
        txtLunch = (TextView) convertView.findViewById(R.id.txtLunch);
        txtDate = (TextView) convertView.findViewById(R.id.txtDate);
        txtDay = (TextView) convertView.findViewById(R.id.txtDay);
        imgBulletCanteen = (ImageView) convertView.findViewById(R.id.imgBulletCanteen);

        if(canteenModels.get(0).getCanteenData().get(position).get("MenuDay").equalsIgnoreCase("Wednesday")){
            txtDate.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
            txtDay.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
            imgBulletCanteen.setImageResource(R.drawable.orange_bulletpoint_withline);
        }else {
            txtDate.setBackgroundColor(mContext.getResources().getColor(R.color.light_blue));
            txtDay.setBackgroundColor(mContext.getResources().getColor(R.color.light_blue));
            imgBulletCanteen.setImageResource(R.drawable.blue_bulletpoint_withline);
        }

        if(canteenModels.get(0).getCanteenData().get(position).get("FlvrMilk").equalsIgnoreCase("")){
            llChildRowFlvMilk.setVisibility(View.GONE);
        }else {
            llChildRowFlvMilk.setVisibility(View.VISIBLE);
            txtMilk.setText(canteenModels.get(0).getCanteenData().get(position).get("FlvrMilk"));
        }

        txtDate.setText(canteenModels.get(0).getCanteenData().get(position).get("MenuDate"));
        txtDay.setText(canteenModels.get(0).getCanteenData().get(position).get("MenuDay"));
        txtBreakfast.setText(canteenModels.get(0).getCanteenData().get(position).get("Breakfast"));
        txtLunch.setText(canteenModels.get(0).getCanteenData().get(position).get("Lunch"));
        return convertView;
    }

}