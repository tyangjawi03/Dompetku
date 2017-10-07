package com.ahmadrosid.dompetku.report;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ahmadrosid.dompetku.R;

public class ReportActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ReportActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }

}
