package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.anandniketanbhadaj.skool360.skool360.Models.FeesResponseModel.FeesFinalResponse;
import com.anandniketanbhadaj.skool360.skool360.Models.FeesResponseModel.FeesMainResponse;
import com.anandniketanbhadaj.skool360.skool360.Models.PaymentLedgerModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PaymentFragment extends Fragment {
    FeesMainResponse feesMainResponse;
    List<String> termheader;
    ArrayList<String> listDataChild;
    ExpandableListView lvExpPayment;
    ExpandableListAdapterPayment expandableListAdapterPayment;
    ArrayList<String> listDataHeader;
    HashMap<String, ArrayList<PaymentLedgerModel.Data>> listDataChildPayment;
    TableRow tableRow13;
    Fragment fragment;
    PaymentListAdapter paymentListAdapter;
    private TextView paynow_term1_txt, paynow_term2_txt, payment_history;
    private View rootView;
    private Button btnMenu, btnBackUnitTest;
    private TextView txtNoRecordsUnitTest;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private FeesDetailsAsyncTask getFeesDetailsAsyncTask = null;
    private int lastExpandedPosition = -1;
    private GetPaymentLedgerAsyncTask getPaymentLedgerAsyncTask = null;
    private ArrayList<PaymentLedgerModel> paymentdetailsModel = new ArrayList<>();
    private FragmentManager fragmentManager = null;
    private TableLayout table_layout;
    private RecyclerView payment_report_list;
    private LinearLayout lv_header;
    LinearLayout linearBack;


    public PaymentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_payment, container, false);
        mContext = getActivity();

        initViews();
        setListners();
        getFeesData();
        getPaymentLedger();

        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecordsUnitTest = (TextView) rootView.findViewById(R.id.txtNoRecordsUnitTest);
        btnBackUnitTest = (Button) rootView.findViewById(R.id.btnBackUnitTest);
        linearBack=(LinearLayout)rootView.findViewById(R.id.linearBack);
        paynow_term1_txt = (TextView) rootView.findViewById(R.id.paynow_term1_txt);
        paynow_term2_txt = (TextView) rootView.findViewById(R.id.paynow_term2_txt);
        //lvExpPayment = (ExpandableListView) rootView.findViewById(R.id.lvExpPayment);
        payment_history = (TextView) rootView.findViewById(R.id.payment_history);
        tableRow13 = (TableRow) rootView.findViewById(R.id.tableRow13);
        table_layout = (TableLayout) rootView.findViewById(R.id.table_layout);
        payment_report_list = (RecyclerView) rootView.findViewById(R.id.payment_report_list);
        lv_header = (LinearLayout) rootView.findViewById(R.id.lv_header);
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
                fragment = new FeesFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new FeesFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
