package com.ahmadrosid.dompetku.di;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.models.TransactionRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by staf on 03-Oct-17.
 */

@Module
public class AppModule {

    private DompetkuApp dompetkuApp;

    public AppModule(DompetkuApp dompetkuApp) {
        this.dompetkuApp = dompetkuApp;
    }

    @Provides
    @Singleton
    TransactionRepository provideTransactionRepository() {
        return new TransactionRepository();
    }

    @Provides
    @Singleton
    DatabaseReference provideDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference("transaction");
    }

}
