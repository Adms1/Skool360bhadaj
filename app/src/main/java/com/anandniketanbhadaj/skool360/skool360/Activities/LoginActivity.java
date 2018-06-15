package com.anandniketanbhadaj.skool360.skool360.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.VerifyLoginAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.HashMap;

public class LoginActivity extends Activity {

    private EditText edtUserName, edtPassword;
    private Button btnLogin;
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
        checkUnmPwd();
        initViews();
        setListners();
    }

    public void initViews() {
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        chkRemember = (CheckBox) findViewById(R.id.chkRemember);
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
                                if (chkRemember.isChecked()) {
                                    saveUserNamePwd(edtUserName.getText().toString(), edtPassword.getText().toString());
                                }
                                Utility.setPref(mContext, "studid", result.get("StudentID"));//
                                Utility.setPref(mContext, "FamilyID", result.get("FamilyID"));
                                Utility.setPref(mContext, "standardID", result.get("StandardID"));
                                Utility.setPref(mContext, "ClassID", result.get("ClassID"));
                                Utility.setPref(mContext, "TermID", result.get("TermID"));//result.get("TermID"));

                                Utility.pong(mContext, "Login Successful");
                                Intent intentDashboard = new Intent(LoginActivity.this, SplashScreenActivity.class);
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
                Intent intentDashboard = new Intent(LoginActivity.this, SplashScreenActivity.class);
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

}
