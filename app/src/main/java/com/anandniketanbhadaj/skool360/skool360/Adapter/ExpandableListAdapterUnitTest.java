package com.anandniketanbhadaj.skool360.skool360.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Models.UnitTestModel;

import java.util.ArrayList;
import java.util.Arrays;
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
        final TextView subject_name_txt, syllabus_txt;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_unit_test, null);
        }

        subject_name_txt = (TextView) convertView.findViewById(R.id.subject_name_txt);
        syllabus_txt = (TextView) convertView.findViewById(R.id.syllabus_txt);
        syllabus_linear = (LinearLayout) convertView.findViewById(R.id.syllabus_linear);
        subject_name_txt.setText(childData.get(childPosition).getSubject());

        String[] data = childData.get(childPosition).getDetail().split("\\|");

        List<String> stringList = new ArrayList<String>(Arrays.asList(data));


        if (syllabus_linear.getChildCount() > 0) {
            syllabus_linear.removeAllViews();
        }
        final TextView[] myTextViews = new TextView[stringList.size()];
        for (int i = 0; i < stringList.size(); i++) {

            final TextView rowTextView = new TextView(_context);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            params.setMargins(0,10,0,0);
//            rowTextView.setLayoutParams(params);
            rowTextView.setBackgroundResource(R.drawable.list_line_textbox);
            rowTextView.setTextSize(12);
            rowTextView.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER);


            rowTextView.setText(stringList.get(i));
            // add the textview to the linearlayout
            syllabus_linear.addView(rowTextView);
            // save a reference to the textview for later
            myTextViews[i] = rowTextView;

        }


        syllabus_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visible == true) {
                    syllabus_linear.setVisibility(View.VISIBLE);
                    visible = false;
                } else {
                    syllabus_linear.setVisibility(View.GONE);
                    visible = true;
                }
            }
        });
        syllabus_linear.setVisibility(View.GONE);
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
