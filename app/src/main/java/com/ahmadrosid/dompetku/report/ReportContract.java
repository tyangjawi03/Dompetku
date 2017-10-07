package com.ahmadrosid.dompetku.report;

import android.graphics.Bitmap;

import java.util.Calendar;

/**
 * Created by yogja on 10/7/17.
 */

public interface ReportContract {

    interface View {
        void showReport(Report report);
    }

    interface Presenter {
        void loadReport(Calendar calendar);
        void share(Bitmap bitmap);
    }

}
