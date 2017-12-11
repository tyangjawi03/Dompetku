package com.ahmadrosid.dompetku.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ahmadrosid.dompetku.main.MainActivity;

/**
 * Created by yogja on 11/29/17.
 */

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    private SplashContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SplashPresenter(this);
        presenter.loadData();
    }

    @Override
    public void showMain() {
        MainActivity.start(this);
        finish();
    }

}
