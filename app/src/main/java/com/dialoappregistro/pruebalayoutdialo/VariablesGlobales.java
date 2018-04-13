package com.dialoappregistro.pruebalayoutdialo;

import android.app.Application;
import android.widget.EditText;

import org.w3c.dom.Text;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by gpablo on 13/04/18.
 */

public class VariablesGlobales extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("font/Montserrat.ttf").setFontAttrId(R.attr.fontPath).build());
    }
}
