package com.ahmadrosid.dompetku.signin;

/**
 * Created by yogja on 12/11/17.
 */

public interface LoadDataContract {

    interface View {
        void nextProcess();
    }

    interface Presenter {
        void loadData(String id);
    }

}
