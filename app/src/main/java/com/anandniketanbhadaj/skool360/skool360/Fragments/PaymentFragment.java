package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.Activities.Server_Error;
import com.anandniketanbhadaj.skool360.skool360.Adapter.PaymentPageAdapter;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.FeesDetailsAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.FeesModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PaymentFragment extends Fragment {
    FeesModel feesMainResponse;
    List<String> termheader;
    TableRow tableRow13;
    Fragment fragment;
    LinearLayout linearBack;
    View line_view;
    View view;
    PaymentPageAdapter adapter;
    private TextView paynow_term1_txt, paynow_term2_txt, payment_history;
    private View rootView;
    private Button btnMenu, btnBackUnitTest;
    private TextView txtNoRecordsUnitTest;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private FeesDetailsAsyncTask getFeesDetailsAsyncTask = null;
    private FragmentManager fragmentManager = null;
    private LinearLayout table_layout;
    //TabLAyout
    private TabLayout tablayout_ptm_main;
    private ViewPager viewPager;

    public PaymentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_payment, container, false);
        mContext = getActivity();

        AppConfiguration.position = 10;

        initViews();
        setListners();
        getFeesData();
        return rootView;
    }

    public void initViews() {
        btnMenu = rootView.findViewById(R.id.btnMenu);
        txtNoRecordsUnitTest = rootView.findViewById(R.id.txtNoRecordsUnitTest);
        btnBackUnitTest = rootView.findViewById(R.id.btnBackUnitTest);
        linearBack = rootView.findViewById(R.id.linearBack);
        paynow_term1_txt = rootView.findViewById(R.id.paynow_term1_txt);
        paynow_term2_txt = rootView.findViewById(R.id.paynow_term2_txt);
        payment_history = rootView.findViewById(R.id.payment_history);
        tableRow13 = rootView.findViewById(R.id.tableRow13);
        table_layout = rootView.findViewById(R.id.table_layout);
        line_view = rootView.findViewById(R.id.line_view);

        viewPager = rootView.findViewById(R.id.pager);
        view = rootView.findViewById(R.id.view);
        tablayout_ptm_main = rootView.findViewById(R.id.tablayout_ptm_main);
        tablayout_ptm_main.addTab(tablayout_ptm_main.newTab().setText("Payment Summary"), true);
        tablayout_ptm_main.addTab(tablayout_ptm_main.newTab().setText("Online Transaction"));

        tablayout_ptm_main.setTabMode(TabLayout.MODE_FIXED);
        tablayout_ptm_main.setTabGravity(TabLayout.GRAVITY_FILL);


        adapter = new PaymentPageAdapter(getFragmentManager(), tablayout_ptm_main.getTabCount());
//Adding adapter to pager
        viewPager.setAdapter(adapter);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void setListners() {
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                tablayout_ptm_main));
        tablayout_ptm_main.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });

        btnBackUnitTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                getChildFragmentManager().beginTransaction().replace(R.id.frame_container, new FeesFragment()).addToBackStack(null).commit();
//
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
        paynow_term1_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!feesMainResponse.getPaynowurl().equalsIgnoreCase("")) {
                    fragment = new PayOnlineFragment();
                    Bundle args = new Bundle();
                    args.putString("url", feesMainResponse.getPaynowurl());
                    fragment.setArguments(args);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                } else {
//                    new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.AppTheme))
//                            .setCancelable(false)
//                            .setIcon(mContext.getResources().getDrawable(R.drawable.ic_launcher))
//                            .setMessage(feesMainResponse.getTerm1Msg())
//                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            })
//                            .setIcon(R.drawable.ic_launcher)
//                            .show();
                }
            }
        });
//        paynow_term2_txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (feesMainResponse.getTerm2Msg().equalsIgnoreCase("")) {
//                    fragment = new PayOnlineFragment();
//                    Bundle args = new Bundle();
//                    args.putString("url", feesMainResponse.getTerm2URL());
//                    fragment.setArguments(args);
//                    fragmentManager = getFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
//                            .replace(R.id.frame_container, fragment).commit();
//                } else {
//                    new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.AppTheme))
//                            .setCancelable(false)
//                            .setIcon(mContext.getResources().getDrawable(R.drawable.ic_launcher))
//                            .setMessage(feesMainResponse.getTerm2Msg())
//                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            })
//                            .setIcon(R.drawable.ic_launcher)
//                            .show();
//                }
//            }
//        });
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

                        HashMap<String, String> params = new HashMap<>();
                        params.put("StudentID", Utility.getPref(mContext, "studid"));
                        params.put("Term", Utility.getPref(mContext, "TermID"));
