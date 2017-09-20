package com.anandniketanbhadaj.skool360.skool360.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Models.UnitTestModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admsandroid on 9/4/2017.
 */

public class ExpandableListAdapterUnitTest extends BaseExpandableListAdapter {

    private Context _context;
    boolean visible = true;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<UnitTestModel.Data>> _listDataChild;

    public ExpandableListAdapterUnitTest(Context context, List<String> listDataHeader,
                                         HashMap<String, ArrayList<UnitTestModel.Data>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public ArrayList<UnitTestModel.Data> getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final ArrayList<UnitTestModel.Data> childData = getChild(groupPosition, 0);
        final LinearLayout syllabus_linear;
        final TextView subject_name_txt, syllabus_txt, syllabus_detail_txt;


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_unit_test, null);
        }

        subject_name_txt = (TextView) convertView.findViewById(R.id.subject_name_txt);
        syllabus_txt = (TextView) convertView.findViewById(R.id.syllabus_txt);
        syllabus_detail_txt = (TextView) convertView.findViewById(R.id.syllabus_detail_txt);
        syllabus_linear = (LinearLayout) convertView.findViewById(R.id.syllabus_linear);

        subject_name_txt.setText(childData.get(childPosition).getSubject());
        syllabus_detail_txt.setText(childData.get(childPosition).getDetail());
        syllabus_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visible == true) {
                    syllabus_linear.setVisibility(View.VISIBLE);
                    syllabus_detail_txt.setVisibility(View.VISIBLE);
                    visible = false;
                } else {
                    syllabus_linear.setVisibility(View.GONE);
                    syllabus_detail_txt.setVisibility(View.GONE);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String[] headerTemp = getGroup(groupPosition).toString().split("\\|");
        String headerTitle = headerTemp[0];
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_timetable, null);
        }

        if (isExpanded) {
            convertView.setBackgroundResource(R.drawable.homework_selected_bg);
        } else {
            convertView.setBackgroundResource(R.drawable.homework_subject_bg);
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
