package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.Adapter.NotificationMessageListAdapter;

import java.util.ArrayList;


public class NotificationFragment extends Activity {

    Button btnMenu, btnBackNotification;
    TextView txtNoRecordsNotification;
    Context mContext;
    NotificationMessageListAdapter notificationMessageListAdapter = null;
    ListView listMessageNotificationData;
    private ProgressDialog progressDialog = null;
    private String putExtras = "0";
    ArrayList<String> message = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notification);
        mContext=NotificationFragment.this;
        putExtras = getIntent().getStringExtra("message");
        Log.d("MEghaData", putExtras);
        message.add(putExtras);
        initViews();
        setListners();
    }

    public void initViews() {
        btnMenu = (Button) findViewById(R.id.btnMenu);
        txtNoRecordsNotification = (TextView) findViewById(R.id.txtNoRecordsNotification);
        btnBackNotification = (Button) findViewById(R.id.btnBackNotification);
        listMessageNotificationData = (ListView) findViewById(R.id.listMessageNotificationData);
        notificationMessageListAdapter = new NotificationMessageListAdapter(mContext, message);
        listMessageNotificationData.setAdapter(notificationMessageListAdapter);
        listMessageNotificationData.deferNotifyDataSetChanged();
    }

    public void setListners() {

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });

        btnBackNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DashBoardActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });

    }

}