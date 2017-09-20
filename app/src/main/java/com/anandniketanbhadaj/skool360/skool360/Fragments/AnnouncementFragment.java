package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.Adapter.AnnouncementListAdapter;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetAnnouncementAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.AnnouncementModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class AnnouncementFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnBackAnnouncement;
    private ListView listAnnouncement;
    private TextView txtNoRecordsClasswork;
    private Context mContext;
    private GetAnnouncementAsyncTask getAnnouncementAsyncTask = null;
    private AnnouncementListAdapter announcementListAdapter = null;
    private ProgressDialog progressDialog = null;
    private ArrayList<AnnouncementModel> announcementModels = new ArrayList<>();

    public AnnouncementFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.announcement_fragment, container, false);
        mContext = getActivity();

        initViews();
        setListners();
        getAnnouncementData();

        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecordsClasswork = (TextView) rootView.findViewById(R.id.txtNoRecordsClasswork);
        btnBackAnnouncement = (Button) rootView.findViewById(R.id.btnBackAnnouncement);
        listAnnouncement = (ListView) rootView.findViewById(R.id.listAnnouncement);

    }

    public void setListners() {

        listAnnouncement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view.getTag().toString().contains(".pdf")){
                    downloadPDF(view.getTag().toString());
                }
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });

        btnBackAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

    }

    public void downloadPDF(final String pdfURL){
        final String fileName = pdfURL.substring(pdfURL.lastIndexOf('/') + 1);

        if(Utility.isFileExists(fileName, "announcement")){
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            Utility.pong(mContext, "File already exists at "+new File(extStorageDirectory, Utility.parentFolderName+"/"+Utility.childAnnouncementFolderName+"/"+fileName).getPath());

        }else {
            progressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Utility.downloadFile(pdfURL, fileName, "announcement");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                            Utility.pong(mContext, "File download complete at "+new File(extStorageDirectory, Utility.parentFolderName+"/"+Utility.childAnnouncementFolderName+"/"+fileName).getPath());
                        }
                    });
                }
            }).start();
        }
    }

    public void getAnnouncementData(){
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
                        getAnnouncementAsyncTask = new GetAnnouncementAsyncTask(params);
                        announcementModels = getAnnouncementAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (announcementModels.size() > 0) {
                                    txtNoRecordsClasswork.setVisibility(View.GONE);
                                    announcementListAdapter = new AnnouncementListAdapter(mContext, announcementModels);
                                    listAnnouncement.setAdapter(announcementListAdapter);

                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordsClasswork.setVisibility(View.VISIBLE);
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
