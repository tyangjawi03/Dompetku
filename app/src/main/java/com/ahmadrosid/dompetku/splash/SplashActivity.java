package com.ahmadrosid.dompetku.splash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ahmadrosid.dompetku.main.MainActivity;

public class SplashActivity extends AppCompatActivity implements Splash.View {

    private Splash.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SplashPresenter(this);
        presenter.checkAccount();
    }

    @Override
    public void goToLogin() {

    }

    @Override
    public void goToMain() {
        
    }

}
