package com.ahmadrosid.dompetku.report;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ahmadrosid.dompetku.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportActivity extends AppCompatActivity implements ReportContract.View {


    @BindView(R.id.report_date)
    TextView reportDate;
    @BindView(R.id.current_balance)
    TextView currentBalance;
    @BindView(R.id.expend)
    TextView expend;
    @BindView(R.id.detail_list)
    ListView detailList;
    @BindView(R.id.content_report)
    LinearLayout contentReport;

    private ReportContract.Presenter presenter;

    public static void start(Context context) {
        Intent starter = new Intent(context, ReportActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        presenter = new ReportPresenter(this);

        Calendar calendar = Calendar.getInstance();

        presenter.loadReport(calendar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                contentReport.setDrawingCacheEnabled(true);
                presenter.share(contentReport.getDrawingCache());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showReport(Report report) {
        reportDate.setText(report.getReportDate());
        currentBalance.setText(report.getBalance());
        expend.setText(report.getExpend());

        ReportAdapter reportAdapter = new ReportAdapter(this, report.getDetilExpend());
        detailList.setAdapter(reportAdapter);
    }

    @Override
    public void imageGenerated(File path) {

        Intent share = new Intent(Intent.ACTION_SEND);

        // Type of file to share
        share.setType("image/jpeg");

        // Locate the image to Share
        Uri uri = Uri.fromFile(path);

        // Pass the image into an Intnet
        share.putExtra(Intent.EXTRA_STREAM, uri);

        // Show the social share chooser list
        startActivity(Intent.createChooser(share, "Share Report"));
    }

}
