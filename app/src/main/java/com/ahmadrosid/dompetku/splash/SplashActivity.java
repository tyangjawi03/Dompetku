package com.ahmadrosid.dompetku.splash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ahmadrosid.dompetku.main.MainActivity;
import com.ahmadrosid.dompetku.signin.LoginActivity;

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
        LoginActivity.start(this);
        finish();
    }

    @Override
    public void goToMain() {
        MainActivity.start(this);
        finish();
    }

}
