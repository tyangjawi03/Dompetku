package com.ahmadrosid.dompetku.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ahmadrosid.dompetku.main.MainActivity;
import com.ahmadrosid.dompetku.signin.LoadDataContract;
import com.ahmadrosid.dompetku.signin.LoadDataPresenter;
import com.ahmadrosid.dompetku.signin.SigninActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by yogja on 11/29/17.
 */

public class SplashActivity extends AppCompatActivity implements LoadDataContract.View {

    private LoadDataContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new LoadDataPresenter(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {

        if (account == null) {
            SigninActivity.start(this);
            finish();
        } else {
            presenter.loadData(account.getId());
        }

    }

    @Override
    public void nextProcess() {
        MainActivity.start(this);
        finish();
    }

}
