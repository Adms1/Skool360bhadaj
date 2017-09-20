package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.Adapter.ExpandableListAdapterHomework;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetStudHomeworkAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.HomeWorkModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class HomeworkFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnFilterHomework, btnBackHomework;
    private static TextView fromDate, toDate;
    private TextView txtNoRecordsHomework;
    private static String dateFinal;
    private Context mContext;
    private GetStudHomeworkAsyncTask getStudHomeworkAsyncTask = null;
    private ArrayList<HomeWorkModel> homeWorkModels = new ArrayList<>();
    private ProgressDialog progressDialog = null;
    private static boolean isFromDate = false;
    private int lastExpandedPosition = -1;

    ExpandableListAdapterHomework listAdapter;
    ExpandableListView lvExpHomework;
    List<String> listDataHeader;
    HashMap<String, ArrayList<HomeWorkModel.HomeWorkData>> listDataChild;


    public HomeworkFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.homework_fragment, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        fromDate = (TextView) rootView.findViewById(R.id.fromDate);
        toDate = (TextView) rootView.findViewById(R.id.toDate);
        btnFilterHomework = (Button) rootView.findViewById(R.id.btnFilterHomework);
        txtNoRecordsHomework = (TextView) rootView.findViewById(R.id.txtNoRecordsHomework);
        btnBackHomework = (Button) rootView.findViewById(R.id.btnBackHomework);
        lvExpHomework = (ExpandableListView) rootView.findViewById(R.id.lvExpHomework);

        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH) + 1;
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        //load today's data first
        fromDate.setText(dd + "/" + mm + "/" + yy);
        toDate.setText(dd + "/" + mm + "/" + yy);
        getHomeworkData(fromDate.getText().toString(), toDate.getText().toString());
    }

    public void setListners() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = true;
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = false;
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        btnFilterHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fromDate.getText().toString().equalsIgnoreCase("")) {
                    if (!toDate.getText().toString().equalsIgnoreCase("")) {

                        getHomeworkData(fromDate.getText().toString(), toDate.getText().toString());

                    } else {
                        Utility.pong(mContext, "You need to select a to date");
                    }
                } else {
                    Utility.pong(mContext, "You need to select a from date");
                }
            }
        });

        btnBackHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        lvExpHomework.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lvExpHomework.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    public void getHomeworkData(final String fromDate, final String toDate) {

        if(Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
//                    params.put("StudentID", "1027");
                        params.put("StudentID", Utility.getPref(mContext, "studid"));
                        params.put("HomeWorkFromDate", fromDate);
                        params.put("HomeWorkToDate", toDate);
                        homeWorkModels.clear();
                        getStudHomeworkAsyncTask = new GetStudHomeworkAsyncTask(params);
                        homeWorkModels = getStudHomeworkAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (homeWorkModels.size() > 0) {
                                    txtNoRecordsHomework.setVisibility(View.GONE);
                                    prepaareList();
                                    listAdapter = new ExpandableListAdapterHomework(getActivity(), listDataHeader, listDataChild);
                                    lvExpHomework.setAdapter(listAdapter);
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordsHomework.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }else{
            Utility.ping(mContext,"Network not available");
        }
    }

    public void prepaareList() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, ArrayList<HomeWorkModel.HomeWorkData>>();

        for (int i = 0; i < homeWorkModels.size(); i++) {
            listDataHeader.add(homeWorkModels.get(i).getHomeWorkDate());

            ArrayList<HomeWorkModel.HomeWorkData> rows = new ArrayList<HomeWorkModel.HomeWorkData>();
            for (int j = 0; j < homeWorkModels.get(i).getHomeWorkDatas().size(); j++) {
                rows.add(homeWorkModels.get(i).getHomeWorkDatas().get(j));
            }
            listDataChild.put(listDataHeader.get(i), rows);
        }
    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            dateFinal = day + "/" + month + "/" + year;
            if (isFromDate) {
                fromDate.setText(dateFinal);
            } else {
                toDate.setText(dateFinal);
            }
        }
    }
}
