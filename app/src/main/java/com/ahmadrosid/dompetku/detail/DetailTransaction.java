package com.ahmadrosid.dompetku.detail;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.ahmadrosid.dompetku.models.Transaction;
import com.ahmadrosid.dompetku.transaction.TransactionContract;

/**
 * Created by yogja on 10/6/17.
 */

public class DetailTransaction extends Dialog implements TransactionContract.EditView {

    private Transaction transaction;

    public DetailTransaction(@NonNull Context context, Transaction data) {
        super(context);
        this.transaction = data;
    }

    @Override
    public void showData(Transaction transaction) {

    }

    @Override
    public void showError(String message) {

    }

}
