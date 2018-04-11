package com.ahmadrosid.dompetku.splash;

/**
 * Created by Bashir Arrohman on 4/11/2018.
 */

public interface Splash {

    interface View {
        void goToLogin();
        void goToMain();
    }

    interface Presenter {
        void checkAccount();
    }

}
