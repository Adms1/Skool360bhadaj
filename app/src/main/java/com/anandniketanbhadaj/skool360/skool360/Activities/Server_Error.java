package com.anandniketanbhadaj.skool360.skool360.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.anandniketanbhadaj.skool360.R;

public class Server_Error extends AppCompatActivity implements View.OnClickListener {
    Button ok_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server__error);
        getSupportActionBar().hide();
        ok_btn = (Button) findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ok_btn) {
            System.exit(0);
        }
    }
}
