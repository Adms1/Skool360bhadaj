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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.Adapter.ExpandableListAdapterEvent;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.EventAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.EventModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class EventFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnBackEvent;
    public static Button btnCloseOverLay;
    private ExpandableListView lvExpEvents;
    public static RelativeLayout rlOverLay;
    public static ImageView imgFullScreenImage;
    private Context mContext;
    private EventAsyncTask eventAsyncTask = null;
    private ExpandableListAdapterEvent expandableListAdapterEvent = null;
    private ArrayList<EventModel> eventModels = new ArrayList<>();
    private ProgressDialog progressDialog = null;
    private int lastExpandedPosition = -1;
    List<String> listDataHeader;
    HashMap<String, ArrayList<String>> listDataChild;
    public static TextView txtNoRecordsEvent;

    public EventFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.event_fragment, container, false);
        mContext = getActivity();

        initViews();
        setListners();
        fillEventData();

        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        btnBackEvent = (Button) rootView.findViewById(R.id.btnBackEvent);
        lvExpEvents = (ExpandableListView) rootView.findViewById(R.id.lvExpEvents);
        rlOverLay = (RelativeLayout) rootView.findViewById(R.id.rlOverLay);
        btnCloseOverLay = (Button) rootView.findViewById(R.id.btnCloseOverLay);
        imgFullScreenImage = (ImageView) rootView.findViewById(R.id.imgFullScreenImage);
    }

    public void setListners() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });

        btnBackEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        lvExpEvents.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lvExpEvents.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        btnCloseOverLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlOverLay.setVisibility(View.GONE);
            }
        });

        rlOverLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlOverLay.setVisibility(View.GONE);
            }
        });

    }

    public void fillEventData() {
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
                        eventAsyncTask = new EventAsyncTask(params);
                        eventModels = eventAsyncTask.execute().get();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (eventModels.size() > 0) {
                                    txtNoRecordsEvent.setVisibility(View.GONE);
                                    prepaareList();
                                    expandableListAdapterEvent = new ExpandableListAdapterEvent(getActivity(), listDataHeader, listDataChild);
                                    lvExpEvents.setAdapter(expandableListAdapterEvent);
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordsEvent.setVisibility(View.VISIBLE);
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
        listDataChild = new HashMap<String, ArrayList<String>>();

        for (int i = 0; i < eventModels.size(); i++) {
            listDataHeader.add(eventModels.get(i).getDescription());
            ArrayList<String> rows = new ArrayList<String>();

            for (int j = 0; j < eventModels.get(i).getEventImages().size(); j++) {
                rows.add(eventModels.get(i).getEventImages().get(j).getImagePath());

            }
            listDataChild.put(listDataHeader.get(i), rows);
        }
    }

}
