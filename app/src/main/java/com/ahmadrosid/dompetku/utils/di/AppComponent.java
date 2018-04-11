package com.ahmadrosid.dompetku.utils.di;

import com.ahmadrosid.dompetku.main.MainPresenter;
import com.ahmadrosid.dompetku.report.ReportPresenter;
import com.ahmadrosid.dompetku.signin.LoginPresenter;
import com.ahmadrosid.dompetku.splash.SplashPresenter;
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

    void inject(SplashPresenter splashPresenter);

    void inject(LoginPresenter loginPresenter);
}