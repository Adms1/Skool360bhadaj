package com.anandniketanbhadaj.skool360.skool360.Activities;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "DEFAULT", "Fonts/opensans_regular.ttf");
//        FontsOverride.setDefaultFont(this, "MONOSPACE", "font/TitilliumWeb-Regular.ttf");
//        FontsOverride.setDefaultFont(this, "SERIF", "font/TitilliumWeb-Regular.ttf");
//        FontsOverride.setDefaultFont(this, "SANS_SERIF", "font/TitilliumWeb-Regular.ttf");


    }
}
