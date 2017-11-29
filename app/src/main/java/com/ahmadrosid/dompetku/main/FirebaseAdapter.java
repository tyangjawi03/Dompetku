package com.ahmadrosid.dompetku.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmadrosid.dompetku.R;
import com.ahmadrosid.dompetku.models.Transaction;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

/**
 * Created by yogja on 11/29/17.
 */

public class FirebaseAdapter extends FirebaseRecyclerAdapter<Transaction, TransactionViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FirebaseAdapter(FirebaseRecyclerOptions options) {
        super(options);
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallet_list, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(TransactionViewHolder holder, int position, Transaction model) {
        holder.setTransaction(model);
    }

}
