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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetReportcardAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.ReportCardModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class ReportCardFragment extends Fragment {
    WebView webview_report_card;
    HashMap<Integer, String> spinnerTermDetailIdMap;
    String FinalTermDetailIdStr;
    private View rootView;
    private Button btnMenu, btnBackUnitTest;
    private TextView txtNoRecordsUnitTest;
    private Context mContext;
    private Spinner term_detail_spinner;
    private RadioGroup termrg;
    private RadioButton term1rb, term2rb;
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

        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        txtNoRecordsUnitTest = (TextView) rootView.findViewById(R.id.txtNoRecordsUnitTest);
        btnBackUnitTest = (Button) rootView.findViewById(R.id.btnBackUnitTest);
        webview_report_card = (WebView) rootView.findViewById(R.id.webview);
        term_detail_spinner = (Spinner) rootView.findViewById(R.id.term_detail_spinner);
        termrg = (RadioGroup) rootView.findViewById(R.id.termrg);
        term1rb = (RadioButton) rootView.findViewById(R.id.term1_rb);
        term2rb = (RadioButton) rootView.findViewById(R.id.term2_rb);

        WebSettings webSettings = webview_report_card.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview_report_card.getSettings().setUseWideViewPort(true);
        webview_report_card.getSettings().setLoadWithOverviewMode(true);
        webview_report_card.getSettings().setBuiltInZoomControls(true);
        // Force links and redirects to open in the WebView instead of in a browser
        webview_report_card.setWebViewClient(new WebViewClient());

//        fillTermDetailSpinner();
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

        termrg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioButtonID=termrg.getCheckedRadioButtonId();
                switch (radioButtonID){
                    case R.id.term1_rb:
                        FinalTermDetailIdStr="1";
                        break;
                    case R.id.term2_rb:
                        FinalTermDetailIdStr="2";
                        break;
                }
                getReportData();

            }
        });
//        term_detail_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String name = term_detail_spinner.getSelectedItem().toString();
//                String getid = spinnerTermDetailIdMap.get(term_detail_spinner.getSelectedItemPosition());
//
//                Log.d("TermDetailValue", name + "" + getid);
//                FinalTermDetailIdStr = getid.toString();
//                Log.d("FInalTermDetailId", FinalTermDetailIdStr);
//                getReportData();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

    }

    public void getReportData() {
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
                        params.put("Studentid", Utility.getPref(mContext, "studid"));
                        params.put("TermID", Utility.getPref(mContext, "TermID"));
                        params.put("TermDetailID", FinalTermDetailIdStr);
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
        } else {
            Utility.ping(mContext, "Network not available");
        }
    }

    //Use for Fill TermDetail Spinner
    public void fillTermDetailSpinner() {
        ArrayList<Integer> termdetailId = new ArrayList<>();
        termdetailId.add(1);
        termdetailId.add(2);


        ArrayList<String> termdetail = new ArrayList<>();
        termdetail.add("Term 1");
        termdetail.add("Term 2");


        String[] spinnertermdetailIdArray = new String[termdetailId.size()];

        spinnerTermDetailIdMap = new HashMap<Integer, String>();
        for (int i = 0; i < termdetailId.size(); i++) {
            spinnerTermDetailIdMap.put(i, String.valueOf(termdetailId.get(i)));
            spinnertermdetailIdArray[i] = termdetail.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(term_detail_spinner);

            popupWindow.setHeight(spinnertermdetailIdArray.length > 2 ? 500 : spinnertermdetailIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTermdetail = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermdetailIdArray);
        term_detail_spinner.setAdapter(adapterTermdetail);
        Log.d("termDetailSpinner", String.valueOf(Arrays.asList(spinnertermdetailIdArray)));
    }
}
