package com.ahmadrosid.dompetku.signin;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.models.DompetAccount;
import com.ahmadrosid.dompetku.models.SyncRealtime;
import com.ahmadrosid.dompetku.models.Transaction;
import com.ahmadrosid.dompetku.models.TransactionRepository;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by yogja on 12/11/17.
 */

public class LoadDataPresenter implements LoadDataContract.Presenter {

    private LoadDataContract.View view;

    @Inject
    TransactionRepository transactionRepository;

    @Inject
    SyncRealtime syncRealtime;

    @Inject
    DompetAccount dompetAccount;

    public LoadDataPresenter(LoadDataContract.View view) {
        this.view = view;

        DompetkuApp.getIntance().getAppComponent().inject(this);
    }

    @Override
    public void loadData(String id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(id);

        if (!syncRealtime.isSynced()) {
            final List<Transaction> transactions = transactionRepository.getTransaksiList();

            if (transactions.size() > 0) {
                Map<String, Transaction> trans = new HashMap<>();
                for (Transaction transaction : transactions) {
                    trans.put(transaction.date+"", transaction);
                }
                databaseReference.setValue(trans, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            syncRealtime.setSync(true);
                        } else {
                            syncRealtime.setSync(false);
                        }
                    }
                });
            }
        }

        dompetAccount.setAccount(id);
        view.nextProcess();
    }

}
