package com.ahmadrosid.dompetku.report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ahmadrosid.dompetku.R;

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
    public void showReport(Report report) {
        reportDate.setText(report.getReportDate());
        currentBalance.setText(report.getBalance());
        expend.setText(report.getExpend());

        ReportAdapter reportAdapter = new ReportAdapter(this, report.getDetilExpend());
        detailList.setAdapter(reportAdapter);
    }

}
