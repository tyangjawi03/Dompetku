package com.ahmadrosid.dompetku.signin;

import android.content.Context;
import android.content.Intent;

import com.ahmadrosid.dompetku.DompetkuApp;
import com.ahmadrosid.dompetku.models.preferences.DompetAccount;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

/**
 * Created by Bashir Arrohman on 4/11/2018.
 */

public class LoginPresenter implements Login.Presenter {

    @Inject
    Context context;

    @Inject
    DompetAccount dompetAccount;

    private Login.View view;

    public LoginPresenter(Login.View view) {
        this.view = view;
        DompetkuApp.getIntance().getAppComponent().inject(this);
    }

    @Override
    public void doLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

        view.doGoogleSignIn(mGoogleSignInClient);
    }

    @Override
    public void loginResult(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            dompetAccount.setAccount(account.getEmail()+"_"+account.getId());
            view.successLogin();

        } catch (ApiException e) {
            view.loginFailed("signInResult:failed code=" + e.getStatusCode() + " " +e.getMessage());
        }
    }

}
