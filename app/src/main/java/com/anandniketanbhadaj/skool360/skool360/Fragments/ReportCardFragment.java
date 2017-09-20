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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetReportcardAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.ReportCardModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;


import java.util.ArrayList;
import java.util.HashMap;


public class ReportCardFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnBackUnitTest;
    private TextView txtNoRecordsUnitTest;
    private Context mContext;
    WebView webview_report_card;

    private ProgressDialog progressDialog = null;
    private GetReportcardAsyncTask getReportCardAsyncTask = null;
    private ArrayList<ReportCardModel> reportModels = new ArrayList<>();

    public ReportCardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_report_card, container, false);
        mContext = getActivity();

        initViews();
        setListners();
        getReportData();
        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecordsUnitTest = (TextView) rootView.findViewById(R.id.txtNoRecordsUnitTest);
        btnBackUnitTest = (Button) rootView.findViewById(R.id.btnBackUnitTest);
        webview_report_card = (WebView) rootView.findViewById(R.id.webview);

        WebSettings webSettings = webview_report_card.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview_report_card.getSettings().setUseWideViewPort(true);
        webview_report_card.getSettings().setLoadWithOverviewMode(true);
        webview_report_card.getSettings().setBuiltInZoomControls(true);
        // Force links and redirects to open in the WebView instead of in a browser
        webview_report_card.setWebViewClient(new WebViewClient());
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

    }

    public void getReportData() {
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
                        params.put("StudentID", Utility.getPref(mContext, "studid"));
                        getReportCardAsyncTask = new GetReportcardAsyncTask(params);
                        reportModels = getReportCardAsyncTask.execute().get();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (reportModels.size() > 0) {
                                    progressDialog.dismiss();
                                    txtNoRecordsUnitTest.setVisibility(View.GONE);
                                    webview_report_card.loadUrl(reportModels.get(0).getURL());
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
