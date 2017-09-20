package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.Adapter.MessageListAdapter;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetPrincipalMessageAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.PrincipalModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;

public class PrincipalMessageFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnBackImprest;
    private TextView txtNoRecordsUnitTest;
    private Context mContext;
    private GetPrincipalMessageAsyncTask getPrincipalMessageAsyncTask = null;
    private ArrayList<PrincipalModel> principalModels = new ArrayList<>();
    private ProgressDialog progressDialog = null;
    MessageListAdapter messageListAdapter = null;
    ListView listMessageData;

    public PrincipalMessageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_principal_message, container, false);
        mContext = getActivity();

        initViews();
        setListners();
        filllistData();
        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        btnBackImprest = (Button) rootView.findViewById(R.id.btnBackImprest);
        listMessageData = (ListView) rootView.findViewById(R.id.listMessageData);
        txtNoRecordsUnitTest = (TextView) rootView.findViewById(R.id.txtNoRecordsUnitTest);
    }

    public void setListners() {

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });

        btnBackImprest.setOnClickListener(new View.OnClickListener() {
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

    public void filllistData() {
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
                        getPrincipalMessageAsyncTask = new GetPrincipalMessageAsyncTask(params);
                        principalModels = getPrincipalMessageAsyncTask.execute().get();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (principalModels.size() > 0) {
                                    messageListAdapter = new MessageListAdapter(mContext, principalModels);
                                    listMessageData.setAdapter(messageListAdapter);
                                    listMessageData.deferNotifyDataSetChanged();
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordsUnitTest.setVisibility(View.VISIBLE);
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
}
