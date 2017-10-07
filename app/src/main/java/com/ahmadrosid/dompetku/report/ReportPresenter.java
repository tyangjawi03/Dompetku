package com.ahmadrosid.dompetku.report;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.models.Transaction;
import com.ahmadrosid.dompetku.models.TransactionRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by yogja on 10/7/17.
 */

public class ReportPresenter implements ReportContract.Presenter {

    private ReportContract.View view;

    @Inject
    TransactionRepository transactionRepository;

    public ReportPresenter(ReportContract.View view) {
        this.view = view;
        DompetkuApp.getIntance().getAppComponent().inject(this);
    }

    @Override
    public void loadReport(Calendar calendar) {
        Calendar start = Calendar.getInstance();
        start.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 0, 23, 59, 59);

        Calendar end = Calendar.getInstance();
        end.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, 0, 23, 59, 59);

        List<Transaction> data = transactionRepository.getReport(start.getTimeInMillis(), end.getTimeInMillis());

        int balance = 0;
        int expend = 0;

        List<Report.Category> categories = new ArrayList<>();

        for (Transaction transaction : data) {
            if (transaction.type.ordinal() == Transaction.TransactionType.PEMASUKAN.ordinal()) {
                balance += transaction.amount;
            } else {
                balance -= transaction.amount;
                expend += transaction.amount;

                Report.Category category = new Report.Category();
                category.title = transaction.title;
                category.amount = transaction.amount;

                categories.add(category);
            }
        }

        Report report = new Report();
        report.setBalance(balance);
        report.setExpend(expend);
        report.setReportDate(calendar);
        report.setDetilExpend(categories);

        view.showReport(report);

    }

}
