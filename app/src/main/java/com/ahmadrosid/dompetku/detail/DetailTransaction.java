package com.ahmadrosid.dompetku.detail;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.helper.CurrencyHelper;
import com.ahmadrosid.dompetku.models.Transaction;
import com.ahmadrosid.dompetku.transaction.EditTransactionActivity;
import com.ahmadrosid.dompetku.utils.LabelTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yogja on 10/6/17.
 */

public class DetailTransaction extends Dialog {


    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.title)
    AppCompatTextView title;
    @BindView(R.id.title_bar)
    RelativeLayout titleBar;
    @BindView(R.id.item_amount)
    LabelTextView itemAmount;
    @BindView(R.id.item_time)
    LabelTextView itemTime;
    @BindView(R.id.btn_edit)
    Button btnEdit;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.item_title)
    LabelTextView itemTitle;
    @BindView(R.id.item_date)
    LabelTextView itemDate;

    private Transaction transaction;
    private DetailActionListener listener;

    public DetailTransaction(@NonNull Context context, Transaction data, DetailActionListener listener) {
        super(context);
        this.transaction = data;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.detail_view);
        ButterKnife.bind(this);

        if (transaction.type.ordinal() == Transaction.TransactionType.PEMASUKAN.ordinal()) {
            title.setText("Pemasukan");
            titleBar.setBackgroundResource(R.color.colorPrimary);
        } else {
            title.setText("Pengeluaran");
            titleBar.setBackgroundResource(R.color.colorAccent);
        }

        itemAmount.setLabel("Amount");
        itemAmount.setValue(CurrencyHelper.format(transaction.amount));

        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        itemDate.setLabel("Date");
        itemDate.setValue(dateFormat.format(transaction.date));

        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        itemTime.setLabel("Time");
        itemTime.setValue(timeFormat.format(transaction.date));

        itemTitle.setLabel("Title");
        itemTitle.setValue(transaction.title);

    }

    @OnClick({R.id.img_close, R.id.btn_edit, R.id.btn_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                break;
            case R.id.btn_edit:
                listener.onEditClick();
                break;
            case R.id.btn_delete:
                listener.onDeleteClick();
                break;
        }
        dismiss();
    }


}
