package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.Adapter.ExpandableListAdapterPayment;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.FeesDetailsAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetPaymentLedgerAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.FeesModel;
import com.anandniketanbhadaj.skool360.skool360.Models.PaymentLedgerModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PaymentFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnBackUnitTest;
    private TextView txtNoRecordsUnitTest, previous_balance_term1_txt, previous_balance_term2_txt,
            tutionfees_term2_txt, tutionfees_term1_txt, transportfees_term1_txt, transportfees_term2_txt,
            imprest_term1_txt, imprest_term2_txt, latefees_term1_txt, latefees_term2_txt, totalpayablefees_term1_txt,
            totalpayablefees_term2_txt, paidfees_term1_txt, paidfees_term2_txt, paynow_term1_txt, paynow_term2_txt,
            admission_fees_term1_txt, admission_fees_term2_txt, caution_fees_term1_txt, caution_fees_term2_txt, discount_fee_term1_txt, discount_fee_term2_txt,
            balance_term1_txt, balance_term2_txt, payment_history, term_fees_term1_txt, term_fees_term2_txt;
    private Context mContext;
    private FragmentManager fragmentManager = null;
    private ProgressDialog progressDialog = null;
    private FeesDetailsAsyncTask getFeesDetailsAsyncTask = null;
    private ArrayList<FeesModel> feesdetailModels = new ArrayList<>();
    ArrayList<FeesModel.Data> feesData = new ArrayList<>();
    List<String> termheader;
    ArrayList<String> listDataChild;

    ExpandableListView lvExpPayment;
    private int lastExpandedPosition = -1;
    private GetPaymentLedgerAsyncTask getPaymentLedgerAsyncTask = null;
    private ArrayList<PaymentLedgerModel> paymentdetailsModel = new ArrayList<>();
    ExpandableListAdapterPayment expandableListAdapterPayment;
    //    LinkedList<String> listDataHeader;
    ArrayList<String> listDataHeader;
    HashMap<String, ArrayList<PaymentLedgerModel.Data>> listDataChildPayment;


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
        previous_balance_term1_txt = (TextView) rootView.findViewById(R.id.previous_balance_term1_txt);
        previous_balance_term2_txt = (TextView) rootView.findViewById(R.id.previous_balance_term2_txt);
        tutionfees_term2_txt = (TextView) rootView.findViewById(R.id.tutionfees_term2_txt);
        tutionfees_term1_txt = (TextView) rootView.findViewById(R.id.tutionfees_term1_txt);
        transportfees_term1_txt = (TextView) rootView.findViewById(R.id.transportfees_term1_txt);
        transportfees_term2_txt = (TextView) rootView.findViewById(R.id.transportfees_term2_txt);
        imprest_term1_txt = (TextView) rootView.findViewById(R.id.imprest_term1_txt);
        imprest_term2_txt = (TextView) rootView.findViewById(R.id.imprest_term2_txt);
        latefees_term1_txt = (TextView) rootView.findViewById(R.id.latefees_term1_txt);
        latefees_term2_txt = (TextView) rootView.findViewById(R.id.latefees_term2_txt);
        totalpayablefees_term1_txt = (TextView) rootView.findViewById(R.id.totalpayablefees_term1_txt);
        totalpayablefees_term2_txt = (TextView) rootView.findViewById(R.id.totalpayablefees_term2_txt);
        paidfees_term1_txt = (TextView) rootView.findViewById(R.id.paidfees_term1_txt);
        paidfees_term2_txt = (TextView) rootView.findViewById(R.id.paidfees_term2_txt);
        paynow_term1_txt = (TextView) rootView.findViewById(R.id.paynow_term1_txt);
        paynow_term2_txt = (TextView) rootView.findViewById(R.id.paynow_term2_txt);
        lvExpPayment = (ExpandableListView) rootView.findViewById(R.id.lvExpPayment);
        balance_term1_txt = (TextView) rootView.findViewById(R.id.balance_term1_txt);
        balance_term2_txt = (TextView) rootView.findViewById(R.id.balance_term2_txt);
        admission_fees_term1_txt = (TextView) rootView.findViewById(R.id.admission_fees_term1_txt);
        admission_fees_term2_txt = (TextView) rootView.findViewById(R.id.admission_fees_term2_txt);
        caution_fees_term1_txt = (TextView) rootView.findViewById(R.id.caution_fees_term1_txt);
        caution_fees_term2_txt = (TextView) rootView.findViewById(R.id.caution_fees_term2_txt);
        discount_fee_term1_txt = (TextView) rootView.findViewById(R.id.discount_fee_term1_txt);
        discount_fee_term2_txt = (TextView) rootView.findViewById(R.id.discount_fee_term2_txt);
        payment_history = (TextView) rootView.findViewById(R.id.payment_history);
        term_fees_term1_txt = (TextView) rootView.findViewById(R.id.term_fees_term1_txt);
        term_fees_term2_txt = (TextView) rootView.findViewById(R.id.term_fees_term2_txt);

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
                Fragment fragment = new FeesFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        lvExpPayment.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lvExpPayment.collapseGroup(lastExpandedPosition);

                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    public void getFeesData() {
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
                        getFeesDetailsAsyncTask = new FeesDetailsAsyncTask(params);
                        feesdetailModels = getFeesDetailsAsyncTask.execute().get();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (feesdetailModels.size() > 0) {
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
        }else{
            Utility.ping(mContext,"Network not available");
        }
    }

    public void setData() {
        termheader = new ArrayList<String>();
        listDataChild = new ArrayList<>();
        ArrayList<FeesModel.Data> rows = new ArrayList<FeesModel.Data>();
        for (int i = 0; i < feesdetailModels.size(); i++) {
            for (int j = 0; j < feesdetailModels.get(i).getDataArrayList().size(); j++) {
                rows.add(feesdetailModels.get(i).getDataArrayList().get(j));
            }
        }
        for (int k = 0; k < rows.size(); k++) {
            previous_balance_term1_txt.setText(rows.get(0).getPreviousBalance());
            tutionfees_term1_txt.setText(rows.get(0).getTutionFees());
            transportfees_term1_txt.setText(rows.get(0).getTransportFees());
            imprest_term1_txt.setText(rows.get(0).getImprest());
            latefees_term1_txt.setText(rows.get(0).getLateFees());
            totalpayablefees_term1_txt.setText(rows.get(0).getTotalPayableFees());
            paidfees_term1_txt.setText(rows.get(0).getPaidFees());
            balance_term1_txt.setText(rows.get(0).getTotalFees());
            admission_fees_term1_txt.setText(rows.get(0).getAdmissionFees());
            caution_fees_term1_txt.setText(rows.get(0).getCautionFees());
            discount_fee_term1_txt.setText(rows.get(0).getDiscount());
            term_fees_term1_txt.setText(rows.get(0).getTermFees());

            previous_balance_term2_txt.setText(rows.get(1).getPreviousBalance());
            tutionfees_term2_txt.setText(rows.get(1).getTutionFees());
            transportfees_term2_txt.setText(rows.get(1).getTransportFees());
            imprest_term2_txt.setText(rows.get(1).getImprest());
            latefees_term2_txt.setText(rows.get(1).getLateFees());
            totalpayablefees_term2_txt.setText(rows.get(1).getTotalPayableFees());
            paidfees_term2_txt.setText(rows.get(1).getPaidFees());
            balance_term2_txt.setText(rows.get(1).getTotalFees());
            admission_fees_term2_txt.setText(rows.get(1).getAdmissionFees());
            caution_fees_term2_txt.setText(rows.get(1).getCautionFees());
            discount_fee_term2_txt.setText(rows.get(1).getDiscount());
            term_fees_term2_txt.setText(rows.get(0).getTermFees());
        }

//        if (totalpayablefees_term1_txt.getText().toString().equalsIgnoreCase("0.00")) {
//            paidfees_term1_txt.setBackgroundColor(getResources().getColor(R.color.green));
//        }
//
//        if (!totalpayablefees_term2_txt.getText().toString().equalsIgnoreCase("0.00")) {
//            totalpayablefees_term2_txt.setBackgroundColor(getResources().getColor(R.color.red));
//        }
//        if (buttonvalue1.equalsIgnoreCase("true")) {
//            paynow_term1_txt.setVisibility(View.VISIBLE);
//        } else {
//            paynow_term1_txt.setVisibility(View.GONE);
//        }
//        if (buttonvalue2.equalsIgnoreCase("true")) {
//            paynow_term2_txt.setVisibility(View.VISIBLE);
//        } else {
//            paynow_term2_txt.setVisibility(View.GONE);
//        }

    }

    public void getPaymentLedger() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(Utility.isNetworkConnected(mContext)) {
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
                                    prepaareList();
                                    expandableListAdapterPayment = new ExpandableListAdapterPayment(getActivity(), listDataHeader, listDataChildPayment);
                                    lvExpPayment.setAdapter(expandableListAdapterPayment);
                                } else {
                                    txtNoRecordsUnitTest.setVisibility(View.VISIBLE);
                                    payment_history.setVisibility(View.GONE);
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    Utility.ping(mContext,"Network not available");
                }
            }
        }).start();
    }

    public void prepaareList() {
        listDataHeader = new ArrayList<>();
        listDataChildPayment = new HashMap<String, ArrayList<PaymentLedgerModel.Data>>();

        for (int i = 0; i < paymentdetailsModel.size(); i++) {
            paymentdemo pdemo = new paymentdemo();
            pdemo.PayDate = paymentdetailsModel.get(i).getPayDate().toString();
            pdemo.Paid = paymentdetailsModel.get(i).getPaid().toString();
            listDataHeader.add(pdemo.PayDate.toString() + "|" + pdemo.Paid.toString());
            Log.d("displaypositiondata", listDataHeader.get(0));

            ArrayList<PaymentLedgerModel.Data> rows = new ArrayList<PaymentLedgerModel.Data>();
            for (int j = 0; j < paymentdetailsModel.get(i).getDataArrayList().size(); j++) {
                rows.add(paymentdetailsModel.get(i).getDataArrayList().get(j));

            }
            listDataChildPayment.put(listDataHeader.get(i), rows);
        }
    }

    public class paymentdemo {
        private String PayDate;
        private String Paid;
    }
}
