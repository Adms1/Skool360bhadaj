package com.anandniketanbhadaj.skool360.skool360.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.ChangePasswordAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.ForgotpasswordAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetHolidayAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.VerifyLoginAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.ExamModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;

public class LoginActivity extends Activity {


    //Use for dialog
    Dialog forgotDialog;
    String communicationnoStr;
    ExamModel forgotModelResponse;
    private ForgotpasswordAsyncTask forgotpasswordAsyncTask = null;
    private EditText edtUserName, edtPassword, edtmobileno;
    private TextView forgot_title_txt;
    private Button btnLogin, cancel_btn, submit_btn;
    private CheckBox chkRemember;
    private VerifyLoginAsyncTask verifyLoginAsyncTask = null;
    private Context mContext;
    private ProgressDialog progressDialog;
    private HashMap<String, String> result = new HashMap<String, String>();
    private HashMap<String, String> param = new HashMap<String, String>();
    private String putExtras = "0";
    private String putExtrasData = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        putExtrasData = getIntent().getStringExtra("message");
        putExtras = getIntent().getStringExtra("fromNotification");//getAction();

        System.out.println("Login Extra : " + putExtrasData);
        Log.d("Data", Utility.getPref(mContext, "data"));
        Log.d("message", Utility.getPref(mContext, "message"));
        checkUnmPwd();
        initViews();
        setListners();
    }

    public void initViews() {
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        chkRemember = (CheckBox) findViewById(R.id.chkRemember);
        forgot_title_txt = (TextView) findViewById(R.id.forgot_title_txt);
    }

    public void setListners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isNetworkConnected(mContext)) {
                    if (!edtUserName.getText().toString().equalsIgnoreCase("")) {
                        if (!edtPassword.getText().toString().equalsIgnoreCase("")) {
                            login();
                        } else {
                            Utility.pong(mContext, "Please Enter Password");
                            edtPassword.requestFocus();
                        }
                    } else {
                        Utility.pong(mContext, "Please Enter User Name");
                        edtUserName.requestFocus();
                    }
                } else {
                    Utility.ping(mContext, "Network not available");
                }
            }
        });
        forgot_title_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPasswordDialog();
            }
        });
    }

    public void login() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        param.put("UserID", edtUserName.getText().toString().trim());
        param.put("Password", edtPassword.getText().toString().trim());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    verifyLoginAsyncTask = new VerifyLoginAsyncTask(param);
                    result = verifyLoginAsyncTask.execute().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (result.size() > 0) {
//                              TODO: Store result values for future use
//                                if (chkRemember.isChecked()) {
                                    saveUserNamePwd(edtUserName.getText().toString(), edtPassword.getText().toString());
//                                }
                                Utility.setPref(mContext, "studid", result.get("StudentID"));//
                                Utility.setPref(mContext, "FamilyID", result.get("FamilyID"));
                                Utility.setPref(mContext, "standardID", result.get("StandardID"));
                                Utility.setPref(mContext, "ClassID", result.get("ClassID"));
                                Utility.setPref(mContext, "TermID", result.get("TermID"));//result.get("TermID"));
                                Utility.setPref(mContext, "RegisterStatus", result.get("RegisterStatus"));

                                Utility.pong(mContext, "Login Successful");
                                Intent intentDashboard = new Intent(LoginActivity.this, DashBoardActivity.class);//SplashScreenActivity
                                intentDashboard.putExtra("message", putExtrasData);
                                intentDashboard.putExtra("fromNotification", putExtras);
                                System.out.println("messageLogin: " + putExtrasData);
                                startActivity(intentDashboard);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                finish();

                            } else {
                                Utility.pong(mContext, "Invalid Username/ password");
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void checkUnmPwd() {
        if (!Utility.getPref(mContext, "unm").equalsIgnoreCase("")) {

            Intent intentDashboard = new Intent(LoginActivity.this, DashBoardActivity.class);
            intentDashboard.putExtra("message", putExtrasData);
            intentDashboard.putExtra("fromNotification", putExtras);
            startActivity(intentDashboard);
            finish();
        }
    }

    public void saveUserNamePwd(String unm, String pwd) {
        Utility.setPref(mContext, "unm", unm);
        Utility.setPref(mContext, "pwd", pwd);
    }

    public void forgotPasswordDialog() {
        forgotDialog = new Dialog(mContext, R.style.Theme_Dialog);
        Window window = forgotDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        forgotDialog.getWindow().getAttributes().verticalMargin = 0.0f;
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

//        forgotDialog.getWindow().setBackgroundDrawableResource(R.drawable.session_confirm);
        forgotDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        forgotDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        forgotDialog.setCancelable(false);
        forgotDialog.setContentView(R.layout.forgot_password);

        cancel_btn = (Button) forgotDialog.findViewById(R.id.cancel_btn);
        submit_btn = (Button) forgotDialog.findViewById(R.id.submit_btn);
        edtmobileno = (EditText) forgotDialog.findViewById(R.id.edtmobileno);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotDialog.dismiss();
            }
        });

        edtmobileno.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i== EditorInfo.IME_ACTION_DONE){
                    communicationnoStr = edtmobileno.getText().toString();
                    if (!communicationnoStr.equalsIgnoreCase("")) {
                        if (communicationnoStr.length() >= 10) {
                            getForgotData();
                        } else {
                            edtmobileno.setError("Please enter valid no");
                        }
                    } else {
                        edtmobileno.setError("Please enter communication no");
                    }
                }
                return false;
            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicationnoStr = edtmobileno.getText().toString();
                if (!communicationnoStr.equalsIgnoreCase("")) {
                    if (communicationnoStr.length() >= 10) {
                        getForgotData();
                    } else {
                        edtmobileno.setError("Please enter valid no");
                    }
                } else {
                    edtmobileno.setError("Please enter communication no");
                }
            }
        });

        forgotDialog.show();

    }

    public void getForgotData() {
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
                        params.put("MobileNo", communicationnoStr);
                        forgotpasswordAsyncTask = new ForgotpasswordAsyncTask(params);
                        forgotModelResponse = forgotpasswordAsyncTask.execute().get();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (forgotModelResponse.getSuccess().equalsIgnoreCase("True")) {
                                    Utility.ping(mContext,"Please check your inbox for id or password");
                                    forgotDialog.dismiss();
                                } else {
                                    progressDialog.dismiss();
                                    Utility.ping(mContext,forgotModelResponse.getMessage().toString());
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
}
