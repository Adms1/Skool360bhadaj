package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.Activities.Server_Error;
import com.anandniketanbhadaj.skool360.skool360.Adapter.ExpandableListAdapterTimeTable;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetTimetableAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.TimetableModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class TimeTableFragment extends Fragment {
    ExpandableListAdapterTimeTable listAdapterTimeTable;
    ExpandableListView lvExpTimeTable;
    List<String> listDataHeader;
    HashMap<String, ArrayList<TimetableModel.Timetable.TimetableData>> listDataChild;
    LinearLayout linearBack;
    private View rootView;
    private Button btnMenu, btnBackTimeTable;
    private TextView txtNoRecordsTimetable;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private GetTimetableAsyncTask getTimetableAsyncTask = null;
    private ArrayList<TimetableModel> timetableModels = new ArrayList<>();
    private int lastExpandedPosition = -1;

    public TimeTableFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.timetable_fragment, container, false);
        mContext = getActivity();

        AppConfiguration.position = 17;

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        btnMenu = rootView.findViewById(R.id.btnMenu);
        txtNoRecordsTimetable = rootView.findViewById(R.id.txtNoRecordsTimetable);
        btnBackTimeTable = rootView.findViewById(R.id.btnBackTimeTable);
        linearBack = rootView.findViewById(R.id.linearBack);
        lvExpTimeTable = rootView.findViewById(R.id.lvExpTimeTable);

        getTimeTableData();
    }

    public void setListners() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });

        btnBackTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 0;
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 0;
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        lvExpTimeTable.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lvExpTimeTable.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    public void getTimeTableData() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("StudentID", Utility.getPref(mContext, "studid"));
                        getTimetableAsyncTask = new GetTimetableAsyncTask(params);
                        timetableModels = getTimetableAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (timetableModels != null) {
                                    if (timetableModels.size() > 0) {
                                        txtNoRecordsTimetable.setVisibility(View.GONE);
                                        prepaareList();
                                        listAdapterTimeTable = new ExpandableListAdapterTimeTable(getActivity(), listDataHeader, listDataChild);
                                        lvExpTimeTable.setAdapter(listAdapterTimeTable);
                                    } else {
                                        progressDialog.dismiss();
                                        txtNoRecordsTimetable.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    Intent serverintent = new Intent(mContext, Server_Error.class);
                                    startActivity(serverintent);
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Utility.ping(mContext, "Network not available");
        }
    }

    public void prepaareList() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        for (int i = 0; i < timetableModels.get(0).getTimetables().size(); i++) {
            listDataHeader.add(timetableModels.get(0).getTimetables().get(i).getDay());
            ArrayList<TimetableModel.Timetable.TimetableData> rows = new ArrayList<>();
            for (int j = 0; j < timetableModels.get(0).getTimetables().get(i).getTimetableDatas().size(); j++) {

                rows.add(timetableModels.get(0).getTimetables().get(i).getTimetableDatas().get(j));

            }
            listDataChild.put(listDataHeader.get(i), rows);
        }
    }
}
