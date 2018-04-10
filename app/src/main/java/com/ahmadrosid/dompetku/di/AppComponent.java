package com.ahmadrosid.dompetku.di;

import com.ahmadrosid.dompetku.main.MainActivity;
import com.ahmadrosid.dompetku.main.MainPresenter;
import com.ahmadrosid.dompetku.report.ReportPresenter;
import com.ahmadrosid.dompetku.splash.SplashActivity;
import com.ahmadrosid.dompetku.signin.LoadDataPresenter;
import com.ahmadrosid.dompetku.transaction.TransactionPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by staf on 03-Oct-17.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    MainPresenter inject(MainPresenter mainPresenter);
    TransactionPresenter inject(TransactionPresenter transactionPresenter);
    ReportPresenter inject(ReportPresenter reportPresenter);
    void inject(SplashActivity splashActivity);
    void inject(LoadDataPresenter loadDataPresenter);
    void inject(MainActivity mainActivity);
}
