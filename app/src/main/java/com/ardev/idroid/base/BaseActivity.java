package com.ardev.idroid.base;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import com.ardev.idroid.app.IdroidApplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.WindowCompat;
import com.ardev.idroid.app.theme.DarkThemeHelper;
import com.ardev.idroid.ext.UtilKt;

public abstract class BaseActivity extends AppCompatActivity {

    private boolean isDelegateCreated = false;

    protected SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = IdroidApplication.getDefaultSharedPreferences();

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        UtilKt.addSystemWindowInsetToPadding(getRootActivityView(), false, false, false, false);
    }

    @Override
    public AppCompatDelegate getDelegate() {
        AppCompatDelegate delegate = super.getDelegate();
        if (!isDelegateCreated) {
            isDelegateCreated = true;
            DarkThemeHelper.apply(this);
        }
        return delegate;
    }

    @NonNull
    private View getRootActivityView() {
        return getWindow().getDecorView().findViewById(android.R.id.content);
    }

   
}
