package com.android.skill.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

import com.android.skill.R;

public class UIUtils {
    private UIUtils(){}
    public static String getAppName(Context context){
        return context.getResources().getString(R.string.app_name);
    }

    public static Drawable getAppLauncherIcon(Context context){
        return context.getResources().getDrawable(R.drawable.ic_launcher_background);
    }

    public static View getMainLayout(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.activity_main, null);
        return view;
    }
}
