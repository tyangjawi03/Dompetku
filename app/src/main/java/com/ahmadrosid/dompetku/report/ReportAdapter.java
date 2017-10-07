package com.ahmadrosid.dompetku.report;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.helper.CurrencyHelper;
import com.ahmadrosid.dompetku.models.Transaction;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
            convertView = inflater.inflate(R.layout.item_wallet_list, parent, false);
        }

        new ViewHolder(convertView, category);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.icon_wallet_list)
        ImageView iconWalletList;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.type)
        TextView type;

        ViewHolder(View view, Report.Category category) {
            ButterKnife.bind(this, view);

            title.setText(category.title);
            iconWalletList.setImageResource(R.drawable.wallet_out);
            amount.setTextColor(view.getResources().getColor(R.color.colorPrimaryText));
            amount.setText(category.getAmount());

            time.setVisibility(View.GONE);
            type.setVisibility(View.GONE);

        }

    }
}
