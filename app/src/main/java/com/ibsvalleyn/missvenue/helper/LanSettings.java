package com.ibsvalleyn.missvenue.helper;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by maxmya on 12/15/17.
 */

public class LanSettings {



    public static void changeLang(String lang, Context c) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        c.getResources().updateConfiguration(config,
                c.getResources().getDisplayMetrics());
    }


}