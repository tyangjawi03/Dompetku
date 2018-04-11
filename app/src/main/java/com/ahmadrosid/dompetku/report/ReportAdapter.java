package com.ahmadrosid.dompetku.report;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.utils.Curency;

import java.util.List;

/**
 * Created by yogja on 10/7/17.
 */

public class ReportAdapter extends ArrayAdapter<Report.Category> {

    private List<Report.Category> categories;

    public ReportAdapter(@NonNull Context context, @NonNull List<Report.Category> categories) {
        super(context, 0, categories);
        this.categories = categories;
    }

    @Nullable
    @Override
    public Report.Category getItem(int position) {
        return categories.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Report.Category category = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adapter_report, parent, false);
        }

//        LabelTextView labelTextView = (LabelTextView) convertView.findViewById(R.id.item);

//        labelTextView.setLabel(category.title);
//        labelTextView.setValue(category.getAmount());

        TextView label = (TextView) convertView.findViewById(R.id.label);
        Curency value = (Curency) convertView.findViewById(R.id.amount);

        label.setText(category.title);
        value.setValue(category.amount);

        return convertView;
    }

}
