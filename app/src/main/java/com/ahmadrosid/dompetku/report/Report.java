package com.ahmadrosid.dompetku.report;

import com.ahmadrosid.dompetku.helper.CurrencyHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yogja on 10/7/17.
 */

public class Report {

    private int balance;

    private int expend;

    private Calendar reportDate;

    private List<Category> detilExpend;

    public String getBalance() {
        return CurrencyHelper.format(balance);
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getExpend() {
        return CurrencyHelper.format(expend);
    }

    public void setExpend(int expend) {
        this.expend = expend;
    }

    public String getReportDate() {
        DateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        return format.format(reportDate.getTimeInMillis());
    }

    public void setReportDate(Calendar reportDate) {
        this.reportDate = reportDate;
    }

    public List<Category> getDetilExpend() {
        return detilExpend;
    }

    public void setDetilExpend(List<Category> detilExpend) {
        this.detilExpend = detilExpend;
    }

    public static class Category {
        public String title;
        public int amount;

        public String getAmount() {
            return CurrencyHelper.format(amount);
        }
    }

}
