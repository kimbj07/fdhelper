package com.bj.fdhelper;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by J on 2014-12-29.
 */
public class FDHScriptManager {
    private static Map<String, String> SCRIPT_MAP = new HashMap<String, String>();

    public static String getScript(Context context, String scriptFile) {
        if (SCRIPT_MAP.containsKey(scriptFile)) {
            return SCRIPT_MAP.get(scriptFile);
        }

        try {
            InputStream input = context.getResources().getAssets().open(scriptFile);
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            input.close();
            SCRIPT_MAP.put(scriptFile, Base64.encodeToString(buffer, Base64.NO_WRAP));
        } catch (Exception e) {
            Log.e(FDHConstants.TAG, "Fail to injectScriptFile! File Name : [" + scriptFile + "]", e);
        }

        return SCRIPT_MAP.get(scriptFile);
    }
}
