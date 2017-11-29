package com.ahmadrosid.dompetku.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.main.MainActivity;
import com.ahmadrosid.dompetku.models.Transaction;
import com.ahmadrosid.dompetku.models.TransactionRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yogja on 11/29/17.
 */

public class SplashActivity extends AppCompatActivity {

    @Inject
    TransactionRepository transactionRepository;

    DatabaseReference reference = FirebaseDatabase.getInstance()
            .getReference()
            .child("transaction");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DompetkuApp.getIntance().getAppComponent().inject(this);

        List<Transaction> transactions = transactionRepository.getTransaksiList();

        reference.setValue(transactions);

        MainActivity.start(this);
    }

}
