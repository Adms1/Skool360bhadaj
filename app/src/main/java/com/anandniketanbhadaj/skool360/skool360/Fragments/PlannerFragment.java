package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.Activities.ParallaxRecyclerView;
import com.anandniketanbhadaj.skool360.skool360.Adapter.HolidayListAdapter;
import com.anandniketanbhadaj.skool360.skool360.Adapter.HolidayListAdapter1;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetHolidayAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.ExamDatum;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.ExamModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PlannerFragment extends Fragment implements View.OnClickListener {
    Fragment fragment;
    FragmentManager fragmentManager;
    RecyclerView holiday_list;
    ExamModel holidayDataResponse;
    HolidayListAdapter1 holidayListAdapter;
    List<String> montharrayList;
    List<ExamDatum> monthwisedata;
    String month;
    LinearLayout linearBack;
    private View rootView;
    private Button btnMenu, btnBackCanteen;
    private TextView txtNoRecordsClasswork;
    private FloatingActionButton add_leave_fab_btn;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private GetHolidayAsyncTask holidayAsyncTask = null;

    public PlannerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_planner, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        Calendar cal = Calendar.getInstance();
        // month = cal.get(Calendar.MONTH) + 1;

        SimpleDateFormat input = new SimpleDateFormat("MM");
        SimpleDateFormat output = new SimpleDateFormat("MMMM");

        Date date = null;
        String str = null;
        try {
            date = input.parse(String.valueOf(cal.get(Calendar.MONTH) + 1));
            str = output.format(date);
            month = str;
            Log.i("mini", "Month:" + str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(month);
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecordsClasswork = (TextView) rootView.findViewById(R.id.txtNoRecordsClasswork);
        btnBackCanteen = (Button) rootView.findViewById(R.id.btnBackCanteen);
        holiday_list = (RecyclerView) rootView.findViewById(R.id.holiday_list);
        linearBack=(LinearLayout)rootView.findViewById(R.id.linearBack);
        getLeaveData();
    }

    public void setListners() {
        btnMenu.setOnClickListener(this);
        btnBackCanteen.setOnClickListener(this);
        linearBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMenu:
                DashBoardActivity.onLeft();
                break;
            case R.id.btnBackCanteen:
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 0;
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
                break;
            case R.id.linearBack:
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 0;
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
                break;
        }
    }

    public void getLeaveData() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("StandardID", Utility.getPref(mContext, "standardID"));
                        holidayAsyncTask = new GetHolidayAsyncTask(params);
                        holidayDataResponse = holidayAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (holidayDataResponse.getSuccess().equalsIgnoreCase("True")) {
                                    setLeaveDataList();
                                } else {
                                    progressDialog.dismiss();

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

    public void setLeaveDataList() {
        montharrayList = new ArrayList<>();
        monthwisedata = new ArrayList<ExamDatum>();

        holidayListAdapter = new HolidayListAdapter1(mContext, holidayDataResponse, monthwisedata, holiday_list.getHeight());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        holiday_list.setLayoutManager(mLayoutManager);
        holiday_list.setItemAnimator(new DefaultItemAnimator());
        holiday_list.setAdapter(holidayListAdapter);
        for (int i = 0; i < holidayDataResponse.getFinalArray().size(); i++) {
            if (holidayDataResponse.getFinalArray().get(i).getMonthName().equalsIgnoreCase(month)) {
                holiday_list.getLayoutManager().scrollToPosition(i);
            }
        }

    }

}