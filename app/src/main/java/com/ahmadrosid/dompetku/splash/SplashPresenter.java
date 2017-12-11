package com.ahmadrosid.dompetku.splash;

import android.content.SharedPreferences;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.models.Transaction;
import com.ahmadrosid.dompetku.models.TransactionRepository;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yogja on 12/11/17.
 */

public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View view;

    @Inject
    TransactionRepository transactionRepository;

    @Inject
    DatabaseReference databaseReference;

    @Inject
    SharedPreferences sharedPreferences;

    private String SYNCTOREALTIME = "SYNCTOREALTIME";

    public SplashPresenter(SplashContract.View view) {
        this.view = view;

        DompetkuApp.getIntance().getAppComponent().inject(this);
    }

    @Override
    public void loadData() {
        sharedPreferences.edit().putBoolean(SYNCTOREALTIME, false).commit();

        if (!sharedPreferences.getBoolean(SYNCTOREALTIME, false)) {
            final List<Transaction> transactions = transactionRepository.getTransaksiList();

            if (transactions.size() > 0) {
                databaseReference.setValue(transactions, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            sharedPreferences.edit().putBoolean(SYNCTOREALTIME, true).commit();
                        } else {
                            sharedPreferences.edit().putBoolean(SYNCTOREALTIME, false).commit();
                        }
                    }
                });
            }
        }


        view.showMain();
    }

}
