package com.anandniketanbhadaj.skool360.skool360.Adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private boolean fromClass = false;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData, boolean fromClass) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.fromClass = fromClass;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    TextView title, txtListChild, title_1, lblchaptername, title_2, lblobjective, title_3, lblque;
    ImageView imgRightSign;
    LinearLayout linear1, linear2, linear3;
    boolean visible = true;

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_classwork, null);
        }

        title = (TextView) convertView.findViewById(R.id.title);
        txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        imgRightSign = (ImageView) convertView.findViewById(R.id.imgRightSign);
        title_1= (TextView) convertView.findViewById(R.id.title);
        lblchaptername= (TextView) convertView.findViewById(R.id.title);
        title_2= (TextView) convertView.findViewById(R.id.title);
        lblobjective= (TextView) convertView.findViewById(R.id.title);
        String[] data = childText.trim().split(":");
        if(!childText.contains(":")){
            title.setText("Proxy :");
            imgRightSign.setVisibility(View.VISIBLE);
            txtListChild.setText("");
        }else {
            title.setText(data[0].trim()+" : ");
            txtListChild.setText(data[1].trim());
            imgRightSign.setVisibility(View.GONE);
        }
        txtListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visible == true) {

                    visible = false;
                } else {

                    visible = true;
                }
            }
        });


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
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        if (isExpanded) {
            convertView.setBackgroundResource(R.color.orange);
        } else {
            convertView.setBackgroundResource(R.color.gray);
        }


        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

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