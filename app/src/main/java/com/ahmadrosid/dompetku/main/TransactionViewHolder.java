package com.ahmadrosid.dompetku.main;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.models.Transaction;
import com.ahmadrosid.dompetku.utils.Curency;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogja on 11/29/17.
 */

public class TransactionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.icon_wallet_list)
    ImageView iconWalletList;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.amount)
    Curency amount;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.type)
    TextView type;

    public TransactionViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setTransaction(Transaction transaction) {
        title.setText(transaction.title);

        Date dates = new Date(transaction.date);
        String date = new SimpleDateFormat("EEE, d MMM yyyy").format(dates);
        this.time.setText(date);

        amount.setValue(transaction.amount);

        if (transaction.type.ordinal() == Transaction.TransactionType.PEMASUKAN.ordinal()) {
            amount.setTextColor(R.color.colorPrimary);
            iconWalletList.setImageResource(R.drawable.wallet_in);
            type.setText("PEMASUKAN");
            type.setBackgroundResource(R.color.colorPrimary);
        } else {
            amount.setTextColor(R.color.colorAccent);
            iconWalletList.setImageResource(R.drawable.wallet_out);
            type.setText("PENGELUARAN");
            type.setBackgroundResource(R.color.colorAccent);
        }
    }
}
