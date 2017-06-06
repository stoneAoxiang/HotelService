package com.hotel.service.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotel.service.R;


public class AboutPassionlifeActivity extends Activity {
    private ImageView returnBack;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about_passionlife);
        returnBack = (ImageView) findViewById(R.id.return_back);

        textView = (TextView) findViewById(R.id.activity_name);
        textView.setText("关于");

        returnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }


}