//        lvExpPayment.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                if (lastExpandedPosition != -1
//                        && groupPosition != lastExpandedPosition) {
//                    lvExpPayment.collapseGroup(lastExpandedPosition);
//
//                }
//                lastExpandedPosition = groupPosition;
//            }
//        });
        paynow_term1_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new PayOnlineFragment();
                Bundle args = new Bundle();
                args.putString("url", feesMainResponse.getTerm1URL());
                fragment.setArguments(args);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        paynow_term2_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new PayOnlineFragment();
                Bundle args = new Bundle();
                args.putString("url", feesMainResponse.getTerm2URL());
                fragment.setArguments(args);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
    }

    public void getFeesData() {
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
                        params.put("Term", Utility.getPref(mContext, "TermID"));
                        params.put("StandardID", Utility.getPref(mContext, "standardID"));

                        getFeesDetailsAsyncTask = new FeesDetailsAsyncTask(params);
                        feesMainResponse = getFeesDetailsAsyncTask.execute().get();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (feesMainResponse.getFinalArray().size() > 0) {
                                    txtNoRecordsUnitTest.setVisibility(View.GONE);
                                    progressDialog.dismiss();
                                    setData();
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

    public void setData() {
        termheader = new ArrayList<String>();
        listDataChild = new ArrayList<>();
        if (feesMainResponse.getTerm1Btn().equals(false)) {
            paynow_term1_txt.setVisibility(View.GONE);
        } else {
            paynow_term1_txt.setVisibility(View.VISIBLE);
        }

        if (feesMainResponse.getTerm2Btn().equals(false)) {
            paynow_term2_txt.setVisibility(View.GONE);
        } else {
            paynow_term2_txt.setVisibility(View.VISIBLE);
        }


        for (int i = 0; i < feesMainResponse.getFinalArray().size(); i++) {
//            int count=feesMainResponse.getFinalArray().get(i);

            TableRow row1 = new TableRow(mContext);
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = 1;
            params.topMargin = 1;
            params.rightMargin = 1;
            params.leftMargin = 1;
            row1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            TextView a = new TextView(mContext);
            TextView b = new TextView(mContext);
            TextView c = new TextView(mContext);

            a.setLayoutParams(params);
            b.setLayoutParams(params);
            c.setLayoutParams(params);

            a.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            b.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            c.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

            a.setPadding(5,0,0,0);
            b.setPadding(0,0,5,0);
            c.setPadding(0,0,5,0);

            a.setWidth(90);
            b.setWidth(90);
            c.setWidth(90);

            a.setHeight(90);
            b.setHeight(90);
            c.setHeight(90);

            a.setTextSize(14f);
            b.setTextSize(14f);
            c.setTextSize(14f);

            a.setBackgroundColor(getResources().getColor(R.color.white));
            b.setBackgroundColor(getResources().getColor(R.color.white));
            c.setBackgroundColor(getResources().getColor(R.color.white));

            a.setTextColor(getResources().getColor(R.color.text_color));
            b.setTextColor(getResources().getColor(R.color.text_color));
            c.setTextColor(getResources().getColor(R.color.text_color));

            a.setText(feesMainResponse.getFinalArray().get(i).getLedgerName());//feesMainResponse.getFinalArray().get(i).getLedgerName()
            b.setText("₹" + " " + String.valueOf(Math.round(feesMainResponse.getFinalArray().get(i).getTerm1Amt())));
            c.setText("₹" + " " + String.valueOf(Math.round(feesMainResponse.getFinalArray().get(i).getTerm2Amt())));

            row1.addView(a);
            row1.addView(b);
            row1.addView(c);
            table_layout.addView(row1);

        }

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
                                if (paymentdetailsModel.size() > 0) {
                                    txtNoRecordsUnitTest.setVisibility(View.GONE);
                                    payment_history.setVisibility(View.VISIBLE);
                                    lv_header.setVisibility(View.VISIBLE);
                                    payment_report_list.setVisibility(View.VISIBLE);
                                    paymentListAdapter = new PaymentListAdapter(mContext, paymentdetailsModel, new onViewClick() {
                                        @Override
                                        public void getViewClick() {
                                            String ReceiptUrl;
                                            ReceiptUrl= String.valueOf(paymentListAdapter.getRowValue());
                                            fragment=new ReceiptFragment();
                                            Bundle args = new Bundle();
                                            args.putString("url",ReceiptUrl);
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
//                                    prepaareList();
//                                    expandableListAdapterPayment = new ExpandableListAdapterPayment(getActivity(), listDataHeader, listDataChildPayment);
//                                    lvExpPayment.setAdapter(expandableListAdapterPayment);
                                } else {
                                    txtNoRecordsUnitTest.setVisibility(View.GONE);
                                    payment_history.setVisibility(View.GONE);
                                    lv_header.setVisibility(View.GONE);
                                    payment_report_list.setVisibility(View.GONE);
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

    public void prepaareList() {
        listDataHeader = new ArrayList<>();
        listDataChildPayment = new HashMap<String, ArrayList<PaymentLedgerModel.Data>>();

        for (int j = 0; j < paymentdetailsModel.size(); j++) {
            listDataHeader.add(paymentdetailsModel.get(j).getPayDate() + "|" + paymentdetailsModel.get(j).getPaid());

            ArrayList<PaymentLedgerModel.Data> rows = new ArrayList<PaymentLedgerModel.Data>();
            for (int k = 0; k < paymentdetailsModel.get(j).getDataArrayList().size(); k++) {
                rows.add(paymentdetailsModel.get(j).getDataArrayList().get(k));
            }
            listDataChildPayment.put(listDataHeader.get(j), rows);
        }


    }
}
