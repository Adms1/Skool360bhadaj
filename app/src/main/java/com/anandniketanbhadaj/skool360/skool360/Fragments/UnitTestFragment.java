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
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.Adapter.ExpandableListAdapterUnitTest;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetTestDetailAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.Data;
import com.anandniketanbhadaj.skool360.skool360.Models.UnitTestModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class UnitTestFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnBackUnitTest;
    private TextView txtNoRecordsUnitTest;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private GetTestDetailAsyncTask getTestDetailAsyncTask = null;
    private ArrayList<UnitTestModel> testdetailModels = new ArrayList<>();
    private int lastExpandedPosition = -1;

    ExpandableListAdapterUnitTest expandableListAdapterUnitTest;
    ExpandableListView lvExpUnitTest;
    List<String> listDataHeader;
    HashMap<String, ArrayList<Data>> listDataChild;


    public UnitTestFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.unit_test_fragment, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecordsUnitTest = (TextView) rootView.findViewById(R.id.txtNoRecordsUnitTest);
        btnBackUnitTest = (Button) rootView.findViewById(R.id.btnBackUnitTest);
        lvExpUnitTest = (ExpandableListView) rootView.findViewById(R.id.lvExpUnitTest);

        getUnitTestData();
    }

    public void setListners() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });

        btnBackUnitTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        lvExpUnitTest.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lvExpUnitTest.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    public void getUnitTestData() {
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
                        params.put("StudentID", Utility.getPref(mContext, "studid"));
                        params.put("TermID", Utility.getPref(mContext, "TermID"));
                        getTestDetailAsyncTask = new GetTestDetailAsyncTask(params);
                        testdetailModels = getTestDetailAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (testdetailModels.size() > 0) {
                                    txtNoRecordsUnitTest.setVisibility(View.GONE);
                                    progressDialog.dismiss();
                                    prepaareList();
                                    expandableListAdapterUnitTest = new ExpandableListAdapterUnitTest(getActivity(), listDataHeader, listDataChild);
                                    lvExpUnitTest.setAdapter(expandableListAdapterUnitTest);
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
        } else {
            Utility.ping(mContext, "Network not available");
        }
    }

    public void prepaareList() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, ArrayList<Data>>();

        for (int i = 0; i < testdetailModels.size(); i++) {
            listDataHeader.add(testdetailModels.get(i).getTestDate());

            ArrayList<Data> rows = new ArrayList<Data>();
            for (int j = 0; j < testdetailModels.get(i).getDataArrayList().size(); j++) {
                rows.add(testdetailModels.get(i).getDataArrayList().get(j));

            }
            listDataChild.put(listDataHeader.get(i), rows);
        }
    }
}
