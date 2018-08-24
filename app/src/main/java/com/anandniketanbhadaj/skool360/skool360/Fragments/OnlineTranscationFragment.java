package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.Server_Error;
import com.anandniketanbhadaj.skool360.skool360.Adapter.OnlinePaymentAdapter;
import com.anandniketanbhadaj.skool360.skool360.Adapter.PaymentListAdapter;
import com.anandniketanbhadaj.skool360.skool360.Adapter.PaymentPageAdapter;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.FeesDetailsAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetPaymentLedgerAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Interfacess.onViewClick;
import com.anandniketanbhadaj.skool360.skool360.Models.FeesResponseModel.FeesMainResponse;
import com.anandniketanbhadaj.skool360.skool360.Models.PaymentLedgerModel;
import com.anandniketanbhadaj.skool360.skool360.Models.Suggestion.SuggestionInboxModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class OnlineTranscationFragment extends Fragment {
    Fragment fragment;
    OnlinePaymentAdapter onlinePaymentAdapter;
    LinearLayout linearBack;
TextView txtNoRecordsUnitTest;
    private View rootView;
    private Context mContext;
    private GetPaymentLedgerAsyncTask getPaymentLedgerAsyncTask = null;
   SuggestionInboxModel paymentdetailsModel;
    private RecyclerView payment_online_report_list;
    private LinearLayout lv_header;


    public OnlineTranscationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_online_transcation, container, false);
        mContext = getActivity();

        initViews();
        setListners();


        return rootView;
    }

    public void initViews() {
        payment_online_report_list = (RecyclerView) rootView.findViewById(R.id.payment_online_report_list);
        lv_header = (LinearLayout) rootView.findViewById(R.id.lv_header);
        txtNoRecordsUnitTest=(TextView)rootView.findViewById(R.id.txtNoRecordsUnitTest);
        setUserVisibleHint(true);
    }
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            getPaymentLedger();
        }
        // execute your data loading logic.
    }

    public void setListners() {

    }


    public void getPaymentLedger() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Utility.isNetworkConnected(mContext)) {
                    try {

                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("studentid", Utility.getPref(mContext, "studid"));
                        getPaymentLedgerAsyncTask = new GetPaymentLedgerAsyncTask(params);
                        paymentdetailsModel = getPaymentLedgerAsyncTask.execute().get();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (paymentdetailsModel != null) {
                                    if (paymentdetailsModel.getOnlineTransaction().size() > 0) {
                                        txtNoRecordsUnitTest.setVisibility(View.GONE);
                                        lv_header.setVisibility(View.VISIBLE);
                                        payment_online_report_list.setVisibility(View.VISIBLE);
                                        onlinePaymentAdapter=new OnlinePaymentAdapter(mContext,paymentdetailsModel);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                        payment_online_report_list.setLayoutManager(mLayoutManager);
                                        payment_online_report_list.setItemAnimator(new DefaultItemAnimator());
                                        payment_online_report_list.setAdapter(onlinePaymentAdapter);
                                    } else {
                                        txtNoRecordsUnitTest.setVisibility(View.GONE);
                                        lv_header.setVisibility(View.GONE);
                                        payment_online_report_list.setVisibility(View.GONE);
                                    }
                                } else {
                                    Intent serverintent = new Intent(mContext, Server_Error.class);
                                    startActivity(serverintent);
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Utility.ping(mContext, "Network not available");
                }
            }
        }).start();
    }
}
