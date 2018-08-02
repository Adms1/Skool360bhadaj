package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.Adapter.ExpandableListAdapterPayment;
import com.anandniketanbhadaj.skool360.skool360.Adapter.PaymentListAdapter;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.FeesDetailsAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetPaymentLedgerAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Interfacess.onViewClick;
import com.anandniketanbhadaj.skool360.skool360.Models.FeesResponseModel.FeesMainResponse;
import com.anandniketanbhadaj.skool360.skool360.Models.PaymentLedgerModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ReceiptFragment extends Fragment {

    Fragment fragment;
    private View rootView;
    private Button btnMenu, btnBackUnitTest;
    private Context mContext;
    private FragmentManager fragmentManager = null;
    private WebView receipt_view;
    private String recieptUrl;
LinearLayout linearBack;
    public ReceiptFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_receipt, container, false);
        mContext = getActivity();
        recieptUrl = getArguments().getString("url");

        recieptUrl=(recieptUrl.substring(1, recieptUrl.length()-1));
        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        btnBackUnitTest = (Button) rootView.findViewById(R.id.btnBackUnitTest);
        linearBack=(LinearLayout)rootView.findViewById(R.id.linearBack);
        receipt_view = (WebView) rootView.findViewById(R.id.receipt_view);
        WebSettings webSettings = receipt_view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        receipt_view.getSettings().setUseWideViewPort(true);
        receipt_view.getSettings().setLoadWithOverviewMode(true);
        receipt_view.setInitialScale(10);
        receipt_view.getSettings().setBuiltInZoomControls(true);
        // Force links and redirects to open in the WebView instead of in a browser
        receipt_view.setWebViewClient(new WebViewClient());
        receipt_view.loadUrl(recieptUrl);
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
                fragment = new PaymentFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new PaymentFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
    }


}
