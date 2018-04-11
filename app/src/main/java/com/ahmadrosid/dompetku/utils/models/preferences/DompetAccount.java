package com.ahmadrosid.dompetku.utils.models.preferences;

import android.content.Context;

/**
 * Created by Bashir Arrohman on 4/11/2018.
 */

public class DompetAccount extends DompetPreferences {

    private String ACCOUNT_KEY = "account";

    public DompetAccount(Context context) {
        super(context);
    }

    public String getAccount() throws NullPointerException {
        return sharedPreferences.getString(ACCOUNT_KEY, null);
    }

    public boolean setAccount(String account) {
        return editor.putString(ACCOUNT_KEY, account).commit();
    }

}
