package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.Server_Error;
import com.anandniketanbhadaj.skool360.skool360.Adapter.PaymentListAdapter;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetPaymentLedgerAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Interfacess.onViewClick;
import com.anandniketanbhadaj.skool360.skool360.Models.Suggestion.SuggestionInboxModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.HashMap;

public class PaymentReceiptFragment extends Fragment {
    Fragment fragment;
    PaymentListAdapter paymentListAdapter;
    LinearLayout linearBack;
    SuggestionInboxModel paymentdetailsModel;
    private View rootView;
    private TextView txtNoRecordsUnitTest;
    private Context mContext;
    private GetPaymentLedgerAsyncTask getPaymentLedgerAsyncTask = null;
    private FragmentManager fragmentManager = null;
    private RecyclerView payment_report_list;
    private LinearLayout lv_header;

    public PaymentReceiptFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_paymentreceipt, container, false);
        mContext = getActivity();

        initViews();
        setListners();


        return rootView;
    }

    public void initViews() {
        payment_report_list = rootView.findViewById(R.id.payment_report_list);
        txtNoRecordsUnitTest = rootView.findViewById(R.id.txtNoRecordsUnitTest);
        lv_header = rootView.findViewById(R.id.lv_header);
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

                        HashMap<String, String> params = new HashMap<>();
                        params.put("studentid", Utility.getPref(mContext, "studid"));
                        params.put("TermID", Utility.getPref(mContext, "TermID"));
                        getPaymentLedgerAsyncTask = new GetPaymentLedgerAsyncTask(params);
                        paymentdetailsModel = getPaymentLedgerAsyncTask.execute().get();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (paymentdetailsModel != null) {
                                    if (paymentdetailsModel.getFinalArray().size() > 0) {
                                        txtNoRecordsUnitTest.setVisibility(View.GONE);
                                        lv_header.setVisibility(View.VISIBLE);
                                        payment_report_list.setVisibility(View.VISIBLE);
                                        paymentListAdapter = new PaymentListAdapter(mContext, paymentdetailsModel, new onViewClick() {
                                            @Override
                                            public void getViewClick() {
                                                String ReceiptUrl;
                                                ReceiptUrl = String.valueOf(paymentListAdapter.getRowValue());
                                                fragment = new ReceiptFragment();
                                                Bundle args = new Bundle();
                                                args.putString("url", ReceiptUrl);
                                                fragment.setArguments(args);
                                                fragmentManager = getFragmentManager();
                                                fragmentManager.beginTransaction()
                                                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                                        .replace(R.id.frame_container, fragment).commit();
                                            }
                                        });
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                        payment_report_list.setLayoutManager(mLayoutManager);
                                        payment_report_list.setItemAnimator(new DefaultItemAnimator());
                                        payment_report_list.setAdapter(paymentListAdapter);
                                    } else {
                                        txtNoRecordsUnitTest.setVisibility(View.GONE);
                                        lv_header.setVisibility(View.GONE);
                                        payment_report_list.setVisibility(View.GONE);
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
