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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetReportcardAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetTermAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.ReportCardModel;
import com.anandniketanbhadaj.skool360.skool360.Models.TermModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;


public class PayOnlineFragment extends Fragment {
    WebView webview_report_card;
    LinearLayout linearBack;
    private View rootView;
    private Button btnMenu, btnBackUnitTest;
    private TextView txtNoRecordsUnitTest;
    private Context mContext;
    private String url;

    public PayOnlineFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_pay_online, container, false);
        mContext = getActivity();

        initViews();
        setListner();
        return rootView;
    }

    public void initViews() {
        url = getArguments().getString("url");

        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecordsUnitTest = (TextView) rootView.findViewById(R.id.txtNoRecordsUnitTest);
        btnBackUnitTest = (Button) rootView.findViewById(R.id.btnBackUnitTest);
        linearBack = (LinearLayout) rootView.findViewById(R.id.linearBack);
        webview_report_card = (WebView) rootView.findViewById(R.id.webview);
        WebSettings webSettings = webview_report_card.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview_report_card.getSettings().setUseWideViewPort(true);
        webview_report_card.getSettings().setLoadWithOverviewMode(true);
        webview_report_card.getSettings().setBuiltInZoomControls(true);
        // Force links and redirects to open in the WebView instead of in a browser
        webview_report_card.setWebViewClient(new WebViewClient());
        webview_report_card.loadUrl(url);
    }

    public void setListner() {
        btnBackUnitTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PaymentFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PaymentFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
    }
}
