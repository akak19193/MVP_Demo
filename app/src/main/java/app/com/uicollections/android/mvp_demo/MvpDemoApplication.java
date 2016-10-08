package app.com.uicollections.android.mvp_demo;

import android.app.Application;
import android.content.Context;

import app.com.uicollections.android.mvp_demo.func.dagger.MvpDemoComponent;


public class MvpDemoApplication extends Application {
    private MvpDemoComponent mComponent;

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mComponent = MvpDemoComponent.MvpDemoInitialize.init();
    }

    public static MvpDemoComponent getComponent() {
        return ((MvpDemoApplication) mContext.getApplicationContext()).mComponent;
    }
}