//                        params.put("StandardID", Utility.getPref(mContext, "standardID"));
//                        params.put("Term", FinalTermIdStr);
                        params.put("LocationID", Utility.getPref(mContext, "locationId"));

                        getFeesDetailsAsyncTask = new FeesDetailsAsyncTask(params);
                        feesMainResponse = getFeesDetailsAsyncTask.execute().get();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (feesMainResponse != null) {
                                    if (feesMainResponse.getFinalArray().size() > 0) {
                                        txtNoRecordsUnitTest.setVisibility(View.GONE);
                                        progressDialog.dismiss();
                                        setData();
                                    } else {
                                        progressDialog.dismiss();
                                        txtNoRecordsUnitTest.setVisibility(View.VISIBLE);

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
                }
            }).start();
        } else {
            Utility.ping(mContext, "Network not available");
        }
    }

    public void setData() {
        termheader = new ArrayList<>();

//        if (feesMainResponse.getTerm1Btn().equals(false) && feesMainResponse.getTerm2Btn().equals(false)) {
//            tableRow13.setVisibility(View.GONE);
//            line_view.setVisibility(View.VISIBLE);
//        } else {
//            line_view.setVisibility(View.GONE);
//            tableRow13.setVisibility(View.VISIBLE);
//        }
        if (feesMainResponse.getPaynowbtn().equals("true")) {
            paynow_term1_txt.setVisibility(View.VISIBLE);
            tableRow13.setVisibility(View.VISIBLE);
        } else {
            paynow_term1_txt.setVisibility(View.GONE);
            tableRow13.setVisibility(View.GONE);
        }

//        if (feesMainResponse.getTerm2Btn().equals(false)) {
//            paynow_term2_txt.setVisibility(View.GONE);
//        } else {
//            paynow_term2_txt.setVisibility(View.VISIBLE);
//        }

        for (int i = 0; i < feesMainResponse.getFinalArray().size(); i++) {
            LinearLayout childLayout = new LinearLayout(mContext);
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            LinearLayout.LayoutParams linearParams1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.3f);

            LinearLayout.LayoutParams linearParams2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.6f);

            //childLayout.setOrientation(LinearLayout.HORIZONTAL);
            childLayout.setLayoutParams(linearParams);

            EditText mType = new EditText(mContext);
            TextView mValue = new TextView(mContext);
//            TextView mValue1 = new TextView(mContext);
            linearParams1.setMargins(2, 1, 0, 0);
            linearParams2.setMargins(2, 1, 0, 0);
            mValue.setLayoutParams(linearParams2);
//            linearParams2.setMargins(2, 1, 0, 0);
//            mValue1.setLayoutParams(linearParams2);

            mType.setPadding(5, 0, 0, 0);
            mValue.setPadding(0, 0, 5, 0);
//            mValue1.setPadding(0, 0, 5, 0);

//            mType.setWidth(271);
//            mValue.setWidth(220);
//            mValue1.setWidth(220);

//            mType.setHeight(80);
//            mValue.setHeight(80);
//            mValue1.setHeight(80);
            mType.setLayoutParams(linearParams1);
            mValue.setLayoutParams(linearParams2);
//            mValue1.setLayoutParams(linearParams2);

            mType.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            mType.setSingleLine(false);

            mValue.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            mValue.setSingleLine(false);

//            mValue1.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
//            mValue1.setSingleLine(false);


            mType.setBackgroundColor(getResources().getColor(R.color.white));
            mValue.setBackgroundColor(getResources().getColor(R.color.white));
//            mValue1.setBackgroundColor(getResources().getColor(R.color.white));

            mType.setTextSize(14);
            mType.setPadding(5, 3, 0, 3);
            mType.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

            mValue.setTextSize(14);
            mValue.setPadding(0, 3, 5, 3);
            mValue.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

//            mValue1.setTextSize(14);
//            mValue1.setPadding(0, 3, 5, 3);
//            mValue1.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

            //mType.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(15) });
