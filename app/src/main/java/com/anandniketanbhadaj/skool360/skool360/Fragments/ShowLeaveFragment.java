package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.Adapter.LeaveListAdapter;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetLeaveDataAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.ExamModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.HashMap;


public class ShowLeaveFragment extends Fragment implements View.OnClickListener {
    Fragment fragment;
    FragmentManager fragmentManager;
    RecyclerView listLeave;
    ExamModel leaveDataResponse;
    LeaveListAdapter leaveListAdapter;
    private View rootView;
    private Button btnMenu, btnBackCanteen;
    private TextView txtNoRecordsClasswork;
    private LinearLayout header_linear;
    private FloatingActionButton add_leave_fab_btn;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private GetLeaveDataAsyncTask leaveDataAsyncTask = null;

    public ShowLeaveFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_show_leave, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {

        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecordsClasswork = (TextView) rootView.findViewById(R.id.txtNoRecordsClasswork);
        btnBackCanteen = (Button) rootView.findViewById(R.id.btnBackCanteen);
        add_leave_fab_btn = (FloatingActionButton) rootView.findViewById(R.id.add_leave_fab_btn);
        listLeave = (RecyclerView) rootView.findViewById(R.id.listLeave);
        header_linear=(LinearLayout)rootView.findViewById(R.id.header_linear) ;

        getLeaveData();
    }


    public void setListners() {

        btnMenu.setOnClickListener(this);
        add_leave_fab_btn.setOnClickListener(this);
        btnBackCanteen.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_leave_fab_btn:
                fragment = new LeaveFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
                break;

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
                        params.put("StudentId", Utility.getPref(mContext, "studid"));
                        leaveDataAsyncTask = new GetLeaveDataAsyncTask(params);
                        leaveDataResponse = leaveDataAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (leaveDataResponse.getSuccess().equalsIgnoreCase("True")) {
                                    txtNoRecordsClasswork.setVisibility(View.GONE);
                                    header_linear.setVisibility(View.VISIBLE);
                                    setLeaveDataList();
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordsClasswork.setVisibility(View.VISIBLE);
                                    header_linear.setVisibility(View.GONE);
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
        leaveListAdapter = new LeaveListAdapter(mContext, leaveDataResponse);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        listLeave.setLayoutManager(mLayoutManager);
        listLeave.setItemAnimator(new DefaultItemAnimator());
        listLeave.setAdapter(leaveListAdapter);
    }
}
