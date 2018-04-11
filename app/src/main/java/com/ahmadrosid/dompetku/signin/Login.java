package com.ahmadrosid.dompetku.signin;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

/**
 * Created by Bashir Arrohman on 4/11/2018.
 */

public interface Login {

    interface View {
        void showLoading();
        void doGoogleSignIn(GoogleSignInClient signInClient);
        void loginFailed(String message);
        void successLogin();
    }

    interface Presenter {
        void doLogin();
        void loginResult(Intent data);
    }

}
