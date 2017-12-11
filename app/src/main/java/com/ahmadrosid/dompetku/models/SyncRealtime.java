package com.ahmadrosid.dompetku.models;

import android.content.Context;

/**
 * Created by yogja on 12/11/17.
 */

public class SyncRealtime extends DompetPreferences {

    private String SYNCTOREALTIME = "sync_realtime";

    public SyncRealtime(Context context) {
        super(context);
    }

    public boolean isSynced() {
        return sharedPreferences.getBoolean(SYNCTOREALTIME, false);
    }

    public boolean setSync(boolean sync) {
        return editor.putBoolean(SYNCTOREALTIME, sync).commit();
    }

}
