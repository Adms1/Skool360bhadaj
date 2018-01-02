package com.anandniketanbhadaj.skool360.skool360.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Models.ImprestResponse.Datum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ImprestListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<Datum>> _listDataChild;

    public ImprestListAdapter(Context context, List<String> listDataHeader,
                              HashMap<String, ArrayList<Datum>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Datum getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_imprest_row, null);
        }
        TextView txtOpening, txtDeduct, txtBalance, txtMessage;

        txtOpening = (TextView) convertView.findViewById(R.id.txtOpening);
        txtDeduct = (TextView) convertView.findViewById(R.id.txtDeduct);
        txtBalance = (TextView) convertView.findViewById(R.id.txtBalance);
        txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);


        txtOpening.setText(getChild(groupPosition,childPosition).getOpeningBalance());
        txtDeduct.setText(getChild(groupPosition,childPosition).getDeductAmount());
        txtBalance.setText(getChild(groupPosition,childPosition).getBalance());
        txtMessage.setText(getChild(groupPosition,childPosition).getMessage());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_imprest, null);
        }

        if (isExpanded) {
            convertView.setBackgroundResource(R.color.orange);
        } else {
            convertView.setBackgroundResource(R.color.gray);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        try {
            SimpleDateFormat inFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Date date = inFormat.parse(headerTitle);
            SimpleDateFormat outFormat = new SimpleDateFormat("dd/MM/yyyy");
            String day = outFormat.format(date);
            lblListHeader.setText(day);
        }catch (Exception e){
            e.printStackTrace();
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

