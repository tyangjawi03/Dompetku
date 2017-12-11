package com.ahmadrosid.dompetku.models;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yogja on 12/11/17.
 */

public class DompetPreferences {

    private final String DOMPETKUAPP = "id.tyangjawi03.dompetkuapp";
    protected SharedPreferences sharedPreferences;

    protected SharedPreferences.Editor editor;

    public DompetPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(DOMPETKUAPP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

}
