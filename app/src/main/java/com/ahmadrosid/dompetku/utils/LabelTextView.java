package com.ahmadrosid.dompetku.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahmadrosid.dompetku.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yogja on 10/6/17.
 */

public class LabelTextView extends LinearLayout {

    @BindView(R.id.label)
    TextView label;
    @BindView(R.id.value)
    TextView value;

    public LabelTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.label_textview, this, true);
        ButterKnife.bind(this);

    }

    public void setLabel(String label) {
        this.label.setText(label);
    }

    public void setValue(String value) {
        this.value.setText(value);
    }

}
