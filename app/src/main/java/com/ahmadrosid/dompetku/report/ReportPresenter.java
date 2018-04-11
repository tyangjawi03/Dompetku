package com.ahmadrosid.dompetku.report;

import android.graphics.Bitmap;
import android.os.Environment;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.utils.models.Transaction;
import com.ahmadrosid.dompetku.utils.models.TransactionRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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

        Report.Category makan = new Report.Category();
        makan.title = "Makan";
        makan.amount = 0;

        Report.Category online = new Report.Category();
        online.title = "Belanja Online";
        online.amount = 0;

        for (Transaction transaction : data) {
            if (transaction.type.ordinal() == Transaction.TransactionType.PEMASUKAN.ordinal()) {
                balance += transaction.amount;
            } else {
                balance -= transaction.amount;
                expend += transaction.amount;

                if (transaction.title.toLowerCase().contains("makan") || transaction.title.toLowerCase().contains("sarapan")) {
                    makan.amount += transaction.amount;
                } else if (transaction.title.toLowerCase().contains("online")) {
                    online.amount += transaction.amount;
                } else {
                    Report.Category category = new Report.Category();
                    category.title = transaction.title;
                    category.amount = transaction.amount;

                    categories.add(category);
                }
            }
        }

        if (makan.amount > 0)
            categories.add(makan);

        if (online.amount > 0)
            categories.add(online);

        Collections.sort(categories, new Comparator<Report.Category>() {
            @Override
            public int compare(Report.Category category, Report.Category t1) {
                return t1.amount - category.amount;
            }
        });

        List<Report.Category> categoryList = compressList(categories);

        Report report = new Report();
        report.setBalance(balance);
        report.setExpend(expend);
        report.setReportDate(calendar);
        report.setDetilExpend(categoryList);

        view.showReport(report);

    }

    public List<Report.Category> compressList(List<Report.Category> categories) {
        if (categories.size() <= 10) {
            return categories;
        } else {
            List<Report.Category> categoryList = new ArrayList<Report.Category>();

            for (int i = 0; i < categories.size() - 1; i++) {
                categoryList.add(categories.get(i));
            }

            categoryList.get(categoryList.size() - 1).title = "Lain-lain";
            categoryList.get(categoryList.size() - 1).amount += categories.get(categories.size()-1).amount;

            return compressList(categoryList);
        }
    }

    @Override
    public void share(Bitmap bitmap) {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        try {
            File sdCard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File file = new File(sdCard, "DompetKu_report_"+ dateFormat.format(calendar.getTimeInMillis())+".jpg");
            FileOutputStream fos = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, fos);

            view.imageGenerated(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
