package com.anandniketanbhadaj.skool360.skool360.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Models.CircularModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admsandroid on 11/1/2017.
 */

public class ExpandableListAdapterCircular extends BaseExpandableListAdapter {

    private Context _context;
    boolean visible = true;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<CircularModel>> _listDataChildcircular;
    TextView txtCircularSubject, txtCircularDate;
    LinearLayout llHeaderRow;
    ImageView imgBulletCircular;

    public ExpandableListAdapterCircular(Context context, List<String> listDataHeader,
                                         HashMap<String, ArrayList<CircularModel>> listDataChildcircular) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChildcircular = listDataChildcircular;
    }

    @Override
    public ArrayList<CircularModel> getChild(int groupPosition, int childPosititon) {
        return this._listDataChildcircular.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final ArrayList<CircularModel> childData = getChild(groupPosition, 0);
        WebView circular_description_webview;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_circular_row, null);
        }
        circular_description_webview = (WebView) convertView.findViewById(R.id.circular_description_webview);
        circular_description_webview.loadData(childData.get(childPosition).getDiscription(), "text/html", "UTF-8");
        WebSettings webSettings=circular_description_webview.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.SMALLER);
        Log.d("webview",childData.get(childPosition).getDiscription());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChildcircular.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String[] headerTemp = getGroup(groupPosition).toString().split("\\|");
        String headerTitle = headerTemp[0];
        String headerTitle1 = headerTemp[1];
        Log.d("positon", "" + headerTitle + "" + headerTitle1);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_item, null);
        }
        txtCircularSubject = (TextView) convertView.findViewById(R.id.txtCircularSubject);
        txtCircularDate = (TextView) convertView.findViewById(R.id.txtCircularDate);
        llHeaderRow = (LinearLayout) convertView.findViewById(R.id.llHeaderRow);
        imgBulletCircular = (ImageView) convertView.findViewById(R.id.imgBulletCircular);

        txtCircularSubject.setTypeface(null, Typeface.BOLD);
        txtCircularSubject.setText(headerTitle);
        txtCircularDate.setTypeface(null, Typeface.BOLD);
        txtCircularDate.setText("("+headerTitle1+")"+"");

        if (isExpanded) {
            txtCircularSubject.setBackgroundColor(Color.parseColor("#86c129"));
            txtCircularDate.setBackgroundColor(Color.parseColor("#86c129"));
            imgBulletCircular.setImageResource(R.drawable.green_bulletpointwithgreenline);
        } else {
            txtCircularSubject.setBackgroundColor(Color.parseColor("#1791d8"));
            txtCircularDate.setBackgroundColor(Color.parseColor("#1791d8"));
            imgBulletCircular.setImageResource(R.drawable.blue_bulletpoint_withline);
        }


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }
}

