package com.ahmadrosid.dompetku.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.models.TransactionRepository;
import com.ahmadrosid.dompetku.models.preferences.DompetAccount;

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
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("DompetKuApp", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    DompetAccount provideDompetAccount(Context context) {
        return new DompetAccount(context);
    }

}
