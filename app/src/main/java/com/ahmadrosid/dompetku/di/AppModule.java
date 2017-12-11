package com.ahmadrosid.dompetku.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.models.DompetAccount;
import com.ahmadrosid.dompetku.models.SyncRealtime;
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
    Context provideContext() {
        return dompetkuApp;
    }

    @Provides
    @Singleton
    TransactionRepository provideTransactionRepository() {
        return new TransactionRepository();
    }

    @Provides
    @Singleton
    DatabaseReference provideDatabaseReference(DompetAccount dompetAccount) {
        return FirebaseDatabase.getInstance().getReference(dompetAccount.getAccount());
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("DompetKuApp", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    SyncRealtime provideSyncRealtime(Context context) {
        return new SyncRealtime(context);
    }

    @Provides
    @Singleton
    DompetAccount provideDompetAccount(Context context) {
        return new DompetAccount(context);
    }

}
