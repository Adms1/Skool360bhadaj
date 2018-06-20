package com.anandniketanbhadaj.skool360.skool360.Adapter;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.ExamDatum;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.ExamFinalArray;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapterAnnouncement extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<ExamFinalArray>> _listDataChild;
    File filepdf;
    String file1;


    public ExpandableListAdapterAnnouncement(Context context, List<String> listDataHeader,
                                             HashMap<String, ArrayList<ExamFinalArray>> listDataChild) {

        _context = context;
        _listDataChild = listDataChild;
        _listDataHeader = listDataHeader;
    }

    @Override
    public ArrayList<ExamFinalArray> getChild(int groupPosition, int childPosititon) {
        return _listDataChild.get(_listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, final ViewGroup parent) {

        final ArrayList<ExamFinalArray> childData = getChild(groupPosition, childPosition);
        final TextView description_title_txt,show_file;

        if (convertView == null) {
            LayoutInflater infalInflater = LayoutInflater.from(_context);
            convertView = infalInflater.inflate(R.layout.announcement_listitem, null);
        }
        description_title_txt = (TextView) convertView.findViewById(R.id.description_title_txt);
        show_file=(TextView)convertView.findViewById(R.id.show_file);
        if (childData.get(childPosition).getAnnoucementDescription().equalsIgnoreCase("")) {
            String extStorageDirectory = "";
            String saveFilePath = null;
            long currentTime = Calendar.getInstance().getTimeInMillis();
            Log.d("date", "" + currentTime);
            Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
            Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();
            final String fileName = childData.get(childPosition).getAnnoucementPDF().substring(childData.get(childPosition).getAnnoucementPDF().lastIndexOf('/') + 1);
            if (isSDSupportedDevice && isSDPresent) {
                // yes SD-card is present
                Utility.ping(_context, "present");
                extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                saveFilePath = String.valueOf(new File(extStorageDirectory, Utility.parentFolderName + "/" + Utility.childAnnouncementFolderName + "/" + fileName).getPath());

            } else {
                // Sorry
//                            Utility.ping(mContext, "notpresent");
//
                File cDir = _context.getExternalFilesDir(null);
                saveFilePath = String.valueOf(new File(cDir.getPath() + "/" + fileName));
                Log.d("path", saveFilePath);

            }
//
            Log.d("path", extStorageDirectory);

            String fileURL = childData.get(childPosition).getAnnoucementPDF();
            Log.d("URL", fileURL);
            if (Utility.isNetworkConnected(_context)) {

                Ion.with(_context)
                        .load(fileURL)  // download url
                        .write(new File(saveFilePath))  // File no path
                        .setCallback(new FutureCallback<File>() {
                            //                                    @Override
                            public void onCompleted(Exception e, File file) {

                                if (file.length() > 0) {

                                    //Utility.ping(_context, "Download complete.");
                                    file1 = file.getPath();
                                    filepdf = file.getAbsoluteFile();
                                    Log.d("file11", "" + filepdf);
                                    description_title_txt.setText(String.valueOf(filepdf));
                                    show_file.setVisibility(View.VISIBLE);
                                } else {
                                    Utility.ping(_context, "Something error");
                                    show_file.setVisibility(View.GONE);
                                }
                            }


                        });
            } else {
                Utility.ping(_context, "Network not available");
            }
        } else {
            show_file.setVisibility(View.GONE);
            description_title_txt.setText(childData.get(childPosition).getAnnoucementDescription());
        }

        show_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(file1);
                Log.d("DownloadfilePath", "File to download = " + String.valueOf(file));
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String ext = file.getName().substring(file.getName().indexOf(".") + 1);
                String type = mime.getMimeTypeFromExtension(ext);
                Log.d("type", type);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), type);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                _context.startActivity(intent);
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

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.announcement_groupitem, null);
        }
        ImageView imgAnnIcon;
        TextView txtAnnText, txtDate;

        imgAnnIcon = (ImageView) convertView.findViewById(R.id.imgAnnIcon);
        txtAnnText = (TextView) convertView.findViewById(R.id.txtAnnText);
        txtDate = (TextView) convertView.findViewById(R.id.txtDate);
        txtDate.setText(headerTemp[0]);
        if (headerTemp[2].equalsIgnoreCase("")) {
            imgAnnIcon.setImageResource(R.drawable.ann_download_img);
            txtAnnText.setText(headerTemp[1]);
        } else {
            imgAnnIcon.setImageResource(R.drawable.ann_text_img);
            txtAnnText.setText(headerTemp[1]);
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

