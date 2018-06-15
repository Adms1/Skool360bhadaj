package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.Adapter.ExpandableListAdapterCircular;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetCircularAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.CircularModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;


public class AnnouncmentFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnBack;
    private ExpandableListView listannouncment;
    private TextView txtNoRecords;
    private Context mContext;
    private GetCircularAsyncTask getCircularAsyncTask = null;
    private ExpandableListAdapterCircular circularListAdapter = null;
    private ProgressDialog progressDialog = null;
    private ArrayList<CircularModel> circularModels = new ArrayList<>();
    ArrayList<String> listDataHeader;
    HashMap<String, ArrayList<CircularModel>> listDataChildCircular;
    private int lastExpandedPosition = -1;
    public AnnouncmentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_announcment, container, false);
        mContext = getActivity();

        initViews();
        setListners();
        getCircularData();

        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecords = (TextView) rootView.findViewById(R.id.txtNoRecords);
        btnBack = (Button) rootView.findViewById(R.id.btnBack);
        listannouncment = (ExpandableListView) rootView.findViewById(R.id.listannouncment);
    }

    public void setListners() {

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });

        listannouncment.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    listannouncment.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

    }

    public void getCircularData(){
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
                        getCircularAsyncTask = new GetCircularAsyncTask(params);
                        circularModels = getCircularAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (circularModels.size() > 0) {
                                    txtNoRecords.setVisibility(View.GONE);

                                    prepaareList();
                                    circularListAdapter = new ExpandableListAdapterCircular(getActivity(),listDataHeader,listDataChildCircular);
                                    listannouncment.setAdapter(circularListAdapter);

                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecords.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }else{
            Utility.ping(mContext,"Network not avialable");
        }
    }

    public void prepaareList() {
        listDataHeader = new ArrayList<>();
        listDataChildCircular = new HashMap<String, ArrayList<CircularModel>>();
    }
}
