package com.anandniketanbhadaj.skool360.skool360.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Models.Suggestion.InboxFinalArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapterInbox extends BaseExpandableListAdapter {

    TextView txtsuggestion, txtreply,txtsuggestion_view,txtreply_view,txt_dot,txt_dot1;
    String pageType;
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<InboxFinalArray>> listChildData;

    public ExpandableListAdapterInbox(Context context, List<String> listDataHeader,
                                      HashMap<String, List<InboxFinalArray>> listChildData, String pageType) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listChildData;
        this.pageType = pageType;
        notifyDataSetChanged();
    }

    @Override
    public List<InboxFinalArray> getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        List<InboxFinalArray> childData = getChild(groupPosition, 0);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_inbox, null);
        }

        txtreply = (TextView) convertView.findViewById(R.id.txtreply);
        txtsuggestion = (TextView) convertView.findViewById(R.id.txtsuggestion);
        txtsuggestion_view=(TextView)convertView.findViewById(R.id.txtsuggestion_view);
        txtreply_view=(TextView)convertView.findViewById(R.id.txtreply_view);
        txt_dot=(TextView)convertView.findViewById(R.id.txt_dot);
        txt_dot1=(TextView)convertView.findViewById(R.id.txt_dot1);

        txtreply.setText(childData.get(childPosition).getResponse());
        txtsuggestion.setText(childData.get(childPosition).getSuggestion());

        if (pageType.equalsIgnoreCase("Inbox")) {
            txtreply.setVisibility(View.VISIBLE);
            txtreply_view.setVisibility(View.VISIBLE);
//            txtreply.setTypeface(txtreply.getTypeface(), Typeface.BOLD);
//            txtreply_view.setTypeface(txtreply_view.getTypeface(),Typeface.BOLD);
//            txtsuggestion.setTypeface(txtsuggestion.getTypeface(), Typeface.NORMAL);
//            txtsuggestion_view.setTypeface(txtsuggestion.getTypeface(), Typeface.NORMAL);
        } else {
            txtreply.setVisibility(View.GONE);
            txtreply_view.setVisibility(View.GONE);
            txt_dot.setVisibility(View.GONE);

//            txtsuggestion.setTypeface(txtsuggestion.getTypeface(), Typeface.BOLD);
            txtsuggestion_view.setVisibility(View.GONE);
            txt_dot1.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition)).size();
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
        String[] headerTitle = getGroup(groupPosition).toString().split("\\|");

        String headerTitle1 = headerTitle[0];
        String headerTitle2 = headerTitle[1];


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_inbox, null);
        }
        TextView  date_inbox_txt, subject_inbox_txt, view_inbox_txt;
//        ImageView view_inbox_txt;
        date_inbox_txt = (TextView) convertView.findViewById(R.id.date_inbox_txt);
        subject_inbox_txt = (TextView) convertView.findViewById(R.id.subject_inbox_txt);
        view_inbox_txt = (TextView) convertView.findViewById(R.id.view_inbox_txt);

        String inputPattern = "dd/MM/yyyy";
        String outputPattern1 = "dd MMM";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern1);


        Date startdateTime = null;
        String str = null, StartTimeStr = null;

        try {
            startdateTime = inputFormat.parse(headerTitle1);
            StartTimeStr = outputFormat.format(startdateTime);


            Log.i("mini", "Converted Date Today:" + StartTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        date_inbox_txt.setText(StartTimeStr);
        subject_inbox_txt.setText(headerTitle2);

        if (isExpanded) {
            view_inbox_txt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.up_add_family,0);
            Drawable mDrawable = _context.getResources().getDrawable(R.drawable.up_add_family);
            mDrawable.setColorFilter(new
                    PorterDuffColorFilter(_context.getResources().getColor(R.color.blue),PorterDuff.Mode.MULTIPLY));
        } else {
            view_inbox_txt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.down_add_family,0);
            Drawable mDrawable = _context.getResources().getDrawable(R.drawable.down_add_family);
            mDrawable.setColorFilter(new
                    PorterDuffColorFilter(_context.getResources().getColor(R.color.blue),PorterDuff.Mode.MULTIPLY));
        }
//        if (isExpanded) {
//            view_inbox_txt.setImageResource(R.drawable.up_add_family);
//            view_inbox_txt.setColorFilter(ContextCompat.getColor(_context, R.color.blue), android.graphics.PorterDuff.Mode.MULTIPLY);
//        } else {
//            view_inbox_txt.setImageResource(R.drawable.down_add_family);
//            view_inbox_txt.setColorFilter(ContextCompat.getColor(_context, R.color.blue), android.graphics.PorterDuff.Mode.MULTIPLY);
//        }


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




