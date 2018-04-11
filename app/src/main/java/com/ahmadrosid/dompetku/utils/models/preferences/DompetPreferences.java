package com.ahmadrosid.dompetku.utils.models.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Bashir Arrohman on 4/11/2018.
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
