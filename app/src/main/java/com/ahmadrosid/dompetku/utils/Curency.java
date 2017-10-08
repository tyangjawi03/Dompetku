package com.ahmadrosid.dompetku.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahmadrosid.dompetku.R;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogja on 10/8/17.
 */

public class Curency extends LinearLayout {

    @BindView(R.id.currency_title)
    TextView currencyTitle;
    @BindView(R.id.currency_amount)
    TextView currencyAmount;

    public Curency(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.curency, this, true);

        ButterKnife.bind(this);
    }

    public void setTextColor(int resource) {
        int color = getResources().getColor(resource);
        currencyTitle.setTextColor(color);
        currencyAmount.setTextColor(color);
    }

    public void setValue(int amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String value = formatter.format((double) amount);
        currencyTitle.setText("Rp.");
        currencyAmount.setText(value+",-");
    }

}
