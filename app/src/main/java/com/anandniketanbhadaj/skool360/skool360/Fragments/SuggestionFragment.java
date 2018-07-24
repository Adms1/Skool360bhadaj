package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.CreateSuggestionAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.InsertStudentLeaveAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.CreateLeaveModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.Calendar;
import java.util.HashMap;


public class SuggestionFragment extends Fragment {
    CreateLeaveModel suggestionResponse;
    String purpose, description;
    Fragment fragment;
    FragmentManager fragmentManager;
    Dialog thankyouDialog;
    private View rootView;
    private Context mContext;
    private EditText edtSubject, edtSuggestion;
    private Button btnSave, btnCancel, btnMenu, btnBack;
    private ProgressDialog progressDialog = null;
    private CreateSuggestionAsyncTask createSuggestionAsyncTask = null;

    public SuggestionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_suggestion, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        edtSubject = (EditText) rootView.findViewById(R.id.edtSubject);
        edtSuggestion = (EditText) rootView.findViewById(R.id.edtSuggestion);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        btnBack = (Button) rootView.findViewById(R.id.btnBack);

    }

    public void setListners() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashBoardActivity.onLeft();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 0;
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getsendSuggestionData();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSubject.setText("");
                edtSuggestion.setText("");
            }
        });
    }

    public void getsendSuggestionData() {
        purpose = edtSubject.getText().toString();
        description = edtSuggestion.getText().toString();

        if (Utility.isNetworkConnected(mContext)) {
            if (!purpose.equalsIgnoreCase("") && !description.equalsIgnoreCase("")) {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HashMap<String, String> params = new HashMap<String, String>();
                            params.put("StudentId", Utility.getPref(mContext, "studid"));
                            params.put("Subject", purpose);
                            params.put("Comment", description);

                            createSuggestionAsyncTask = new CreateSuggestionAsyncTask(params);
                            suggestionResponse = createSuggestionAsyncTask.execute().get();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    if (suggestionResponse.getSuccess().equalsIgnoreCase("True")) {
                                        edtSubject.setText("");
                                        edtSuggestion.setText("");
                                        ThankyouDialog();
                                    } else {
                                        progressDialog.dismiss();

                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else {
                Utility.ping(mContext, "Blank field not allowed.");
            }
        } else {
            Utility.ping(mContext, "Network not available");
        }
    }

    public void ThankyouDialog() {
        thankyouDialog = new Dialog(getActivity(), R.style.Theme_Dialog1);
        Window window = thankyouDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        thankyouDialog.getWindow().getAttributes().verticalMargin = 0.10f;
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        thankyouDialog.getWindow().setBackgroundDrawableResource(R.color.white);

        thankyouDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        thankyouDialog.setCancelable(false);
        thankyouDialog.setContentView(R.layout.thankyou_dialog);
        TextView ok_txt = (TextView) thankyouDialog.findViewById(R.id.ok_txt);


        ok_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thankyouDialog.dismiss();
            }
        });
        thankyouDialog.show();
    }
}
