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
import com.anandniketanbhadaj.skool360.skool360.Adapter.CircularListAdapter;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetCircularAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.CircularModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class CircularFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnBackCircular;
    private ListView listCircular;
    private TextView txtNoRecordsClasswork;
    private Context mContext;
    private GetCircularAsyncTask getCircularAsyncTask = null;
    private CircularListAdapter circularListAdapter = null;
    private ProgressDialog progressDialog = null;
    private ArrayList<CircularModel> circularModels = new ArrayList<>();

    public CircularFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.circular_fragment, container, false);
        mContext = getActivity();

        initViews();
        setListners();
        getAnnouncementData();

        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecordsClasswork = (TextView) rootView.findViewById(R.id.txtNoRecordsClasswork);
        btnBackCircular = (Button) rootView.findViewById(R.id.btnBackCircular);
        listCircular = (ListView) rootView.findViewById(R.id.listCircular);

    }

    public void setListners() {

        listCircular.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.getTag().toString().contains(".pdf")) {
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

        btnBackCircular.setOnClickListener(new View.OnClickListener() {
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

    public void downloadPDF(final String pdfURL) {
        final String fileName = pdfURL.substring(pdfURL.lastIndexOf('/') + 1);

        if (Utility.isFileExists(fileName, "circular")) {
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            Utility.pong(mContext, "File already exists at " + new File(extStorageDirectory, Utility.parentFolderName + "/" + Utility.childCircularFolderName + "/" + fileName).getPath());

        } else {
            progressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Utility.downloadFile(pdfURL, fileName, "circular");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                            Utility.pong(mContext, "File download complete at " + new File(extStorageDirectory, Utility.parentFolderName + "/" + Utility.childCircularFolderName + "/" + fileName).getPath());
                        }
                    });
                }
            }).start();
        }
    }

    public void getAnnouncementData() {
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
                        getCircularAsyncTask = new GetCircularAsyncTask(params);
                        circularModels = getCircularAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (circularModels.size() > 0) {
                                    txtNoRecordsClasswork.setVisibility(View.GONE);
                                    circularListAdapter = new CircularListAdapter(mContext, circularModels);
                                    listCircular.setAdapter(circularListAdapter);

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
        } else {
            Utility.ping(mContext, "Network not available");
        }
    }
}
