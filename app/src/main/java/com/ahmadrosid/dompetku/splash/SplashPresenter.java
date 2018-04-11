package com.ahmadrosid.dompetku.splash;

import android.content.Context;
import android.text.TextUtils;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.utils.models.preferences.DompetAccount;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import javax.inject.Inject;

/**
 * Created by Bashir Arrohman on 4/11/2018.
 */

public class SplashPresenter implements Splash.Presenter {

    private Splash.View view;

    @Inject
    Context context;

    @Inject
    DompetAccount dompetAccount;

    public SplashPresenter(Splash.View view) {
        this.view = view;
        DompetkuApp.getIntance().getAppComponent().inject(this);
    }

    @Override
    public void checkAccount() {

        String savedAccount = dompetAccount.getAccount();

        if (TextUtils.isEmpty(savedAccount)) {
            view.goToLogin();
            return;
        }

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);

        if (account == null) {
            view.goToLogin();
            return;
        }

        view.goToMain();
    }

}