//            String tenCharPerLineString = "";
//            String text="";
//            text=feesMainResponse.getFinalArray().get(i).getLedgerName();
//            String buffer = "";
//            while (text.length() > 15) {
//                buffer = text.substring(0, 15);
//
//                tenCharPerLineString = tenCharPerLineString + buffer + "\n";
//                text = text.substring(15);
//
//                mType.setText(buffer+"\n"+text.substring(0));
//            }
            if(feesMainResponse.getFinalArray().get(i).getLedgerName().equalsIgnoreCase("opening balance")
                    || feesMainResponse.getFinalArray().get(i).getLedgerName().equalsIgnoreCase("total dues")) {

                if ((feesMainResponse.getFinalArray().get(i).getTerm1Amt().contains("Cr")
                        || feesMainResponse.getFinalArray().get(i).getTerm1Amt().equalsIgnoreCase("0"))) {

                    mValue.setTextColor(getActivity().getResources().getColor(R.color.green_trophy_room));

                } else if (feesMainResponse.getFinalArray().get(i).getTerm1Amt().contains("Dr")) {

                    mValue.setTextColor(getActivity().getResources().getColor(R.color.red));

                } else {
                    mValue.setTextColor(getActivity().getResources().getColor(R.color.black));

                }

//                if ((feesMainResponse.getFinalArray().get(i).getTerm2Amt().contains("Cr")
//                        || feesMainResponse.getFinalArray().get(i).getTerm2Amt().equalsIgnoreCase("0"))) {
//
//                    mValue1.setTextColor(getActivity().getResources().getColor(R.color.green_trophy_room));
//
//                } else if (feesMainResponse.getFinalArray().get(i).getTerm2Amt().contains("Dr")) {
//
//                    mValue1.setTextColor(getActivity().getResources().getColor(R.color.red));
//
//                }else {
//                    mValue1.setTextColor(getActivity().getResources().getColor(R.color.black));
//
//                }

                mType.setTypeface(null, Typeface.BOLD);
                mValue.setTypeface(null, Typeface.BOLD);
//                mValue1.setTypeface(null, Typeface.BOLD);


            }

            mType.setText(feesMainResponse.getFinalArray().get(i).getLedgerName());
            mValue.setText("₹" + " " + String.valueOf(feesMainResponse.getFinalArray().get(i).getTerm1Amt()));
//            mValue1.setText("₹" + " " + String.valueOf(feesMainResponse.getFinalArray().get(i).getTerm2Amt()));
//            tenCharPerLineString = tenCharPerLineString + text.substring(0);

//            if (feesMainResponse.getFinalArray().get(i).getLedgerName().length() > 16 && feesMainResponse.getFinalArray().get(i).getLedgerName().length() < 32) {
//                mType.setText(feesMainResponse.getFinalArray().get(i).getLedgerName());//feesMainResponse.getFinalArray().get(i).getLedgerName()
//                mValue.setText("₹" + " " + String.valueOf(Math.round(feesMainResponse.getFinalArray().get(i).getTerm1Amt()) + "\n"));
//                mValue1.setText("₹" + " " + String.valueOf(Math.round(feesMainResponse.getFinalArray().get(i).getTerm2Amt()) + "\n"));
//            } else if (feesMainResponse.getFinalArray().get(i).getLedgerName().length() > 32) {
//                mType.setText(feesMainResponse.getFinalArray().get(i).getLedgerName()+ "\n" + "\n");//feesMainResponse.getFinalArray().get(i).getLedgerName()
//                mValue.setText("₹" + " " + String.valueOf(Math.round(feesMainResponse.getFinalArray().get(i).getTerm1Amt()) + "\n" + "\n"));
//                mValue1.setText("₹" + " " + String.valueOf(Math.round(feesMainResponse.getFinalArray().get(i).getTerm2Amt()) + "\n" + "\n"));
//            } else {
//                mType.setText(feesMainResponse.getFinalArray().get(i).getLedgerName());//feesMainResponse.getFinalArray().get(i).getLedgerName()
//                mValue.setText("₹" + " " + String.valueOf(Math.round(feesMainResponse.getFinalArray().get(i).getTerm1Amt())));
//                mValue1.setText("₹" + " " + String.valueOf(Math.round(feesMainResponse.getFinalArray().get(i).getTerm2Amt())));
//            }


            childLayout.addView(mType);
            childLayout.addView(mValue);
//            childLayout.addView(mValue1);

            table_layout.addView(childLayout);

        }

    }
}
