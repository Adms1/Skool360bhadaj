package com.anandniketanbhadaj.skool360.skool360.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.anandniketanbhadaj.skool360.R;

public class Server_Error extends AppCompatActivity implements View.OnClickListener {
    Button ok_btn;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server__error);
        getSupportActionBar().hide();
        ok_btn = findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(this);
        imageView = findViewById(R.id.imageView);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ok_btn) {
            System.exit(0);
        }
    }
}
